package com.flip.urlshortener.domain

import com.flip.urlshortener.asUrl
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream

class ShortUrlTest {
    companion object {
        @JvmStatic
        fun urls(): Stream<Arguments> =
            listOf(
                Arguments.of(0, "https://www.example.com/path", "0"),
                Arguments.of(1, "https://www.example.com/path", "1"),
                Arguments.of(100_000, "https://www.example.com/path", "255s"),
                Arguments.of(2_176_782_335, "https://www.example.com/path", "zzzzzz")
            ).stream()
    }

    @Test
    fun `spec is protected from modifications`() {
        assertEquals(36, ShortUrl.Spec.base())
        assertEquals(6, ShortUrl.Spec.MAX_LENGTH)
        assertEquals(2_176_782_336, ShortUrl.Spec.maxId())
    }

    @ParameterizedTest
    @MethodSource("urls")
    fun `successfully create short URL from original`(id: Long, originalUrl: String, expectedShortId: String) {
        val shortUrl = ShortUrl(
            id = ShortUrl.Id(id),
            originalUrl = ShortUrl.OriginalUrl(originalUrl.asUrl())
        )

        assertEquals(id, shortUrl.id.value)
        assertEquals(expectedShortId, shortUrl.shortId.value)
        assertEquals(originalUrl, shortUrl.originalUrl.value.toString())
    }

    @Test
    fun `creating a short URL should fail when an invalid URL is provided`() {
        assertThrowsExactly(IllegalArgumentException::class.java) {
            ShortUrl(
                id = ShortUrl.Id(12345),
                originalUrl = ShortUrl.OriginalUrl("this-is-not-a-url".asUrl())
            )
        }
    }

    @Test
    fun `original URL rejects protocols other than HTTP or HTTPS`() {
        assertThrowsExactly(IllegalArgumentException::class.java) {
            ShortUrl(
                id = ShortUrl.Id(12345),
                originalUrl = ShortUrl.OriginalUrl("ftp://example.com".asUrl())
            )
        }
    }

    @Test
    fun `test equals method`() {
        val originalUrl1 = ShortUrl.OriginalUrl("https://example.com/original1".asUrl())
        val originalUrl2 = ShortUrl.OriginalUrl("https://example.com/original2".asUrl())

        val shortUrl1 = ShortUrl(ShortUrl.Id(12345L), originalUrl1)
        val shortUrl2 = ShortUrl(ShortUrl.Id(12345L), originalUrl2)
        val shortUrl3 = ShortUrl(ShortUrl.Id(67890L), originalUrl1)

        assertEquals(shortUrl1, shortUrl2, "ShortUrls with the same id should be equal")
        assertNotEquals(shortUrl1, shortUrl3, "ShortUrls with different ids should not be equal")
    }

    @Test
    fun `test hashCode method`() {
        val originalUrl1 = ShortUrl.OriginalUrl("https://example.com/original1".asUrl())
        val originalUrl2 = ShortUrl.OriginalUrl("https://example.com/original2".asUrl())

        val shortUrl1 = ShortUrl(ShortUrl.Id(12345L), originalUrl1)
        val shortUrl2 = ShortUrl(ShortUrl.Id(12345L), originalUrl2)
        val shortUrl3 = ShortUrl(ShortUrl.Id(67890L), originalUrl1)

        assertEquals(shortUrl1.hashCode(), shortUrl2.hashCode(), "Hash codes should match for equal objects")
        assertNotEquals(shortUrl1.hashCode(), shortUrl3.hashCode(), "Hash codes should differ for different objects")
    }
}
