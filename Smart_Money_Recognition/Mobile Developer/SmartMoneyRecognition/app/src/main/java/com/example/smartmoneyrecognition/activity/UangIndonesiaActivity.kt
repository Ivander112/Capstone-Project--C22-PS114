package com.example.smartmoneyrecognition.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import com.example.smartmoneyrecognition.R
import com.example.smartmoneyrecognition.databinding.ActivityUangIndonesiaBinding

class UangIndonesiaActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUangIndonesiaBinding
//    private lateinit var viewModel: JenisUangIndonesiaViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUangIndonesiaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.title = ""

        supportActionBar?.setDisplayShowTitleEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val webView = findViewById<WebView>(R.id.webview)
        webView.webViewClient = WebViewClient()
        webView.loadUrl("https://smr-extrav2-b7waoljlma-et.a.run.app/Extra3")
        val webSettings = webView.settings
        webSettings.javaScriptEnabled = true

//        viewModel = ViewModelProvider(this).get(JenisUangIndonesiaViewModel::class.java)
//
//        val getList = viewModel.getRvLists()
//
//        val rvAdapter = RvUangIndonesiaAdapter(getList)
//        binding.rvJenisUangIndonesia.adapter = rvAdapter
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}