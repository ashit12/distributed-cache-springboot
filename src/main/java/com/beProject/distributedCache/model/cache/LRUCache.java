package com.beProject.distributedCache.model.cache;

import java.util.HashMap;
import java.util.concurrent.locks.ReentrantLock;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class LRUCache<K, V> {
  private final int capacity;
  private final HashMap<K, Node<K, V>> hashMap = new HashMap<>();
  private Node<K, V> tail;
  private Node<K, V> head;
  private final ReentrantLock lock = new ReentrantLock();


  public V getValue(K key){
    lock.lock();
    try {
      var node = hashMap.get(key);
      if(node != null){
        moveToHead(node);
        return node.getValue();
      }
      return null;
    }
    finally {
      lock.unlock();
    }
  }

  public void putValue(K key, V value){
    lock.lock();
    try {
      if (hashMap.containsKey(key)) {
        Node<K, V> node = hashMap.get(key);
        node.setValue(value);
        moveToHead(node);
      } else {
        Node<K, V> node = new Node<K, V>(key, value);
        hashMap.put(key, node);
        addNodeToHead(node);
        if (hashMap.size() > capacity) {
          hashMap.remove(tail.getKey());
          removeNode(tail);
        }
      }
    } finally {
      lock.unlock();
    }
  }

  public void removeKey(K key){
    lock.lock();
    try {
    var node = hashMap.get(key);
    if(node == null){
      return ;
    }
    hashMap.remove(key);
    removeNode(node);
    } finally {
      lock.unlock();
    }
  }

  private void addNodeToHead(Node <K,V> node){
    if(head != null){
      head.setNext(node);
      node.setPrev(head);
    }
    head = node;

    if(tail == null){
      tail = head;
    }
  }

  private void removeNode(Node <K,V> node){
    if(node == tail){
      if(tail == head){
        tail = head = null;
      } else {
        tail = node.getNext();
        tail.setPrev(null);
      }
    } else if(node == head){
      head = head.getPrev();
      if(head != null){
        head.setNext(null);
      }
    } else {
      var prevNode = node.getPrev();
      var nextNode = node.getNext();
      if(prevNode != null){
        prevNode.setNext(nextNode);
      }
      if(nextNode != null){
        nextNode.setPrev(prevNode);
      }
    }
  }

  private void moveToHead(Node <K,V> node){
    removeNode(node);
    addNodeToHead(node);
  }

  public void clearCache(){
    hashMap.clear();
    head = tail = null;
  }

  public void debugPrint() {
    System.out.print("Cache state (tail -> head): ");
    Node<K, V> current = tail;
    while (current != null) {
      System.out.print("[" + current.getKey() + ":" + current.getValue() + "] ");
      current = current.getNext();
    }
    System.out.println();
  }
}
