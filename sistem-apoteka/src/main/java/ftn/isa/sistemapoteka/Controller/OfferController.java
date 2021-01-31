package ftn.isa.sistemapoteka.Controller;

import ftn.isa.sistemapoteka.Service.OfferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("offers")
public class OfferController {

    @Autowired
    private OfferService offerService;
}
