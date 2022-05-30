package webserver;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Collection;

import controller.Controller;
import db.DataBase;
import model.RequestInfo;
import model.ResponseInfo;
import model.StatusCode;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.*;
import util.HttpRequestUtils;
import util.HttpRequestUtils.Pair;
import util.IOUtils;

import javax.xml.crypto.Data;

public class RequestHandler extends Thread {
    private static final Logger log = LoggerFactory.getLogger(RequestHandler.class);

    private Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        log.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            // TODO 사용자 요청에 대한 처리는 이 곳에 구현하면 된다.

            RequestInfo requestInfo = parseRequest(in);
            DataOutputStream dos = new DataOutputStream(out);
            WebService webService = checkService(requestInfo);

            Controller controller =  new Controller(requestInfo, webService);
            ResponseInfo responseInfo = controller.makeBody();

            responseHeader(dos, responseInfo);
            responseBody(dos, responseInfo.getBody());
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    private void response500LoginFailedHeader(DataOutputStream dos) {
        try {
            dos.writeBytes("HTTP/1.1 500 Internal Server Error\r\n");
            dos.writeBytes("Content-Type: text/html\r\n");
            dos.writeBytes("Set-Cookie: logined=false\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    private void response200LoginSuccessHeader(DataOutputStream dos) {
        try {
            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            dos.writeBytes("Content-Type: text/html\r\n");
            dos.writeBytes("Set-Cookie: logined=true\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    private void response200Header(DataOutputStream dos, int lengthOfBodyContent) {
        try {
            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            dos.writeBytes("Content-Type: text/html;charset=utf-8\r\n");
            dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    private void response302Header(DataOutputStream dos, int lengthOfBodyContent) {
        try {
            dos.writeBytes("HTTP/1.1 302 Found\r\n");
            dos.writeBytes("Location: http://localhost:8080/webapp/index.html\r\n");
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    private void response200CssHeader(DataOutputStream dos, int lengthOfBodyContent) {
        try {
            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            dos.writeBytes("Content-Type: text/css;charset=utf-8\r\n");
            dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    private void response200ListHeader(DataOutputStream dos, ResponseInfo responseInfo) {
        try {
            Collection<User> users = DataBase.findAll();

            StringBuilder sb = new StringBuilder();
            sb.append("<table border='1'>");
            for(User user : users) {
                sb.append("<tr>");
                sb.append("<td>" + user.getUserId() + "</td>");
                sb.append("<td>" + user.getName() + "</td>");
                sb.append("<td>" + user.getEmail() + "</td>");
                sb.append("</tr");
            }
            sb.append("</table>");
            byte[] body = sb.toString().getBytes(StandardCharsets.UTF_8);
            responseInfo.setBody(body);

            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            dos.writeBytes("Content-Type: text/html;charset=utf-8\r\n");
            dos.writeBytes("Content-Length: " + body.length + "\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    private void responseHeader(DataOutputStream dos, ResponseInfo responseInfo) {
        byte[] body = responseInfo.getBody();
        StatusCode statusCode = responseInfo.getStatusCode();

        switch (statusCode) {
            case FOUND:
                response302Header(dos, body.length);
                break;
            case LOGIN_SUCCESS:
                response200LoginSuccessHeader(dos);
                break;
            case LOGIN_FAILED:
                response500LoginFailedHeader(dos);
                break;
            case LIST:
                response200ListHeader(dos, responseInfo);
                break;
            case CSS:
                response200CssHeader(dos, body.length);
                break;
            default:
                response200Header(dos, body.length);
        }
    }

    private void responseBody(DataOutputStream dos, byte[] body) {
        try {
            dos.write(body, 0, body.length);
            dos.flush();
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    private RequestInfo parseRequest(InputStream in) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
        String line = bufferedReader.readLine();
        String[] tokens = line.split(" ");

        RequestInfo requestInfo = new RequestInfo(tokens[0], tokens[1], tokens[2]);

        log.debug(line);
        if(requestInfo.getMethod().equals("GET")) {
            parserGetMethod(requestInfo, line, bufferedReader);
        }

        if(requestInfo.getMethod().equals("POST")) {
            parsePostMethod(requestInfo, line, bufferedReader);
        }

        return requestInfo;
    }

    private void parserGetMethod(RequestInfo requestInfo, String line, BufferedReader bufferedReader) throws IOException {
        while ((line = bufferedReader.readLine()) != null) {
            if (line.isEmpty()) {
                break;
            }
            log.debug(line);
            Pair pair = HttpRequestUtils.parseHeader(line);
            requestInfo.putHeader(pair.getKey(), pair.getValue());
        }
    }

    private void parsePostMethod(RequestInfo requestInfo, String line, BufferedReader bufferedReader) throws IOException {
        while ((line = bufferedReader.readLine()) != null) {
            if (line.isEmpty()) {
                break;
            }

            log.debug(line);
            Pair pair = HttpRequestUtils.parseHeader(line);
            requestInfo.putHeader(pair.getKey(), pair.getValue());
        }

        String contentLength = requestInfo.getHeader("Content-Length");
        String body = IOUtils.readData(bufferedReader, Integer.parseInt(contentLength));

        requestInfo.setBody(body);
    }

    private WebService checkService(RequestInfo requestInfo) {
        String url = requestInfo.getUrl();
        String method = requestInfo.getMethod();

        if(url.contains(HttpRequestUtils.CREATE)) {
            return new CreateService(requestInfo);
        }

        if(url.contains(HttpRequestUtils.LOGIN)) {
            if(method.contains(HttpRequestUtils.GET)) {
                return new BaseService();
            }

            if(method.contains(HttpRequestUtils.POST)) {
                return new LoginService(requestInfo);
            }
        }

        if(url.contains(HttpRequestUtils.LIST)) {
            return new ListService(requestInfo);
        }

        if(url.endsWith(".css")) {
            return new CssService(requestInfo);
        }

        return new BaseService();
    }
}
