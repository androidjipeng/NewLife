package com.xiaoshulin.vipbanlv.utils;

import android.content.Context;

import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXTextObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.xiaoshulin.vipbanlv.App;

/**
 * Created by jipeng on 2018/7/21.
 */

public class ShareUtils {

    public static void send_text(Context context,String text, int type)
    {

        IWXAPI api = WXAPIFactory.createWXAPI(context, Constants.APP_ID,false);
        api.registerApp(Constants.APP_ID);

        WXTextObject textObj = new WXTextObject();
        textObj.text = text;


        WXMediaMessage msg = new WXMediaMessage();
        msg.mediaObject = textObj;
        msg.title = "小树林";
        msg.description = text;


        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("text");
        req.message = msg;
        req.scene = type;
        api.sendReq(req);
    }



    private static String buildTransaction(final String type) {
        return (type == null) ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
    }
}

