package Backend.utilities;

public enum ELocationType {
    WAREHOUSE("WAREHOUSE"), TRANSACTION_OFFICE("TRANSACTION_OFFICE");

    private final String type;
    private ELocationType(String type) {this.type = type;}
    public String toString() {return this.type;}
}
