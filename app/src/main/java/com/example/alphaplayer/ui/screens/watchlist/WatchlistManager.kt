package com.example.alphaplayer.data.manager

import android.content.Context
import android.content.SharedPreferences
import com.example.alphaplayer.data.model.M3UItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

object WatchlistManager {

    private const val PREF_NAME = "watchlist_prefs"
    private const val KEY_WATCHED_ITEMS = "watched_items"

    private lateinit var prefs: SharedPreferences

    private val _watchedItems = MutableStateFlow<List<M3UItem>>(emptyList())
    val watchedItems: StateFlow<List<M3UItem>> = _watchedItems.asStateFlow()

    // Context initialize karne ke liye init function
    fun init(context: Context) {
        prefs = context.applicationContext.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        loadFromPrefs()
    }

    fun addToWatchlist(item: M3UItem) {
        val currentList = _watchedItems.value.toMutableList()
        currentList.removeAll { it.url == item.url } // Duplicate avoid karne ke liye
        currentList.add(0, item) // Naya item sabse upar dikhega

        _watchedItems.value = currentList
        saveToPrefs(currentList)
    }

    fun removeFromWatchlist(item: M3UItem) {
        val currentList = _watchedItems.value.toMutableList()
        currentList.removeAll { it.url == item.url }

        _watchedItems.value = currentList
        saveToPrefs(currentList)
    }

    fun clearWatchlist() {
        _watchedItems.value = emptyList()
        saveToPrefs(emptyList())
    }

    private fun saveToPrefs(items: List<M3UItem>) {
        if (!::prefs.isInitialized) return
        try {
            val jsonString = Json.encodeToString(items)
            prefs.edit().putString(KEY_WATCHED_ITEMS, jsonString).apply()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun loadFromPrefs() {
        if (!::prefs.isInitialized) return
        val jsonString = prefs.getString(KEY_WATCHED_ITEMS, null) ?: return
        try {
            val items = Json.decodeFromString<List<M3UItem>>(jsonString)
            _watchedItems.value = items
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}