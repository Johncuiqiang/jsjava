package com.ling.javajs.model;

import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

/**
 * Created by cuiqiang on 2017/2/23.
 */

public class JSModel {

    private Context mContext;
    public JSModel(Context mContext){
        this.mContext = mContext;
    }

    @JavascriptInterface
    public void openWifiFunc(){
        Intent intent = new Intent(Settings.ACTION_WIFI_SETTINGS);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(intent);
    }

    @JavascriptInterface
    public String getMsgByAndroid(){
        return "from android native";
    }

    @JavascriptInterface
    public void showToast(String message){
        Toast.makeText(mContext,message,Toast.LENGTH_SHORT).show();
    }

    @JavascriptInterface
    public void test(){
        Toast.makeText(mContext,"helloJava",Toast.LENGTH_SHORT).show();
    }

}
