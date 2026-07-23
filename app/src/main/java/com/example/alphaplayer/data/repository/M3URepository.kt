package com.example.alphaplayer.data.repository

import android.util.LruCache
import com.example.alphaplayer.data.model.M3UItem
import com.example.alphaplayer.parser.M3UParser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.HttpURLConnection
import java.net.URL

class M3URepository {

    companion object {
        // Use LruCache to prevent memory leaks by limiting the number of cached playlists
        private val playlistCache = LruCache<String, List<M3UItem>>(15)

        fun clearCache() {
            playlistCache.evictAll()
        }

        fun isCached(url: String): Boolean {
            return playlistCache.get(url) != null
        }

        fun getCachedPlaylist(url: String): List<M3UItem>? {
            return playlistCache.get(url)
        }
    }

    suspend fun loadPlaylist(url: String): List<M3UItem> = withContext(Dispatchers.IO) {
        try {
            val connection = (URL(url).openConnection() as HttpURLConnection).apply {
                requestMethod = "GET"
                connectTimeout = 15000 // 15 seconds timeout
                readTimeout = 15000

                // User-Agent and Referer headers so IPTV / Web servers don't block request (403 Forbidden)
                setRequestProperty(
                    "User-Agent",
                    "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36"
                )

                // Dynamic Referer setting based on URL host
                val refererHost = getRefererForUrl(url)
                setRequestProperty("Referer", refererHost)
            }

            val responseCode = connection.responseCode
            if (responseCode == HttpURLConnection.HTTP_OK) {
                val content = connection.inputStream.bufferedReader().use { it.readText() }
                connection.disconnect()

                // Pass URL as baseUrl so parser can resolve relative image/video paths
                M3UParser.parse(content, baseUrl = url)
            } else {
                connection.disconnect()
                emptyList()
            }
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }

    suspend fun loadPlaylistWithCache(
        url: String,
        forceRefresh: Boolean = false
    ): List<M3UItem> {
        if (!forceRefresh) {
            playlistCache.get(url)?.let {
                return it
            }
        }

        val data = loadPlaylist(url)
        if (data.isNotEmpty()) {
            playlistCache.put(url, data)
        }
        return data
    }

    suspend fun preloadPlaylist(url: String) {
        if (playlistCache.get(url) == null) {
            val data = loadPlaylist(url)
            if (data.isNotEmpty()) {
                playlistCache.put(url, data)
            }
        }
    }

    // Helper to extract proper Referer header for network request
    private fun getRefererForUrl(url: String): String {
        return when {
            url.contains("githubusercontent") || url.contains("github.com") -> "https://github.com/"
            url.contains("fibwatch") -> "https://fibwatch.art/"
            else -> {
                try {
                    val uri = java.net.URI(url)
                    "${uri.scheme}://${uri.host}/"
                } catch (e: Exception) {
                    "https://google.com/"
                }
            }
        }
    }
}