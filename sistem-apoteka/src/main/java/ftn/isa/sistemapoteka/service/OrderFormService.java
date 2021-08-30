package ftn.isa.sistemapoteka.service;

import ftn.isa.sistemapoteka.model.Offer;
import ftn.isa.sistemapoteka.model.OrderForm;
import ftn.isa.sistemapoteka.model.Pharmacy;

import java.util.List;
import java.util.Set;

public interface OrderFormService {

    List<OrderForm> findAllOrderForms();

    OrderForm findOne(Long id);

    OrderForm saveOrderForm(OrderForm orderForm);

    Set<Offer> getOffers(OrderForm orderForm);

    Set<OrderForm> getOrderForms(Pharmacy pharmacy);

    Offer acceptOffer(Offer offer);

    String buildEmail(OrderForm orderForm, Offer offer);

    void deleteOrderForm(OrderForm orderForm);

    OrderForm updateOrderForm(OrderForm orderForm);
}
