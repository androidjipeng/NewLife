package com.xiaoshulin.vipbanlv.activity;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.Settings;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.google.gson.Gson;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.xiaoshulin.vipbanlv.App;
import com.xiaoshulin.vipbanlv.MainActivity;
import com.xiaoshulin.vipbanlv.R;
import com.xiaoshulin.vipbanlv.adapter.ChatMessageAdapter;
import com.xiaoshulin.vipbanlv.adapter.ShortCutKeyAdapter;
import com.xiaoshulin.vipbanlv.base.BaseMVPActivity;
import com.xiaoshulin.vipbanlv.base.BasePresenter;
import com.xiaoshulin.vipbanlv.bean.JPushBean;
import com.xiaoshulin.vipbanlv.bean.ResolveBean;
import com.xiaoshulin.vipbanlv.bean.ShortCutBean;
import com.xiaoshulin.vipbanlv.database.entity.ChatMessageEntity;
import com.xiaoshulin.vipbanlv.dialog.CheckUpLoadDialog;
import com.xiaoshulin.vipbanlv.dialog.MainDialog;
import com.xiaoshulin.vipbanlv.dialog.PremissionsDialog;
import com.xiaoshulin.vipbanlv.jpush.LocalBroadcastManager;
import com.xiaoshulin.vipbanlv.popup.JpushPopupwindow;
import com.xiaoshulin.vipbanlv.presenter.ChatMessagePresenter;
import com.xiaoshulin.vipbanlv.utils.DataBaseUtils;
import com.xiaoshulin.vipbanlv.utils.JpushBeanUtils;
import com.xiaoshulin.vipbanlv.utils.ParsingTools;
import com.xiaoshulin.vipbanlv.utils.SharePreferenceUtil;
import com.xiaoshulin.vipbanlv.utils.TitleBar;
import com.xiaoshulin.vipbanlv.utils.ToastUtil;
import com.xiaoshulin.vipbanlv.utils.Utils;
import com.xiaoshulin.vipbanlv.view.IChatMessageView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cn.jpush.android.api.JPushInterface;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

import static android.view.KeyEvent.KEYCODE_FORWARD_DEL;

@Route(path = "/activity/ChatMessageActivity")
public class ChatMessageActivity extends BaseMVPActivity implements IChatMessageView, View.OnClickListener {
    private static final String TAG = "ChatMessageActivity";
    @Autowired
    public String titleName;
    @Autowired
    public String messageSectionid;

    private ChatMessagePresenter presenter;
    private CheckUpLoadDialog premissdialog;
    private Context context;
    private TitleBar title_bar;

    private XRecyclerView chat_recycle;
    private ImageView img_custom, img_emoji, img_keyword;
    private EditText et_keyword;
    private RecyclerView viewPage;
    private TextView send;
    boolean isshow = true;
    private List<ChatMessageEntity> list = new ArrayList<>();
    private ChatMessageAdapter adapter;
    boolean sendKey = false;
    private String content;

    private MainDialog dialog;
    String copystring = "";
    ResolveBean bean;

    TextView title_inform;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ARouter.getInstance().inject(this);
        super.onCreate(savedInstanceState);
        registerMessageReceiver();
        //监听输入框
        initEditTextListener();


    }

    private void initEditTextListener() {

        et_keyword.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                Log.e(TAG, "-------onTouch: " + motionEvent.getAction());
                isshow = true;
                viewPage.setVisibility(View.GONE);
                return false;
            }
        });
        et_keyword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int before, int count) {
                Log.e(TAG, "   beforeTextChanged: " + "----charSequence:" + charSequence.toString() + "---start:" + start + "---before:" + before + "---count:" + count);
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int count, int after) {

                Log.e(TAG, "   onTextChanged: " + "----charSequence:" + charSequence.toString() + "---start:" + start + "---after:" + after + "---count:" + count);

                if (charSequence.toString().length() > 0) {
                    send.setVisibility(View.VISIBLE);
                    img_keyword.setVisibility(View.GONE);

                } else {
                    send.setVisibility(View.GONE);
                    img_keyword.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                Log.e(TAG, "afterTextChanged: " + editable.toString());
            }
        });
    }


    @Override
    protected int getLayoutId() {
        return R.layout.activity_chat_message;
    }

    @Override
    protected void initView() {
        context = this;
        title_bar = findViewById(R.id.title_bar);
        if (messageSectionid.equals("1374")) {
            title_bar.setCenterText("客服小哥");
        } else if (messageSectionid.equals("1377")) {
            title_bar.setCenterText("客服小妹");
        } else {
            title_bar.setCenterText(titleName);
        }
        et_keyword = findViewById(R.id.et_keyword);

        img_custom = findViewById(R.id.img_custom);
        img_emoji = findViewById(R.id.img_emoji);
        img_keyword = findViewById(R.id.img_keyword);
        img_custom.setOnClickListener(this);
        img_emoji.setOnClickListener(this);
        img_keyword.setOnClickListener(this);

        send = findViewById(R.id.send);
        send.setOnClickListener(this);

        chat_recycle = findViewById(R.id.chat_recycle);
        viewPage = findViewById(R.id.viewPage);
        viewPage.setVisibility(View.GONE);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(context);
        chat_recycle.setLayoutManager(manager);

        RecyclerView.LayoutManager managerPager = new LinearLayoutManager(context);
        ((LinearLayoutManager) managerPager).setOrientation(LinearLayoutManager.HORIZONTAL);
        viewPage.setLayoutManager(managerPager);


        title_inform = findViewById(R.id.title_inform);
        title_inform.setOnClickListener(this);

    }

    @Override
    public BasePresenter initPresenter() {
        return presenter = new ChatMessagePresenter(this);
    }

    @Override
    protected void initData() {
        adapter = new ChatMessageAdapter(context, list);
        chat_recycle.setAdapter(adapter);
        /**获取聊天数据*/
        presenter.chatMessagedata(context, messageSectionid);

        presenter.ShortCutMessage();

        chatMessageAdapterListener();
    }

    private void chatMessageAdapterListener() {
        adapter.setOnItemClickLitener(new ChatMessageAdapter.OnItemClickLitener() {
            @Override
            public void onItemLeftClick(View view, int position) {
                copystring = list.get(position).getText();
                JpushBeanUtils utils = new JpushBeanUtils();
                bean = utils.resolveMessage(context, list.get(position).getText());
                if (bean.getTag() == 0) {
                    dialog = new MainDialog(context, bean.getShareuid(), bean.getContent(), new ShareListener());
                    dialog.show();
                } else if (bean.getTag() == 1) {
                    dialog = new MainDialog(context, bean.getUrl(), copystring, new ShareListener());
                    dialog.show();
                } else if (bean.getTag() == 2) {
                    ARouter.getInstance().build("/activity/WebUserActivity")
                            .withInt("tag", 4)
                            .withString("cricleid", bean.getContent())
                            .withString("produceid", bean.getUrl())
                            .navigation();
                } else {
                    //获取剪贴板管理器：
                    ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                    // 创建普通字符型ClipData
                    ClipData mClipData = ClipData.newPlainText("Label", copystring);
                    // 将ClipData内容放到系统剪贴板里。
                    cm.setPrimaryClip(mClipData);
                    ToastUtil.showToast("复制成功");
                }
                Log.e(TAG, "onItemLeftClick:  这是免费内容或通知分享内容的字段分割--------待实现");

            }

            @Override
            public void onItemRightClick(View view, int position) {
                copystring = list.get(position).getText();
                JpushBeanUtils utils = new JpushBeanUtils();
                bean = utils.resolveMessage(context, list.get(position).getText());
                if (bean.getTag() == 0) {
                    dialog = new MainDialog(context, bean.getShareuid(), bean.getContent(), new ShareListener());
                    dialog.show();
                } else if (bean.getTag() == 1) {
                    dialog = new MainDialog(context, bean.getUrl(), copystring, new ShareListener());
                    dialog.show();
                } else if (bean.getTag() == 2) {
                    ARouter.getInstance().build("/activity/WebUserActivity")
                            .withInt("tag", 4)
                            .withString("cricleid", bean.getContent())
                            .withString("produceid", bean.getUrl())
                            .navigation();
                } else {
                    //获取剪贴板管理器：
                    ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                    // 创建普通字符型ClipData
                    ClipData mClipData = ClipData.newPlainText("Label", copystring);
                    // 将ClipData内容放到系统剪贴板里。
                    cm.setPrimaryClip(mClipData);
                    ToastUtil.showToast("复制成功");
                }
                Log.e(TAG, "onItemLeftClick:  这是免费内容或通知分享内容的字段分割--------待实现");
            }
        });
    }

    public class ShareListener implements View.OnClickListener {

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
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_custom:
                ARouter.getInstance()
                        .build("/activity/OrderInformationActivity")
                        .withString("Name", "小树林客服")
                        .withString("Dayend", "")
                        .withString("Wechat", "")
                        .withString("Orderid", "")
                        .withString("Password", "")
                        .withString("pushuid", "")
                        .withString("pushwechat", "")
                        .withInt("tag", 0)
                        .navigation();
                break;
            case R.id.img_emoji:
                break;
            case R.id.img_keyword:

                showOrHide(isshow);

                if (isshow) {
                    isshow = false;
                } else {
                    isshow = true;
                }
                break;
            case R.id.send:
                sendMessageCheck();
                break;
            case R.id.title_inform:
                /**进入客服页面*/
                ARouter.getInstance()
                        .build("/activity/OrderInformationActivity")
                        .withString("Name","小树林客服")
                        .withString("Dayend","")
                        .withString("Wechat","")
                        .withString("Orderid","")
                        .withString("Password","")
                        .withString("pushuid","")
                        .withString("pushwechat","")
                        .withInt("tag",0)
                        .navigation();
                break;
            default:
                break;
        }
    }

    PremissionsDialog premissionsDialog;
    private void sendMessageCheck() {
        content = et_keyword.getText().toString().trim();

        NotificationManagerCompat manager = NotificationManagerCompat.from(App.app);
        boolean isOpened = manager.areNotificationsEnabled();
        if (isOpened)
        {
            if (content.equals("")) {
                ToastUtil.showToast("不能发空消息哦");
            } else {
                sendMessage();
            }
        }else {
            premissionsDialog=new PremissionsDialog(context, "请开启接收小树林APP通知功能，不然将收不到你要的重要信息哦", "开启", new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // 根据isOpened结果，判断是否需要提醒用户跳转AppInfo页面，去打开App通知权限
                    Intent intent = new Intent();
                    intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    Uri uri = Uri.fromParts("package", App.app.getPackageName(), null);
                    intent.setData(uri);
                    startActivity(intent);
                    premissionsDialog.dismiss();
                }
            });
            premissionsDialog.show();
        }
    }

    @Override
    public void getShortCutMess(List<ShortCutBean> shortCutBeanList) {
        ShortCutKeyAdapter shortCutKeyAdapter = new ShortCutKeyAdapter(shortCutBeanList);
        viewPage.setAdapter(shortCutKeyAdapter);
        shortCutKeyAdapter.setOnItemClickLitener(new ShortCutKeyAdapter.OnItemClickLitener() {
            @Override
            public void onItemTopClick(View view, int position) {
                ShortCutBean bean = shortCutBeanList.get(position);
                content = bean.getContent1();
                sendMessage();
            }

            @Override
            public void onItemBottomClick(View view, int position) {
                ShortCutBean bean = shortCutBeanList.get(position);
                content = bean.getContent2();
                sendMessage();
            }
        });
    }

    @Override
    public ResolveBean getResolveBean() {
        return bean;
    }

    @Override
    public void getVideoUrl(String url) {
        /**
         * 免费看看
         * */
        ParsingTools tools = new ParsingTools();
        tools.SecondParseTool(ChatMessageActivity.this, url);
    }

    @Override
    public void showOldMessage(String mess) {
        /**
         * 信息过时
         * */
        ToastUtil.showToast("分享内容已过期或者修改了分享信息内容");
    }

    private void sendMessage() {
        presenter.sendInformation(content);
    }

    @Override
    public void sendSuccess() {

        JPushBean bean = new JPushBean();
        bean.setContent(content);
        JSONObject customfield = new JSONObject();
        try {
            customfield.put("nickName", "用户ID:" + messageSectionid);
            customfield.put("type", "0");
            customfield.put("icon", SharePreferenceUtil.getinstance().getMyIcon());
            customfield.put("messageSection", messageSectionid);//iOrderInformView.getpushuid()
            customfield.put("time", Utils.getTime1());
            customfield.put("chatId", Utils.getTime2());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        bean.setCustomfield(customfield.toString());
        bean.setPushtype("14");
        saveData(bean);
        et_keyword.setText("");
    }

    boolean isfrist = true;

    private void showOrHide(boolean b) {
        if (isfrist) {
            isfrist = false;
            viewPage.setVisibility(View.VISIBLE);
            img_keyword.setImageResource(R.drawable.keyboard);
        } else {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
            if (b) {
                img_keyword.setImageResource(R.drawable.keyboard);
                viewPage.setVisibility(View.VISIBLE);
            } else {
                img_keyword.setImageResource(R.drawable.more_ios);
                viewPage.setVisibility(View.GONE);
            }
        }

    }


    @Override
    public void getChatMessagedata(List<ChatMessageEntity> chatlist) {
        list.clear();
        list.addAll(chatlist);
        adapter.notifyDataSetChanged();
        chat_recycle.scrollToPosition(list.size());
    }

    @Override
    public String getmessageSectionid() {
        return messageSectionid;
    }

    /**
     * start监听光广播
     */
    private JpushPopupwindow window1;

    @Override
    protected void onDestroy() {

        LocalBroadcastManager.getInstance(this).unregisterReceiver(mMessageReceiver);
        super.onDestroy();
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
                        Log.e(TAG, "========onReceive: 这里没有插入数据而是去刷新数据了");
                        presenter.chatMessagedata(context, messageSectionid);
                    }
                }
            } catch (Exception e) {
            }
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
                                                /**刷新*/
                                                presenter.chatMessagedata(context, messageSectionid);
                                            } else {
                                                ToastUtil.showToast("插入--聊天列表--失败");
                                            }
                                        }
                                    }, new Consumer<Throwable>() {
                                        @Override
                                        public void accept(Throwable throwable) throws Exception {

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
        window1 = new JpushPopupwindow(ChatMessageActivity.this, ALERT, content);
        window1.showAtLocation(findViewById(R.id.ll_chatMessage),
                Gravity.CENTER | Gravity.TOP,
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 0, context.getResources().getDisplayMetrics()),
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, -20, context.getResources().getDisplayMetrics()));
        window1.showAsDropDown(findViewById(R.id.cnl),
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 0, context.getResources().getDisplayMetrics()),
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, -20, context.getResources().getDisplayMetrics()),
                Gravity.CENTER | Gravity.TOP);
        window1.update();

    }

    /**
     * end
     */

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
    /**/
}
