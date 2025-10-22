package com.bnpparibas.tr.timer.it.support;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.LinkedHashMap;
import java.util.Map;

public final class TimerRequests {
    private static final ObjectMapper MAPPER = new ObjectMapper();

    private TimerRequests() {}

    public static String create(String name, String zoneId, String triggerTime, boolean suspended) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("name", name);
        putIfNotNull(body, "zoneId", zoneId);
        putIfNotNull(body, "triggerTime", triggerTime);
        body.put("suspended", suspended);
        return toJson(body);
    }

    public static String createCron(String name, String zoneId, String cronExpression, boolean suspended) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("name", name);
        putIfNotNull(body, "zoneId", zoneId);
        putIfNotNull(body, "cronExpression", cronExpression);
        body.put("suspended", suspended);
        return toJson(body);
    }

    public static String update(String name, String zoneId, String triggerTime, String cronExpression, boolean suspended) {
        Map<String, Object> body = new LinkedHashMap<>();
        putIfNotNull(body, "name", name);
        putIfNotNull(body, "zoneId", zoneId);
        putIfNotNull(body, "triggerTime", triggerTime);
        putIfNotNull(body, "cronExpression", cronExpression);
        body.put("suspended", suspended);
        return toJson(body);
    }

    private static void putIfNotNull(Map<String, Object> m, String k, Object v) {
        if (v != null) {
            m.put(k, v);
        }
    }

    private static String toJson(Map<String, Object> m) {
        try {
            return MAPPER.writeValueAsString(m);
        } catch (Exception e) {
            throw new RuntimeException("Failed to serialize request", e);
        }
    }
}


