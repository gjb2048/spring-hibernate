package com.app.spring.model;

import com.app.spring.util.CustomerNotFoundException;
import java.util.List;

/**
 *
 * @author Gareth
 */
public interface CustomerInterface {

    public void addCustomer(Customer p);

    public void updateCustomer(Customer p);

    public List<Customer> listCustomers();

    public Customer getCustomerById(int id) throws CustomerNotFoundException;

    public void removeCustomer(int id) throws CustomerNotFoundException;
}
