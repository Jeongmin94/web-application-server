package controller;

public enum Action {
    INDEX("/index.html"), USER_FORM("/user/form.html"), CREATE("/create");

    private String path;

    Action(String path) {
        this.path = path;
    }

    public String getPath() {
        return this.path;
    }
}
