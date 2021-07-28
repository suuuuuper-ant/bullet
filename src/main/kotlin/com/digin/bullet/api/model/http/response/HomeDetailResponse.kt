package com.digin.bullet.api.model.http.response

import com.digin.bullet.company.model.dto.CompanyAnnualDTO
import com.digin.bullet.company.model.dto.CompanyDTO
import com.digin.bullet.company.model.dto.CompanyQuarterDTO
import com.digin.bullet.consensus.model.dto.ConsensusDTO
import com.digin.bullet.market.model.dto.MarketStackDTO
import com.digin.bullet.news.model.dto.NewsDTO

data class HomeDetailResponse(
    val company: CompanyDTO,
    val consensusList: List<ConsensusDTO>,
    val newsList: List<NewsDTO>,
    val stacks: List<MarketStackDTO>,
    val annuals: List<CompanyAnnualDTO>,
    val quarters: List<CompanyQuarterDTO>
)
