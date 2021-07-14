package com.digin.bullet.company.domain.entity

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table

@Table("company")
class Company(
    @Id
    val id: Long? = null,
    val stockCode: String,
    val shortName: String,
    val krName: String,
    val enName: String,
    val marketType: String,
    var likeCount: Int,
    @Column("is_kospi_200")
    val isKospi200: Boolean,
    val total: Int?,
    var searchCount: Int,
    var imageUrl: String?,
    val category: String?
) {

}