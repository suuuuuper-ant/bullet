package com.digin.bullet.api.model.http.response

import com.digin.bullet.company.model.dto.CompanyDTO
import com.digin.bullet.consensus.model.dto.ConsensusDTO
import com.digin.bullet.news.model.dto.NewsDTO
import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonInclude

//data class HomeResponse(
//    val groups: List<Group>
//)
//
//
//data class Group(
//    val section: GroupSection,
//    val header: GroupHeader,
//    val contents: List<GroupContent>
//)
//
//// TODO meta typeㅇㅣ 아닌 뷰 타입으로 변경
//enum class GroupSection {
//    COMPANY, FAVORITES
//}
//
//enum class GroupHeader {
//    SLIDE, LIST
//}
//
//enum class GroupType {
//    TYPE
//}
//
//data class GroupContent(
//    val type: GroupType,
//    val items: List<GroupItem>
//)
//
//
//data class GroupItem(
//        val company: CompanyDTO,
//        @JsonInclude(JsonInclude.Include.NON_NULL)
//        val consensusList: List<ConsensusDTO>? = null,
//        @JsonInclude(JsonInclude.Include.NON_NULL)
//        val newsList: List<NewsDTO>? = null
//)


data class HomeResponse(
        val groups: List<Group>
)


data class Group(
        val type: GroupType,
        val action: GroupAction,
        val contents: List<GroupContent>
)

enum class GroupType {
    COMPANY, FAVORITES
}

enum class GroupAction {
    SLIDE, LIST
}

data class GroupContent(
        val company: CompanyDTO,
        @JsonInclude(JsonInclude.Include.NON_NULL)
        val consensusList: List<ConsensusDTO>? = null,
        @JsonInclude(JsonInclude.Include.NON_NULL)
        val newsList: List<NewsDTO>? = null
)
