package com.example.smartmoneyrecognition.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.smartmoneyrecognition.R
import com.example.smartmoneyrecognition.databinding.FragmentHeadlineBinding


class HeadlineFragment : Fragment(), View.OnClickListener {
    private var _binding: FragmentHeadlineBinding?= null
    private val  binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHeadlineBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val content = """
            <h3>Saat Bertransaksi</h3>
        <ol>
            <li>Tolak dan jelaskan secara sopan anda meragukan keaslian uang tersebut</li>
            <li>Minta kepada pihak pemberi untuk memberikan uang lainnya sebagai pengganti uang tersebut (lakukan pengecekan ulang)</li>
            <li>Sarankan pihak pemberi untuk melakukan pengecekan uang ke bank, kepolisian, atau meminta klarifikasi langsung ke kantor Bank Indonesia terdekat.</li>
            <li>Gunakan praduga tak bersalah karena pihak pemberi mungkin adalah korban yang tidak menyadari bahwa uang tersebut adalah uang yang diragukan keasliannya.</li>
        </ol>
        <h3>Setelah Bertransaksi</h3>
        <ol>
            <li>Menjaga fisik dan tidak mengedarkan kembali uang yang diragukan keasliannya.</li>
            <li>Melaporkan temuan tersebut disertai fisik uang yang diragukan keasliannya kepada bank, kepolisian, atau meminta klarifikasi langsung ke kantor Bank Indonesia terdekat.</li>
        </ol>
        <p>Laporan masyarakat atas uang yang diragukan keasliannya kepada Bank Indonesia, baik yang disampaikan langsung atau melalui bank, akan diteliti lebih lanjut. Uang yang diragukan keasliannya dan dinyatakan tidak asli, tidak memperoleh penggantian. Sementara bagi yang dinyatakan asli, dapat memperoleh penggantian sesuai ketentuan berlaku.</p>
        """.trimIndent()
        binding.contentHeadline.setHtml(content)
        binding.telBicara.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        if(v?.id == R.id.tel_bicara){
            val dialIntent = Intent(Intent.ACTION_DIAL)
            dialIntent.data = Uri.parse("tel:" + "131")
            startActivity(dialIntent)
        }
    }
}