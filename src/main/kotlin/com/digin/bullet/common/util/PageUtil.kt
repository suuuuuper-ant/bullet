package com.digin.bullet.common.util

import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.queryParamOrNull


fun getPageRequest(serverRequest: ServerRequest): PageRequest {
    val page = serverRequest.queryParamOrNull("page")?.toInt() ?: 0
    val size = serverRequest.queryParamOrNull("size")?.toInt() ?: 5
    val sort = serverRequest.queryParamOrNull("sort") ?: "desc"
    if (sort.equals("desc")) {
        return PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "updatedAt"))
    }
    return PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "updatedAt"))
}


val defaultPageRequest = PageRequest.of(0, 5, Sort.by(Sort.Direction.DESC, "updatedAt"))


