package com.xiaoshulin.vipbanlv.presenter;

import com.xiaoshulin.vipbanlv.base.BasePresenter;
import com.xiaoshulin.vipbanlv.view.IShareWeChatView;

/**
 * ShareWechatPresenter
 *
 * @author jipeng
 * @date 2018/7/31
 */
public class ShareWechatPresenter extends BasePresenter {
    IShareWeChatView shareWeChatView;

    public ShareWechatPresenter(IShareWeChatView shareWeChatView) {
        this.shareWeChatView = shareWeChatView;
    }
}
