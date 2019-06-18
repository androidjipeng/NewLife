package com.xiaoshulin.vipbanlv.wxapi;

import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import com.xiaoshulin.vipbanlv.utils.Constants;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;


public class WXEntryActivity extends Activity implements IWXAPIEventHandler{

	private static final String TAG = "WXEntryActivity";

    private IWXAPI api;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    	api = WXAPIFactory.createWXAPI(this, Constants.APP_ID);
		api.registerApp(Constants.APP_ID);

        try {
        	api.handleIntent(getIntent(), this);
        } catch (Exception e) {
        	e.printStackTrace();
        }
    }

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		setIntent(intent);
        api.handleIntent(intent, this);
	}

	@Override
	public void onReq(BaseReq req) {
		switch (req.getType()) {
		case ConstantsAPI.COMMAND_GETMESSAGE_FROM_WX:
			Log.e(TAG, "onReq:   ConstantsAPI.COMMAND_GETMESSAGE_FROM_WX:"+ConstantsAPI.COMMAND_GETMESSAGE_FROM_WX);
			break;
		case ConstantsAPI.COMMAND_SHOWMESSAGE_FROM_WX:
			Log.e(TAG, "onReq:   ConstantsAPI.COMMAND_SHOWMESSAGE_FROM_WX:"+ConstantsAPI.COMMAND_SHOWMESSAGE_FROM_WX);
			break;
		default:
			break;
		}
	}

	@Override
	public void onResp(BaseResp resp) {
		int result = 0;
		
//		Toast.makeText(this, "baseresp.getType = " + resp.getType(), Toast.LENGTH_SHORT).show();

		switch (resp.errCode) {
		case BaseResp.ErrCode.ERR_OK:
			result =1;
			Log.e(TAG, "onResp:   result:"+result );
			break;
		case BaseResp.ErrCode.ERR_USER_CANCEL:

			result = 2;
			Log.e(TAG, "onResp:   result:"+result );
			break;
		case BaseResp.ErrCode.ERR_AUTH_DENIED:
			result =3;
			Log.e(TAG, "onResp:   result:"+result );
			break;
		case BaseResp.ErrCode.ERR_UNSUPPORT:
			result = 4;
			Log.e(TAG, "onResp:   result:"+result );
			break;
		default:
			result = 5;
			Log.e(TAG, "onResp:   result:"+result );
			break;
		}
		
//		Toast.makeText(this, result, Toast.LENGTH_LONG).show();
        finish();
	}
	

}