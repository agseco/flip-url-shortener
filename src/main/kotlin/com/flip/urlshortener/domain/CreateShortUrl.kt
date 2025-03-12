package com.flip.urlshortener.domain

class CreateShortUrl(
    private val repository: UrlsRepository
) {
    operator fun invoke(originalUrl: ShortUrl.OriginalUrl) = ShortUrl(
        id = repository.nextId(),
        originalUrl = originalUrl
    ).also { repository.save(it) }
}
