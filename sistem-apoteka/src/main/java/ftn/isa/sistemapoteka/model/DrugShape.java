package ftn.isa.sistemapoteka.model;

public enum DrugShape {
    POWDER("Powder"),
    CAPSULE("Capsule"),
    PILL("Pill"),
    OINTMENT("Ointment"),
    PASTE("Paste"),
    GEL("Gel"),
    DILUTION("Dilution"),
    SYRUP("Syrup");

    private final String displayName;

    DrugShape(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
