package com.loocool2.rssreader.data.remote

import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import tw.ktrssreader.kotlin.model.channel.RssStandardChannel
import tw.ktrssreader.kotlin.parser.RssStandardParser

class RssClient(private val httpClient: HttpClient) {

    suspend fun getArticles(
        urlString: String
    ): RssStandardChannel {
        val response = httpClient.get(urlString).bodyAsText()
        return RssStandardParser().parse(response)
    }
}