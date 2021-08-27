package ftn.isa.sistemapoteka.service;

import ftn.isa.sistemapoteka.model.OrderForm;

import java.util.List;

public interface OrderFormService {

    List<OrderForm> findAllOrderForms();

    OrderForm findOne(Long id);
}
