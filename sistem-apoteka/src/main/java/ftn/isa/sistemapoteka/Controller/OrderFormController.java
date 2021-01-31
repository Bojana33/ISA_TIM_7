package ftn.isa.sistemapoteka.Controller;

import ftn.isa.sistemapoteka.Service.OrderFormService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("orderForms")
public class OrderFormController {

    @Autowired
    private OrderFormService orderFormService;
}
