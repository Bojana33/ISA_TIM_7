package ftn.isa.sistemapoteka.Entity;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
public class eRecipe {

    @Id
    private Long code;

    @Column
    private LocalDateTime dateOfIssue;

    @OneToMany(mappedBy = "eRecipe", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Drug> drugs = new HashSet<>();

    public Long getCode() {
        return code;
    }

    public void setCode(Long code) {
        this.code = code;
    }

    public LocalDateTime getDateOfIssue() {
        return dateOfIssue;
    }

    public void setDateOfIssue(LocalDateTime dateOfIssue) {
        this.dateOfIssue = dateOfIssue;
    }

    public Set<Drug> getDrugs() {
        return drugs;
    }

    public void setDrugs(Set<Drug> drugs) {
        this.drugs = drugs;
    }
}
