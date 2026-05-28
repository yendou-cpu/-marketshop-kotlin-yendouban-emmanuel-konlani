package com.example.projetgestion1.data.remote.models

import android.media.Image

data class ProduitsResquest (
    val id: Int,
    val title: String,
    val price: Float,
    val description: String,
    val category: String,
    val image: Image
)