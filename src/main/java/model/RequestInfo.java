package model;

import java.util.HashMap;
import java.util.Map;

public class RequestInfo {
    private String method;
    private String url;
    private String version;
    private Map<String, String> headerMap;
    private String body;

    public RequestInfo(String method, String url, String version) {
        this.method = method;
        this.url = url;
        this.version = version;

        headerMap = new HashMap<>();
    }

    public void putHeader(String key, String value) {
        headerMap.put(key, value);
    }

    public String getUrl() {
        return this.url;
    }

    public String getMethod() {
        return this.method;
    }

    public String getHeader(String key) {
        return headerMap.get(key);
    }

    public Map<String, String> getHeaderMap() {
        return this.headerMap;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getBody() {
        return this.body;
    }
}
