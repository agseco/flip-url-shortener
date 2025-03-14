package com.flip.urlshortener.application

import com.flip.urlshortener.domain.CreateShortUrl
import com.flip.urlshortener.domain.FetchShortUrl
import com.flip.urlshortener.domain.ShortUrl
import com.flip.urlshortener.domain.ShortUrl.OriginalUrl
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.net.URL

fun Application.shortUrlRoutes(
    fetchShortUrl: FetchShortUrl,
    createShortUrl: CreateShortUrl
) {
    routing {
        get("/{shortUrl}") {
            val shortUrl = call.parameters["shortUrl"]
            if (shortUrl == null) {
                call.respond(HttpStatusCode.BadRequest)
                return@get
            }

            val found = fetchShortUrl(ShortUrl.ShortId(shortUrl))
            if (found == null) {
                call.respond(HttpStatusCode.NotFound)
                return@get
            }

            call.respondText(
                status = HttpStatusCode.MovedPermanently,
                text = found.originalUrl.value.toString()
            )
        }
        post("/") {
            val request = call.receive<CreateShortUrlRequest>()
            val shortUrl = createShortUrl(
                originalUrl = OriginalUrl(request.url)
            )
            call.respond(
                status = HttpStatusCode.OK,
                message = CreateShortUrlResponse(
                    shortUrl = shortUrl.shortUrl()
                )
            )
        }
    }
}

data class CreateShortUrlRequest(
    val url: URL
)

data class CreateShortUrlResponse(
    val shortUrl: URL
)
