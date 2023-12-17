package Backend.utilities;

public enum ParcelStatus {

    START_POS("START_POS"), FIRST_DEPOT("FIRST_DEPOT"), LAST_DEPOT("LAST_DEPOT"),
    END_POS("END_POS"), SUCCESS("SUCCESS"), FAIL("FAIL");
    private final String type;

    private ParcelStatus(String type) {
        this.type = type;
    }

    public String toString() {
        return this.type;
    }
}