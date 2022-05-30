package controller;

public enum Action {
    INDEX("/index.html"), USER_FORM("/user/form.html"), CREATE("/create"), LOGIN("/user/login.html"),
    POST_LOGIN("/user/login.html"), LIST("/user/list"), CSS("/css/style.css");

    private String path;

    Action(String path) {
        this.path = path;
    }

    public String getPath() {
        return this.path;
    }
}
