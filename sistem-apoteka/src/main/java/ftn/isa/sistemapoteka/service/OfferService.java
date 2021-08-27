package ftn.isa.sistemapoteka.service;

import ftn.isa.sistemapoteka.model.Offer;
import ftn.isa.sistemapoteka.model.OfferStatus;
import ftn.isa.sistemapoteka.model.Supplier;

import java.util.List;

public interface OfferService {

    Offer saveOffer(Offer offer) throws Exception;

    List<Offer> findAllBySupplier(Supplier supplier);

    Offer findOne(Long id);

    Offer changeOffer(Offer offer) throws Exception;

    List<Offer> findByOfferStatusAndSupplier(OfferStatus offerStatus, Supplier supplier);
}
