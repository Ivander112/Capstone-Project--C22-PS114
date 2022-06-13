package com.example.smartmoneyrecognition.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import com.example.smartmoneyrecognition.R
import com.example.smartmoneyrecognition.databinding.ActivityUangDuniaBinding

class UangDuniaActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUangDuniaBinding
//    private lateinit var viewModel: MataUangDuniaViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUangDuniaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.title = ""

        supportActionBar?.setDisplayShowTitleEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val webView = findViewById<WebView>(R.id.webview)
        webView.webViewClient = WebViewClient()
        webView.loadUrl("https://smr-extrav2-b7waoljlma-et.a.run.app")
        val webSettings = webView.settings
        webSettings.javaScriptEnabled = true

//        viewModel = ViewModelProvider(this).get(MataUangDuniaViewModel::class.java)
//
//        val getList = viewModel.getRvMataUangLists()
//
//        val rvAdapter = RvMataUangAdapter(getList)
//        binding.rvMataUangDunia.adapter = rvAdapter
    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}