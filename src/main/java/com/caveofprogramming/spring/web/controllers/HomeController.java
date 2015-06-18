package com.caveofprogramming.spring.web.controllers;

import com.caveofprogramming.spring.web.dao.Offer;
import com.caveofprogramming.spring.web.service.OffersService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.List;

@Controller
public class HomeController {

	private static Logger logger = Logger.getLogger(HomeController.class);

	@Autowired
	private OffersService offersService;
	
	@RequestMapping("/")
	public String showHome(Model model, Principal principal) {
		// getting all offers
		List<Offer> offers = offersService.getCurrent();
		// adding these offers to model
		model.addAttribute("offers", offers);

		boolean hasOffer = false;

		if (principal != null){
			// get logged in username by a principal
			// and checking if that user has an offer
			hasOffer = offersService.hasOffer(principal.getName());
		}
		// adding that to the model
		model.addAttribute("hasOffer", hasOffer);


		return "home";
	}

}
