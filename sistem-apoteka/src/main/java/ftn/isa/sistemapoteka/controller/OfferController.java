package ftn.isa.sistemapoteka.controller;

import ftn.isa.sistemapoteka.model.Offer;
import ftn.isa.sistemapoteka.model.OfferStatus;
import ftn.isa.sistemapoteka.model.OrderForm;
import ftn.isa.sistemapoteka.model.Supplier;
import ftn.isa.sistemapoteka.service.OfferService;
import ftn.isa.sistemapoteka.service.impl.OrderFormServiceImpl;
import ftn.isa.sistemapoteka.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("offers")
public class OfferController {

    private OfferService offerService;
    private OrderFormServiceImpl orderFormService;
    private UserServiceImpl userService;

    @Autowired
    public OfferController(OfferService offerService, OrderFormServiceImpl orderFormService, UserServiceImpl userService){
        this.offerService = offerService;
        this.orderFormService = orderFormService;
        this.userService = userService;
    }

    @GetMapping("/giveOffer/{orderFormId}")
    @PreAuthorize("hasRole('SUPPLIER')")
    public ModelAndView giveOfferForm(Model model, @PathVariable Long orderFormId){
        Offer offer = new Offer();
        model.addAttribute(offer);
        model.addAttribute(orderFormId);
        return new ModelAndView("offer");
    }

    @PostMapping("/giveOffer/{orderFormId}/submit")
    @PreAuthorize("hasRole('SUPPLIER')")
    public ModelAndView giveOffer(@ModelAttribute Offer offer, @PathVariable Long orderFormId, Authentication authentication) throws Exception{
        offer.setSupplier((Supplier) this.userService.findByEmail(authentication.getName()));
        offer.setOrderForm(this.orderFormService.findOne(orderFormId));
        this.offerService.saveOffer(offer);
        return new ModelAndView("redirect:/user/supplier/home");
    }

    @GetMapping("/myOffers")
    @PreAuthorize("hasRole('SUPPLIER')")
    public ModelAndView myOffers(Model model, Authentication authentication){
        Offer offer = new Offer();
        model.addAttribute("offer",offer);
        model.addAttribute("offers",this.offerService.findAllBySupplier((Supplier) this.userService.findByEmail(authentication.getName())));
        return new ModelAndView("supplier-offers");
    }

    @GetMapping("/changeOffer/{offerId}")
    @PreAuthorize("hasRole('SUPPLIER')")
    public ModelAndView changeOfferForm(Model model, @PathVariable Long offerId){
        Offer offer = this.offerService.findOne(offerId);
        model.addAttribute(offer);
        model.addAttribute(offerId);
        return new ModelAndView("change-offer");
    }

    @PostMapping("/changeOffer/{offerId}/submit")
    @PreAuthorize("hasRole('SUPPLIER')")
    public ModelAndView changeOffer(@PathVariable Long offerId) throws Exception{
        Offer offer = this.offerService.findOne(offerId);
        this.offerService.changeOffer(offer);
        return new ModelAndView("redirect:/offer/myOffers");
    }

    @RequestMapping("/filterOffers")
    @PreAuthorize("hasRole('SUPPLIER')")
    public ModelAndView filterOffers(@ModelAttribute Offer offer, Model model, Authentication authentication){
        List<Offer> newList = this.offerService.findByOfferStatusAndSupplier(offer.getOfferStatus(),(Supplier) this.userService.findByEmail(authentication.getName()));
        model.addAttribute("offer",offer);
        model.addAttribute("offers",newList);
        return new ModelAndView("supplier-offers");
    }


}
