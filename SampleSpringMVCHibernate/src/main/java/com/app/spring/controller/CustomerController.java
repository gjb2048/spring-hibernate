package com.app.spring.controller;

import com.app.spring.model.Customer;
import com.app.spring.model.CustomerInterface;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class CustomerController {

	private CustomerInterface customerService;

	@Autowired(required = true)
	@Qualifier(value = "customerService")
	public void setCustomerService(CustomerInterface cs) {
		this.customerService = cs;
	}

	@RequestMapping(value = "/", method = RequestMethod.GET)
        public String index(Model model) {
            model.addAttribute("datetime", new Date());
            return "index";
        }
	@RequestMapping(value = "/customers", method = RequestMethod.GET)
	public String listCustomers(Model model) {
		model.addAttribute("customer", new Customer());
		model.addAttribute("listCustomers", this.customerService.listCustomers());
		return "customer";
	}

	// For add and update person both
	@RequestMapping(value = "/customer/add", method = RequestMethod.POST)
	public String addCustomer(@ModelAttribute("customer") Customer c) {

		if (c.getId() == 0) {
			// new person, add it
			this.customerService.addCustomer(c);
		} else {
			// existing person, call update
			this.customerService.updateCustomer(c);
		}

		return "redirect:/customers";

	}

	@RequestMapping("/customer/remove/{id}")
	public String removeCustomer(@PathVariable("id") int id) {

		this.customerService.removeCustomer(id);
		return "redirect:/customers";
	}

	@RequestMapping("/customer/edit/{id}")
	public String editCustomer(@PathVariable("id") int id, Model model) {
		model.addAttribute("customer", this.customerService.getCustomerById(id));
		model.addAttribute("listCustomers", this.customerService.listCustomers());
		return "customer";
	}

}
