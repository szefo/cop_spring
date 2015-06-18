package com.caveofprogramming.spring.web.controllers;

import com.caveofprogramming.spring.web.dao.Offer;
import com.caveofprogramming.spring.web.service.OffersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@Controller
public class OffersController {

    private OffersService offersService;

    @Autowired
    public void setOffersService(OffersService offersService) {
        this.offersService = offersService;
    }

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public String showTest(Model model, @RequestParam("id") String id) {
        System.out.println("Id is: " + id);
        return "home";
    }

    @RequestMapping("/offers")
    public String showOffers(Model model) {

        //offersService.throwTestException();

        List<Offer> offers = offersService.getCurrent();

        model.addAttribute("offers", offers);

        return "offers";
    }

    @RequestMapping("/createoffer")
    public String createOffer(Model model, Principal principal) {
        Offer offer = null;

        // if user is logged then we are getting his offer
        if (principal != null) {
            String username = principal.getName();
            offer = offersService.getOffer(username);
        }

        // when uer hasn't offer we are creating one
        if (offer == null){
            offer = new Offer();
        }

        model.addAttribute("offer", offer);

        return "createoffer";
    }

    @RequestMapping(value = "/docreate", method = RequestMethod.POST)
    public String doCreate(Model model, @Valid Offer offer, BindingResult result,
                           Principal principal) {

        if (result.hasErrors()) {
            return "createoffer";
        }
        String username = principal.getName();
        offer.getUser().setUsername(username);

        offersService.saveOrUpdate(offer);


        return "offercreated";
    }

    /**
     @ExceptionHandler(DataAccessException.class)
     public String handleDatabaseException(DataAccessException ex) {
     return "error";
     }
     */

}