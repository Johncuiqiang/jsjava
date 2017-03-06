package com.ling.javajs;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.Toast;

import com.ling.javajs.model.JSModel;

import java.util.Random;

public class WebViewActivity extends AppCompatActivity {

    private WebView webContent;
    private Button btnAdd;
    private Button btnPlus;
    private int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        initView();
        initData();
    }

    private void initView() {
        webContent = (WebView) findViewById(R.id.web_content);
        btnAdd = (Button) findViewById(R.id.btn_add);
        btnPlus = (Button) findViewById(R.id.btn_plus);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count++;
                String addMessage = "android native add p:"+count;
                //传递参数时必须使用转义双引号
                webContent.loadUrl("javascript:callJs(\""+ addMessage +"\")");
            }
        });

        btnPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int red = new Random().nextInt()%255;
                int green = new Random().nextInt()%255;
                int blue = new Random().nextInt()%255;
                String color = "rgb("+red+","+green+","+blue+")";
                webContent.loadUrl("javascript:changeBg(\""+color+"\")");
            }
        });

    }

    @SuppressLint({"JavascriptInterface", "SetJavaScriptEnabled"})
    private void initData() {
        btnAdd.setText("新增<p>");
        btnPlus.setText("改变背景色");
        if (webContent == null){
            return;
        }
        String filePath = "file:///android_asset/index.html";
        webContent.getSettings().setDefaultTextEncodingName("utf-8");
        //开启android webView 调用JS开关
        webContent.getSettings().setJavaScriptEnabled(true);
        webContent.loadUrl(filePath);
        //开启html alert,如需自定义alert显示样式需重载onJsAlert方法
        webContent.setWebChromeClient(new WebChromeClient(){
            @Override
            public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
                new AlertDialog.Builder(WebViewActivity.this)
                        .setTitle("Warning")
                        .setMessage(message)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Toast.makeText(WebViewActivity.this,"点击确认按钮",Toast.LENGTH_SHORT).show();
                            }
                        })
                        .show();
                result.confirm();//如果不调用无法再次响应JS调用
                //如果返回false则会同时弹出自定义Alert和原生Alert
                return true;
            }
        });
        //定义与HTML5交互通道名与对象
       webContent.addJavascriptInterface(new JSModel(getBaseContext()),"WithJS");

    }

}
