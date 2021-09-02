package ftn.isa.sistemapoteka.controller;

import ftn.isa.sistemapoteka.service.impl.OrderFormServiceImpl;
import ftn.isa.sistemapoteka.service.impl.UserServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("orderForms")
public class OrderFormController {

    private final OrderFormServiceImpl orderFormService;
    private final UserServiceImpl userService;

    public OrderFormController(OrderFormServiceImpl orderFormService, UserServiceImpl userService) {
        this.orderFormService = orderFormService;
        this.userService = userService;
    }

    @GetMapping("/allOrderForms")
    public ModelAndView getOrderForms(Model model) {
        model.addAttribute("orderForms", this.orderFormService.findAllOrderForms());
        return new ModelAndView("order-forms");
    }

  /*  @GetMapping("/check/{orderFormId}")
    @PreAuthorize("hasRole('SUPPLIER')")
    public ModelAndView checkDrugs(Model model, Authentication authentication, @PathVariable Long orderFormId) {
        List<Integer> hasAll = new ArrayList<>();
        OrderForm orderForm = this.orderFormService.findOne(orderFormId);
        boolean giveOffer;
        User user = this.userService.findByEmail(authentication.getName());
        Supplier supplier = (Supplier) user;
        Map<Long, Integer> drugs = orderForm.getDrugs();
        for (Map.Entry<Long, Integer> entry : drugs.entrySet()) {
            Long code = entry.getKey();
            System.out.println(code);
            Integer quantity = entry.getValue();
            System.out.println(quantity);
            if (!supplier.getDrugs().containsKey(code)) {
                hasAll.add(0);
            } else {
                if (supplier.getDrugs().get(code) >= quantity) {
                    hasAll.add(1);
                } else {
                    hasAll.add(0);
                }
            }
        }
        giveOffer = !hasAll.contains(0);
        System.out.println(hasAll);
        System.out.println(giveOffer);
        System.out.println(authentication.getName());
        model.addAttribute("giveOffer", giveOffer);
        if (giveOffer) {
            return new ModelAndView("redirect:/offers/giveOffer/" + orderForm.getId());
        }
        return new ModelAndView("check-drugs");
    }*/
}
