package com.example.xiaomao.cache;

/**
 * Created by nanyi545 on 16-3-18.
 *
 * interface defining cache operation
 */
public interface Cache<K,V> {

    /** retrieve object from cache**/
    V get(K key);

    /** retrieve the most recently store object from cache**/
    V getLast();

    /** check if the time interval since last object(in) smaller than the threshold
    --> yes means to retrieve the last object is a good substitute for calling remote...**/
    boolean isFresh();

    /** put object in cache  **/
    void put(K key,V obj);

    /** check if a key is cache **/
    boolean isCached(K key);

    /** is the time interval since last object(in) larger than the threshold ,
     * if yes the cache is expired (call evictAll() )**/
    boolean isExpired();

    /** evict all items in the cache  **/
    void evictAll();

}
