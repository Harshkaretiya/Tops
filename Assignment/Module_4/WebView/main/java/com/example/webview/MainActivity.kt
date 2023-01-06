package com.example.webview

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Button
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    lateinit var webView : WebView
    lateinit var forward : Button
    lateinit var back : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        webView = findViewById(R.id.webview)
        forward = findViewById(R.id.forwardButton)
        back = findViewById(R.id.backButton)

        webView.webViewClient = WebViewClient()

        webView.loadUrl("https://www.google.com")

        webView.settings.javaScriptEnabled = true

        if(isNetworkAvailable(this))
        {
            Toast.makeText(this, "connected to internet", Toast.LENGTH_SHORT).show()
        }
        else
        {
            Toast.makeText(this, "not connected to internet", Toast.LENGTH_SHORT).show()
        }

        back.setOnClickListener {
            if (webView.canGoBack())
                webView.goBack()
        }
        forward.setOnClickListener {
            if(webView.canGoForward())
                webView.goForward()
        }

    }

    fun isNetworkAvailable(context: Context): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        var activeNetworkInfo: NetworkInfo? = null
        activeNetworkInfo = cm.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting
    }
    

}