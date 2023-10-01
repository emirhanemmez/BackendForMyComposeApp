package com.mycompose.plugins

import com.mycompose.Response
import com.mycompose.Status
import com.mycompose.list.GetListResponse
import com.mycompose.list.ListErrorCode
import com.mycompose.listData
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.application.call
import io.ktor.server.response.respond
import io.ktor.server.routing.get
import io.ktor.server.routing.routing

fun Application.configureRouting() {
    routing {
        get("") {
            call.respond("Hello")
        }
        get("/list") {
            val pageNumberParameter = call.request.queryParameters["pageNumber"]?.toIntOrNull()
            val searchParameter = call.request.queryParameters["search"]
            if (pageNumberParameter != null) {
                if (searchParameter != null) {
                    val searchData = listData.filter { it.name.contains(searchParameter) }
                    val nextPageAvailable = searchData.size > (pageNumberParameter + 1) * PAGE_SIZE
                    val list = if (searchData.isNotEmpty())
                        searchData.subList(pageNumberParameter * PAGE_SIZE, (pageNumberParameter + 1) * PAGE_SIZE)
                    else
                        listOf()

                    Thread.sleep(2000)
                    call.respond(
                        HttpStatusCode.OK,
                        Response<GetListResponse, ListErrorCode>(
                            status = Status.SUCCESS,
                            result = GetListResponse(
                                currentPage = pageNumberParameter,
                                nextPageAvailable = nextPageAvailable,
                                list = list
                            ),
                            errorCode = null
                        )
                    )
                } else {
                    val nextPageAvailable = listData.size > (pageNumberParameter + 1) * PAGE_SIZE
                    val list = if (listData.isNotEmpty()) listData.subList(
                        pageNumberParameter * PAGE_SIZE,
                        (pageNumberParameter + 1) * PAGE_SIZE
                    ) else
                        listOf()

                    Thread.sleep(2000)
                    call.respond(
                        HttpStatusCode.OK,
                        Response<GetListResponse, ListErrorCode>(
                            status = Status.SUCCESS,
                            result = GetListResponse(
                                currentPage = pageNumberParameter,
                                nextPageAvailable = nextPageAvailable,
                                list = list
                            ),
                            errorCode = null
                        )
                    )
                }
            } else {
                call.respond(
                    HttpStatusCode.OK,
                    Response<GetListResponse, ListErrorCode>(
                        status = Status.FAIL,
                        result = null,
                        errorCode = ListErrorCode.MISSING_PAGE_NUMBER
                    )
                )
            }
        }
    }
}

const val PAGE_SIZE = 10
