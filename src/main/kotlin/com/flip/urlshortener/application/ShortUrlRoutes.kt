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
            }

            val found = fetchShortUrl(ShortUrl.ShortId(shortUrl!!))
            if (found == null) {
                call.respond(HttpStatusCode.NotFound)
            }

            call.respond(HttpStatusCode.OK, OriginalUrlResponse(url = found!!.originalUrl.value))
        }
        post("/") {
            val request = call.receive<CreateShortUrlRequest>()
            val shortUrl = createShortUrl(
                originalUrl = OriginalUrl(request.url)
            )
            call.respond(
                HttpStatusCode.OK,
                ShortUrlResponse(
                    shortUrl = shortUrl.shortUrl()
                )
            )
        }
    }
}

data class OriginalUrlResponse(
    val url: URL
)

data class CreateShortUrlRequest(
    val url: URL
)

data class ShortUrlResponse(
    val shortUrl: URL
)
