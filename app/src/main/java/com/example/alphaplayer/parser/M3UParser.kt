
package com.example.alphaplayer.parser

import com.example.alphaplayer.data.model.M3UItem
import org.jsoup.Jsoup

object M3UParser {

    fun parse(content: String, baseUrl: String = ""): List<M3UItem> {

        val list = mutableListOf<M3UItem>()
        val trimmed = content.trim()

        // 1. JSON Format Parsing (Intact & Preserved)
        if (trimmed.startsWith("[")) {
            return parseJson(trimmed)
        }

        // 2. HTML Web Scraping Format Parsing
        if (trimmed.startsWith("<")) {
            return parseHtml(trimmed, baseUrl)
        }

        // 3. M3U / Plain Text Parsing
        val lines = content.lines()
            .map { it.trim() }
            .filter { it.isNotEmpty() }

        var currentTitle = ""
        var currentLogo: String? = null
        var currentGroup: String? = null
        val pendingExtVlcHeaders = mutableMapOf<String, String>()

        for (line in lines) {

            if (line.startsWith("#EXTINF")) {

                currentLogo = extract(line, "tvg-logo=\"", "\"")
                currentGroup = extract(line, "group-title=\"", "\"")
                currentTitle = line.substringAfterLast(",").trim()

                // Proxy URL Clean: HuggingFace proxy URL strip karke direct CDN link nikalna
                currentLogo = cleanLogoUrl(currentLogo)

            } else if (line.startsWith("#EXTVLCOPT:")) {
                val opt = line.substringAfter("#EXTVLCOPT:").trim()
                val eqIdx = opt.indexOf("=")
                if (eqIdx > 0) {
                    val key = opt.substring(0, eqIdx).trim().lowercase()
                    val value = opt.substring(eqIdx + 1).trim()
                    when {
                        key.contains("user-agent") -> pendingExtVlcHeaders["User-Agent"] = value
                        key.contains("referrer") || key.contains("referer") -> pendingExtVlcHeaders["Referer"] = value
                    }
                }
            } else if (!line.startsWith("#")) {

                val parts = line.split("|")
                var url = parts.first().trim()

                // Resolve relative video URLs
                if (!url.startsWith("http://") && !url.startsWith("https://") && baseUrl.isNotEmpty()) {
                    url = resolveUrl(baseUrl, url)
                }

                val headers = mutableMapOf<String, String>()

                // Default Browser User-Agent
                headers["User-Agent"] = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36"

                // Apply #EXTVLCOPT headers if present before URL pipe parsing
                if (pendingExtVlcHeaders.isNotEmpty()) {
                    headers.putAll(pendingExtVlcHeaders)
                }

                // Extract pipe-separated headers (|Referer=... |User-Agent=...)
                if (parts.size > 1) {
                    parts.drop(1).forEach {
                        val index = it.indexOf("=")
                        if (index > 0) {
                            val key = it.substring(0, index).trim()
                            val value = it.substring(index + 1).trim()
                            headers[key] = value
                        }
                    }
                }

                // Dynamic Fallback Referer Logic based on Stream Domain
                if (!headers.containsKey("Referer")) {
                    when {
                        url.contains("fibwatch") || url.contains("zertgn.b-cdn.net") -> {
                            headers["Referer"] = "https://fibwatch.art/"
                        }
                        url.contains("githubusercontent") || url.contains("raw.github") -> {
                            headers["Referer"] = "https://github.com/"
                        }
                        baseUrl.isNotEmpty() -> {
                            headers["Referer"] = baseUrl
                        }
                    }
                }

                if (currentLogo != null && !currentLogo.startsWith("http") && baseUrl.isNotEmpty()) {
                    currentLogo = resolveUrl(baseUrl, currentLogo)
                }

                list.add(
                    M3UItem(
                        title = currentTitle,
                        url = url,
                        logo = currentLogo,
                        group = currentGroup ?: "General",
                        headers = headers
                    )
                )

                pendingExtVlcHeaders.clear()
            }
        }

        return list
    }

    private fun parseHtml(html: String, baseUrl: String = ""): List<M3UItem> {

        val list = mutableListOf<M3UItem>()
        val document = Jsoup.parse(html)

        document.select("li.thumb").forEach { item ->

            var image = item.selectFirst("img")?.attr("src")?.trim()

            val title = item.selectFirst("figcaption p")
                ?.text()
                ?.trim()
                ?: item.selectFirst("img")
                    ?.attr("alt")
                    ?.trim()
                ?: ""

            var url = item.selectFirst("figure > a")
                ?.attr("href")
                ?.trim()
                ?: ""

            image = cleanLogoUrl(image)

            // Fix Relative URLs for Images & Video Links
            if (!image.isNullOrEmpty() && !image.startsWith("http://") && !image.startsWith("https://") && baseUrl.isNotEmpty()) {
                image = resolveUrl(baseUrl, image)
            }

            if (url.isNotEmpty() && !url.startsWith("http://") && !url.startsWith("https://") && baseUrl.isNotEmpty()) {
                url = resolveUrl(baseUrl, url)
            }

            if (title.isNotEmpty() && url.isNotEmpty()) {
                list.add(
                    M3UItem(
                        title = title,
                        url = url,
                        logo = image,
                        group = "Latest Releases",
                        headers = mapOf(
                            "User-Agent" to "Mozilla/5.0 (Windows NT 10.0; Win64; x64)",
                            "Referer" to if (baseUrl.isNotEmpty()) baseUrl else "https://fibwatch.art/"
                        )
                    )
                )
            }
        }

        return list
    }

    // Generic JSON parsing implementation without ApiChannel dependency
    private fun parseJson(content: String): List<M3UItem> {
        return try {
            val jsonElement = kotlinx.serialization.json.Json.parseToJsonElement(content)
            val list = mutableListOf<M3UItem>()

            if (jsonElement is kotlinx.serialization.json.JsonArray) {
                for (element in jsonElement) {
                    if (element is kotlinx.serialization.json.JsonObject) {
                        val title = element["title"]?.toString()?.removeSurrounding("\"") ?: "Unknown"
                        val videoUrl = element["videoUrl"]?.toString()?.removeSurrounding("\"") ?: continue
                        val imageUrl = cleanLogoUrl(element["imageUrl"]?.toString()?.removeSurrounding("\""))

                        val headers = mutableMapOf<String, String>()
                        element["userAgent"]?.toString()?.removeSurrounding("\"")?.takeIf { it.isNotBlank() }?.let { headers["User-Agent"] = it }
                        element["referer"]?.toString()?.removeSurrounding("\"")?.takeIf { it.isNotBlank() }?.let { headers["Referer"] = it }
                        element["origin"]?.toString()?.removeSurrounding("\"")?.takeIf { it.isNotBlank() }?.let { headers["Origin"] = it }
                        element["cookie"]?.toString()?.removeSurrounding("\"")?.takeIf { it.isNotBlank() }?.let { headers["Cookie"] = it }
                        element["clearkeyLicenseKeyId"]?.toString()?.removeSurrounding("\"")?.takeIf { it.isNotBlank() }?.let { headers["ClearKey"] = it }

                        list.add(
                            M3UItem(
                                title = title,
                                url = videoUrl,
                                logo = imageUrl,
                                group = "Live TV",
                                headers = headers
                            )
                        )
                    }
                }
            }
            list
        } catch (e: Exception) {
            emptyList()
        }
    }

    private fun cleanLogoUrl(rawLogo: String?): String? {
        if (rawLogo.isNullOrBlank()) return null
        return if (rawLogo.contains("hf.space/image?url=")) {
            rawLogo.substringAfter("hf.space/image?url=").trim()
        } else {
            rawLogo.trim()
        }
    }

    private fun resolveUrl(base: String, relativePath: String): String {
        return try {
            val baseUri = java.net.URI(base)
            baseUri.resolve(relativePath).toString()
        } catch (e: Exception) {
            relativePath
        }
    }

    private fun extract(text: String, start: String, end: String): String? {
        val s = text.indexOf(start)
        if (s == -1) return null
        val from = s + start.length
        val e = text.indexOf(end, from)
        if (e == -1) return null
        return text.substring(from, e)
    }
}