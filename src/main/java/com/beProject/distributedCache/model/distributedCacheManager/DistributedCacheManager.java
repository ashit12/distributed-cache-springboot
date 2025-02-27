package com.beProject.distributedCache.model.distributedCacheManager;

import com.beProject.distributedCache.model.cache.LRUCache;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DistributedCacheManager<K, V> {
  private final List<LRUCache<K, V>> cacheList;
  private final ConsistentHashing<K> hashRing;

  private LRUCache<K, V> getCacheNode(K key){
    int index = hashRing.getNodeIndex(key);
    return cacheList.get(index);
  }

  public V getValue(K key){
    LRUCache<K, V> cache = getCacheNode(key);
    return cache.getValue(key);
  }

  public void putValue(K key, V value){
    LRUCache<K, V> cache = getCacheNode(key);
    cache.putValue(key, value);
  }

  public void removeKey(K key){
    LRUCache<K, V> cache = getCacheNode(key);
    cache.removeKey(key);
  }

  public void clearCache() {
    for (LRUCache<K, V> cache : cacheList) {
      cache.clearCache();
    }
  }
}
