package com.xiaoshulin.vipbanlv.utils;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.util.Log;
import android.view.View;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.callback.NavigationCallback;
import com.alibaba.android.arouter.launcher.ARouter;
import com.xiaoshulin.vipbanlv.R;
import com.xiaoshulin.vipbanlv.WebActivity;
import com.xiaoshulin.vipbanlv.activity.OrderListActivity;
import com.xiaoshulin.vipbanlv.bean.EventBusMessage;
import com.xiaoshulin.vipbanlv.bean.ParseBean;
import com.xiaoshulin.vipbanlv.dialog.CheckUpLoadDialog;
import com.xiaoshulin.vipbanlv.dialog.PremissionsDialog;

import org.greenrobot.eventbus.EventBus;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static org.greenrobot.eventbus.EventBus.TAG;

/**
 * Created by jipeng on 2019/4/15.
 * 解析规则工具
 */
public class ParsingTools {


    /**
     * 实例1：
     * http*888**0ae3e463@t,0$u,VipUserCentreViewController*888**
     * 1、分割出http和0ae3e463@t,0$u,VipUserCentreViewController两个字符串符合规则
     * 2、分割出0ae3e463和t,0$u,VipUserCentreViewController两个字符串
     * 3、在t,0$u,VipUserCentreViewController后面拼接固定字符串37*3^jhw,9-1397zx.cASD!@#得t,0$u,VipUserCentreViewController37*3^jhw,9-1397zx.cASD!@#
     * 4、把第三步得到的加密字符串从第15位截取后面的8位得截取后得0ae3e463对比第四步和第二步截取的加密字符串是否一致如果一致就符合规则
     * 5、用$分割t,0$u,VipUserCentreViewController得t,0和u,VipUserCentreViewController两个字符串。再去分别用,分割每一个字符串得到对应的key和value
     * 6、t对应的就是一级类型。
     * 7、当t等于0时主要是跳转到对应的原生类里，是根据u对应的原生类里。
     *
     * 实例2：
     * http*888**6a5a1a5b@t,3$u,http://pr.binglunhrq.com/back/20181213/3b4a8ea7f9e49e12673e01e0e110d85c.mp4*888**
     * 8、当t等于1或者等于3时主要是视频链接，取u对应的值跳转到网页里就行了。
     * 实例3：
     * http*888**999b1c08@t,2$u,alipayqr://platformapi/startapp?saId=88886666&passcode=96251656$i,https://m.alipay.com/gTbvMGX?abFrom=C2C_APP_NEWundefined*888**
     * 9、当t等于2时主要就是打开其他三方APP的类型，取出u对应的值走跳转其他三方APP逻辑，你写的那些判断条件要有，如果是支付宝没有按钮就取i对应的值跳转到手机自带浏览器。
     * 实例4：
     * http*888**db6f8f85@t,4$u,gifshow://$i,https://itunes.apple.com/us/app/zhang-hao-ban-lu/id992488773?l=zh&ls=1&mt=8*888**
     * 10、当t等于4时主要是其他APP下载推广类型，取出u对应的值走打开三方APP方法，如果异常了，警告框提示title：温馨提示content：您还没有安装对应APP无法享受这个福利，点击去看看就可以下载APP享受福利了。弹框有两个按钮一个是取消，一个是去看看，点击去看看取出i对应的值跳转手机自带浏览器里就行了。
     * 实例5：
     * http*888**0e970027@t,6$u,taobao:$i,￥MMhLbrVDhh5￥*888**
     * 11、当t等于6时主要是简单的复制文字到剪贴板，取出i对应的值复制到手机粘贴版，提示复制成功就可以了。
     * 实例6：
     * http*888**d19fcace@t,9$u,324$i,小树林APP*888**
     * 12、当t等于9时主要是进入单个圈子详情页类型，取出u对应的值就是一条圈子的id就可以进入对应圈子详情了。
     * 实例7：
     * http*888**e32bc5c7@t,10$u,1374$i,temporary11*888**
     * 13、当t等于10时主要进入单个人圈子页类型，取出u对应的值就是用户id取出i对应的值就是用户的头像，用户头像如果不是一个url就是一个本地图片文件名。
     * 实例8：
     * http*888**92174cd1@t,11$u,1374$i,335*888**
     * 14、当t等于11时主要进入圈子备注页面类型，要走一个获取圈子备注内容的接口，之前你写过的，取出i对应的值就是shareid对应的值取出u对应的值就是shareuid对应的值然后走之前你走过的获取圈子备注的接口，还有两个参数一个是sharetype是固定值1还有一个shareverify值和之前一样的加密方法。
     *
     * 实例9：
     * http*888**2c10d3f0@t,8$t1,0$u,http*1**http://pr.binglunhrq.com/back/20181213/3b4a8ea7f9e49e12673e01e0e110d85c.mp4$i,美女视频等你欣赏*888**
     * 实例10：
     * http*888**a2dcba10@t,8$t1,1$u,小树林APP送支付宝红包啦！复制文字中数字356633563打开支付宝输入数字口令就可以领取支付宝红包咯$i,点击去看看分享给好朋友，自己和朋友都可以得到一个支付宝口令红包*888**
     * 15、当t等于8时，主要是带警告弹窗的类型，title是固定文字：温馨提示content是取出i对应的值，两个按钮一个是取消一个是去看看，点击去看看判断二级类型 t1取出的值如果等于0，取出u对应的值再走一遍url解析的方法，如果不满足所有条件就拿取出的值跳转到APP网页里。如果t1等于1是分享文本类型，取出u对应的值跳转到分享页就行了。当t1等于2时取出u对应的值进入圈子备注页面
     * */
    Context context;
    public void SecondParseTool(Context context,String parseMess){
        this.context=context;
        String url = "";
        try {
            url= URLDecoder.decode(parseMess, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String[] split1 = url.split("\\*888\\*\\*");
        if (split1.length>=2)
        {
            String second=split1[1];
            String[] split2 = second.split("@");
            String s = split2[1];
            String s1=s+"37*3^jhw,9-1397zx.cASD!@#";
            String stringMD5 = MD5Util.getStringMD5(s1);
            String substring = stringMD5.substring(15, 23);
            List<ParseBean> list=new ArrayList<>();
            if (split2[0].equals(substring)){
                String[] split3 = s.split("\\$");
                for (int i = 0; i <split3.length ; i++) {
                    String s2 = split3[i];
                    String[] split4 = s2.split(",");
                    ParseBean bean=new ParseBean();
                    bean.setKey(split4[0]);
                    bean.setValue(split4[1]);
                    list.add(i,bean);
                }
                if (list.size() >= 2) {
                    if (list.get(0).getKey().equals("t")) {
                        String value = list.get(0).getValue();
                        int i = Integer.parseInt(value);
                        String mess = list.get(1).getValue();
                        switch (i) {
                            case 0:
                                jumpAppClassName(mess);
                                break;
                            case 1:
                                ARouter.getInstance()
                                        .build("/activity/WebActivity")
                                        .withString("url", mess)
                                        .navigation();
                                break;
                            case 2:
                                jumpCheckApp(mess);
                                break;
                            case 3:
                                ARouter.getInstance()
                                        .build("/activity/WebActivity")
                                        .withString("url", mess)
                                        .navigation();
                                break;
                            case 4:
                                ARouter.getInstance()
                                        .build("/activity/WebActivity")
                                        .withString("url", mess)
                                        .navigation();
                                break;
                            case 6:
                                /**copy内容*/
                                copyMess(mess);
                                break;
                            case 9:
                                /**单个圈子*/
                                jumpSinpleCircle(mess);
                                break;
                            case 10:
                                /**单个人圈子*/
                                jumpSinplePersionCircle(mess);
                                break;
                            case 11:
                                /**备注*/
                                jumpCircleRemark(mess, list.get(2).getValue());
                                break;
                            case 8:
                                showTipDialog(list);
                                break;
                            default:
                                break;
                        }

                    }
                }


            }
        }else {
            String[] urlstring = url.split("\\*1\\*\\*");
            if (urlstring.length > 1) {
                String secondurl = urlstring[1];
                ARouter.getInstance()
                        .build("/activity/WebActivity")
                        .withString("url", secondurl)
                        .navigation();
                return;
            }
            String[] urlstring1 = url.split("\\*3\\*\\*");
            if (urlstring1.length > 1) {
                String secondurl = urlstring1[1];
                ARouter.getInstance()
                        .build("/activity/WebActivity")
                        .withString("url", secondurl)
                        .navigation();
                return;
            }

            String[] spliturl = url.split("\\*\\*\\*");
            if (spliturl.length >= 2) {
                String name = spliturl[1];
                jumpAppClassName(name);
                return ;
            }


            String[] sinaurl = url.split("\\*2\\*\\*");

            if (sinaurl.length > 1) {
                String secondurl = sinaurl[1];
                if (sinaurl.length > 2) {
                    //获取剪贴板管理器：
                    ClipboardManager cm = (ClipboardManager)context.getSystemService(Context.CLIPBOARD_SERVICE);
                    // 创建普通字符型ClipData
                    ClipData mClipData = ClipData.newPlainText("Label", sinaurl[2]);
                    // 将ClipData内容放到系统剪贴板里。
                    cm.setPrimaryClip(mClipData);
                    String content;
                    if (sinaurl[1].contains("alipayqr://platformapi/startapp?saId=88886666")) {
                        content = "支付宝红包口令复制成功，去支付宝红包页面粘贴口令领红包吧！";
                    } else {

                        content = "福利内容复制成功，可以去对应APP粘贴获取福利。";
                    }
                    dialog = new PremissionsDialog(context, content, new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            jumpCheckApp(secondurl);
                            dialog.dismiss();
                        }
                    });
                    dialog.show();
                    return ;
                } else {
                    jumpCheckApp(secondurl);
                    return;
                }

            }

            ARouter.getInstance()
                    .build("/activity/WebActivity")
                    .withString("url", parseMess)
                    .navigation();
        }

    }
    PremissionsDialog dialog;
    CheckUpLoadDialog mTipDialog;
    private void showTipDialog(List<ParseBean> list) {

        String content = list.get(3).getValue();
        mTipDialog = new CheckUpLoadDialog(context, "温馨提示", content, "取消", "去看看", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.tv_cancel:

                        mTipDialog.dismiss();
                        break;
                    case R.id.tv_submit:
                        String t1 = list.get(1).getKey();
                        String t1_value = list.get(1).getValue();
                        if (t1.equals("t1")) {
                            String value3 = list.get(2).getValue();
                            if (t1_value.equals("0")) {
                                /**重新走一遍解析*/
                               SecondParseTool(context,value3);
                            } else if (t1_value.equals("1")) {
                                /**分享文本类型*/
                                ARouter.getInstance()
                                        .build("/activity/ShareWechat")
                                        .withString("copystring", value3)
                                        .navigation();
                            } else if (t1_value.equals("2")) {
                                /** 进入圈子备注页面*/
                                jumpCircleRemark(t1_value, value3);
                            } else {
                                ARouter.getInstance()
                                        .build("/activity/WebActivity")
                                        .withString("url", value3)
                                        .navigation();
                            }
                        }
                        mTipDialog.dismiss();
                        break;
                }
            }
        });
        mTipDialog.show();
    }

    private void jumpCircleRemark(String mess, String value) {
        ARouter.getInstance().build("/activity/WebUserActivity")
                .withInt("tag", 4)
                .withString("cricleid", value)
                .withString("produceid", mess)
                .navigation();
    }


    private void jumpSinpleCircle(String mess) {
        ARouter.getInstance()
                .build("/activity/CommonActivity")
                .withInt("tag", 0)
                .withString("cricleid", mess)
                .navigation();
    }

    private void jumpSinplePersionCircle(String mess) {
        ARouter.getInstance()
                .build("/activity/WebUserActivity")
                .withInt("tag", 1)
                .withString("cricleid", mess)
                .navigation();
    }

    private void copyMess(String mess) {
        //获取剪贴板管理器：
        ClipboardManager cm = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        // 创建普通字符型ClipData
        ClipData mClipData = ClipData.newPlainText("Label", mess);
        // 将ClipData内容放到系统剪贴板里。
        cm.setPrimaryClip(mClipData);
        ToastUtil.showToast("复制成功");
    }

    private void jumpCheckApp(String secondurl) {

        if (secondurl.contains("sinaweibo")) {
            if (isWeiboInstalled(context)) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory("android.intent.category.DEFAULT");
                intent.setData(Uri.parse(secondurl));
                context.startActivity(intent);

            } else {
                ToastUtil.showToast("没有安装微博APP无法查看相关内容");
            }
        } else if (secondurl.contains("alipay")) {
            if (isWeiboInstalled(context)) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory("android.intent.category.DEFAULT");
                intent.setData(Uri.parse(secondurl));
                context.startActivity(intent);
            } else {
                ToastUtil.showToast("没有安装支付宝APP无法查看相关内容");
            }
        } else if (secondurl.contains("alipayqr")) {
            if (isWeiboInstalled(context)) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory("android.intent.category.DEFAULT");
                intent.setData(Uri.parse(secondurl));
                context.startActivity(intent);

            } else {
                ToastUtil.showToast("没有安装支付宝APP无法查看相关内容");
            }
        } else if (secondurl.contains("weixin")) {
            if (isWeiboInstalled(context)) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory("android.intent.category.DEFAULT");
                intent.setData(Uri.parse(secondurl));
                context.startActivity(intent);

            } else {
                ToastUtil.showToast("没有安装微信APP无法查看相关内容");
            }
        } else if (secondurl.contains("mqq")) {
            if (isWeiboInstalled(context)) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory("android.intent.category.DEFAULT");
                intent.setData(Uri.parse(secondurl));
                context.startActivity(intent);

            } else {
                ToastUtil.showToast("没有安装QQ无法查看相关内容");
            }
        } else if (secondurl.contains("taobao")) {
            if (isWeiboInstalled(context)) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory("android.intent.category.DEFAULT");
                intent.setData(Uri.parse(secondurl));
                context.startActivity(intent);

            } else {
                ToastUtil.showToast("没有安装淘宝APP无法查看相关内容");
            }
        } else if (secondurl.contains("tmall")) {
            if (isWeiboInstalled(context)) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory("android.intent.category.DEFAULT");
                intent.setData(Uri.parse(secondurl));
                context.startActivity(intent);

            } else {
                ToastUtil.showToast("没有安装天猫APP无法查看相关内容");
            }
        } else if (secondurl.contains("snssdk1128")) {
            if (isWeiboInstalled(context)) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory("android.intent.category.DEFAULT");
                intent.setData(Uri.parse(secondurl));
                context.startActivity(intent);

            } else {
                ToastUtil.showToast("没有安装抖音APP无法查看相关内容");
            }
        } else if (secondurl.contains("gifshow")) {
            if (isWeiboInstalled(context)) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory("android.intent.category.DEFAULT");
                intent.setData(Uri.parse(secondurl));
                context.startActivity(intent);

            } else {
                ToastUtil.showToast("没有安装快手APP无法查看相关内容");
            }
        } else if (secondurl.contains("pinduoduo")) {
            if (isWeiboInstalled(context)) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory("android.intent.category.DEFAULT");
                intent.setData(Uri.parse(secondurl));
                context.startActivity(intent);

            } else {
                ToastUtil.showToast("没有安装拼多多APP无法查看相关内容");
            }
        } else if (secondurl.contains("openApp.jdMobile")) {
            if (isWeiboInstalled(context)) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory("android.intent.category.DEFAULT");
                intent.setData(Uri.parse(secondurl));
                context.startActivity(intent);

            } else {
                ToastUtil.showToast("没有安装京东APP无法查看相关内容");
            }
        }

    }


    public boolean isWeiboInstalled(Context context) {
        PackageManager pm;
        if ((pm = context.getApplicationContext().getPackageManager()) == null) {
            return false;
        }
        List<PackageInfo> packages = pm.getInstalledPackages(0);
        for (PackageInfo info : packages) {
            String name = info.packageName.toLowerCase(Locale.ENGLISH);
            if ("com.sina.weibo".equals(name)) {
                /**微博*/
                return true;
            } else if ("com.tmall.wireless".equals(name)) {
                /**天猫*/
                return true;
            } else if ("com.taobao.taobao".equals(name)) {
                /**淘宝*/
                return true;
            } else if ("com.eg.android.AlipayGphone".equals(name)) {
                /**支付宝*/
                return true;
            } else if ("com.jingdong.app.mall".equals(name)) {
                /**京东*/
                return true;
            } else if ("com.smile.gifmaker".equals(name)) {
                /**快手*/
                return true;
            } else if ("com.tencent.mobileqq".equals(name)) {
                /**qq*/
                return true;
            } else if ("com.tencent.mm".equals(name)) {
                /**微信*/
                return true;
            } else if ("com.xunmeng.pinduoduo".equals(name)) {
                /**拼多多*/
                return true;
            } else if ("com.ss.android.ugc.aweme".equals(name)) {
                /**抖音*/
                return true;
            }
        }
        return false;
    }

    private void jumpAppClassName(String name) {
        if (name.equals("VipUserCentreViewController")) {
            /**
             * 用户中心
             * */
            ARouter.getInstance()
                    .build("/activity/CommonActivity")
                    .withInt("tag", 1)
                    .navigation();


        } else if (name.equals("PurchaseRecordViewController")) {
            /**
             * 我的订单
             * */
            Intent intent = new Intent(context, OrderListActivity.class);
            context.startActivity(intent);
        } else if (name.equals("RecommendViewController")) {
            /**
             * 跳转微信分享
             * */
            jumpShareWeChat();
        } else if (name.equals("ChatMessageListController")) {
            /**消息列表*/

            ARouter.getInstance()
                    .build("/activity/WebUserActivity")
                    .withInt("tag", 2)
                    .navigation();
        }else if (name.equals("ACMomentsViewController")){
            /**进入圈子*/
            ARouter.getInstance()
                    .build("/user/UserInformation")
                    .withInt("tag",14)
                    .navigation();
        }
    }



    private void jumpShareWeChat() {
        String share = "《小树林》原来还可以这样！\n" +
                "我在这里买各种东东方便又便宜，你也可以试试！\n" +
                "小树林APP各大视频网站会员一个月只要几块钱。点击链接即可试用：http://www.vipbanlv.com/www_v1\n" +
                "\n" + "\uD83D\uDC8B" +
                "安卓手机可以点击这个链接下载app：http://dwz.cn/86EfALIU" +
                "\n" +
                "\n" + "\uD83D\uDC8B" +
                "苹果手机可以点击这个链接下载app：http://dwz.cn/lqrDxatM";
        ARouter.getInstance()
                .build("/activity/ShareWechat")
                .withString("copystring", share)
                .navigation();
    }




    /**
     * 1、先用*888**去分割链接如果可以被分割等于大于2个字符串就符合这个规则，往下走
     * 2、把*888**分割出来的第二个字符串再用@去分割可以分割出两字符串，第一个是加密字符串，第二个是被加密字符串。
     * 3、用第二个被加密字符串后面拼接固定字符串37*3^jhw,9-1397zx.cASD!@#然后用MD5加密得到一个加密字符串。
     * 4、把这个加密字符串从第15位开始截取后面的8位字符串然后拿这个8位字符串和第2步分割出的那个加密字符串对比一样符合规则往下走。
     * 5、把第二步被加密的字符串用$分割就可以得到我们需要的key和value了不过此时key和value是用,拼接在一起的然后循环用,分割得到对应的key和value
     * 6、目前key有t类型、t1子类型、u链接或者文字值、i需要复制到粘贴版的值
     * */
    public static List<ParseBean> FirstParseTool(String parseMess){
        String url = "";
        try {
           url= URLDecoder.decode(parseMess, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        List<ParseBean> list=new ArrayList<>();
        String[] split1 = url.split("\\*888\\*\\*");
        if (split1.length>=2)
        {
            String second=split1[1];
            String[] split2 = second.split("@");
            String s = split2[1];
            String s1=s+"37*3^jhw,9-1397zx.cASD!@#";
            String stringMD5 = MD5Util.getStringMD5(s1);
            String substring = stringMD5.substring(15, 23);
            if (split2[0].equals(substring)){
                String[] split3 = s.split("\\$");
                for (int i = 0; i <split3.length ; i++) {
                    String s2 = split3[i];
                    String[] split4 = s2.split(",");
                    ParseBean bean=new ParseBean();
                    bean.setKey(split4[0]);
                    bean.setValue(split4[1]);
                    list.add(i,bean);
                }
                return list;
            }
        }
        return list;
    }
}
