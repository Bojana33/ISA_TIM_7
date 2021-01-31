package ftn.isa.sistemapoteka.Repository;

import ftn.isa.sistemapoteka.Entity.Offer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OfferRepository extends JpaRepository<Offer,Long> {
}
