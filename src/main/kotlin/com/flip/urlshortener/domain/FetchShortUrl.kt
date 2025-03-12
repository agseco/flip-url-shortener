package com.flip.urlshortener.domain

class FetchShortUrl(
    private val repository: UrlsRepository
) {
    operator fun invoke(shortId: ShortUrl.ShortId) = repository.find(shortId)
}
