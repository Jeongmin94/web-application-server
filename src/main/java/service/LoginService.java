package service;

import model.RequestInfo;
import model.User;
import util.HttpRequestUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class LoginService implements WebService {
    private static Map<String, User> userMap = new HashMap<>();

    private RequestInfo requestInfo;

    public LoginService(RequestInfo requestInfo) {
        this.requestInfo = requestInfo;
    }

    public static void addUser(User user) {
        userMap.put(user.getUserId(), user);
    }

    @Override
    public void doSomething() throws RuntimeException {
        String body = requestInfo.getBody();
        Map<String, String> infoMap = HttpRequestUtils.parseQueryString(body);

        String userId = infoMap.get("userId");

        throw new RuntimeException("not");
    }
}
