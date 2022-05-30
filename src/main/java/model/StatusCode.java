package model;

public enum StatusCode {
    OK(200), FOUND(302), NOT_FOUND(404), LOGIN_SUCCESS(200), LOGIN_FAILED(500),
    LIST(200), CSS(200);

    private int statusCode;

    StatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return statusCode;
    }
}
