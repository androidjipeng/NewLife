package com.xiaoshulin.vipbanlv;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NotificationCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.BackgroundColorSpan;
import android.text.style.ImageSpan;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.android.arouter.launcher.ARouter;
import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.google.gson.Gson;
import com.xiaoshulin.vipbanlv.ApiUtils.ApiUtils;
import com.xiaoshulin.vipbanlv.activity.ChatMessageActivity;
import com.xiaoshulin.vipbanlv.activity.OrderListActivity;
import com.xiaoshulin.vipbanlv.adapter.MainAdapter;
import com.xiaoshulin.vipbanlv.base.BasePresenter;
import com.xiaoshulin.vipbanlv.bean.CheckVisionBean;
import com.xiaoshulin.vipbanlv.bean.CostomFieldBean;
import com.xiaoshulin.vipbanlv.bean.EventBusMessage;
import com.xiaoshulin.vipbanlv.bean.JPushBean;
import com.xiaoshulin.vipbanlv.bean.LocalDataBean;
import com.xiaoshulin.vipbanlv.bean.MainDataBean;
import com.xiaoshulin.vipbanlv.bean.RegisterBean;
import com.xiaoshulin.vipbanlv.bean.ResolveBean;
import com.xiaoshulin.vipbanlv.bean.ShareCircleBean;
import com.xiaoshulin.vipbanlv.bean.SupportBean;
import com.xiaoshulin.vipbanlv.bean.danMuBean;
import com.xiaoshulin.vipbanlv.circleFrags.fragment.RemarksFragment;
import com.xiaoshulin.vipbanlv.dialog.CheckUpLoadDialog;
import com.xiaoshulin.vipbanlv.dialog.PremissionsDialog;
import com.xiaoshulin.vipbanlv.dialog.WarningDialog;
import com.xiaoshulin.vipbanlv.fragment.CircleFragment;
import com.xiaoshulin.vipbanlv.fragment.HomeFragment;
import com.xiaoshulin.vipbanlv.fragment.MessageFragment;
import com.xiaoshulin.vipbanlv.fragment.OrderFragment;
import com.xiaoshulin.vipbanlv.fragment.UserFragment;
import com.xiaoshulin.vipbanlv.jpush.ExampleUtil;
import com.xiaoshulin.vipbanlv.jpush.LocalBroadcastManager;
import com.xiaoshulin.vipbanlv.popup.JpushPopupwindow;
import com.xiaoshulin.vipbanlv.presenter.MainPresenter;
import com.xiaoshulin.vipbanlv.dialog.JPushDialog;
import com.xiaoshulin.vipbanlv.utils.BiliDanmukuParser;
import com.xiaoshulin.vipbanlv.utils.DataBaseUtils;
import com.xiaoshulin.vipbanlv.utils.MD5Util;
import com.xiaoshulin.vipbanlv.dialog.MainDialog;
import com.xiaoshulin.vipbanlv.utils.ParsingTools;
import com.xiaoshulin.vipbanlv.utils.SharePreferenceUtil;
import com.xiaoshulin.vipbanlv.utils.ToastUtil;
import com.xiaoshulin.vipbanlv.utils.Utils;
import com.xiaoshulin.vipbanlv.view.MainView;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import android.support.v4.app.Fragment;

import com.xiaoshulin.vipbanlv.base.BaseMVPActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import javax.security.auth.login.LoginException;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import master.flame.danmaku.controller.IDanmakuView;
import master.flame.danmaku.danmaku.loader.ILoader;
import master.flame.danmaku.danmaku.loader.IllegalDataException;
import master.flame.danmaku.danmaku.loader.android.DanmakuLoaderFactory;
import master.flame.danmaku.danmaku.model.BaseDanmaku;
import master.flame.danmaku.danmaku.model.DanmakuTimer;
import master.flame.danmaku.danmaku.model.IDanmakus;
import master.flame.danmaku.danmaku.model.IDisplayer;
import master.flame.danmaku.danmaku.model.android.BaseCacheStuffer;
import master.flame.danmaku.danmaku.model.android.DanmakuContext;
import master.flame.danmaku.danmaku.model.android.Danmakus;
import master.flame.danmaku.danmaku.model.android.SpannedCacheStuffer;
import master.flame.danmaku.danmaku.parser.BaseDanmakuParser;
import master.flame.danmaku.danmaku.parser.IDataSource;
import master.flame.danmaku.danmaku.util.IOUtils;
import okhttp3.Call;
/**首页测试*/
public class MainActivity extends BaseMVPActivity implements MainView, BottomNavigationBar.OnTabSelectedListener {

    private static final String TAG = "MainActivity";
    private JpushPopupwindow window1;
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            "android.permission.READ_EXTERNAL_STORAGE",
            "android.permission.WRITE_EXTERNAL_STORAGE"};

    private MainPresenter presenter;

    private Context context;
    JPushDialog jPushDialog;
    /**
     * 复制的内容
     */
    private String copystring;

    CheckUpLoadDialog premissdialog;


    private BaseDanmakuParser mParser;
    private IDanmakuView mDanmakuView;
    private DanmakuContext mContext;

    private RelativeLayout ll_danmaku;
    private Button text_start, text_stop, text_hongbao, text_support;


    private BaseCacheStuffer.Proxy mCacheStufferAdapter = new BaseCacheStuffer.Proxy() {
        private Drawable mDrawable;

        @Override
        public void prepareDrawing(final BaseDanmaku danmaku, boolean fromWorkerThread) {
            if (danmaku.text instanceof Spanned) { // 根据你的条件检查是否需要需要更新弹幕
                // FIXME 这里只是简单启个线程来加载远程url图片，请使用你自己的异步线程池，最好加上你的缓存池
                new Thread() {

                    @Override
                    public void run() {
                        String url = "http://www.bilibili.com/favicon.ico";
                        InputStream inputStream = null;
                        Drawable drawable = mDrawable;
                        if (drawable == null) {
                            try {
                                URLConnection urlConnection = new URL(url).openConnection();
                                inputStream = urlConnection.getInputStream();
                                drawable = BitmapDrawable.createFromStream(inputStream, "bitmap");
                                mDrawable = drawable;
                            } catch (MalformedURLException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            } finally {
                                IOUtils.closeQuietly(inputStream);
                            }
                        }
                        if (drawable != null) {
                            drawable.setBounds(0, 0, 100, 100);
                            SpannableStringBuilder spannable = createSpannable(drawable);
                            danmaku.text = spannable;
                            if (mDanmakuView != null) {
                                mDanmakuView.invalidateDanmaku(danmaku, false);
                            }
                            return;
                        }
                    }
                }.start();
            }
        }

        @Override
        public void releaseResource(BaseDanmaku danmaku) {
            // TODO 重要:清理含有ImageSpan的text中的一些占用内存的资源 例如drawable
        }
    };

    private SpannableStringBuilder createSpannable(Drawable drawable) {
        String text = "bitmap";
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(text);
        ImageSpan span = new ImageSpan(drawable);//ImageSpan.ALIGN_BOTTOM);
        spannableStringBuilder.setSpan(span, 0, text.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        spannableStringBuilder.append("图文混排");
        spannableStringBuilder.setSpan(new BackgroundColorSpan(Color.parseColor("#8A2233B1")), 0, spannableStringBuilder.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        return spannableStringBuilder;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        context = this;
        /**
         * 注册极光推送监听
         * */
        registerMessageReceiver();
        /**
         * 判断是否第一次登陆
         * */
        isFristLoad();

        EventBus.getDefault().register(this);

        /**弹幕view*/
        findViews();
    }

    private void findViews() {
        // DanmakuView

        // 设置最大显示行数
        HashMap<Integer, Integer> maxLinesPair = new HashMap<Integer, Integer>();
        maxLinesPair.put(BaseDanmaku.TYPE_SCROLL_RL, 7); // 滚动弹幕最大显示5行
        // 设置是否禁止重叠
        HashMap<Integer, Boolean> overlappingEnablePair = new HashMap<Integer, Boolean>();
        overlappingEnablePair.put(BaseDanmaku.TYPE_SCROLL_RL, true);
        overlappingEnablePair.put(BaseDanmaku.TYPE_FIX_TOP, true);

        mDanmakuView = (IDanmakuView) findViewById(R.id.sv_danmaku);
        mContext = DanmakuContext.create();
        mContext.setDanmakuStyle(IDisplayer.DANMAKU_STYLE_STROKEN, 3).setDuplicateMergingEnabled(false).setScrollSpeedFactor(2.0f).setScaleTextSize(1.2f)
                .setCacheStuffer(new SpannedCacheStuffer(), mCacheStufferAdapter) // 图文混排使用SpannedCacheStuffer
//        .setCacheStuffer(new BackgroundCacheStuffer())  // 绘制背景使用BackgroundCacheStuffer
                .setMaximumLines(maxLinesPair)
                .preventOverlapping(overlappingEnablePair).setDanmakuMargin(40);

        if (mDanmakuView != null) {

            mParser = createParser(this.getResources().openRawResource(R.raw.comments));

            mDanmakuView.setCallback(new master.flame.danmaku.controller.DrawHandler.Callback() {
                @Override
                public void updateTimer(DanmakuTimer timer) {
//                    Log.e(TAG, "updateTimer: "+timer.currMillisecond);
                }

                @Override
                public void drawingFinished() {
                    Log.e(TAG, "drawingFinished: ");
                }

                @Override
                public void danmakuShown(BaseDanmaku danmaku) {
//                    Log.d("DFM", "danmakuShown(): text=" + danmaku.text);
                }

                @Override
                public void prepared() {
                    mDanmakuView.start();
                }
            });
            mDanmakuView.setOnDanmakuClickListener(new IDanmakuView.OnDanmakuClickListener() {

                @Override
                public boolean onDanmakuClick(IDanmakus danmakus) {
                    Log.d("DFM", "onDanmakuClick: danmakus size:" + danmakus.size());
                    BaseDanmaku latest = danmakus.last();
                    if (null != latest) {

                        for (int i = 0; i < data.size(); i++) {

                            if (latest.text.equals(data.get(i).getDescribe())) {
                                Log.d("DFM", "onDanmakuClick--------------: for循环 ==text of latest danmaku:" + latest.text);
                                ParsingTools tools = new ParsingTools();
                                tools.SecondParseTool(context, data.get(i).getUrl());
                            }
                        }

                        Log.d("DFM", "onDanmakuClick--------------: 判断是否为空 text of latest danmaku:" + latest.text);
                        return true;
                    }
                    return false;
                }

                @Override
                public boolean onDanmakuLongClick(IDanmakus danmakus) {
                    return false;
                }

                @Override
                public boolean onViewClick(IDanmakuView view) {
                    Log.e(TAG, "onViewClick: dddddddddddd");

                    return false;
                }
            });
            mDanmakuView.prepare(mParser, mContext);
            mDanmakuView.showFPS(false);
            mDanmakuView.enableDanmakuDrawingCache(true);

        }

    }

    private BaseDanmakuParser createParser(InputStream stream) {

        if (stream == null) {
            return new BaseDanmakuParser() {

                @Override
                protected Danmakus parse() {
                    return new Danmakus();
                }
            };
        }

        ILoader loader = DanmakuLoaderFactory.create(DanmakuLoaderFactory.TAG_BILI);

        try {
            loader.load(stream);
        } catch (IllegalDataException e) {
            e.printStackTrace();
        }
        BaseDanmakuParser parser = new BiliDanmukuParser();
        IDataSource<?> dataSource = loader.getDataSource();
        parser.load(dataSource);
        return parser;

    }


    private void AcceptClipboardManager() {
        // 获取系统剪贴板
        ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);

        // 获取剪贴板的剪贴数据集
        ClipData clipData = clipboard.getPrimaryClip();

        if (clipData != null && clipData.getItemCount() > 0) {
            // 从数据集中获取（粘贴）第一条文本数据
            CharSequence text = clipData.getItemAt(0).getText();
            /**
             * 做剪贴板的文字操作
             * */
            String copycontent = text.toString();
            if (copycontent.equals("")) {
                /**
                 * 剪贴板中没有东西的时候
                 * 检查时候本地是否有保存下来的数据
                 * */
                showLocalData();

            } else {

                dostringsomethings(text.toString());
            }

            /**
             * 清空剪贴板
             * */
            ClipData mClipData = ClipData.newPlainText("Label", "");
            // 将ClipData内容放到系统剪贴板里。
            clipboard.setPrimaryClip(mClipData);
        }

    }

    private void showLocalData() {
        JPushBean loacldata = getLoacldata();
        if (TextUtils.isEmpty(loacldata.getPushtype())) {
            return;
        } else {
            jPushDialog = new JPushDialog(MainActivity.this, loacldata.getPushtype(), loacldata.getALERT(), loacldata.getContent(), new JPushLinstener());
            jPushDialog.show();
        }

    }

    private MainDialog dialog;
    String shareuid, id, url;
    ResolveBean bean;

    private void dostringsomethings(String text) {
        copystring = text;
//        费看VIP视频《邪不压正》有一个不错的电影
//        长按复制这条信息💋1374*170a6b6d21a0c4decef60ca2e105dfa2*5💋
//        然后打开👉小树林APP👈
//💋可以免费看VIP视频《邪不压正》有一个不错的电影💋
//        消息有效期到：2018-07-25
//        小树林APP各大视频网站会员一个月只要几块钱
//        点击链接即可试用http://www.vipbanlv.com/www_v1
//
//        原始视频地址💋

        Log.e(TAG, "dostringsomethings: " + copystring);
        String[] split = text.split("\uD83D\uDC8B");
        if (split.length == 5) {//这是免费内容或通知分享内容的字段分割
            String str = split[1];
            String[] split1 = str.split("\\*");
            shareuid = split1[0];
            id = split1[2];
            url = "";
            String newstring = shareuid + id + url + "397zx.cASD!@#";
            String stringMD5 = MD5Util.getStringMD5(newstring);
            Log.e(TAG, "dostringsomethings: " + stringMD5);
            String checkMD5 = split1[1];
            if (stringMD5.equals(checkMD5)) {
                bean = new ResolveBean(0, "", "", "", "");
                dialog = new MainDialog(context, shareuid, split[3], new ShareListener());
                dialog.show();
            }

        } else if (split.length == 7)//分享圈子内容商品的验证
        {
            String first = split[2];
            String[] seconds = first.split("\\*");
            if (seconds.length == 4) {
                String isone = seconds[3];
                if (isone.equals("1")) {
                    /**此时为圈子商品*/
                    String one = seconds[0];//商品id
                    String newcheck = one + "http://dwz.cn/86EfALIU" + "http://dwz.cn/lqrDxatM" + seconds[2] + seconds[3] + "397zx.cASD!@#";
                    String stringMD5 = MD5Util.getStringMD5(newcheck);
                    String stirngeight = stringMD5.substring(5, 13);
                    if (stirngeight.equals(seconds[1])) {
                        /**合法*/
                        ARouter.getInstance().build("/activity/WebUserActivity")
                                .withInt("tag", 4)
                                .withString("cricleid", one)
                                .withString("produceid", seconds[2])
                                .navigation();
                    }
                }
            } else if (seconds.length == 3) {
                //第一个是圈子id,第二个是类型，第三个是分享用户id
                bean = new ResolveBean(1, seconds[0], seconds[1], seconds[2], "");
                dialog = new MainDialog(context, bean.getUrl(), copystring, new ShareListener());
                dialog.show();
            }
        }
        Log.e(TAG, "dostringsomethings: " + text);

    }


    private class ShareListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.tv_cancel:
                    /**
                     * 分享
                     * */
                    ARouter.getInstance()
                            .build("/activity/ShareWechat")
                            .withString("copystring", copystring)
                            .navigation();
                    dialog.dismiss();
                    break;
                case R.id.tv_submit:

                    /**
                     * 免费去看看
                     * */
                    if (bean.getTag() == 0) {
                        FreeWhatVideo();
                    } else if (bean.getTag() == 1) {
                        ARouter.getInstance()
                                .build("/activity/CommonActivity")
                                .withInt("tag", 0)
                                .withString("cricleid", bean.getContent())
                                .navigation();
                    }
                    dialog.dismiss();
                    break;
                default:
                    break;
            }
        }
    }

    private void FreeWhatVideo() {
        presenter.getVideoUrl();
    }


    private void isFristLoad() {

        String stringUId = SharePreferenceUtil.getinstance().getStringUId();
        if (TextUtils.isEmpty(stringUId)) {
            verifyStoragePermissions(MainActivity.this);
        } else {
            setTagAndAlias();
        }

    }

    public void verifyStoragePermissions(Activity activity) {

        try {
            //检测是否有写的权限
            int permission = ActivityCompat.checkSelfPermission(activity,
                    "android.permission.WRITE_EXTERNAL_STORAGE");
            if (permission != PackageManager.PERMISSION_GRANTED) {
                // 没有写的权限，去申请写的权限，会弹出对话框
                ActivityCompat.requestPermissions(activity, PERMISSIONS_STORAGE, REQUEST_EXTERNAL_STORAGE);
            } else {
                LocalDataBean bean = readfile();
                String uid = bean.getUid();
                if (!TextUtils.isEmpty(uid)) {
                    SharePreferenceUtil.getinstance().setStringUID(uid);
                    SharePreferenceUtil.getinstance().setStringUIdToken(bean.getUidtoken());
                    setTagAndAlias();
                } else {
                    initdata();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void initdata() {
        /**进行注册*/
        presenter.getData();
    }

    @Override
    public BasePresenter initPresenter() {
        return presenter = new MainPresenter(this);
    }


    @Override
    protected void initData() {
        /**
         * 获取极光推送的内容
         * */
        Intent intent = getIntent();
        if ("1".equals(intent.getAction())) {
            Bundle bundle = intent.getExtras();
            String ALERT = bundle.getString(JPushInterface.EXTRA_ALERT);
            String EXTRA = bundle.getString(JPushInterface.EXTRA_EXTRA);
//            String ID = bundle.getString(JPushInterface.EXTRA_NOTIFICATION_ID);
            String ALERT_TYPE = bundle.getString(JPushInterface.EXTRA_ALERT_TYPE);
            String NOTIFICATION_CONTENT_TITLE = bundle.getString(JPushInterface.EXTRA_NOTIFICATION_TITLE);
            String MSG_ID = bundle.getString(JPushInterface.EXTRA_MSG_ID);

            Gson gson = new Gson();
            JPushBean jPushBean = gson.fromJson(EXTRA, JPushBean.class);
            jPushBean.setALERT(ALERT);
//            jPushBean.setID(ID);
            jPushBean.setALERT_TYPE(ALERT_TYPE);
            jPushBean.setNOTIFICATION_CONTENT_TITLE(NOTIFICATION_CONTENT_TITLE);
            jPushBean.setMSG_ID(MSG_ID);

            SharePreferenceUtil instance = SharePreferenceUtil.getinstance();
            instance.setString("ALERT", ALERT);
//            instance.setString("ALERT_TYPE",ALERT_TYPE);
            instance.setString("NOTIFICATION_CONTENT_TITLE", NOTIFICATION_CONTENT_TITLE);
            instance.setString("MSG_ID", MSG_ID);
            instance.setString("content", jPushBean.getContent());
            instance.setString("pushtype", jPushBean.getPushtype());
            instance.setString("customfield", jPushBean.getCustomfield());

        }

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
                    = getApplicationContext().getPackageManager();
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

    class JPushLinstener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.tv_cancel:
                    /**
                     * 保存数据
                     * */
//                    saveData();
                    jPushDialog.dismiss();
                    break;
                case R.id.tv_submit:

                    /**
                     * 看看内容
                     * */
                    todoJpushThing();

                    jPushDialog.dismiss();
                    break;
                case R.id.tv_know:
                    //清楚消息内容
                    JPushBean loacldata = getLoacldata();
                    if (loacldata.getPushtype().equals("14")) {
                        saveData(loacldata);
                        clearData();
                    } else {
                        clearData();
                    }
                    jPushDialog.dismiss();
                    break;
                default:
                    break;
            }
        }
    }

    private void todoJpushThing() {

        SharePreferenceUtil instance = SharePreferenceUtil.getinstance();
        String pushtype = instance.getString("pushtype");
        String customfield = instance.getString("customfield");
        String content = instance.getString("content");

        if (pushtype.equals("4")) {
            /**
             * 去web
             * */

            ParsingTools tools = new ParsingTools();
            tools.SecondParseTool(MainActivity.this, customfield);
        } else if (pushtype.equals("8")) {
            /**
             * 去订单列表
             * */
            Intent intent = new Intent(context, OrderListActivity.class);
            startActivity(intent);
        } else if (pushtype.equals("15")) {
            //获取剪贴板管理器：
            ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            // 创建普通字符型ClipData
            ClipData mClipData = ClipData.newPlainText("Label", content);
            // 将ClipData内容放到系统剪贴板里。
            cm.setPrimaryClip(mClipData);
            /**
             * 展示内容
             * */
            AcceptClipboardManager();

        } else if (pushtype.equals("21")) {
            /**跳转到升级系统浏览器*/
            Uri uri = Uri.parse(customfield);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        }

        //清楚消息内容
        clearData();
    }


    private void clearData() {
        SharePreferenceUtil instance = SharePreferenceUtil.getinstance();
        instance.setString("ALERT", "");
        instance.setString("NOTIFICATION_CONTENT_TITLE", "");
        instance.setString("MSG_ID", "");
        instance.setString("content", "");
        instance.setString("pushtype", "");
        instance.setString("customfield", "");
    }

    private JPushBean getLoacldata() {
        SharePreferenceUtil instance = SharePreferenceUtil.getinstance();
        JPushBean jPushBean = new JPushBean();
        jPushBean.setALERT(instance.getString("ALERT"));
        jPushBean.setNOTIFICATION_CONTENT_TITLE(instance.getString("NOTIFICATION_CONTENT_TITLE"));
        jPushBean.setMSG_ID(instance.getString("MSG_ID"));
        jPushBean.setContent(instance.getString("content"));
        jPushBean.setPushtype(instance.getString("pushtype"));
        jPushBean.setCustomfield(instance.getString("customfield"));
        return jPushBean;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }


    @Override
    protected void initView() {
        ll_danmaku = findViewById(R.id.ll_danmaku);
        text_start = findViewById(R.id.text_start);
        text_stop = findViewById(R.id.text_stop);
        text_hongbao = findViewById(R.id.text_hongbao);
        text_support = findViewById(R.id.text_support);
        initListener();


        bottomNavigationBar = findViewById(R.id.bottom_navigation_bar);
        bottomNavigationBar
                .setTabSelectedListener(this)
                .setMode(BottomNavigationBar.MODE_FIXED)
                .setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC)
                .setActiveColor("#BA2835") //选中颜色
                .setInActiveColor("#666666") //未选中颜色
                .setBarBackgroundColor("#ffffff");//导航栏背景色
        /** 添加导航按钮 */
        bottomNavigationBar
                .addItem(new BottomNavigationItem(R.drawable.shouye_gray, "首页"))
                .addItem(new BottomNavigationItem(R.drawable.dingdan_gray, "订单"))
                .addItem(new BottomNavigationItem(R.drawable.quanzi, "圈子"))
                .addItem(new BottomNavigationItem(R.drawable.xiaoxi, "消息"))
                .addItem(new BottomNavigationItem(R.drawable.grzx_gray, "我的"))
                .setFirstSelectedPosition(lastSelectedPosition)
                .initialise(); //initialise 一定要放在 所有设置的最后一项

        setDefaultFragment();//设置默认导航栏

    }

    private void initListener() {

        text_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ll_danmaku.setVisibility(View.VISIBLE);
                mDanmakuView.setVisibility(View.VISIBLE);
            }
        });
        text_stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ll_danmaku.setVisibility(View.GONE);
                mDanmakuView.setVisibility(View.GONE);
            }
        });
        text_hongbao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e(TAG, "text_hongbao-------onClick: ");
                String mess = "http*888**795c17d8@t,8$t1,0$u,http://mp.weixin.qq.com/s/*2**weixin:*2**xiaoshulinapp$i,点击去看看打开微信，每月一号十轮现金红包日，进群就可能得终身会员使用权，如果您还没有在我们任意每月一号微信红包福利日群里，我们的微信客服会拉你进群，请添加小树林微信客服微信号：xiaoshulinapp*888**";
                ParsingTools tools = new ParsingTools();
                tools.SecondParseTool(MainActivity.this, mess);
            }
        });
        text_support.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e(TAG, "text_support-------onClick: ");
                presenter.getSupportMoney();
            }
        });
    }

    /**
     * 设置默认导航栏
     */
    private void setDefaultFragment() {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        homeFragment = HomeFragment.newInstance();
        transaction.replace(R.id.layFrame, homeFragment);
        transaction.commit();
    }


    @Override
    public void showNullLayout() {

    }

    @Override
    public void hideNullLayout() {

    }

    @Override
    public void showErrorLayout(View.OnClickListener listener) {

    }

    @Override
    public void hideErrorLayout() {

    }

    @Override
    public void getVideoUrl(String url) {
        /**
         * 免费看看
         * */
        ParsingTools tools = new ParsingTools();
        tools.SecondParseTool(MainActivity.this, url);
    }

    @Override
    public String geturl() {
        return url;
    }

    @Override
    public String getid() {
        return id;
    }

    @Override
    public String getshareid() {
        return shareuid;
    }

    @Override
    public String getUID() {
        String stringUId = SharePreferenceUtil.getinstance().getStringUId();
        Log.e(TAG, "getcheckVison:       stringUId:" + stringUId);
        String readfile = "";
        if (TextUtils.isEmpty(stringUId)) {
            LocalDataBean bean = readfile();
            readfile = bean.getUid();
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
        return readfile;
    }

    @Override
    public void showOldMessage(String mess) {
        /**
         * 信息过时
         * */
        ToastUtil.showToast("分享内容已过期或者修改了分享信息内容");
    }

    @Override
    public void sharecircle(ShareCircleBean bean) {
        /**圈子商品跳转*/
        if (bean.getReturncode().equals("0")) {
            String url = URLDecoder.decode(bean.getRemark());
            ParsingTools tools = new ParsingTools();
            tools.SecondParseTool(MainActivity.this, url);
        } else {
            ToastUtil.showToast("消息过期或修改了消息内容");
        }
    }

    /**
     * 获得第一次注册，获取消息
     */
    @Override
    public void getRegisterdata(RegisterBean registerBean) {
        SharePreferenceUtil.getinstance().setStringUIdToken(registerBean.getUidtoken());
        String sign = registerBean.getSign();
        String start = sign.substring(0, 1);
        String content = sign.substring(2, sign.length() - 2);
        String end = sign.substring(sign.length() - 1, sign.length());
        String check = start + content + end;

        int i = Integer.parseInt(check) - 3851947;
        StringBuffer check_uid = new StringBuffer(String.valueOf(i)).reverse();

        int uid = Integer.parseInt(String.valueOf(check_uid));
        if (uid == Integer.parseInt(registerBean.getUid())) {
            Log.e(TAG, "onResponse: ");

            SharePreferenceUtil util = SharePreferenceUtil.getinstance();
            util.setStringUID(registerBean.getUid());
            util.setStringUIdToken(registerBean.getUidtoken());
            /**保存本地*/
            savefile(registerBean.getUid(), registerBean.getUidtoken());
            setTagAndAlias();

        }
    }

    List<danMuBean.DataBean> data;
    int i=0;
    /**
     * 显示弹幕
     */
    @Override
    public void getDanmuData(danMuBean bean) {
        data = bean.getData();

        Log.e(TAG, "getDanmuData: --------------data："+data.size() );

        CountDownTimer timer=new CountDownTimer(data.size()*100000,1000) {
            @Override
            public void onTick(long l) {
                if (i<data.size()){
                    Log.e(TAG, "onTick: l----"+l );
                    BaseDanmaku danmaku = mContext.mDanmakuFactory.createDanmaku(BaseDanmaku.TYPE_SCROLL_RL);
                    if (danmaku == null || mDanmakuView == null) {
                        return;
                    }
                    danmaku.text = data.get(i).getDescribe();
                    danmaku.padding = 5;
                    danmaku.priority = 0;  // 可能会被各种过滤器过滤并隐藏显示
                    danmaku.isLive = true;
                    danmaku.setTime(mDanmakuView.getCurrentTime());
                    danmaku.textSize = 25f * (mParser.getDisplayer().getDensity() - 0.6f);
                    danmaku.textColor = Color.WHITE;
                    danmaku.textShadowColor = Color.WHITE;
                    // danmaku.underlineColor = Color.GREEN;
                    danmaku.borderColor = Color.RED;
                    mDanmakuView.addDanmaku(danmaku);
                    i++;
                }else {
                    i=0;
                }
            }

            @Override
            public void onFinish() {

            }
        }.start();

    }
    WarningDialog dialogSupport;
    @Override
    public void getSupportMoney(SupportBean registerBean) {
        /**打赏的回调数据*/
        String url = URLDecoder.decode(registerBean.getMessage());
        if (registerBean.getReturncode() == 0) {
            ToastUtil.showToast(url);
        } else {
          dialogSupport = new WarningDialog(context, url, "取消", "V币优充", new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    switch (view.getId()) {
                        case R.id.tv_cancel:
                            dialogSupport.dismiss();
                            break;
                        case R.id.tv_submit:
                            ARouter.getInstance()
                                    .build("/activity/WebActivity")
                                    .withString("url", "http://www.vipbanlv.com/v2_test/#!/recharge")
                                    .navigation();
                            dialogSupport.dismiss();
                            break;
                    }
                }
            });
            dialogSupport.show();
        }
    }

    /**每次进来都要去更新版本数据*/
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    public void getCheckVisionBean(CheckVisionBean bean) {

        String type = bean.getVtype();//是否出现弹幕
        String money =bean.getVmoney();//0是新用户

        /**显示是不是新用户弹框*/
        if (money.equals("0")) {
            showUserDialog();
        }

        if (type.contains("666888")) {

            /**高斯毛玻璃效应*/
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.main);
            Bitmap bitmap1 = rsBlur(MainActivity.this, bitmap, 15, 0.125f);
            Drawable drawable = new BitmapDrawable(bitmap1);
            ll_danmaku.setBackground(drawable);
            /**获取弹幕数据*/
            presenter.getDanMuContent();
            mDanmakuView.setVisibility(View.VISIBLE);
            ll_danmaku.setVisibility(View.VISIBLE);
            text_start.setVisibility(View.VISIBLE);
            if (mDanmakuView != null && mDanmakuView.isPrepared() && mDanmakuView.isPaused()) {
                mDanmakuView.resume();
            }

        } else {
            mDanmakuView.stop();
            mDanmakuView.setVisibility(View.GONE);
            ll_danmaku.setVisibility(View.GONE);
            text_start.setVisibility(View.GONE);
        }
    }

    /**
     * ============================极光推送  开始=======================
     */


    private final TagAliasCallback mAliasCallback = new TagAliasCallback() {
        @Override
        public void gotResult(int code, String alias, Set<String> tags) {
            String logs;
            switch (code) {
                case 0:
                    //这里可以往 SharePreference 里写一个成功设置的状态。成功设置一次后，以后不必再次设置了。
                    //UserUtils.saveTagAlias(getHoldingActivity(), true);
                    logs = "Set tag and alias success极光推送别名设置成功";
                    Log.e("TAG", logs);
                    break;
                case 6002:
                    //极低的可能设置失败 我设置过几百回 出现3次失败 不放心的话可以失败后继续调用上面那个方面 重连3次即可 记得return 不要进入死循环了...
                    logs = "Failed to set alias and tags due to timeout. Try again after 60s.极光推送别名设置失败，60秒后重试";
                    Log.e("TAG", logs);
                    break;
                default:
                    logs = "极光推送设置失败，Failed with errorCode = " + code;
                    Log.e("TAG", logs);
                    break;
            }
        }
    };

    /**
     * 设置标签与别名
     */
    private void setTagAndAlias() {
        /**
         *这里设置了别名，在这里获取的用户登录的信息
         *并且此时已经获取了用户的userId,然后就可以用用户的userId来设置别名了
         **/
        //false状态为未设置标签与别名成功
        //if (UserUtils.getTagAlias(getHoldingActivity()) == false) {
        Set<String> tags = new HashSet<String>();
        //这里可以设置你要推送的人，一般是用户uid 不为空在设置进去 可同时添加多个
        SharePreferenceUtil util = SharePreferenceUtil.getinstance();

        if (!TextUtils.isEmpty(util.getStringUId())) {
            tags.add(util.getStringUId());//设置tag
        }
        //上下文、别名【Sting行】、标签【Set型】、回调
        JPushInterface.setAliasAndTags(context, util.getStringUId(), tags,
                mAliasCallback);
        // }
    }

    public static boolean isForeground = false;

    private CheckUpLoadDialog newUserDialog;

    @Override
    protected void onResume() {

        /**
         * 获取剪贴板中的数据
         * 测试
         * */
        Intent intent = getIntent();
        AcceptClipboardManager();//获得粘贴板里的内容
        isForeground = true;
        int tag = intent.getIntExtra("tag", -1);
        /**调用请求更新接口，为了解决所谓的活动标记，做不同的动作*/
        presenter.visionNewTag();

        if (tag == 0) {   /**从欢迎页跳转过来*/

            Log.e(TAG, "onResume:       tag:" + tag);
            lastSelectedPosition = 0;
            String type = intent.getStringExtra("type");//是否出现弹幕
            if (type.contains("666888")) {

                mDanmakuView.setVisibility(View.VISIBLE);
                ll_danmaku.setVisibility(View.VISIBLE);
                text_start.setVisibility(View.VISIBLE);
            } else {
                mDanmakuView.stop();
                mDanmakuView.setVisibility(View.GONE);
                ll_danmaku.setVisibility(View.GONE);
                text_start.setVisibility(View.GONE);
            }
        }
        super.onResume();
        if (mDanmakuView != null && mDanmakuView.isPrepared() && mDanmakuView.isPaused()) {
            mDanmakuView.resume();
        }
    }

    private void showUserDialog() {
        newUserDialog = new CheckUpLoadDialog(context, "温馨提示", "新用户可以一分钱体验我们平台任意会员账号服务哦！", "《土豪忽略》", "《会员体验》", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.tv_cancel:
                        newUserDialog.dismiss();
                        break;
                    case R.id.tv_submit:
                        ParsingTools tools = new ParsingTools();
                        tools.SecondParseTool(context, "http*888**a4192064@t,9$u,348$i,xiaoshulinapp*888**");
                        newUserDialog.dismiss();
                        break;
                }
            }
        });
        newUserDialog.show();
    }


    @Override
    protected void onPause() {
        isForeground = false;
        super.onPause();
        if (mDanmakuView != null && mDanmakuView.isPrepared()) {
            mDanmakuView.pause();
        }
    }


    @Override
    protected void onStop() {
        if (jPushDialog != null) {
            if (jPushDialog.isShowing()) {
                jPushDialog.dismiss();
            }
        }
        if (dialog != null) {
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
        }
        super.onStop();
    }

    @Override
    protected void onDestroy() {

        LocalBroadcastManager.getInstance(this).unregisterReceiver(mMessageReceiver);
        super.onDestroy();
        // 注销订阅者
        EventBus.getDefault().unregister(this);
        if (mDanmakuView != null) {
            // dont forget release!
            mDanmakuView.release();
            mDanmakuView = null;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (mDanmakuView != null) {
            // dont forget release!
            mDanmakuView.release();
            mDanmakuView = null;
        }
    }

    /**
     * eventbus更新通知事件
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(EventBusMessage event) {
        // 更新界面
        if (event.getType() == 1) {

            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction transaction = fm.beginTransaction();
            userFragment = UserFragment.newInstance();
            transaction.replace(R.id.layFrame, userFragment);
            transaction.commit();

            lastSelectedPosition = 4;
            bottomNavigationBar.setFirstSelectedPosition(lastSelectedPosition);
            bottomNavigationBar.initialise();
        }
    }


    private MessageReceiver mMessageReceiver;
    public static final String MESSAGE_RECEIVED_ACTION = "com.example.jpushdemo.MESSAGE_RECEIVED_ACTION";

    public void registerMessageReceiver() {
        mMessageReceiver = new MessageReceiver();
        IntentFilter filter = new IntentFilter();
        filter.setPriority(IntentFilter.SYSTEM_HIGH_PRIORITY);
        filter.addAction(MESSAGE_RECEIVED_ACTION);
        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver, filter);
    }

    public class MessageReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            /**ch*/
            doReceiveIntentMessage(intent);
        }
    }

    private void doReceiveIntentMessage(Intent intent) {
        try {
            if (MESSAGE_RECEIVED_ACTION.equals(intent.getAction())) {

                Bundle bundle = intent.getExtras();
                String ALERT = bundle.getString(JPushInterface.EXTRA_ALERT);
                String EXTRA = bundle.getString(JPushInterface.EXTRA_EXTRA);
                Gson gson = new Gson();
                JPushBean jPushBean = gson.fromJson(EXTRA, JPushBean.class);
                setCostomMsg(ALERT, jPushBean.getContent());
                /**
                 * 作弹框区分
                 * */
                if (jPushBean.getPushtype().equals("2")) {
                    premissdialog = new CheckUpLoadDialog(context, jPushBean.getALERT(), jPushBean.getContent(), "取消", "去反馈", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            switch (view.getId()) {
                                case R.id.tv_cancel:
                                    premissdialog.dismiss();
                                    break;
                                case R.id.tv_submit:
                                    premissdialog.dismiss();
                                    /*来收入通知*/
                                    ARouter.getInstance()
                                            .build("/user/UserInformation")
                                            .withInt("tag", 2)
                                            .navigation();
                                    break;
                            }
                        }
                    });

                    premissdialog.show();

                } else if (jPushBean.getPushtype().equals("3")) {
                    premissdialog = new CheckUpLoadDialog(context, "通知", "您的账号没有通过审核！", "取消", "去反馈", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            switch (view.getId()) {
                                case R.id.tv_cancel:
                                    premissdialog.dismiss();
                                    break;
                                case R.id.tv_submit:
                                    premissdialog.dismiss();
                                    /**小树林支付宝客服*/
                                    jumpAlipay(ToastUtil.zhifubao());
                                    break;
                            }
                        }
                    });

                    premissdialog.show();
                } else if (jPushBean.getPushtype().equals("14")) {
                    Log.e(TAG, "========onReceive: ");
                    saveData(jPushBean);
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "doReceiveIntentMessage:      获得intent信息异常");
        }
    }

    private void saveData(JPushBean jPushBean) {
        new DataBaseUtils().insertChatMessageData(context, jPushBean)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        if (aBoolean) {
                            new DataBaseUtils().insertMessageListData(context, jPushBean)
                                    .subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(new Consumer<Boolean>() {
                                        @Override
                                        public void accept(Boolean aBoolean) throws Exception {
                                            if (aBoolean) {
                                                String customfield = jPushBean.getCustomfield();
                                                Gson gson = new Gson();
                                                CostomFieldBean costomFieldBean = gson.fromJson(customfield, CostomFieldBean.class);
                                                ARouter.getInstance()
                                                        .build("/activity/ChatMessageActivity")
                                                        .withString("titleName", costomFieldBean.getNickName())
                                                        .withString("messageSectionid", costomFieldBean.getMessageSection())
                                                        .navigation();
                                            } else {
                                                ToastUtil.showToast("插入--聊天列表--失败");
                                            }
                                        }
                                    }, new Consumer<Throwable>() {
                                        @Override
                                        public void accept(Throwable throwable) throws Exception {
                                            Log.e(TAG, "accept: " + throwable);
                                        }
                                    });
                        } else {
                            ToastUtil.showToast("插入--聊天记录表---的失败");
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.e(TAG, "插入异常-----------accept: " + throwable);
                    }
                });
    }

    @SuppressLint("NewApi")
    private void setCostomMsg(String ALERT, String content) {
        window1 = new JpushPopupwindow(MainActivity.this, ALERT, content);
        window1.showAtLocation(findViewById(R.id.main),
                Gravity.CENTER | Gravity.TOP,
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 0, context.getResources().getDisplayMetrics()),
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, -20, context.getResources().getDisplayMetrics()));
        window1.showAsDropDown(findViewById(R.id.main),
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 0, context.getResources().getDisplayMetrics()),
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, -20, context.getResources().getDisplayMetrics()),
                Gravity.CENTER | Gravity.TOP);
        window1.update();
    }

    /**===========================极光推送  结束============================*/

    /**
     * 保存文件到本机存储
     *
     * @param toSaveString
     */
    public void savefile(String toSaveString, String uidtoken) {

        Log.e(TAG, "savefile:       ---------------> toSaveString:" + toSaveString);
        int num = Integer.parseInt(toSaveString) + 214;
        Log.e(TAG, "savefile:       ---------------> num:" + num);
        int num1 = num * 3;
        Log.e(TAG, "savefile:       ---------------> num1:" + num1);
        String a = "ab3478re109qw" + num1 + "scd66487ab3t";
        Log.e(TAG, "savefile:       ---------------> a:" + a);
        a = a + "*" + uidtoken;
        File file = new File(Environment.getExternalStorageDirectory(), "/android1/weixinxslapi.txt");
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }

        try {
            FileOutputStream stream = new FileOutputStream(file.getAbsolutePath());
            byte[] buf = a.getBytes();
            stream.write(buf);
            stream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }


    /**
     * 读取本机存储文件内容
     *
     * @return 文件内容
     */
    public LocalDataBean readfile() {

        File file = new File(Environment.getExternalStorageDirectory(), "/android1/weixinxslapi.txt");
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        InputStream in = null;
        BufferedReader reader = null;
        StringBuilder content = new StringBuilder();
        try {

            in = new FileInputStream(file.getAbsolutePath());//文件名
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
        LocalDataBean bean = new LocalDataBean();
        String a = content.toString();
        if (TextUtils.isEmpty(a)) {
            return bean;
        }
        String[] split = a.split("\\*");
        String A = split[0];
        String B = split[1];
        Log.e(TAG, "readfile:   加密后取出来的数据------------->a:" + a);
        String b = A.substring(13, A.length() - 12);
        Log.e(TAG, "readfile:   ------------->b:" + b);
        int c = Integer.parseInt(b) / 3;
        Log.e(TAG, "readfile:   ------------->c:" + c);
        String uid = String.valueOf(c - 214);
        Log.e(TAG, "readfile:   ------------->uid:" + uid);

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
                LocalDataBean localDataBean = readfile();

                if (!TextUtils.isEmpty(localDataBean.getUid())) {

                    SharePreferenceUtil.getinstance().setStringUID(localDataBean.getUid());
                    SharePreferenceUtil.getinstance().setStringUIdToken(localDataBean.getUidtoken());
                    setTagAndAlias();
                } else {
                    //请求数据
                    initdata();
                }

            } else {
                String stringUId = SharePreferenceUtil.getinstance().getStringUId();
                if (TextUtils.isEmpty(stringUId)) {

                    //请求数据
                    initdata();
                } else {
                    setTagAndAlias();
                }
                // 权限被用户拒绝了，洗洗睡吧。
                Log.e("jp", "onRequestPermissionsResult:  权限被用户拒绝了，洗洗睡吧");

            }
        }
    }


    private BottomNavigationBar bottomNavigationBar;
    private int lastSelectedPosition = 0;
    private HomeFragment homeFragment;
    private OrderFragment orderFragment;
    private UserFragment userFragment;
    private MessageFragment messageFragment;
    private CircleFragment circleFragment;

    @Override
    public void onTabSelected(int position) {
        FragmentManager fm = getSupportFragmentManager();
        //开启事务
        FragmentTransaction transaction = fm.beginTransaction();
        switch (position) {
            case 0:
                if (homeFragment == null) {
                    homeFragment = HomeFragment.newInstance();
                }
                transaction.replace(R.id.layFrame, homeFragment);
                break;
            case 1:
                if (orderFragment == null) {
                    orderFragment = OrderFragment.newInstance();
                }
                transaction.replace(R.id.layFrame, orderFragment);
                break;
            case 2:
                if (circleFragment == null) {
                    circleFragment = CircleFragment.newInstance();
                }
                transaction.replace(R.id.layFrame, circleFragment);
                break;
            case 3:
                if (messageFragment == null) {
                    messageFragment = MessageFragment.newInstance();
                }
                transaction.replace(R.id.layFrame, messageFragment);
                break;
            case 4:
                if (userFragment == null) {
                    userFragment = UserFragment.newInstance();
                }
                transaction.replace(R.id.layFrame, userFragment);
                break;
            default:
                break;
        }

        transaction.commit();// 事务提交
    }

    @Override
    public void onTabUnselected(int position) {

    }

    @Override
    public void onTabReselected(int position) {

    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    private Bitmap rsBlur(Context context, Bitmap source, int radius, float scale) {
        Log.i(TAG, "origin size:" + source.getWidth() + "*" + source.getHeight());

        int width = Math.round(source.getWidth() * scale);
        int height = Math.round(source.getHeight() * scale);
        Bitmap inputBmp = Bitmap.createScaledBitmap(source, width, height, false);
        RenderScript renderScript = RenderScript.create(context);
        Log.i(TAG, "scale size:" + inputBmp.getWidth() + "*" + inputBmp.getHeight());
        // Allocate memory for Renderscript to work with
        final Allocation input = Allocation.createFromBitmap(renderScript, inputBmp);
        final Allocation output = Allocation.createTyped(renderScript, input.getType());
        // Load up an instance of the specific script that we want to use.
        ScriptIntrinsicBlur scriptIntrinsicBlur = ScriptIntrinsicBlur.create(renderScript, Element.U8_4(renderScript));
        scriptIntrinsicBlur.setInput(input);
        // Set the blur radius
        scriptIntrinsicBlur.setRadius(radius);
        // Start the ScriptIntrinisicBlur
        scriptIntrinsicBlur.forEach(output);
        // Copy the output to the blurred bitmap
        output.copyTo(inputBmp);
        renderScript.destroy();
        return inputBmp;
    }


}
