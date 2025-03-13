package com.flip.urlshortener

import com.flip.urlshortener.application.shortUrlRoutes
import com.flip.urlshortener.domain.CreateShortUrl
import com.flip.urlshortener.domain.FetchShortUrl
import com.flip.urlshortener.infrastructure.InMemoryUrlsRepositoryAdapter
import io.ktor.serialization.jackson.*
import io.ktor.server.application.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.contentnegotiation.*

fun main(args: Array<String>): Unit = EngineMain.main(args)

@Suppress("unused")
fun Application.module() {
    install(ContentNegotiation) {
        jackson()
    }

    val urlsRepository = InMemoryUrlsRepositoryAdapter()

    shortUrlRoutes(
        fetchShortUrl = FetchShortUrl(urlsRepository),
        createShortUrl = CreateShortUrl(urlsRepository)
    )
}
