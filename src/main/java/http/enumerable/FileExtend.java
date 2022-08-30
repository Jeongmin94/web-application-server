package http.enumerable;

public enum FileExtend {
    HTML(".html"),
    JS(".js"),
    ICO(".ico"),
    CSS(".css"),
    WOFF(".woff");

    private final String value;

    FileExtend(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}
