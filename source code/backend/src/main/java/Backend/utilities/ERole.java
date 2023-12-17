package Backend.utilities;

public enum ERole {
    ROLE_CEO("ROLE_CEO"),
    ROLE_POST_OFFICE_MANAGER("ROLE_POST_OFFICE_MANAGER"),
    ROLE_POST_OFFICE_EMPLOYEE("ROLE_POST_OFFICE_EMPLOYEE"),
    ROLE_DEPOT_MANAGER("ROLE_DEPOT_MANAGER"),
    ROLE_DEPOT_EMPLOYEE("ROLE_DEPOT_EMPLOYEE"),
    ROLE_USER("ROLE_USER");

    private final String type;

    private ERole(String type) {
        this.type = type;
    }

    public String toString() {
        return this.type;
    }
}