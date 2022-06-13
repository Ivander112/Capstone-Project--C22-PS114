package com.example.smartmoneyrecognition.activity

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.smartmoneyrecognition.R
import com.example.smartmoneyrecognition.databinding.ActivityAnalisisBinding
import com.example.smartmoneyrecognition.model.resultsModel
import java.lang.Exception
import java.net.InetAddress
import kotlin.properties.Delegates

class AnalisisActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAnalisisBinding
    private var idx by Delegates.notNull<Int>()
    private var prob by Delegates.notNull<Float>()
    private var label by Delegates.notNull<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAnalisisBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.title = ""

        supportActionBar?.setDisplayShowTitleEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val result = intent.getSerializableExtra("result") as resultsModel
        idx = result.index
        prob = result.prob
        label = result.label

        binding.ivAnalisis.setImageBitmap(MainActivity.resultImage.get(0))

        if(idx==0 || idx==2){
            binding.tvHasilAnalisis.setBackgroundResource(R.drawable.rectangle_red)
            binding.tvHasilAnalisis.setTextColor(Color.RED)
            binding.tvHasilAnalisis.text = "UANG PALSU"
        }

        val detail = """<br>
            <b>Uang : </b> <span>$label</span><br><br>
            <b>Probability : </b> <span>${String.format("%1.4f",prob*100)}%</span><br><br>
            <p>Hasil yang didapatkan dari aplikasi ini tidak sepenuhnya benar.
            Anda dapat mengecek apakah uang ini palsu atau tidak secara manual.
            Anda bisa melihat tips mengecek uang palsu atau tidak pada fitur aplikasi ini</p>
        """.trimIndent()

        binding.tvDetailAnalisis.setHtml(detail)

        binding.tvAnalisisLagi.setOnClickListener {
            finish()
        }

    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}