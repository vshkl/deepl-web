package com.vshkl.deeplweb

import android.annotation.SuppressLint
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class WebViewActivity : AppCompatActivity(R.layout.activity_web_view) {

    companion object {
        private val JS_COPY_TRANSLATION = """
            document.getElementsByClassName("lmt__translations_as_text__text_btn")[0].innerText
        """.trimIndent()
    }

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
        when (item.itemId) {
            R.id.action_open_in_browser ->
                Intent(Intent.ACTION_VIEW, Uri.parse(wvMain.url)).run(::startActivity)
            R.id.action_copy_translation_to_clipboard ->
                copyTranslationToClipboard()
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

    private fun copyTranslationToClipboard() {
        wvMain.evaluateJavascript(JS_COPY_TRANSLATION) { result: String? ->
            val translation = result?.removeSurrounding("\"")

            if (translation?.isNotEmpty() == true) {
                val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                clipboard.setPrimaryClip(ClipData.newPlainText("", translation))
                Toast.makeText(this, R.string.copy_translation_message_copied, Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, R.string.copy_translation_message_not_copied, Toast.LENGTH_SHORT).show()
            }
        }
    }

}
