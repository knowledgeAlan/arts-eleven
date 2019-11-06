package com.zzm.tomcatelevendemo;

import java.util.Hashtable;

/**
 * @author zhongzuoming <zhongzuoming, 1299076979@qq.com>
 * @version v1.0
 * @Description baipao
 * @encoding UTF-8
 * @date 2019-10-31
 * @time 19:55
 * @修改记录 <pre>
 * 版本       修改人         修改时间         修改内容描述
 * --------------------------------------------------
 * <p>
 * --------------------------------------------------
 * </pre>
 */
public class LRUCache {

    private Hashtable<Integer,DLinkedNode> cache = new Hashtable();

    private int size;

    private int capacity;

    private DLinkedNode head;

    private DLinkedNode tail;

    class DLinkedNode{
        int key;
        int value;

        DLinkedNode prev;

        DLinkedNode next ;
    }

    private void addNode(DLinkedNode node){
        node.prev = head;
        node.next = head.next;
        head.next.prev = node;
        head.next = node;
    }

    private void removeNode(DLinkedNode node){
        DLinkedNode prev = node.prev;
        DLinkedNode next = node.next;

        prev.next = next;
        next.prev = prev;
    }

    private void moveToHead(DLinkedNode node){
        removeNode(node);
        addNode(node);
    }

    private DLinkedNode popTail(){
        DLinkedNode res = tail.prev;
        removeNode(res);
        return res;
    }


    public LRUCache(int capacity){
        this.size = 0;
        this.capacity = capacity;

        head = new DLinkedNode();

        tail = new DLinkedNode();
        head.next = tail;
        tail.prev = head;
    }

    public int get(int key){
        DLinkedNode node = cache.get(key);
        if (null == node ) {
            return -1;
        }

        moveToHead(node);
        return node.value;
    }


    public void put(int key,int value){
        DLinkedNode node = cache.get(key);
        if (null == node){
            DLinkedNode newNode = new DLinkedNode();
            newNode.key = key;
            newNode.value = value;

            cache.put(key,newNode);
            addNode(newNode);
            ++size;
            if (size > capacity ){
                DLinkedNode tail = popTail();
                cache.remove(tail.key);
                --size;
            }
        }else {
            node.value = value;
            moveToHead(node);
        }
    }
}
