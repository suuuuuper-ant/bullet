package com.digin.bullet.company.model.http.response

import com.digin.bullet.company.model.dto.CompanyDTO
import com.digin.bullet.consensus.model.dto.ConsensusDTO
import com.digin.bullet.news.model.dto.NewsDTO

data class CompanyDetailResponse(
    val company: CompanyDTO,
    val news: List<NewsDTO>,
    val consensus: List<ConsensusDTO>
)
