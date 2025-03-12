package com.flip.urlshortener.infrastructure

import com.flip.urlshortener.domain.ShortUrl
import com.flip.urlshortener.domain.UrlsRepository
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.atomic.AtomicLong

class InMemoryUrlsRepositoryAdapter : UrlsRepository {
    private val counter = AtomicLong(100_000) // Specific initial value for "prettier" short URLs
    private val urls = ConcurrentHashMap<ShortUrl.Id, ShortUrl>()

    override fun nextId(): ShortUrl.Id = ShortUrl.Id(counter.incrementAndGet())

    override fun find(shortId: ShortUrl.ShortId): ShortUrl? = urls.values.find { it.shortId == shortId }

    override fun save(shortUrl: ShortUrl) {
        urls[shortUrl.id] = shortUrl
    }
}
