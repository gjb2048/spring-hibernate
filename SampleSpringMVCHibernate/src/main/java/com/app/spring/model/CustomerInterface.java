package com.app.spring.model;

import java.util.List;

/**
 *
 * @author Gareth
 */
public interface CustomerInterface {

    public void addCustomer(Customer p);

    public void updateCustomer(Customer p);

    public List<Customer> listCustomers();

    public Customer getCustomerById(int id);

    public void removeCustomer(int id);
}
