package ftn.isa.sistemapoteka.service.impl;

import ftn.isa.sistemapoteka.model.OrderForm;
import ftn.isa.sistemapoteka.repository.OrderFormRepository;
import ftn.isa.sistemapoteka.service.OrderFormService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderFormServiceImpl implements OrderFormService {

    private OrderFormRepository orderFormRepository;

    @Autowired
    public OrderFormServiceImpl(OrderFormRepository orderFormRepository){
        this.orderFormRepository = orderFormRepository;
    }


    @Override
    public List<OrderForm> findAllOrderForms() {
        return this.orderFormRepository.findAll();
    }

    @Override
    public OrderForm findOne(Long id) {
        return this.orderFormRepository.getOne(id);
    }
}
