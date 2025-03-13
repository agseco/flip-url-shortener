package com.flip.urlshortener

import java.net.URI
import java.net.URL

fun String.asUrl(): URL = URI.create(this).toURL()
