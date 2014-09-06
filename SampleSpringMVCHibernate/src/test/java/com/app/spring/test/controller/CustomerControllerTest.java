package com.app.spring.test.controller;

import com.app.spring.config.WebConfig;
import com.app.spring.model.Customer;
import com.app.spring.model.CustomerInterface;
import com.app.spring.test.config.TestConfig;
import com.app.spring.util.CustomerNotFoundException;
import java.util.Arrays;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

/**
 * Ref:
 * http://www.petrikainulainen.net/programming/spring-framework/unit-testing-of-spring-mvc-controllers-configuration/
 * and
 * http://www.petrikainulainen.net/programming/spring-framework/unit-testing-of-spring-mvc-controllers-normal-controllers/
 *
 * @author Gareth
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestConfig.class, WebConfig.class})
@WebAppConfiguration
public class CustomerControllerTest {

    private MockMvc mockMvc;

    @Autowired(required = true)
    @Qualifier(value = "customerService")
    //@Mock
    private CustomerInterface customerServiceMock;

    //@InjectMocks
    //private CustomerController customerController;
    @Autowired
    private WebApplicationContext webApplicationContext;

    @Before
    public void setUp() {
        //We have to reset our mock between tests because the mock objects
        //are managed by the Spring container. If we would not reset them,
        //stubbing and verified behavior would "leak" from one test to another.
        Mockito.reset(customerServiceMock);

        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        // Ref: http://www.zjhzxhz.com/2014/05/unit-testing-of-spring-mvc-controllers/
        //MockitoAnnotations.initMocks(this);
        //mockMvc = MockMvcBuilders.standaloneSetup(customerController).build();
    }

    @Test
    public void listCustomersTest() throws Exception {
        Customer one = new Customer();
        one.setId(1);
        one.setName("Fred Flintstone");
        one.setAddress("12 Bedrock");
        one.setTel("0800 RUBBLE");

        Customer two = new Customer();
        two.setId(2);
        two.setName("Betty Rubble");
        two.setAddress("14 Bedrock");
        two.setTel("0800 STONE");

        when(customerServiceMock.listCustomers()).thenReturn(Arrays.asList(one, two));

        mockMvc.perform(get("/customers"))
                .andExpect(status().isOk())
                .andExpect(view().name("customer"))
                .andExpect(forwardedUrl("/WEB-INF/views/customer.jsp"))
                .andExpect(model().attribute("listCustomers", hasSize(2)))
                .andExpect(model().attribute("listCustomers", hasItem(
                                        allOf(
                                                hasProperty("id", is(1)),
                                                hasProperty("name", is("Fred Flintstone")),
                                                hasProperty("address", is("12 Bedrock")),
                                                hasProperty("tel", is("0800 RUBBLE"))
                                        )
                                )))
                .andExpect(model().attribute("listCustomers", hasItem(
                                        allOf(
                                                hasProperty("id", is(2)),
                                                hasProperty("name", is("Betty Rubble")),
                                                hasProperty("address", is("14 Bedrock")),
                                                hasProperty("tel", is("0800 STONE"))
                                        )
                                )));

        verify(customerServiceMock, times(1)).listCustomers();
        verifyNoMoreInteractions(customerServiceMock);
    }

    @Test
    public void editUnknownCustomerTest() throws Exception {
        when(customerServiceMock.getCustomerById(42)).thenThrow(new CustomerNotFoundException(42, new Exception("editUnknownCustomerTest()")));

        mockMvc.perform(get("/customer/edit/{id}", 42))
                .andExpect(status().isNotFound())
                .andExpect(view().name("error_404"))
                .andExpect(forwardedUrl("/WEB-INF/views/error_404.jsp"));

        verify(customerServiceMock, times(1)).getCustomerById(42);
        verifyNoMoreInteractions(customerServiceMock);
    }

    @Test
    public void addCustomerTest() throws Exception {
        final Customer add = new Customer();
        add.setId(0);  // 0 indicates add.
        add.setName("Fred Flintstone");
        add.setAddress("12 Bedrock");
        add.setTel("0800 RUBBLE");

        // Looked at: http://kevinpotgieter.wordpress.com/2012/07/19/stubbing-void-methods-with-mockito/.
        //Mockito.doNothing().when(customerServiceMock).addCustomer(eq(add));
        // And: http://www.planetgeek.ch/2010/07/20/mockito-answer-vs-return/
        /*
         doAnswer(new Answer<Customer>() {
         @Override
         public Customer answer(InvocationOnMock invocation) throws Throwable {
         Customer cust = (Customer) invocation.getArguments()[0];
         return cust;
         }
         }).when(customerServiceMock).addCustomer(add);

         when(customerServiceMock.getCustomerById(add.getId())).thenAnswer(new Answer<Customer>() {
         @Override
         public Customer answer(InvocationOnMock invocation) throws Throwable {
         return add;
         }
         });
         */
        ResultActions result = mockMvc.perform(post("/customer/add")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("id", String.valueOf(add.getId()))
                .param("name", add.getName())
                .param("address", add.getAddress())
                .param("tel", add.getTel()));

        result.andExpect(status().isMovedTemporarily())
                .andExpect(view().name("redirect:/customers"))
                .andExpect(redirectedUrl("/customers"));
        
        //verify(customerServiceMock, times(1)).addCustomer(add);
        // Bingo ish! -> http://www.hascode.com/2011/03/mocking-stubbing-and-test-spying-using-the-mockito-framework/
        // But... not quite! Eventually solved by overriding the equals method of the Customer class to provide an equality test which did not take into account the objects instance reference.
        // Humm, just discovered ArgumentCaptor so will try that without the equality override.
        verify(customerServiceMock, times(1)).addCustomer(add);
        
        ArgumentCaptor<Customer> formObjectArgument = ArgumentCaptor.forClass(Customer.class);
        verify(customerServiceMock, times(1)).addCustomer(formObjectArgument.capture());
        
        assertEquals(add, formObjectArgument.getValue());
        
        //assertEquals(add, customerServiceMock.getCustomerById(add.getId()));
        verifyNoMoreInteractions(customerServiceMock);
    }
}
