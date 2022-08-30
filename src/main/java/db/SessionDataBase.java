package db;

import http.HttpSession;

import java.util.HashMap;
import java.util.Map;

public class SessionDataBase {
    private SessionDataBase() {
    }

    private static final Map<String, HttpSession> sessions = new HashMap<>();

    public static void addSession(HttpSession session) {
        sessions.put(session.getId(), session);
    }

    public static void addSession(String sessionId, HttpSession session) {
        sessions.put(sessionId, session);
    }

    public static void removeSession(String sessionId) {
        sessions.remove(sessionId);
    }

    public static void removeSession(HttpSession session) {
        removeSession(session.getId());
    }

    public static HttpSession getSession(String sessionId) {
        return sessions.get(sessionId);
    }

    public static HttpSession getSession(HttpSession session) {
        return getSession(session.getId());
    }
}
