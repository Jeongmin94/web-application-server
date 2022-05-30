package service;

import model.RequestInfo;

public class CssService implements WebService {

    private static final String COOKIE = "Cookie";

    private RequestInfo requestInfo;

    public CssService(RequestInfo requestInfo) {
        this.requestInfo = requestInfo;
    }

    @Override
    public void doSomething() {

    }
}