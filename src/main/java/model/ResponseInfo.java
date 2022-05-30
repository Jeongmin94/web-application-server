package model;

import util.IOUtils;

import java.io.IOException;

public class ResponseInfo {
    private byte[] body;
    private StatusCode statusCode;

    public ResponseInfo() throws IOException {
        this.body = IOUtils.makeIndexBody();
        this.statusCode = StatusCode.OK;
    }

    public ResponseInfo(byte[] body, StatusCode statusCode) {
        this.body = body;
        this.statusCode = statusCode;
    }

    public ResponseInfo(StatusCode statusCode) throws IOException {
        this.body = IOUtils.makeIndexBody();
        this.statusCode = statusCode;
    }

    public int getBodyLength() {
        return this.body.length;
    }

    public byte[] getBody() {
        return this.body;
    }

    public void setBody(byte[] body) {
        this.body = body;
    }

    public StatusCode getStatusCode() {
        return this.statusCode;
    }
}
