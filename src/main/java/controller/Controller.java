package controller;

import model.RequestInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class Controller {
    private static final Logger log = LoggerFactory.getLogger(Controller.class);
    private static final String INDEX = "/index.html";
    private static final String WEBAPP = "./webapp";

    private RequestInfo requestInfo;

    public Controller(RequestInfo requestInfo) {
        this.requestInfo = requestInfo;
    }

    public byte[] makeBody() throws IOException {
        if(checkIndexUrl()) {
            return Files.readAllBytes(new File(WEBAPP + INDEX).toPath());
        }

        return "Hello World".getBytes();
    }

    private boolean checkIndexUrl() {
        return (requestInfo.getUrl().equals(INDEX)) || (requestInfo.getUrl().equals("/"));
    }
}
