package service;

import db.DataBase;
import model.RequestInfo;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.HttpRequestUtils;

import javax.xml.crypto.Data;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class LoginService implements WebService {
    private static final Logger log = LoggerFactory.getLogger(LoginService.class);
    private static final String SET_COOKIE = "Set-Cookie";

    private RequestInfo requestInfo;

    public LoginService(RequestInfo requestInfo) {
        this.requestInfo = requestInfo;
    }

    @Override
    public void doSomething() throws RuntimeException {
        String body = requestInfo.getBody();
        Map<String, String> infoMap = HttpRequestUtils.parseQueryString(body);
        Map<String, String> headerMap = requestInfo.getHeaderMap();

        User user = DataBase.findUserById(infoMap.get("userId"));
        String userId = user.getUserId();
        String cookie = headerMap.get(SET_COOKIE);
        if(cookie == null && userId == null) {
            throw new RuntimeException("LoginService - invalid user");
        }

        String password = user.getPassword();
        if(!password.equals(infoMap.get("password"))) {
            throw new RuntimeException("LoginService - invalid password");
        }
    }
}
