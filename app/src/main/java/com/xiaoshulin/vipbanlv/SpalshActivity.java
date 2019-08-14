package com.xiaoshulin.vipbanlv;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;

import com.google.gson.Gson;
import com.xiaoshulin.vipbanlv.ApiUtils.ApiUtils;

import com.xiaoshulin.vipbanlv.bean.CheckVisionBean;
import com.xiaoshulin.vipbanlv.bean.LocalDataBean;
import com.xiaoshulin.vipbanlv.dialog.CheckUpLoadDialog;
import com.xiaoshulin.vipbanlv.dialog.PremissionsDialog;
import com.xiaoshulin.vipbanlv.dialog.WarningDialog;
import com.xiaoshulin.vipbanlv.utils.SharePreferenceUtil;
import com.xiaoshulin.vipbanlv.utils.ToastUtil;
import com.xiaoshulin.vipbanlv.utils.Utils;

import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import okhttp3.Call;

public class SpalshActivity extends AppCompatActivity {

    private static final String TAG = "SpalshActivity";
    private WarningDialog dialog;
    private CheckUpLoadDialog upLoadDialog;
    private int upGradeTag = 0;
    private CheckVisionBean checkVisionBean;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_spalsh);

        showLogin();
    }


    public void showLogin() {
        AlphaAnimation alp = new AlphaAnimation(0.0f, 1.0f);
        alp.setDuration(3000);
        findViewById(R.id.splash_login_container).setAnimation(alp);
        findViewById(R.id.splash_login_container).setVisibility(View.VISIBLE);


//        //模拟子线程耗时操作
//        new Thread() {
//            @Override
//            public void run() {
//                try {
//                    Thread.sleep(3000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//
//            }
//        }.start();

        /**向用户要读写权限*/
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                verifyStoragePermissions(SpalshActivity.this);
            }
        },3000);


    }

    private void getcheckVison() {

        String stringUId = SharePreferenceUtil.getinstance().getStringUId();
        Log.e(TAG, "getcheckVison:       stringUId:" + stringUId);
        String readfile = "";
        if (TextUtils.isEmpty(stringUId)) {
            LocalDataBean bean = readfile();
            readfile=bean.getUid();
            if (!TextUtils.isEmpty(readfile)) {
                if (TextUtils.isEmpty(stringUId)) {
                    SharePreferenceUtil.getinstance().setStringUID(readfile);
                    SharePreferenceUtil.getinstance().setStringUIdToken(bean.getUidtoken());
                }
            } else {
                readfile = "1";
            }
        } else {
            readfile = stringUId;
        }

        String uid = Utils.getUidSign(readfile);
        OkHttpUtils.get()
                .url(ApiUtils.URL)
                .addParams("m", "index")
                .addParams("a", "visionnew")
                .addParams("machine_type", "1")
                .addParams("version", Utils.getLocalVersionName())
                .addParams("uidtoken", SharePreferenceUtil.getinstance().getStringUIdToken())
                .addParams("uid", readfile)
                .addParams("sign", uid)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e(TAG, "onError:    " + e.getMessage());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e(TAG, "onResponse:     " + response);
                        Gson gson = new Gson();
                        checkVisionBean = gson.fromJson(response, CheckVisionBean.class);
                        if (!TextUtils.isEmpty(checkVisionBean.getUtype())) {
                            SharePreferenceUtil.getinstance().setStringUIdToken(checkVisionBean.getUtype());
                        }
                        if (checkVisionBean.getReturncode().equals("-2")) {
                            dialog = new WarningDialog(SpalshActivity.this, "温馨提示", "您的小树林信用等级过低，无法使用APP的任何服务，如有疑问请点击客服去支付宝客服反馈！", new WarningListener());
                            dialog.show();
                        } else if (checkVisionBean.getReturncode().equals("0")) {

                            if (checkVisionBean.getConstraint().equals("0"))
                            {
                                /**无需更新*/
                                jumpMain();

                            }else if (checkVisionBean.getConstraint().equals("1")){
                                /**强制更新*/
                                upGradeTag=1;
                                upLoadDialog=new CheckUpLoadDialog(SpalshActivity.this,"温馨提示",checkVisionBean.getUpdateexplain() ,"退出","更新",new CheckUploadListener());
                                upLoadDialog.show();
                            }else if (checkVisionBean.getConstraint().equals("2")) {
                                /**非强制更新*/
                                upGradeTag=2;
                                upLoadDialog=new CheckUpLoadDialog(SpalshActivity.this,"温馨提示",checkVisionBean.getUpdateexplain(),"稍后","更新",new CheckUploadListener());
                               upLoadDialog.show();
                            }
                        } else {

                            ToastUtil.showToast("分享内容已过期或者修改了分享信息内容");
                        }
                    }
                });
    }


    private void jumpMain() {

        JSONObject object=new JSONObject();
        try {
            object.put("generalizeicon",checkVisionBean.getGeneralizeicon());
            object.put("generalizeurl",checkVisionBean.getGeneralizeurl());
            object.put("generalizelocation",checkVisionBean.getGeneralizelocation());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        SharePreferenceUtil.getinstance().setVipIcon(object.toString());

        Intent intent = new Intent(SpalshActivity.this, MainActivity.class);
        intent.putExtra("tag",0);
        intent.putExtra("type",checkVisionBean.getVtype());
        intent.putExtra("money",checkVisionBean.getVmoney());
        startActivity(intent);
        finish();
    }

    private class CheckUploadListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.tv_cancel:
                    if (upGradeTag == 1) {
                        finish();
                    } else if (upGradeTag == 2) {
                        jumpMain();
                    } else {
                        Log.e(TAG, "onClick:       不是1和2");
                    }
                    upLoadDialog.dismiss();
                    break;
                case R.id.tv_submit:
                    /**
                     *更新
                     * */
                    Uri uri = Uri.parse(checkVisionBean.getDownloadlink());
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(intent);
                    upLoadDialog.dismiss();
                    break;
                default:
                    break;
            }
        }
    }


    private class WarningListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.tv_cancel:
                    finish();
                    dialog.dismiss();
                    break;
                case R.id.tv_submit:
                    /**
                     *找客服
                     * */
                    CustomService();
                    dialog.dismiss();
                    break;
                default:
                    break;
            }
        }
    }

    private void CustomService() {
        jumpAlipay(ToastUtil.zhifubao());
    }

    private void jumpAlipay(String message) {

        //获取剪贴板管理器：
        ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        // 创建普通字符型ClipData
        ClipData mClipData = ClipData.newPlainText("Label", message);
        // 将ClipData内容放到系统剪贴板里。
        cm.setPrimaryClip(mClipData);
        //跳转支付宝
        try {
            PackageManager packageManager
                    = this.getApplicationContext().getPackageManager();
            Intent intent = packageManager.
                    getLaunchIntentForPackage("com.eg.android.AlipayGphone");
            startActivity(intent);
        } catch (Exception e) {
            String url = "https://ds.alipay.com/?from=mobileweb";
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(url));
            startActivity(intent);
        }
    }


    PremissionsDialog premissionsDialog;

    class premissDialogListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.tv_ok:
                    premissionsDialog.dismiss();
                    ActivityCompat.requestPermissions(SpalshActivity.this, PERMISSIONS_STORAGE, REQUEST_EXTERNAL_STORAGE);
                    break;
            }

        }
    }

    public void verifyStoragePermissions(Activity activity) {



        try {
            //检测是否有写的权限
            int permission = ActivityCompat.checkSelfPermission(activity, "android.permission.WRITE_EXTERNAL_STORAGE");
            if (permission != PackageManager.PERMISSION_GRANTED) {
                // 没有写的权限，去申请写的权限，会弹出对话框
                premissionsDialog=new PremissionsDialog(activity,"为了防止您的老数据丢失，请同意小树林APP的存储权限哦",new premissDialogListener());
                premissionsDialog.show();
//                ActivityCompat.requestPermissions(activity, PERMISSIONS_STORAGE, REQUEST_EXTERNAL_STORAGE);
            }else {
                /**这个是给过权限了*/
                //模拟登录成功
                getcheckVison();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            "android.permission.READ_EXTERNAL_STORAGE",
            "android.permission.WRITE_EXTERNAL_STORAGE"};

    public LocalDataBean readfile() {

        File file = new File(Environment.getExternalStorageDirectory(), "/android1/weixinxslapi.txt");
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }

        if (!file.canWrite()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
//        verifyStoragePermissions(MainActivity.this);
        InputStream in = null;
        BufferedReader reader = null;
        StringBuilder content = new StringBuilder();
        try {

            in = new FileInputStream(file.getAbsoluteFile());//文件名
            InputStreamReader input = new InputStreamReader(in, "UTF-8");
            reader = new BufferedReader(input);
            String line = "";
            while ((line = reader.readLine()) != null) {
                content.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        LocalDataBean bean=new LocalDataBean();
        String a = content.toString();
        if (TextUtils.isEmpty(a))
        {
            return bean;
        }
        String[] split = a.split("\\*");
        String A=split[0];
        String B=split[1];
        Log.e(TAG, "readfile:   加密后取出来的数据------------->a:"+a );
        String b = A.substring(13, A.length() - 12);
        Log.e(TAG, "readfile:   ------------->b:"+b );
        int c = Integer.parseInt(b) / 3;
        Log.e(TAG, "readfile:   ------------->c:"+c );
        String uid = String.valueOf(c - 214);
        Log.e(TAG, "readfile:   ------------->uid:"+uid );

        bean.setUid(uid);
        bean.setUidtoken(B);
        return bean;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_EXTERNAL_STORAGE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                // 权限被用户同意，可以去放肆了。
                Log.e("jp", "onRequestPermissionsResult:  权限被用户同意，可以去放肆了");
                String stringUId = SharePreferenceUtil.getinstance().getStringUId();
                LocalDataBean bean = readfile();

                if (!TextUtils.isEmpty(bean.getUid())) {
                    if (TextUtils.isEmpty(stringUId)) {
                        SharePreferenceUtil.getinstance().setStringUID(bean.getUid());
                        SharePreferenceUtil.getinstance().setStringUIdToken(bean.getUidtoken());
                    }

                }
            } else {

                // 权限被用户拒绝了，洗洗睡吧。
                Log.e("jp", "onRequestPermissionsResult:  权限被用户拒绝了，洗洗睡吧");
            }
            /**无论有没有给与权限都会去走版本更新接口*/
            //模拟登录成功
            getcheckVison();
        }

    }

}
