package co.raveblue.teacherassistant;

import android.graphics.Color;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

public class pune_university extends AppCompatActivity {

    WebView webView;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pune_university);

        webView = (WebView)findViewById(R.id.webView);

        progressBar = (ProgressBar)findViewById(R.id.progressBar);
        progressBar.setMax(100);
        progressBar.setVisibility(View.GONE);

        if (savedInstanceState != null)
        {
            webView.restoreState(savedInstanceState);
        }
        else
        {
            webView.getSettings().setJavaScriptEnabled(true);
            webView.getSettings().setSupportZoom(true);
//            webView.getSettings().getBuiltInZoomControls(false);
  //          webView.getSettings().getLoadWithOverviewMode(true);
            webView.getSettings().setUseWideViewPort(true);
            webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
            webView.setBackgroundColor(Color.WHITE);

            webView.setWebChromeClient(new WebChromeClient(){
                @Override
                public void onProgressChanged(WebView view, int progress) {
                    progressBar.setProgress(progress);

                    if (progress < 100 && progressBar.getVisibility() == progressBar.GONE)
                    {
                        progressBar.setVisibility(ProgressBar.VISIBLE);
                    }
                    if (progress == 100)
                    {
                        progressBar.setVisibility(ProgressBar.GONE);
                    }

                }
            });
        }

        webView.loadUrl("http://www.unipune.ac.in/");
        webView.setWebViewClient(new WebViewClient());

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        webView.saveState(outState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.browser_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.item_back:
                if (webView.canGoBack()){
                    webView.goBack();
                }
                return true;
            case R.id.item_forward:
                if (webView.canGoForward()){
                    webView.goForward();
                }
                return true;
            case R.id.item_home:
                webView.loadUrl("http://www.unipune.ac.in/");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }
}
