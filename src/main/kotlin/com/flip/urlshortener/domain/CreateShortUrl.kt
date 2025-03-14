package com.flip.urlshortener.domain

import org.slf4j.LoggerFactory

private val logger = LoggerFactory.getLogger(CreateShortUrl::class.java)

class CreateShortUrl(
    private val repository: UrlsRepository
) {
    operator fun invoke(originalUrl: ShortUrl.OriginalUrl) = ShortUrl(
        id = repository.nextId(),
        originalUrl = originalUrl
    )
        .also { repository.save(it) }
        .also { logger.info("Creating short URL for <${originalUrl.value}>") }
}
