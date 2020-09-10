package com.cpqd.wf.rest.service;

import java.util.*;

public class UserService {

    private static final Map<Long,Map<String, Object>> users = new LinkedHashMap<>();

    static {
        Map<String, Object> user = new LinkedHashMap<>();
        user.put("id", 1L);
        user.put("name", "José da Silva");

        users.put(1L, user);

        user = new LinkedHashMap<>();
        user.put("id", 2L);
        user.put("name", "Maria Aparecida de Souza");

        users.put(2L, user);

        user = new LinkedHashMap<>();
        user.put("id", 3L);
        user.put("name", "Pedro de Alencar");

        users.put(3L, user);

        user = new LinkedHashMap<>();
        user.put("id", 4L);
        user.put("name", "Paulo Gomes");

        users.put(4L, user);

    }

    public static List<Map<String,Object>> findAll() {
        return new ArrayList<>(users.values());
    }

    public static Map<String, Object> findById(String userId) {
        return users.get(Long.parseLong(userId));
    }
}
