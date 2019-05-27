package ticketoffice.model.enums;

public enum Role {
    USER("user"),
    ADMIN("admin"),
    GUEST("guest");

    private String role;

    Role(String s) {
        role = s;
    }

    public String getRole() {
        return role;
    }
}
