package ftn.isa.sistemapoteka.Service.impl;

import ftn.isa.sistemapoteka.Repository.OfferRepository;
import ftn.isa.sistemapoteka.Service.OfferService;
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
