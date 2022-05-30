package service;

import model.RequestInfo;
import util.HttpRequestUtils;

import java.util.Map;

public class ListService implements WebService {

    private static final String COOKIE = "Cookie";

    private RequestInfo requestInfo;

    public ListService(RequestInfo requestInfo) {
        this.requestInfo = requestInfo;
    }

    @Override
    public void doSomething() {
        String body = requestInfo.getBody();
        Map<String, String> headerMap = requestInfo.getHeaderMap();

        String cookie = HttpRequestUtils.parseCookies(headerMap.get(COOKIE)).get("logined");

        boolean isValid = checkCookie(cookie);
        if(!isValid) {
            throw new RuntimeException("ListService - invalid cookie");
        }
    }

    private boolean checkCookie(String cookie) {
        return cookie != null && cookie.equals("true");
    }
}
