package controller;


import model.RequestInfo;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.BaseService;
import service.CreateService;
import service.WebService;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

public class ControllerTest {
    private static final Logger log = LoggerFactory.getLogger(ControllerTest.class);

    private RequestInfo requestInfo;

    private Controller controller;

    @Test
    public void makeBody_index() throws IOException {
        requestInfo = new RequestInfo("GET", "/", "HTTP/1.1");
        controller = new Controller(requestInfo, new BaseService());

        byte[] actual = controller.makeBody();
        byte[] indexBody = Files.readAllBytes(new File("./webapp/index.html").toPath());

        assertThat(actual.length, is(indexBody.length));
        assertThat(actual, is(equalTo(indexBody)));
    }

    @Test
    public void createTest() throws IOException {
        requestInfo = new RequestInfo("GET", "/user/create?userId=javajigi&password=123&name=JaeSung&email=javajigi%40slipp.net", "HTTP/1.1");
        controller = new Controller(requestInfo, new CreateService(requestInfo));

        byte[] actual = controller.makeBody();
        byte[] indexBody = Files.readAllBytes(new File("./webapp/index.html").toPath());

        assertThat(actual.length, is(indexBody.length));
        assertThat(actual, is(equalTo(indexBody)));
    }

}
