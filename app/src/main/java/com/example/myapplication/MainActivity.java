package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.content.Intent;

public class MainActivity extends AppCompatActivity {

    private WebView mWebView;
    private WebViewClient client = new WebViewClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        Intent intent = getIntent();
        String action = intent.getAction();
        String type = intent.getType();
        Log.d("tag", "===============");
        if (action == null) {
            Log.d("action", "null");
        } else {
            Log.d("action", action);
        }
        if (type == null) {
            Log.d("type", "null");
        } else {
            Log.d("type", type);
        }

        mWebView = findViewById(R.id.my_web_view);
        mWebView.setWebViewClient(client);
        mWebView.getSettings().setJavaScriptEnabled(true);

        if (!handleIntent(intent)) {
            mWebView.loadUrl("https://kanshahunter.com");
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        String action = intent.getAction();
        String type = intent.getType();
        Log.d("tag", "---------------------------------------------------");
        if (action == null) {
            Log.d("action", "null");
        } else {
            Log.d("action", action);
        }
        if (type == null) {
            Log.d("type", "null");
        } else {
            Log.d("type", type);
        }
        handleIntent(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private boolean handleIntent(Intent intent) {
        // Get intent, action and MIME type
        String action = intent.getAction();
        String type = intent.getType();
        if (Intent.ACTION_SEND.equals(action) && type != null && "text/plain".equals(type)) {
            return handleSendTextIntent(intent); // Handle text being sent
        } else {
            return false;
        }
    }

    private boolean handleSendTextIntent(Intent intent) {
        mWebView.loadUrl("https://kanshahunter.com/posts/new/general");

        String sharedText = intent.getStringExtra(Intent.EXTRA_TEXT);

        if (sharedText != null) {
            Log.d("data", sharedText);
            final String script = "javascript:$('#post_url').val('" + sharedText + "')";
            Log.d("data", script);
            mWebView.setWebViewClient(new WebViewClient() {
                                          @Override
                                          public void onPageFinished(WebView view, String url) {
                                              mWebView.loadUrl(script);
                                          }
                                      });
            // Update UI to reflect text being shared
        }
        return true;
    }
}