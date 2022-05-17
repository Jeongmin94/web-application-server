package controller;

import model.RequestInfo;
import model.ResponseInfo;
import model.StatusCode;
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
    private static final String LOGIN = "/user/login";

    private RequestInfo requestInfo;

    private WebService webService;

    public Controller(RequestInfo requestInfo, WebService webService) {
        this.requestInfo = requestInfo;
        this.webService = webService;
    }

    public ResponseInfo makeBody() throws IOException, RuntimeException {
        try {
            switch (checkUrl(requestInfo.getUrl())) {
                case LOGIN:
                    webService.doSomething();
                    return new ResponseInfo(IOUtils.makeLoginFormBody(), StatusCode.OK);
                case USER_FORM:
                    return new ResponseInfo(IOUtils.makeUserFormBody(), StatusCode.OK);
                case CREATE:
                    webService.doSomething();
                    return new ResponseInfo(IOUtils.makeUserFormBody(), StatusCode.FOUND);
                default:
                    return new ResponseInfo();
            }
        } catch (RuntimeException e) {
            String msg = e.getMessage();
            log.warn(msg);
            return new ResponseInfo(IOUtils.makeLoginFailFormBody(), StatusCode.OK);
        }
    }

    public static Action checkUrl(String url) {
        if(url.contains(USER_FORM)) {
            return Action.USER_FORM;
        }

        if(url.contains(CREATE)) {
            return Action.CREATE;
        }

        if(url.contains(LOGIN)) {
            return Action.LOGIN;
        }

        return Action.INDEX;
    }
}
