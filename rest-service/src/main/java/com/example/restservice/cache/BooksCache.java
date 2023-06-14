package com.example.restservice.cache;

import com.example.restservice.model.BookModel;
import com.hazelcast.map.IMap;
import com.hazelcast.spring.cache.HazelcastCacheManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.AbstractMap;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BooksCache {

    private final HazelcastCacheManager cacheManager;
    private final String cacheName = "books";
    private final String cacheNameUtil = "util";

    public Map<Long, Map.Entry<Integer, BookModel>> getBooksPageFromCache(int pageN) {
        IMap<Long, Map.Entry<Integer, BookModel>> iMap = cacheManager.getHazelcastInstance()
            .getMap(cacheName); // hazelcast map
        return iMap.entrySet()
            .stream()  // get books from map; filtered by page
            .filter(x -> x.getValue().getKey() != null
                && x.getValue().getKey() == pageN)
            .collect(Collectors
                .toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public Map.Entry<Integer, BookModel> cacheGetBook(long id) {
        return (Map.Entry<Integer, BookModel>) cacheManager.getHazelcastInstance()
            .getMap(cacheName)
            .get(id);
    }

    public void cachePutBook(Integer pageN, BookModel book) {
        cacheManager.getCache(cacheName)
            .put(book.getId(),
                new AbstractMap.SimpleEntry<>(pageN, book));
    }

    public void cachePutUpdateReq(Boolean updateRequired) {
        cacheManager.getCache(cacheNameUtil)
            .put("update", updateRequired);
    }

    public Boolean cacheGetUpdateReq() {
        Boolean updateLast = (Boolean) cacheManager.getHazelcastInstance()
            .getMap(cacheNameUtil)
            .get("update");
        if (updateLast == null) {
            updateLast = false;
        }
        return updateLast;
    }

    public Integer cacheGetMaxPage() {
        return (Integer) cacheManager.getHazelcastInstance()
            .getMap(cacheNameUtil)
            .get("maxPage");
    }

    public void cachePutMaxPage(int pageN) {
        cacheManager.getCache(cacheNameUtil)
            .put("maxPage", pageN);
    }

    public void evictBook(long id) {
        cacheManager.getCache(cacheName)
            .evict(id);
    }
}