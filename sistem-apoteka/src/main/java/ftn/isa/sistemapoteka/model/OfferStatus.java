package ftn.isa.sistemapoteka.model;

public enum OfferStatus { ACCEPTED("Accepted"),
    REJECTED("Rejected"),
    ON_HOLD("On hold");
    private final String displayName;

    OfferStatus(String displayName){
        this.displayName = displayName;
    }

    public String getDisplayName(){
        return displayName;
    }
}
