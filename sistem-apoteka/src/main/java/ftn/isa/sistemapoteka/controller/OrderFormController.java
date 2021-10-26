package ftn.isa.sistemapoteka.controller;

import ftn.isa.sistemapoteka.service.OrderFormService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("orderForms")
public class OrderFormController {

    private OrderFormService orderFormService;

    public OrderFormController(OrderFormService orderFormService){
        this.orderFormService = orderFormService;
    }
}
