package com.beProject.distributedCache;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.beProject.distributedCache.model.distributedCacheManager.DistributedCacheManager;
import com.beProject.distributedCache.repository.CacheRepository;
import com.beProject.distributedCache.service.CacheService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class CacheServiceTest {

  @Mock
  private DistributedCacheManager<String, String> cacheManager;

  @Mock
  private CacheRepository cacheRepository;

  @InjectMocks
  private CacheService cacheService;

  @Test
  public void testCacheHit() {
    when(cacheManager.getValue("key")).thenReturn("value");
    String val = cacheService.readRecord("key");
    assertEquals("value", val);
    verify(cacheRepository, never()).readRecord(anyString());
  }

  @Test
  public void testCacheMissRepositoryHit(){
    when(cacheManager.getValue("key")).thenReturn(null);
    when(cacheRepository.readRecord("key")).thenReturn("repoValue");

    String val = cacheService.readRecord("key");
    assertEquals("repoValue", val);
    verify(cacheRepository).readRecord("key");
    verify(cacheManager).putValue("key", "repoValue");
  }

  @Test
  public void testCacheMissRepositoryMiss(){
    when(cacheManager.getValue("key")).thenReturn(null);
    when(cacheRepository.readRecord("key")).thenReturn(null);
    String val = cacheService.readRecord("key");
    assertEquals("NO RESULT", val);
  }

  @Test
  public void testUpsertRecord() {
    when(cacheRepository.upsertRecord("key", "newValue")).thenReturn(true);

    cacheService.upsertRecord("key", "newValue");
    verify(cacheRepository).upsertRecord("key", "newValue");
    verify(cacheManager).putValue("key", "newValue");
  }
}
