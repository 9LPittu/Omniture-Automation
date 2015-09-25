package com.jcrew.util;

import java.util.HashMap;
import java.util.Map;

public class StateHolder {

    private static StateHolder stateHolder = new StateHolder();
    private static final Map<String, Map<String, String>> stateHolderMap = new HashMap<>();

    public static StateHolder getInstance() {
        return stateHolder;
    }

    private StateHolder() {

    }

    public void put(String key, String value) {
        Map<String, String> threadMap = getMapForCurrentThread();
        threadMap.put(key, value);
    }

    private Map<String, String> getMapForCurrentThread() {
        Map<String, String> threadMap = stateHolderMap.get(Thread.currentThread().getName());
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

    public String get(String key) {
        Map<String, String> threadMap = getMapForCurrentThread();
        return threadMap.get(key);
    }
}
