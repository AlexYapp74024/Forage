package com.example.forage.core.image_processing

interface ImageRepository {
    suspend fun saveImage(image: Image): Boolean
    suspend fun loadImage(name: String): Image
    suspend fun loadAllImage(): List<Image>
}