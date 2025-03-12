package com.flip.urlshortener.domain

import java.net.URI
import java.net.URL
import kotlin.math.pow

class ShortUrl(
    val id: Id,
    val originalUrl: OriginalUrl
) {
    val shortId: ShortId = id.asShortId()

    fun shortUrl(): URL = URI.create("http://localhost:8080/${shortId.value}").toURL()

    @JvmInline
    value class Id(val value: Long) {
        fun asShortId() = ShortId(value.toString(Spec.base()))
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ShortUrl

        return id == other.id
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }

    @JvmInline
    value class ShortId(val value: String)

    @JvmInline
    value class OriginalUrl(val value: URL)

    object Spec {
        private val chars = ('a'..'z') + ('0'..'9')
        const val MAX_LENGTH = 6
        fun maxId() = chars.size.toDouble().pow(MAX_LENGTH).toLong()
        fun base() = chars.size
    }
}
