package com.flip.urlshortener.domain

interface UrlsRepository {
    fun nextId(): ShortUrl.Id
    fun find(shortId: ShortUrl.ShortId): ShortUrl?
    fun save(shortUrl: ShortUrl)
}
