package com.flip.urlshortener.domain

import org.slf4j.LoggerFactory

private val logger = LoggerFactory.getLogger(FetchShortUrl::class.java)

class FetchShortUrl(
    private val repository: UrlsRepository
) {
    operator fun invoke(shortId: ShortUrl.ShortId) = repository.find(shortId)
        .also { logger.info("Fetching short URL <${shortId.value}>") }
}
