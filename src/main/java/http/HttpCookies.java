package http;

import db.SessionDataBase;

import java.util.HashMap;
import java.util.Map;

public class HttpCookies {

    private final Map<String, String> cookies = new HashMap<>();

    public void addCookie(String name, String value) {
        cookies.put(name, value);
    }

    public String getCookie(String name) {
        return cookies.get(name);
    }

    public boolean isSessionExist(String name) {
        String sessionId = cookies.get(name);
        if (sessionId == null) {
            return false;
        }

        HttpSession session = SessionDataBase.getSession(sessionId);

        return session != null;
    }
}
