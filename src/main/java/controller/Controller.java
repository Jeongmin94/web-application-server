package controller;

import model.RequestInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.WebService;
import util.IOUtils;

import java.io.IOException;

public class Controller {
    private static final Logger log = LoggerFactory.getLogger(Controller.class);
    private static final String WEBAPP = "./webapp";
    private static final String INDEX = "/index.html";
    private static final String USER_FORM = "/user/form.html";
    private static final String CREATE = "/user/create";

    private RequestInfo requestInfo;

    private WebService webService;

    public Controller(RequestInfo requestInfo, WebService webService) {
        this.requestInfo = requestInfo;
        this.webService = webService;
    }

    public byte[] makeBody() throws IOException {
        switch (checkUrl(requestInfo.getUrl())) {
            case USER_FORM:
                return IOUtils.makeUserFormBody();
            case CREATE:
                webService.doSomething();
                return IOUtils.makeIndexBody();
            default:
                return IOUtils.makeIndexBody();
        }
    }

    public static Action checkUrl(String url) {
        if(url.contains(USER_FORM)) {
            return Action.USER_FORM;
        }

        if(url.contains(CREATE)) {
            return Action.CREATE;
        }

        return Action.INDEX;
    }
}
