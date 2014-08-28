package com.app.spring.test.controller;

import com.app.spring.config.WebConfig;
import com.app.spring.model.Customer;
import com.app.spring.model.CustomerInterface;
import com.app.spring.test.config.TestConfig;
import com.app.spring.util.CustomerNotFoundException;
import java.util.Arrays;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
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
    public void unknownUrlTest() throws Exception {
        mockMvc.perform(get("/rimmer"))
                .andExpect(status().isOk()); // Odd, should be 404 and is when tested in browser with app.  Should this really test the AppController?
        verifyNoMoreInteractions(customerServiceMock);
    }

}
