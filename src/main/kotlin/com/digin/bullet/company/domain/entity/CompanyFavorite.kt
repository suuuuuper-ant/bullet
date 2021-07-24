package com.digin.bullet.company.domain.entity

import org.joda.time.LocalDateTime
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.Id
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table


@Table("company_favorite")
class CompanyFavorite(
    @Id
    var id: Long? = null,

    @Column("company_id")
    val companyId: Long,

    @Column("account_id")
    val accountId: Long,

    @Column("is_deleted")
    var isDeleted: Boolean,

    @CreatedDate
    val createdAt: LocalDateTime = LocalDateTime.now(),
    @LastModifiedDate
    val updatedAt: LocalDateTime = LocalDateTime.now()
) {

    override fun toString(): String {
        return "CompanyFavorite(id=$id, companyId=$companyId, accountId=$accountId, isDeleted=$isDeleted, createdAt=$createdAt, updatedAt=$updatedAt)"
    }
}