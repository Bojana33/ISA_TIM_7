package ftn.isa.sistemapoteka.service.impl;

import ftn.isa.sistemapoteka.model.Drug;
import ftn.isa.sistemapoteka.model.OrderForm;
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
                }
            }
            if(!found)
            {
                drugOF.setQuantity(0);
                drugService.addToPharmacy(drugOF, orderForm.getPharmacy().getId());
                System.out.println("Dodat lek");
                System.out.println(drugOF.getCode());

            }
        }

        return this.orderFormRepository.save(orderForm);
    }
}
