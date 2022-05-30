package controller;

import model.RequestInfo;
import model.ResponseInfo;
import model.StatusCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.WebService;
import util.HttpRequestUtils;
import util.IOUtils;

import javax.swing.text.html.CSS;
import java.io.IOException;

public class Controller {
    private static final Logger log = LoggerFactory.getLogger(Controller.class);
    private static final String WEBAPP = "./webapp";
    private static final String INDEX = "/index.html";
    private static final String USER_FORM = "/user/form.html";
    private static final String CREATE = "/user/create";
    private static final String LOGIN = "/user/login";
    private static final String LIST = "/user/list";
    private static final String CSS = "/css/style.css";

    private RequestInfo requestInfo;

    private WebService webService;

    public Controller(RequestInfo requestInfo, WebService webService) {
        this.requestInfo = requestInfo;
        this.webService = webService;
    }

    public ResponseInfo makeBody() throws IOException, RuntimeException {
        try {
            switch (checkUrl(requestInfo)) {
                case LIST:
                    webService.doSomething();
                    return new ResponseInfo(StatusCode.LIST);
                case LOGIN:
                    return new ResponseInfo(IOUtils.makeLoginFormBody(), StatusCode.OK);
                case POST_LOGIN:
                    webService.doSomething();
                    return new ResponseInfo(StatusCode.LOGIN_SUCCESS);
                case USER_FORM:
                    return new ResponseInfo(IOUtils.makeUserFormBody(), StatusCode.OK);
                case CREATE:
                    webService.doSomething();
                    return new ResponseInfo(IOUtils.makeUserFormBody(), StatusCode.FOUND);
                case CSS:
                    return new ResponseInfo(IOUtils.makeCssFormBody(requestInfo.getUrl()), StatusCode.CSS);
                default:
                    return new ResponseInfo();
            }
        } catch (RuntimeException e) {
            String msg = e.getMessage();
            log.warn(msg);
            if(msg.contains("invalid")) {
                return new ResponseInfo(IOUtils.makeLoginFailFormBody(), StatusCode.LOGIN_FAILED);
            }
            return new ResponseInfo(IOUtils.makeLoginFailFormBody(), StatusCode.NOT_FOUND);
        }
    }

    public static Action checkUrl(RequestInfo requestInfo) {
        String url = requestInfo.getUrl();
        String method = requestInfo.getMethod();

        if(url.contains(USER_FORM)) {
            return Action.USER_FORM;
        }

        if(url.contains(CREATE)) {
            return Action.CREATE;
        }

        if(url.contains(LOGIN)) {
            if(method.contains(HttpRequestUtils.GET)) {
                return Action.LOGIN;
            }

            if(method.contains(HttpRequestUtils.POST)) {
                return Action.POST_LOGIN;
            }
        }

        if(url.contains(LIST)) {
            return Action.LIST;
        }

        if(url.endsWith(".css")) {
            return Action.CSS;
        }

        return Action.INDEX;
    }
}
