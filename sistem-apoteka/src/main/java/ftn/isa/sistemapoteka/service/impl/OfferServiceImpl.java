package ftn.isa.sistemapoteka.service.impl;

import ftn.isa.sistemapoteka.repository.OfferRepository;
import ftn.isa.sistemapoteka.service.OfferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OfferServiceImpl implements OfferService {

    private OfferRepository offerRepository;

    @Autowired
    public OfferServiceImpl(OfferRepository offerRepository){
        this.offerRepository = offerRepository;
    }
}
