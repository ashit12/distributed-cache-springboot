package com.beProject.distributedCache.model.distributedCacheManager;


import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;
import org.jetbrains.annotations.NotNull;

public class ConsistentHashing<K> {
  private final SortedMap<Integer, Integer> circle = new TreeMap<>();

  public ConsistentHashing(@NotNull List<Integer> nodeIndices, int numberOfReplicas) {
    for (Integer nodeIndex : nodeIndices) {
      for (int i = 0; i < numberOfReplicas; i++) {
        String virtualNodeKey = "NODE-" + nodeIndex + "-REPLICA-" + i;
        int hash = hash(virtualNodeKey);
        circle.put(hash, nodeIndex);
      }
    }
  }

  public int getNodeIndex(@NotNull K key){
    int hash = hash(key.toString());
    SortedMap<Integer, Integer> tailMap = circle.tailMap(hash);

    // If tail map is empty, then returns the first node on the ring, else the first node on the tail map
    int nodeHash = tailMap.isEmpty() ? circle.firstKey() : tailMap.firstKey();

    return circle.get(nodeHash);
  }

  private int hash(@NotNull String key){
    return Math.abs(key.hashCode());
  }

}
