package com.synchro.synchro_prototype01;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

/*  NOTE: since LoginPage is designed to appear only when user first uses app and needs authentication
    there shouldn't be any direct link to it, but for easy access, temporary link made on SearchPage(which is
    homepage)

    NOTE: to access LoginPage, need to re-install app to clear SharedPrefs so it doesn't auto-redirect to landing page

    NOTE: write code to handle connection issue for the ion load url

    NOTE: security issue with addJavaInterface??

* */

public class LoginPage extends AppCompatActivity {
    private Handler handlerForJavascriptInterface = new Handler();
    private String extractToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_bar_login_page);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //sends token for validation, will be null if first time using app
        StoreUrl urls = new StoreUrl(LoginPage.this);
        //for debug
        //Toast.makeText(LoginPage.this, urls.getValidateUrl(), Toast.LENGTH_LONG).show();
        Ion.with(LoginPage.this)
                .load(urls.getValidateUrl())
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    /*  checks returned output for success parameter true or false and redirects accordingly
                    * */
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        if (result.get("Success").toString().equals("true")) {
                            //checks if returned token is a newly generated one for replacement
                            //=_= apparently the returned token is within "" so compare properly!
                            StoreData data = new StoreData(LoginPage.this);
                            if (!result.get("Token").toString().equals("\"" + data.getAuthToken() + "\"")) {
                                data.setAuthToken(result.get("Token").toString());
                            }
                            Intent intent = new Intent(LoginPage.this, SearchPage.class);
                            startActivity(intent);
                        }
                        else {
                            loginUser();
                        }
                    }
                });
    }

    //method for login
    private void loginUser() {
        WebView webview = (WebView) findViewById(R.id.webview);

        webview.getSettings().setJavaScriptEnabled(true);
        webview.addJavascriptInterface(new MyJavaScriptInterface(this), "HtmlViewer");

        webview.loadUrl("https://ivle.nus.edu.sg/api/login/?apikey=PK3n2PGjXR4OooZPZyelQ");

        webview.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {

                //this sets webview to invisible ONLY if not the login page to IVLE
                if (!url.equals("https://ivle.nus.edu.sg/api/login/?apikey=PK3n2PGjXR4OooZPZyelQ")) {
                    view.setVisibility(View.GONE);

                    /* user validated
                        extract token from html
                        redirects to landing page (searchpage for now)
                    */
                    if (url.equals("https://ivle.nus.edu.sg/api/login/login_result.ashx?apikey=PK3n2PGjXR4OooZPZyelQ&r=0")) {
                        view.loadUrl("javascript:window.HtmlViewer.showHTML" +
                                "('&lt;html&gt;'+document.getElementsByTagName('html')[0].innerHTML+'&lt;/html&gt;');");
                        Intent intent = new Intent(LoginPage.this, SearchPage.class);
                        startActivity(intent);
                    }
                    else {  //in case anything else happens
                        Toast.makeText(LoginPage.this, "Error", Toast.LENGTH_LONG).show();
                    }
                }
                //for debug
                //Toast.makeText(LoginPage.this, view.getUrl(), Toast.LENGTH_LONG).show();
            }

            //if there's connection issue or some other error
            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                view.setVisibility(View.GONE);
                Toast.makeText(LoginPage.this, "Internetttttt", Toast.LENGTH_LONG).show();
            }

        });
    }


    //for extracting html source code and saving token into extractToken
    class MyJavaScriptInterface
    {
        private Context ctx;

        MyJavaScriptInterface(Context ctx)
        {
            this.ctx = ctx;
        }

        @JavascriptInterface
        public void showHTML(final String html)
        {
            //code to use html content here
            handlerForJavascriptInterface.post(new Runnable() {
                @Override
                public void run()
                {
                    String[] splitTokens = html.split("<body>");
                    String token = splitTokens[1];
                    splitTokens = token.split("</body>");
                    extractToken = splitTokens[0];

                    StoreData data = new StoreData(LoginPage.this);
                    data.setAuthToken(extractToken);

                    //for debug
                    //System.out.println(extractToken);
                    //Toast.makeText(LoginPage.this, data.getAuthToken(), Toast.LENGTH_LONG).show();
                }});
        }
    }


}
