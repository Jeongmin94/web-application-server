package controller;


import model.RequestInfo;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
    public void makeBody_Hello() throws IOException {
        requestInfo = new RequestInfo("GET", "/hello", "HTTP/1.1");
        controller = new Controller(requestInfo);

        byte[] actual = controller.makeBody();
        byte[] helloBody = "Hello World".getBytes();

        assertThat(actual.length, is(equalTo(helloBody.length)));
        assertThat(actual, is(equalTo(helloBody)));
    }

    @Test
    public void makeBody_index() throws IOException {
        requestInfo = new RequestInfo("GET", "/", "HTTP/1.1");
        controller = new Controller(requestInfo);

        byte[] actual = controller.makeBody();
        byte[] indexBody = Files.readAllBytes(new File("./webapp/index.html").toPath());

        assertThat(actual.length, is(indexBody.length));
        assertThat(actual, is(equalTo(indexBody)));
    }
}
