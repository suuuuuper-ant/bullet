package com.digin.bullet.api.model.http.response

import com.digin.bullet.company.model.dto.CompanyDTO
import com.digin.bullet.consensus.model.dto.ConsensusDTO
import com.digin.bullet.news.model.dto.NewsDTO

data class HomeResponse(
    val groups: List<Group>
)


data class Group(
    val groupId: String,
    val contents: List<GroupContent>
)


data class GroupContent(
    val company: CompanyDTO,
    val consensus: ConsensusDTO,
    val newsList: List<NewsDTO>
)
