package service;

import db.DataBase;
import model.RequestInfo;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.HttpRequestUtils;

import java.util.Map;

public class CreateService implements WebService {
    private static final Logger log = LoggerFactory.getLogger(CreateService.class);

    private RequestInfo requestInfo;

    private User user;

    public CreateService(RequestInfo requestInfo) {
        this.requestInfo = requestInfo;
    }

    @Override
    public void doSomething() {
        if (requestInfo.getMethod().equals("GET")) {
            user = getMethodCreate();
        }

        if(requestInfo.getMethod().equals("POST")) {
            user = postMethodCreate();
        }

        log.debug(user.toString());
    }

    private User getMethodCreate() {
        String url = requestInfo.getUrl();
        int index = url.indexOf("?");

        return makeUser(url.substring(index + 1));
    }

    private User postMethodCreate() {
        String body = requestInfo.getBody();

        return makeUser(body);
    }

    private User makeUser(String data) {
        log.debug(data);
        Map<String, String> infoMap = HttpRequestUtils.parseQueryString(data);

        String username = infoMap.get("userId");
        String password = infoMap.get("password");
        String name = infoMap.get("name");
        String email = infoMap.get("email");

        User user = new User(username, password, name, email);
        DataBase.addUser(user);

        return user;
    }
}
