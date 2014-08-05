package com.app.spring.service;

import com.app.spring.model.Customer;
import com.app.spring.model.CustomerInterface;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Qualifier(value = "customerService")
public class CustomerService implements CustomerInterface {

    @Autowired
    @Qualifier(value = "customerDAO")
    private CustomerInterface customerDAO;

    @Override
    @Transactional
    public void addCustomer(Customer c) {
        this.customerDAO.addCustomer(c);
    }

    @Override
    @Transactional
    public void updateCustomer(Customer c) {
        this.customerDAO.updateCustomer(c);
    }

    @Override
    @Transactional
    public List<Customer> listCustomers() {
        return this.customerDAO.listCustomers();
    }

    @Override
    @Transactional
    public Customer getCustomerById(int id) {
        return this.customerDAO.getCustomerById(id);
    }

    @Override
    @Transactional
    public void removeCustomer(int id) {
        this.customerDAO.removeCustomer(id);
    }

}
