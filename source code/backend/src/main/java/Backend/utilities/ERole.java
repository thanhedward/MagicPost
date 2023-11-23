package Backend.utilities;

public enum ERole {
    ROLE_CEO("ROLE_CEO"), ROLE_BRANCH_MANAGER("ROLE_BRANCH_MANAGER"), ROLE_TRANSACTION_CLERK("ROLE_TRANSACTION_CLERK"),
    ROLE_WAREHOUSE_MANAGER("ROLE_WAREHOUSE_MANAGER"), ROLE_WAREHOUSE_CLERK("ROLE_WAREHOUSE_CLERK"), ROLE_USER("ROLE_USER");

    private final String type;

    private ERole(String type) {
        this.type = type;
    }

    public String toString() {
        return this.type;
    }
}