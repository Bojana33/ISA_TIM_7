package ftn.isa.sistemapoteka.service.impl;

import ftn.isa.sistemapoteka.repository.OrderFormRepository;
import ftn.isa.sistemapoteka.service.OrderFormService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderFormServiceImpl implements OrderFormService {

    private OrderFormRepository orderFormRepository;

    @Autowired
    public OrderFormServiceImpl(OrderFormRepository orderFormRepository){
        this.orderFormRepository = orderFormRepository;
    }
}
