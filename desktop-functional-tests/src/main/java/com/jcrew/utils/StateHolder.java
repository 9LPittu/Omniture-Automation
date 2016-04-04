package com.jcrew.utils;

import java.util.HashMap;
import java.util.Map;

public class StateHolder {

    private static final StateHolder stateHolder = new StateHolder();
    private static final Map<String, Map<String, Object>> stateHolderMap = new HashMap<>();

    private StateHolder() {

    }

    public static StateHolder getInstance() {
        return stateHolder;
    }

    public void put(String key, Object value) {
        Map<String, Object> threadMap = getMapForCurrentThread();
        threadMap.put(key, value);
    }

    private Map<String, Object> getMapForCurrentThread() {
        Map<String, Object> threadMap = stateHolderMap.get(Thread.currentThread().getName());
        if (threadMap == null) {
            synchronized (stateHolderMap) {
                if (threadMap == null) {
                    threadMap = new HashMap<>();
                }

                stateHolderMap.put(Thread.currentThread().getName(), threadMap);
            }
        }
        return threadMap;
    }

    public Object get(String key) {
        Map<String, Object> threadMap = getMapForCurrentThread();
        return threadMap.get(key);
    }

    public void clear() {
        Map<String, Object> threadMap = stateHolderMap.get(Thread.currentThread().getName());
        if (threadMap != null) {
            threadMap.clear();
        }
    }
}
