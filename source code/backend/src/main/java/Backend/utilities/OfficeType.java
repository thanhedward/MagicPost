package Backend.utilities;

public enum OfficeType {
    DEPOT("DEPOT"), POST_OFFICE("POST_OFFICE");

    private final String type;
    private OfficeType(String type) {this.type = type;}
    public String toString() {return this.type;}
}
