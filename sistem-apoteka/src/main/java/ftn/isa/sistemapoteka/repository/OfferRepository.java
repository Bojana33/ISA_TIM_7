package ftn.isa.sistemapoteka.repository;

import ftn.isa.sistemapoteka.model.Offer;
import ftn.isa.sistemapoteka.model.OfferStatus;
import ftn.isa.sistemapoteka.model.OrderForm;
import ftn.isa.sistemapoteka.model.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OfferRepository extends JpaRepository<Offer, Long> {

    Offer findBySupplierAndOrderForm(Supplier supplier, OrderForm orderForm);

    List<Offer> findAllBySupplier(Supplier supplier);

    List<Offer> findAllByOfferStatusAndSupplier(OfferStatus offerStatus, Supplier supplier);
}
