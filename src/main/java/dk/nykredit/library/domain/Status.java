package dk.nykredit.library.domain;

public enum Status {
    READ("read"), UNREAD("unread");

    private String value;

    private Status(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
