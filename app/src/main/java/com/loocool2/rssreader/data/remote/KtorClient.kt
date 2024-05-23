package com.loocool2.rssreader.data.remote

import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO

val ktorClient = HttpClient(CIO)
