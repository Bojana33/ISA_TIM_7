package ftn.isa.sistemapoteka.service.impl;

import ftn.isa.sistemapoteka.email.EmailSender;
import ftn.isa.sistemapoteka.model.*;
import ftn.isa.sistemapoteka.repository.OrderFormRepository;
import ftn.isa.sistemapoteka.service.DrugService;
import ftn.isa.sistemapoteka.service.OrderFormService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Set;

@Service
public class OrderFormServiceImpl implements OrderFormService {

    private final OrderFormRepository orderFormRepository;

    private EmailSender emailSender;

    private DrugService drugService;

    @Autowired
    public OrderFormServiceImpl(OrderFormRepository orderFormRepository){
        this.orderFormRepository = orderFormRepository;
    }

    @Override
    public OrderForm saveOrderForm(OrderForm orderForm) {
        if(orderForm.getOfferDueDate().isBefore(LocalDateTime.now()))
        {
            throw new IllegalStateException("Rok mora biti kasniji");
        }
        Set<Drug> drugsOF = orderForm.getDrugs();
        Set<Drug> drugsPH = orderForm.getPharmacy().getDrugs();
        boolean found;
        for (Drug drugOF :
                drugsOF) {
            found=false;
            for (Drug drugPH:
                 drugsPH) {
                if(drugOF.getCode().equals(drugPH.getCode()))
                {
                    found=true;
                    break;
                }
            }
            if(!found)
            {
                drugOF.setQuantity(0);
                this.drugService.addToPharmacy(drugOF, orderForm.getPharmacy().getId());
                System.out.println("Dodat lek");
                System.out.println(drugOF.getCode());

            }
        }

        return this.orderFormRepository.save(orderForm);
    }

    @Override
    public Set<Offer> getOffers(OrderForm orderForm) {
        return orderForm.getOffers();
    }

    @Override
    public Set<OrderForm> getOrderForms(Pharmacy pharmacy) {
        return pharmacy.getOrderForms();
    }

    @Override
    public Offer acceptOffer(Offer offer) {
        offer.setOfferStatus(OfferStatus.ACCEPTED);

        Set<Offer> offers = offer.getOrderForm().getOffers();

        for (Offer o :
                offers) {
            o.setOfferStatus(OfferStatus.REJECTED);
        }

        emailSender.send(offer.getSupplier().getEmail(), buildEmail(offer.getOrderForm(), offer));
        return offer;
    }

    @Override
    public String buildEmail(OrderForm orderForm, Offer offer) {

        return "<div> \n" +
                "<p style=\"margin: 0 0 20px 0; font-size: 19px; line-height: 25px; color: #0b0c0c\"> \n" +
                "Order for " + orderForm.getPharmacy().getName() + ", due for " + orderForm.getOfferDueDate().toString() + " has been " + offer.getOfferStatus().toString() + ". Your offer was " + offer.getTotalPrice() + ". </p>\n" +
                "</div>";
    }

    @Override
    public void deleteOrderForm(OrderForm orderForm) {
        if (orderForm.getOffers().isEmpty()) {
            this.orderFormRepository.delete(orderForm);
        } else {
            throw new IllegalStateException("Postoje ponude u formi!");
        }
    }

    @Override
    public OrderForm updateOrderForm(OrderForm orderForm) {
        if (orderForm.getOffers().isEmpty()) {
            OrderForm newForm = new OrderForm();
            newForm.setCreator(orderForm.getCreator());
            newForm.setDrugs(orderForm.getDrugs());
            newForm.setQuantity(orderForm.getQuantity());
            newForm.setOffers(orderForm.getOffers());
            newForm.setPharmacy(orderForm.getPharmacy());
            newForm.setOfferDueDate(orderForm.getOfferDueDate());

            this.orderFormRepository.save(newForm);
        } else {
            throw new IllegalStateException("Postoje ponude u formi!");
        }
        return orderForm;
    }


}
