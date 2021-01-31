package ftn.isa.sistemapoteka.Controller;

import ftn.isa.sistemapoteka.Service.OrderFormService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderFormController {

    @Autowired
    private OrderFormService orderFormService;
}
