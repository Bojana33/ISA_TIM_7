package ftn.isa.sistemapoteka.Service.impl;

import ftn.isa.sistemapoteka.Repository.OrderFormRepository;
import ftn.isa.sistemapoteka.Service.OrderFormService;
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
