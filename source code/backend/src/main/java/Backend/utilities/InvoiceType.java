package Backend.utilities;

public enum InvoiceType {

    POST_OFFICE_TO_DEPOT("POST_OFFICE_TO_DEPOT"),
    DEPOT_TO_DEPOT("DEPOT_TO_DEPOT"),
    DEPOT_TO_POST_OFFICE("DEPOT_TO_POST_OFFICE"),
    POST_OFFICE_TO_HOME("POST_OFFICE_TO_HOME");
    private final String type;

    private InvoiceType(String type) {
        this.type = type;
    }

    public String toString() {
        return this.type;
    }
}