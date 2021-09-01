package ftn.isa.sistemapoteka.model;

public enum ComplaintType { DERMATOLOGIST("Dermatologist"),
    PHARMACIST("Pharmacist"),
    PHARMACY("Pharmacy");

    private final String displayName;

    ComplaintType(String displayName){
        this.displayName = displayName;
    }

    public String getDisplayName(){
        return displayName;
    }
}
