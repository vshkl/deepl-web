package com.vshkl.deeplweb

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity

class WebViewActivity : AppCompatActivity(R.layout.activity_web_view) {

    private lateinit var wvMain: WebView
    private lateinit var pbLoading: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        wvMain = findViewById(R.id.wv_main)
        pbLoading = findViewById(R.id.pb_loading)

        setupActionBar()
        setupWebView()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_open_in_browser) {
            Intent(Intent.ACTION_VIEW, Uri.parse(wvMain.url)).run(::startActivity)
        }
        return super.onOptionsItemSelected(item)
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
        wvMain.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView, url: String) {
                pbLoading.visibility = View.GONE
            }
        }
    }

}
