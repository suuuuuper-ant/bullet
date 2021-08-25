package com.digin.bullet.news.model.dto

import java.time.LocalDateTime

val images = listOf(
    "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRcDHniUCDahDMZ4a7H9z6VYKssNBJl50hC5w&usqp=CAU",
    "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSn4F1hqZOf9dOdI2Qk--n877d-krSPSueIyA&usqp=CAU",
    "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcR8fpC2xiz1_xdLaxtmE7vbeon79r4AmoF9FA&usqp=CAU",
    "https://cdn.pixabay.com/photo/2012/03/04/00/43/architecture-22039__480.jpg",
    "https://cdn.pixabay.com/photo/2015/07/17/22/42/startup-849804__480.jpg"
)

data class NewsDTO(
    val id: Long,
    val stockCode: String,
    val title: String,
    val link: String,
    val description: String,
    val createdAt: LocalDateTime,
//    val updatedAt: LocalDateTime,
    val imageUrl: String = images.random()
)
