package com.example.smartmoneyrecognition.fragments

import android.app.Activity
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.example.smartmoneyrecognition.*
import com.example.smartmoneyrecognition.activity.AnalisisActivity
import com.example.smartmoneyrecognition.activity.CameraActivity
import com.example.smartmoneyrecognition.activity.MainActivity
import com.example.smartmoneyrecognition.api.ApiConfig
import com.example.smartmoneyrecognition.databinding.FragmentCameraBinding
import com.example.smartmoneyrecognition.model.resultsModel
import com.example.smartmoneyrecognition.responses.ApiResponse
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class CameraFragment : Fragment() {
    private var _binding: FragmentCameraBinding?= null
    private val  binding get() = _binding!!
    private var getFile: File? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCameraBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val content = """
             <p>Berikut merupakan tata cara untuk menganalasis uang anda apakah palsu atau tidak menggunakan fitur ini.</p>
             <p>Mengambil gambar uang menggunakan kamera :</p>
             <ol>
                <li>Tekan tombol Ambil Foto dibawah ini.</li>
                <li>Posisikan uang anda di sumber cahaya</li>
                <li>Kemudian capture gambar</li>
                <li>Tekan tombol analisis untuk menganalisis uang anda</li>
             </ol>
             <p>Mengambil gambar uang menggunakan galeri :</p>
             <ol>
                <li>Tekan tombol Galeri dibawah ini.</li>
                <li>Pilih gambar uang yang tersimpan di galeri anda (Gambar harus berukuran 1:1).</li>
                <li>Tekan tombol analisis untuk menganalisis uang anda</li>
             </ol>
        """.trimIndent()
        binding.procedure.setHtml(content)

        binding.analyze.setOnClickListener{
            if(MainActivity.resultImage.get(0)!=null){
                showLoading(true)
                uploadImage()
            }else{
                Toast.makeText(requireActivity(),"Insert dulu gambar", Toast.LENGTH_SHORT).show()
            }
        }
        binding.takePicture.setOnClickListener{
            startCameraX()
        }

        binding.gallery.setOnClickListener{
            startGallery()
        }

    }

    private fun startCameraX() {
        val intent = Intent(requireContext(), CameraActivity::class.java)
        intent.putExtra("step","cameraStep3")
        launcherIntentCameraX.launch(intent)
    }

    private fun startGallery() {
        val intent = Intent()
        intent.action = Intent.ACTION_GET_CONTENT
        intent.type = "image/*"
        val chooser = Intent.createChooser(intent, "Choose a Picture")
        launcherIntentGallery.launch(chooser)
    }

    private val launcherIntentGallery = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val selectedImg: Uri = result.data?.data as Uri
            val myFile = uriToFile(selectedImg, requireActivity())
            getFile = myFile
            val size = getIMGSize(myFile)
            val result = BitmapFactory.decodeFile(myFile.path)

            if(size.get(0) == size.get(1)){
                MainActivity.resultImage.set(0,result)
                binding.img.setImageBitmap(result)
            }else{
                Toast.makeText(requireActivity(),"Gambar harus berukuran 1:1", Toast.LENGTH_SHORT).show()
            }

        }
    }

    private val launcherIntentCameraX = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == MainActivity.CAMERA_X_RESULT) {
            val myFile = it.data?.getSerializableExtra("picture") as File
            getFile = myFile
            val result = rotateBitmap(BitmapFactory.decodeFile(myFile.path))
            MainActivity.resultImage.set(0,result)
            binding.img.setImageBitmap(result)
        }
    }

    override fun onResume() {
        super.onResume()
        if(MainActivity.resultImage.get(0)!=null){
            binding.img.setImageBitmap(MainActivity.resultImage.get(0))
        }

    }

    private fun uploadImage(){
        val results = ArrayList<Float>()
        val arrLabels = arrayOf("Rp. 100000 Palsu","Rp. 100000 Asli","Rp. 50000 Palsu","Rp. 50000 Asli")
        if(getFile != null) {
            val file = reduceFileImage(getFile as File)
            val requestImageFile = file.asRequestBody("image/jpeg".toMediaTypeOrNull())
            val imageMultipart: MultipartBody.Part = MultipartBody.Part.createFormData(
                "img",
                file.name,
                requestImageFile
            )
            val service = ApiConfig.getApiService().postImage(imageMultipart)
            service.enqueue(object : Callback<ApiResponse> {
                override fun onResponse(
                    call: Call<ApiResponse>,
                    response: Response<ApiResponse>
                ) {
                    if(response.isSuccessful){
                        val responseBody = response.body()
                        if(responseBody != null){
                            results.add(responseBody.r_0.toFloat())
                            results.add(responseBody.r_1.toFloat())
                            results.add(responseBody.r_2.toFloat())
                            results.add(responseBody.r_3.toFloat())
                            val idx = getMax(results)
                            val lastResult = resultsModel(idx,arrLabels[idx],results.get(idx))
                            val intent = Intent(requireContext(), AnalisisActivity::class.java)
                            intent.putExtra("result",lastResult)
                            showLoading(false)
                            startActivity(intent)
                        }
                    }else{
                        showLoading(false)
                        Toast.makeText(requireActivity(), response.body().toString()+" "+ response.message(), Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                    showLoading(false)
                    Toast.makeText(requireActivity(), "Failed to create Instance", Toast.LENGTH_SHORT).show()
                }

            })
        }else{
            showLoading(false)
            Toast.makeText(requireActivity(), "Insert gafal", Toast.LENGTH_SHORT).show()
        }
    }

    private fun getMax(arr: ArrayList<Float>): Int{
        var idx = 0
        var min = 0.0f

        for(i in 0..3){
            if(arr.get(i)>min){
                idx = i
                min = arr.get(i)
            }
        }
        return idx
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }
}