package com.example.mirim.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapView;

public class Town extends AppCompatActivity {

    private WebView webview;
    private WebSettings mWebSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_town);

        webview = (WebView)findViewById(R.id.webview);

        webview.setWebViewClient(new WebViewClient());
        mWebSettings = webview.getSettings();
        mWebSettings.setJavaScriptEnabled(true);

        webview.loadUrl("http://yettsljh2001.dothome.co.kr/alyoumap/subway.html");


    }
}
