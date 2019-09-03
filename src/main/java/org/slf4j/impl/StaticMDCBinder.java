package org.slf4j.impl;

import org.slf4j.spi.MDCAdapter;

import java.util.HashMap;
import java.util.Map;

public final class StaticMDCBinder {

    public static final StaticMDCBinder SINGLETON = new StaticMDCBinder();

    private static final MDCAdapter adapter = new MDCAdapter() {

        private final ThreadLocal<Map<String, String>> data = new InheritableThreadLocal<Map<String, String>>() {
            @Override
            protected Map<String, String> initialValue() {
                return new HashMap<>();
            }
        };

        @Override
        public void put(final String key, final String value) {
            data.get().put(key, value);
        }

        @Override
        public String get(final String key) {
            return data.get().get(key);
        }

        @Override
        public void remove(final String key) {
            data.get().remove(key);
        }

        @Override
        public void clear() {
            data.get().clear();
        }

        @Override
        public Map<String, String> getCopyOfContextMap() {
            return new HashMap<>(data.get());
        }

        @Override
        public void setContextMap(final Map<String, String> contextMap) {
            data.get().clear();
            for (Map.Entry<String, String> entry : contextMap.entrySet()) {
                data.get().put(entry.getKey(), entry.getValue());
            }
        }
    };

    public MDCAdapter getMDCA() {
        return adapter;
    }

    public String getMDCAdapterClassStr() {
        return adapter.getClass().getName();
    }
}
