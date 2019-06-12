package ticketoffice.model.enums;

/**
 * Enum of available web-roles for users
 */
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
