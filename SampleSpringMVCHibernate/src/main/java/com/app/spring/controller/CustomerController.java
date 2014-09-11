package com.app.spring.controller;

import com.app.spring.model.Customer;
import com.app.spring.model.CustomerInterface;
import com.app.spring.util.CustomerNotFoundException;
import com.app.spring.util.Utils;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class CustomerController {

    private static final Logger logger = LoggerFactory.getLogger(CustomerController.class);

    @Autowired(required = true)
    @Qualifier(value = "customerService")
    private CustomerInterface customerService;

    public void setCustomerService(CustomerInterface cs) {
        this.customerService = cs;
    }

    @RequestMapping(value = "/customers", method = RequestMethod.GET)
    public String listCustomers(Model model) {
        model.addAttribute("customer", new Customer());
        model.addAttribute("listCustomers", this.customerService.listCustomers());
        return "customer";
    }

    @RequestMapping(value = "/customers/rest", method = RequestMethod.GET)
    public @ResponseBody List<Customer> listCustomersRest() {
        return this.customerService.listCustomers();
    }
    
    // For both add and update person.
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
    public String removeCustomer(@PathVariable("id") int id) throws CustomerNotFoundException {
        this.customerService.removeCustomer(id);
        return "redirect:/customers";
    }

    @ExceptionHandler(CustomerNotFoundException.class)
    //@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "No such customer")  // 404
    public ModelAndView handleCustomerNotFoundException(HttpServletRequest req, HttpServletResponse resp, CustomerNotFoundException exception) {
        logger.error("CustomerNotFoundException: " + req.getRequestURI() + ", customer id: " + exception.getCustomerId());

        resp.setStatus(HttpServletResponse.SC_NOT_FOUND);

        return Utils.getErrorMAV(req, exception, "error_404");
    }

    @RequestMapping("/customer/edit/{id}")
    public String editCustomer(@PathVariable("id") int id, Model model) throws CustomerNotFoundException {
        model.addAttribute("customer", this.customerService.getCustomerById(id));
        model.addAttribute("listCustomers", this.customerService.listCustomers());
        return "customer";
    }

}
