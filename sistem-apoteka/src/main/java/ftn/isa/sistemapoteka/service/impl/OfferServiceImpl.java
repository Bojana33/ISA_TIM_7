package ftn.isa.sistemapoteka.service.impl;

import ftn.isa.sistemapoteka.model.Offer;
import ftn.isa.sistemapoteka.model.OfferStatus;
import ftn.isa.sistemapoteka.model.Supplier;
import ftn.isa.sistemapoteka.repository.OfferRepository;
import ftn.isa.sistemapoteka.service.OfferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class OfferServiceImpl implements OfferService {

    private final OfferRepository offerRepository;

    @Autowired
    public OfferServiceImpl(OfferRepository offerRepository) {
        this.offerRepository = offerRepository;
    }

    @Override
    public Offer saveOffer(Offer offer) throws Exception {
        if (this.offerRepository.findBySupplierAndOrderForm(offer.getSupplier(), offer.getOrderForm()) != null) {
            throw new Exception("You already gave your offer for this order form!");
        }
        Offer o = new Offer();
        o.setTotalPrice(offer.getTotalPrice());
        o.setSupplier(offer.getSupplier());
        o.setDeliver_due_date(offer.getDeliver_due_date());
        o.setOrderForm(offer.getOrderForm());

        return this.offerRepository.save(o);
    }

    @Override
    public List<Offer> findAllBySupplier(Supplier supplier) {
        return this.offerRepository.findAllBySupplier(supplier);
    }

    @Override
    public Offer findOne(Long id) {
        return this.offerRepository.getOne(id);
    }

    @Override
    public Offer changeOffer(Offer offer) throws Exception {
        Offer o = this.findOne(offer.getId());
        if (o.getOrderForm().getOfferDueDate().isBefore(LocalDateTime.now())) {
            throw new Exception("Offer Due Date has past. You can't change your offer now!");
        }
        o.setTotalPrice(offer.getTotalPrice());
        o.setDeliver_due_date(offer.getDeliver_due_date());
        return this.offerRepository.save(o);
    }

    @Override
    public List<Offer> findByOfferStatusAndSupplier(OfferStatus offerStatus, Supplier supplier) {
        return this.offerRepository.findAllByOfferStatusAndSupplier(offerStatus, supplier);
    }
}
