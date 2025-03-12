package com.flip.urlshortener

import com.flip.urlshortener.domain.CreateShortUrl
import com.flip.urlshortener.domain.FetchShortUrl
import com.flip.urlshortener.domain.ShortUrl
import com.flip.urlshortener.infrastructure.InMemoryUrlsRepositoryAdapter
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotEquals
import org.junit.jupiter.api.Test

class UrlShortenerIT {
    private val repository = InMemoryUrlsRepositoryAdapter()
    private val createShortUrl = CreateShortUrl(repository)
    private val fetchShortUrl = FetchShortUrl(repository)

    @Test
    fun `create and fetch short URL flow`() {
        val createdShortUrl = createShortUrl(
            originalUrl = ShortUrl.OriginalUrl("http://example.com".asUrl())
        )

        fetchShortUrl(createdShortUrl.shortId)!!.let {
            assertEquals(createdShortUrl.id, it.id)
            assertEquals(createdShortUrl.shortId, it.shortId)
            assertEquals(createdShortUrl.originalUrl, createdShortUrl.originalUrl)
        }
    }

    @Test
    fun `creating short URLs for same original URL results in different short URL instances`() {
        val originalUrl = ShortUrl.OriginalUrl("http://example.com".asUrl())

        val shortUrl1 = createShortUrl(
            originalUrl = originalUrl
        )
        val shortUrl2 = createShortUrl(
            originalUrl = originalUrl
        )

        assertNotEquals(shortUrl1.id, shortUrl2.id)
        assertNotEquals(shortUrl1.shortId, shortUrl2.shortId)
        assertEquals(shortUrl1.originalUrl, shortUrl2.originalUrl)
    }
}
