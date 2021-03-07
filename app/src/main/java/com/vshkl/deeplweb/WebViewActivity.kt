package com.vshkl.deeplweb

import android.annotation.SuppressLint
import android.os.Bundle
import android.webkit.WebView
import androidx.appcompat.app.AppCompatActivity

class WebViewActivity : AppCompatActivity(R.layout.activity_web_view) {

    private lateinit var wvMain: WebView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        wvMain = findViewById(R.id.wv_main)

        setupActionBar()
        setupWebView()
    }

    private fun setupActionBar() {
        supportActionBar?.title = getString(R.string.app_name_short)
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun setupWebView() {
        wvMain.loadUrl(getString(R.string.url_main))
        wvMain.settings.apply {
            javaScriptEnabled = true
            domStorageEnabled = true
        }
    }

}
