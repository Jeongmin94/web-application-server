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

    public int getBodyLength() {
        return this.body.length;
    }

    public byte[] getBody() {
        return this.body;
    }

    public StatusCode getStatusCode() {
        return this.statusCode;
    }
}
