package service;

import model.RequestInfo;
import model.User;
import util.HttpRequestUtils;

import java.util.Map;

public class CreateService implements WebService {

    private RequestInfo requestInfo;

    private User user;

    public CreateService(RequestInfo requestInfo) {
        this.requestInfo = requestInfo;
    }

    @Override
    public void doSomething() {
        String url = requestInfo.getUrl();
        int index = url.indexOf("?");

        Map<String, String> queryStringMap = HttpRequestUtils.parseQueryString(url.substring(index + 1, url.length()));

        String username = queryStringMap.get("username");
        String password = queryStringMap.get("password");
        String name = queryStringMap.get("name");
        String email = queryStringMap.get("email");

        user = new User(username, password, name, email);
    }
}
