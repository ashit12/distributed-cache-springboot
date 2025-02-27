package com.beProject.distributedCache.service;

import com.beProject.distributedCache.repository.CacheRepository;
import com.beProject.distributedCache.model.distributedCacheManager.DistributedCacheManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CacheService {
  private final DistributedCacheManager<String, String> manager;
  private final CacheRepository cacheRepository;

  @Transactional(readOnly = true)
  public String readRecord(String key){
    String res = manager.getValue(key);
    if(res == null){
      var repositoryResult = cacheRepository.readRecord(key);
      if(repositoryResult != null){
        manager.putValue(key, repositoryResult);
        return repositoryResult;
      }
      return "NO RESULT";
    }
    return res;
  }

  @Transactional
  public void upsertRecord(String key, String value){
    boolean success = cacheRepository.upsertRecord(key, value);
    if(success){
      manager.putValue(key, value);
    }
  }

  @Transactional
  public void deleteRecord(String key){
    boolean success = cacheRepository.deleteRecord(key);
    if(success){
      manager.removeKey(key);
    }
  }

  @Transactional
  public void clearCache() {
    cacheRepository.clearCache();
    manager.clearCache();
  }
}
