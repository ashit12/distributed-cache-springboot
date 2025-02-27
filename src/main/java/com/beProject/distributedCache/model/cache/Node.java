package com.beProject.distributedCache.model.cache;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class Node<K,V> {
  private final K key;
  private V value;
  Node <K,V> prev = null;
  Node <K,V> next = null;

  public Node(K key, V val){
    this.key = key;
    this.value = val;
  }
}
