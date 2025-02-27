package com.beProject.distributedCache.controller;


import com.beProject.distributedCache.service.CacheService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/cache")
public class CacheController {
  private final CacheService cacheService;

  @GetMapping("/{key}")
  public ResponseEntity<String> getValue(@PathVariable String key){
    String value = cacheService.readRecord(key);
    if ("NO RESULT".equals(value)) {
      return ResponseEntity.notFound().build();
    }
    return ResponseEntity.ok(value);
  }

  @PostMapping
  public ResponseEntity<String> upsertValue(@RequestParam String key, @RequestParam String value) {
    cacheService.upsertRecord(key, value);
    return ResponseEntity.ok("Key updated in cache");
  }

  @DeleteMapping("/{key}")
  public ResponseEntity<String> removeKey(@PathVariable String key) {
    cacheService.upsertRecord(key, null); // Removing the key from cache and DB
    return ResponseEntity.ok("Key removed from cache");
  }

  @DeleteMapping
  public ResponseEntity<String> clearCache() {
    cacheService.clearCache();
    return ResponseEntity.ok("Cache cleared");
  }
}
