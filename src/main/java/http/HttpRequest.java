package http;

import http.enumerable.FileExtend;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.HttpRequestUtils;
import util.IOUtils;
import util.SessionUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Optional;

public class HttpRequest {
    private static final Logger log = LoggerFactory.getLogger(HttpRequest.class);

    private static final String NON_EXTEND = "";
    private static final String HTML_EXTEND = ".html";

    private RequestLine requestLine;

    private HttpHeaders headers;

    private HttpCookies cookies;

    private final RequestParams requestParams = new RequestParams();

    private String extend;

    public HttpRequest(InputStream is) {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            requestLine = new RequestLine(createRequestLine(br));
            extend = processFileExtend(requestLine);
            requestParams.addQueryString(requestLine.getQueryString());
            headers = processHeaders(br);
            cookies = processCookies(headers);
            requestParams.addBody(IOUtils.readData(br, headers.getContentLength()));
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    private String createRequestLine(BufferedReader br) throws IOException {
        String line = br.readLine();
        if (line == null) {
            throw new IllegalStateException();
        }
        return line;
    }

    private HttpHeaders processHeaders(BufferedReader br) throws IOException {
        HttpHeaders newHeaders = new HttpHeaders();
        String line;
        while (!(line = br.readLine()).equals("")) {
            newHeaders.add(line);
        }
        return newHeaders;
    }

    private String processFileExtend(RequestLine requestLine) {
        Optional<FileExtend> extend = Arrays.stream(FileExtend.values())
                .filter(it -> requestLine.getPath().endsWith(it.getValue()))
                .findAny();

        if (extend.isPresent()) {
            return extend.get().getValue();
        }

        return NON_EXTEND;
    }

    private HttpCookies processCookies(HttpHeaders headers) {
        HttpCookies newCookies = new HttpCookies();
        String cookieValue = headers.getHeader("Cookie");

        HttpRequestUtils.parseCookies(cookieValue)
                .forEach(newCookies::addCookie);

        return newCookies;
    }

    public HttpMethod getMethod() {
        return requestLine.getMethod();
    }

    public String getPath() {
        return requestLine.getPath();
    }

    public String getHeader(String name) {
        return headers.getHeader(name);
    }

    public String getParameter(String name) {
        return requestParams.getParameter(name);
    }

    public boolean isSessionExist() {
        return cookies.isSessionExist(SessionUtils.JSESSIONID);
    }

    public boolean isHtmlExtend() {
        return !extend.equals(NON_EXTEND) && extend.equals(HTML_EXTEND);
    }

    public String getCookieValue(String name) {
        return cookies.getCookie(name);
    }

}
