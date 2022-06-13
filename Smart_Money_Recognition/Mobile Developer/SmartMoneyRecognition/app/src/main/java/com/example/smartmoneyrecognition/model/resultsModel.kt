package com.example.smartmoneyrecognition.model

import java.io.Serializable

data class resultsModel(
    val index: Int,
    val label : String,
    val prob : Float
) : Serializable
