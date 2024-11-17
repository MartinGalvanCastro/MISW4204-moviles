package com.example.vinilosapp.di

import android.util.LruCache
import javax.inject.Inject
import javax.inject.Singleton

data class CacheEntry<T>(
    val value: T,
    val timestamp: Long = System.currentTimeMillis(),
)

@Singleton
class Cache @Inject constructor() {

    private val listCache: LruCache<String, CacheEntry<List<Any>>> = LruCache(4 * 1024 * 1024) // 4MB
    private val detailCache: LruCache<String, CacheEntry<Any>> = LruCache(4 * 1024 * 1024) // 4MB

    private val EVICTION_TIME: Long = 2 * 60 * 60 * 1000 // 2 hours

    private fun isEntryValid(cacheEntry: CacheEntry<*>?): Boolean {
        return cacheEntry != null &&
            (System.currentTimeMillis() - cacheEntry.timestamp < EVICTION_TIME)
    }

    fun <T> getList(key: String): List<T>? {
        val cachedValue = listCache.get(key)
        return if (isEntryValid(cachedValue)) {
            cachedValue?.value as List<T>
        } else {
            listCache.remove(key)
            null
        }
    }

    fun <T> getDetail(key: String): T? {
        val cachedValue = detailCache.get(key)
        return if (isEntryValid(cachedValue)) {
            cachedValue?.value as T
        } else {
            detailCache.remove(key)
            null
        }
    }

    fun putList(key: String, value: List<Any>) {
        listCache.put(key, CacheEntry(value))
    }

    fun putDetail(key: String, value: Any) {
        detailCache.put(key, CacheEntry(value))
    }

    fun clear() {
        listCache.evictAll()
        detailCache.evictAll()
    }
}
