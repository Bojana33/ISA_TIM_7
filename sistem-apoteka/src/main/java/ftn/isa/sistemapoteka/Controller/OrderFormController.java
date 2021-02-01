package ftn.isa.sistemapoteka.Controller;

import ftn.isa.sistemapoteka.Service.OrderFormService;
import org.springframework.beans.factory.annotation.Autowired;
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
