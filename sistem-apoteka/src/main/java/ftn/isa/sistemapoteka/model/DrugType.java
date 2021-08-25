package ftn.isa.sistemapoteka.model;

public enum DrugType { PAINKILLER("Painkiller"),
    ANTIBIOTIC("Antibiotic"),
    ANESTHETIC("Anesthetic"),
    ANTIHISTAMINE("Antihistamine");

    private final String displayName;

    DrugType(String displayName){
        this.displayName = displayName;
    }

    public String getDisplayName(){
        return displayName;
    }
}
