package com.beProject.distributedCache.repository;

import com.example.beProjects.jooq.tables.CacheEntries;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CacheRepository {
  private final DSLContext dsl;

  public String readRecord(String key){
    return dsl.select(CacheEntries.CACHE_ENTRIES.VALUE)
        .from(CacheEntries.CACHE_ENTRIES)
        .where(CacheEntries.CACHE_ENTRIES.KEY.eq(key))
        .fetchOneInto(String.class);
  }


  public boolean upsertRecord(String key, String value){
    int rows = dsl.insertInto(CacheEntries.CACHE_ENTRIES)
        .columns(CacheEntries.CACHE_ENTRIES.KEY,CacheEntries.CACHE_ENTRIES.VALUE)
        .values(key, value).onConflict(CacheEntries.CACHE_ENTRIES.KEY)
        .doUpdate()
        .set(CacheEntries.CACHE_ENTRIES.VALUE, value)
        .execute();
    return rows > 0;
  }

  public boolean deleteRecord(String key){
    int rows = dsl.deleteFrom(CacheEntries.CACHE_ENTRIES).where(CacheEntries.CACHE_ENTRIES.KEY.eq(key)).execute();
    return rows > 0;
  }

  public void clearCache() {
    dsl.deleteFrom(CacheEntries.CACHE_ENTRIES).execute();
  }
}

