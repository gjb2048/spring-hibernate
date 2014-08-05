package com.app.spring.dao;

import com.app.spring.model.Customer;
import com.app.spring.model.CustomerInterface;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Named;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

/**
 *
 * @author malalanayake
 *
 */
@Repository
@Qualifier(value = "customerDAO")
public class CustomerDAO implements CustomerInterface {

    private static final Logger logger = LoggerFactory.getLogger(CustomerDAO.class);

    @Inject
    @Named("hibernate4AnnotatedSessionFactory")
    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sf) {
        this.sessionFactory = sf;
    }

    @Override
    public void addCustomer(Customer p) {
        Session session = this.sessionFactory.getCurrentSession();
        session.persist(p);
        logger.info("Customer saved successfully, Customer Details=" + p);
    }

    @Override
    public void updateCustomer(Customer p) {
        Session session = this.sessionFactory.getCurrentSession();
        session.update(p);
        logger.info("Customer updated successfully, Person Details=" + p);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Customer> listCustomers() {
        Session session = this.sessionFactory.getCurrentSession();
        List<Customer> customersList = session.createQuery("from Customer").list();
        for (Customer c : customersList) {
            logger.info("Customer List::" + c);
        }
        return customersList;
    }

    @Override
    public Customer getCustomerById(int id) {
        Session session = this.sessionFactory.getCurrentSession();
        Customer c = (Customer) session.load(Customer.class, new Integer(id));
        logger.info("Customer loaded successfully, Customer details=" + c);
        return c;
    }

    @Override
    public void removeCustomer(int id) {
        Session session = this.sessionFactory.getCurrentSession();
        Customer c = (Customer) session.load(Customer.class, new Integer(id));
        if (null != c) {
            session.delete(c);
        }
        logger.info("Customer deleted successfully, Customer details=" + c);
    }

}
