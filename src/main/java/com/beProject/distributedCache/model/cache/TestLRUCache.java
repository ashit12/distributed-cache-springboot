package com.beProject.distributedCache.model.cache;

public class TestLRUCache {
  public static void main(String[] args) {
    // 1) Instantiate the cache
    LRUCache<String, String> cache = new LRUCache<>(3);

    // 2) Manually insert (PUT) key-value pairs
    System.out.println("=== Testing putValue ===");
    cache.putValue("A", "Alpha");  // Cache: A
    cache.putValue("B", "Bravo");  // Cache: A -> B
    cache.putValue("C", "Charlie"); // Cache: A -> B -> C
    printCacheState(cache, "After inserting A, B, C");

    // 3) Exceed capacity
    cache.putValue("D", "Delta");  // Evicts LRU (A), Cache: B -> C -> D
    printCacheState(cache, "After inserting D (capacity 3, so A evicted)");

    // 4) Retrieve (GET) a key
    System.out.println("\n=== Testing getValue ===");
    System.out.println("getValue(B): " + cache.getValue("B"));
    // B is accessed, becomes MRU. Cache might reorder to: C -> D -> B
    printCacheState(cache, "After getting B");

    // 5) Update an existing key
    cache.putValue("C", "Charlie-Updated");
    printCacheState(cache, "After updating C -> Charlie-Updated");

    // 6) Remove a key
    System.out.println("\n=== Testing removeKey ===");
    cache.removeKey("D");  // Removes D
    printCacheState(cache, "After removing key D");

    // 7) Clear the cache
    System.out.println("\n=== Testing clearCache ===");
    cache.clearCache();
    printCacheState(cache, "After clearing cache");
  }

  // Helper method to print the cache's internal state if you have a way to do so
  // For example, you could add a toString() or debug method to LRUCache
  private static void printCacheState(LRUCache<String, String> cache, String message) {
    System.out.println(message);
    // If your LRUCache had a method like cache.debugPrint(), call it here
    // e.g., cache.debugPrint();
    cache.debugPrint();

  }
}
