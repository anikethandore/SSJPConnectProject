package com.anikethandore.ssjpconnect.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.anikethandore.ssjpconnect.R;

import java.net.URLEncoder;

public class pdfViewActivity extends AppCompatActivity {
    WebView pdfView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf_view);

        pdfView=(WebView)findViewById(R.id.viewpdf);

        String filetitle=getIntent().getStringExtra("fileTitle");
        String fileurl=getIntent().getStringExtra("fileUrl");

        ProgressDialog pd=new ProgressDialog(this);
        pd.setTitle(filetitle);
        pd.setMessage("Loading File....");

        pdfView.getSettings().setBuiltInZoomControls(true);
        pdfView.getSettings().setJavaScriptEnabled(true);

        pdfView.setWebViewClient(new WebViewClient(){

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                pd.show();
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                pd.dismiss();
            }
        });

        String url="";
        try {
            url= URLEncoder.encode(fileurl,"UTF-8");

        }catch (Exception e){ }

        pdfView.loadUrl("http://docs.google.com/gview?embedded=true&url="+url);
    }



}