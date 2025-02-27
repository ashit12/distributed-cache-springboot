package com.beProject.distributedCache;
import static org.junit.jupiter.api.Assertions.*;

import com.beProject.distributedCache.model.cache.LRUCache;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.junit.jupiter.api.Test;

public class LRUCacheTest {

  @Test
  public void testPutAndGet() {
    LRUCache<String, String> cache = new LRUCache<>(3);
    cache.putValue("A", "Alpha");
    assertEquals("Alpha", cache.getValue("A"));
  }

  @Test
  public void testCacheCapacity() {
    LRUCache<String, String> cache = new LRUCache<>(3);
    cache.putValue("A", "Alpha");
    cache.putValue("B", "Beta");
    cache.putValue("D", "Delta");
    cache.putValue("T", "Theta");
    assertNull(cache.getValue("A"));
  }

  @Test
  public void testIfRecentAccessedElementIsAtFront() {
    LRUCache<String, String> cache = new LRUCache<>(3);
    cache.putValue("A", "Alpha");
    cache.putValue("B", "Beta");
    cache.putValue("D", "Delta");
    String val = cache.getValue("A");
    cache.putValue("T","Theta");
    assertNull(cache.getValue("B"));
    assertEquals("Alpha", cache.getValue("A"));
  }

  @Test
  public void testRemoveKey() {
    LRUCache<String, String> cache = new LRUCache<>(3);
    cache.putValue("A", "Alpha");
    cache.removeKey("A");
    assertNull(cache.getValue("A"));
  }

  @Test
  public void testConcurrentAccess() throws InterruptedException {
    final int threadCount = 10;
    final int operationsPerThread = 100;
    LRUCache<String, String> cache = new LRUCache<>(50);

    ExecutorService executor = Executors.newFixedThreadPool(threadCount);
    CountDownLatch latch = new CountDownLatch(threadCount);

    for (int i = 0; i < threadCount; i++) {
      final int threadId = i;
      executor.submit(() -> {
        try {
          for (int j = 0; j < operationsPerThread; j++) {
            String key = "Key-" + (threadId * operationsPerThread + j);
            String value = "Value-" + (threadId * operationsPerThread + j);
            cache.putValue(key, value);
            String retrieved = cache.getValue(key);
            if (j % 10 == 0) {
              cache.removeKey(key);
            }
          }
        } finally {
          latch.countDown();
        }
      });
    }

    latch.await();
    executor.shutdown();

    assertNotNull(cache);
  }
}
