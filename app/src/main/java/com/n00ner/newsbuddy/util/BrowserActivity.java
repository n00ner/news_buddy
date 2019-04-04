package com.n00ner.newsbuddy.util;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;

import com.n00ner.newsbuddy.Constants;
import com.n00ner.newsbuddy.R;

public class BrowserActivity extends AppCompatActivity {

    @BindView(R.id.webView) WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browser);
        ButterKnife.bind(this);
        String label = getIntent().getStringExtra(Constants.BROWSER_LABEL);
        String link = getIntent().getStringExtra(Constants.BROWSER_LINK);
        setTitle(getIntent().getStringExtra(Constants.BROWSER_LABEL));
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(getIntent().getStringExtra(Constants.BROWSER_LINK));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.action_browser_refresh){
            webView.reload();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.browser_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
}
