package com.gamal.slingshotflight;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.webkit.WebSettings;
import android.graphics.Color;
public class MainActivity extends Activity {
  private WebView web;
  @Override public void onCreate(Bundle state){super.onCreate(state);
    getWindow().setStatusBarColor(Color.rgb(16,21,42));getWindow().setNavigationBarColor(Color.rgb(16,21,42));
    web=new WebView(this);web.setBackgroundColor(Color.rgb(16,21,42));
    WebSettings s=web.getSettings();s.setJavaScriptEnabled(true);s.setDomStorageEnabled(true);s.setTextZoom(100);
    s.setMixedContentMode(WebSettings.MIXED_CONTENT_NEVER_ALLOW);
    web.setWebViewClient(new WebViewClient(){@Override public boolean shouldOverrideUrlLoading(WebView view,String url){return !url.startsWith("https://slingshot-flight.gamal-hametzaits.workers.dev/");}});
    web.setOverScrollMode(View.OVER_SCROLL_NEVER);setContentView(web);
    if(state!=null)web.restoreState(state);else web.loadUrl("https://slingshot-flight.gamal-hametzaits.workers.dev/");
  }
  @Override protected void onSaveInstanceState(Bundle out){super.onSaveInstanceState(out);web.saveState(out);}
  @Override public void onBackPressed(){if(web.canGoBack())web.goBack();else super.onBackPressed();}
  @Override protected void onDestroy(){web.destroy();super.onDestroy();}
}
