package com.example.smartmoneyrecognition.model

import androidx.lifecycle.ViewModel
import com.example.smartmoneyrecognition.R
import com.example.smartmoneyrecognition.data_class.rvData

class HomeViewModel: ViewModel() {
    fun getRvImages() : ArrayList<Int>{
        val rvImageLists = ArrayList<Int>()
        rvImageLists.add(R.drawable.jenis_jenis_uang)
        rvImageLists.add(R.drawable.mata_uang_dunia)
        rvImageLists.add(R.drawable.uang_palsu)
        return rvImageLists
    }

    fun getRvTitles() : ArrayList<String>{
        val rvTitleLists = ArrayList<String>()
        rvTitleLists.add("Jenis - Jenis Uang di Indonesia")
        rvTitleLists.add("Mata Uang di Dunia")
        rvTitleLists.add("Tips Anti Uang Palsu")
        return rvTitleLists
    }

    fun getRvLists(): ArrayList<rvData>{
        val rvImages = getRvImages()
        val rvTitles = getRvTitles()
        val rvLists = ArrayList<rvData>()
        for(position in 0..rvImages.size-1){
            val rvData = rvData(rvImages.get(position),rvTitles.get(position))
            rvLists.add(rvData)
        }
        return rvLists
    }
}