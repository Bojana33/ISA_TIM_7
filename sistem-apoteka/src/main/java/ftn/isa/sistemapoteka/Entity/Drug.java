package ftn.isa.sistemapoteka.Entity;

import org.springframework.security.access.prepost.PreInvocationAuthorizationAdviceVoter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Drug implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    @NotEmpty(message = "This field can not be empty!")
    private Long code;

    @Column
    private String name;

    @Column
    private String contraindications;

    @Column
    private String structure;

    @Column
    private int dailyIntake;

    @Column
    private Boolean reserved;

    @Column
    private String producer;

    @Column
    private Boolean onPrescription;

    @Column
    private String additionalNote;

    @Column
    private float loyaltyPoints;

    @Column
    private int quantity;

    @Column
    private float price;

    @ManyToMany(targetEntity = Drug.class)
    @JoinTable(name = "ReplacementDrugs", joinColumns = @JoinColumn(name = "DrugId", referencedColumnName = "id")
            , inverseJoinColumns = @JoinColumn(name = "ReplacementDrugId", referencedColumnName = "id"))
    private Set<Drug> replacementDrugs = new HashSet<>();

    @Column
    private DrugType drugType;

    @Column
    private DrugShape drugShape;

    public Drug() {
    }

    public Drug(@NotEmpty(message = "This field can not be empty!") Long code, String name, String contraindications, String structure, int dailyIntake, Boolean reserved, String producer, Boolean onPrescription, String additionalNote, float loyaltyPoints, int quantity, float price, DrugType drugType, DrugShape drugShape) {
        this.code = code;
        this.name = name;
        this.contraindications = contraindications;
        this.structure = structure;
        this.dailyIntake = dailyIntake;
        this.reserved = reserved;
        this.producer = producer;
        this.onPrescription = onPrescription;
        this.additionalNote = additionalNote;
        this.loyaltyPoints = loyaltyPoints;
        this.quantity = quantity;
        this.price = price;
        this.drugType = drugType;
        this.drugShape = drugShape;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCode() {
        return code;
    }

    public void setCode(Long code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContraindications() {
        return contraindications;
    }

    public void setContraindications(String contraindications) {
        this.contraindications = contraindications;
    }

    public String getStructure() {
        return structure;
    }

    public void setStructure(String structure) {
        this.structure = structure;
    }

    public int getDailyIntake() {
        return dailyIntake;
    }

    public void setDailyIntake(int dailyIntake) {
        this.dailyIntake = dailyIntake;
    }

    public Boolean getReserved() {
        return reserved;
    }

    public void setReserved(Boolean reserved) {
        this.reserved = reserved;
    }

    public String getProducer() {
        return producer;
    }

    public void setProducer(String producer) {
        this.producer = producer;
    }

    public Boolean getOnPrescription() {
        return onPrescription;
    }

    public void setOnPrescription(Boolean onPrescription) {
        this.onPrescription = onPrescription;
    }

    public String getAdditionalNote() {
        return additionalNote;
    }

    public void setAdditionalNote(String additionalNote) {
        this.additionalNote = additionalNote;
    }

    public float getLoyaltyPoints() {
        return loyaltyPoints;
    }

    public void setLoyaltyPoints(float loyaltyPoints) {
        this.loyaltyPoints = loyaltyPoints;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public Set<Drug> getReplacementDrugs() {
        return replacementDrugs;
    }

    public void setReplacementDrugs(Set<Drug> replacementDrugs) {
        this.replacementDrugs = replacementDrugs;
    }

    public DrugType getDrugType() {
        return drugType;
    }

    public void setDrugType(DrugType drugType) {
        this.drugType = drugType;
    }

    public DrugShape getDrugShape() {
        return drugShape;
    }

    public void setDrugShape(DrugShape drugShape) {
        this.drugShape = drugShape;
    }

}
