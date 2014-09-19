package com.app.spring.test.controller;

import com.app.spring.config.WebConfig;
import com.app.spring.model.Customer;
import com.app.spring.model.CustomerInterface;
import com.app.spring.test.config.TestConfig;
import com.app.spring.util.CustomerNotFoundException;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
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
 * and
 * http://www.petrikainulainen.net/programming/spring-framework/unit-testing-of-spring-mvc-controllers-rest-api/
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

    private static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8")
    );

    @Test
    public void listCustomersRestTest() throws Exception {
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

        ResultActions result = mockMvc.perform(get("/customers/rest"));

        System.out.println("listCustomersRestTest() - " + result.andReturn().getResponse().getContentAsString());

        result.andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].name", is("Fred Flintstone")))
                .andExpect(jsonPath("$[0].address", is("12 Bedrock")))
                .andExpect(jsonPath("$[0].tel", is("0800 RUBBLE")))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].name", is("Betty Rubble")))
                .andExpect(jsonPath("$[1].address", is("14 Bedrock")))
                .andExpect(jsonPath("$[1].tel", is("0800 STONE")));

        verify(customerServiceMock, times(1)).listCustomers();
        verifyNoMoreInteractions(customerServiceMock);
    }

    @Test
    public void getCustomerRestTest() throws Exception {
        Customer one = new Customer();
        one.setId(2);
        one.setName("Betty Rubble");
        one.setAddress("14 Bedrock");
        one.setTel("0800 STONE");

        when(customerServiceMock.getCustomerById(one.getId())).thenReturn(one);

        ResultActions result = mockMvc.perform(get("/getcustomer/{id}", 2));

        System.out.println("getCustomerRestTest() - " + result.andReturn().getResponse().getContentAsString());

        result.andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.id", is(2)))
                .andExpect(jsonPath("$.name", is("Betty Rubble")))
                .andExpect(jsonPath("$.address", is("14 Bedrock")))
                .andExpect(jsonPath("$.tel", is("0800 STONE")));

        verify(customerServiceMock, times(1)).getCustomerById(one.getId());
        verifyNoMoreInteractions(customerServiceMock);
    }

    @Test
    public void getUnknownCustomerRestTest() throws Exception {

        when(customerServiceMock.getCustomerById(3)).thenThrow(new CustomerNotFoundException(3, new Exception("getUnknownCustomerRestTest()")));

        ResultActions result = mockMvc.perform(get("/getcustomer/{id}", 3));

        System.out.println("getCustomerRestTest() - " + result.andReturn().getResponse().getContentAsString());

        result.andExpect(status().isNotFound());

        verify(customerServiceMock, times(1)).getCustomerById(3);
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

        ResultActions result = mockMvc.perform(post("/customer/add")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("id", String.valueOf(add.getId()))
                .param("name", add.getName())
                .param("address", add.getAddress())
                .param("tel", add.getTel()));

        result.andExpect(status().isMovedTemporarily())
                .andExpect(view().name("redirect:/customers"))
                .andExpect(redirectedUrl("/customers"));

        //result.andExpect(model().attribute("customer", is(add)));
        System.out.println("addCustomerTest() - Model objects start.");
        Map<String, Object> viewObjects = result.andReturn().getModelAndView().getModel();
        System.out.println("addCustomerTest() - Model objects keys start.");
        Iterator<String> itvk = viewObjects.keySet().iterator();
        while (itvk.hasNext()) {
            System.out.println(itvk.next());
        }
        System.out.println("addCustomerTest() - Model objects keys end.");
        System.out.println("addCustomerTest() - Model objects values start.");
        Iterator<Object> itvo = viewObjects.values().iterator();
        while (itvo.hasNext()) {
            System.out.println(itvo.next());
        }
        System.out.println("addCustomerTest() - Model objects values end.");
        System.out.println("addCustomerTest() - Model objects end.");

        Customer modelCust = (Customer) viewObjects.get("customer");
        assertEquals(add.getId(), modelCust.getId());
        assertEquals(add.getAddress(), modelCust.getAddress());
        assertEquals(add.getName(), modelCust.getName());
        assertEquals(add.getTel(), modelCust.getTel());

        // Consider: http://docs.mockito.googlecode.com/hg/org/mockito/Captor.html
        // Consider if Customer compared more than once here: http://docs.mockito.googlecode.com/hg/org/mockito/ArgumentMatcher.html
        // Ref: http://docs.mockito.googlecode.com/hg/org/mockito/ArgumentCaptor.html and http://www.petrikainulainen.net/programming/spring-framework/unit-testing-of-spring-mvc-controllers-normal-controllers/
        ArgumentCaptor<Customer> formObjectArgument = ArgumentCaptor.forClass(Customer.class);
        verify(customerServiceMock, times(1)).addCustomer(formObjectArgument.capture());
        verify(customerServiceMock, never()).updateCustomer(formObjectArgument.capture());
        verifyNoMoreInteractions(customerServiceMock);

        Customer them = formObjectArgument.getValue();
        assertEquals(add.getId(), them.getId());
        assertEquals(add.getAddress(), them.getAddress());
        assertEquals(add.getName(), them.getName());
        assertEquals(add.getTel(), them.getTel());
    }

    @Test
    public void updateCustomerTest() throws Exception {
        final Customer update = new Customer();
        update.setId(1);  // Not 0 indicates update.
        update.setName("Wilma Flintstone");
        update.setAddress("12 Bedrock");
        update.setTel("0800 GRANITE");

        ResultActions result = mockMvc.perform(post("/customer/add")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("id", String.valueOf(update.getId()))
                .param("name", update.getName())
                .param("address", update.getAddress())
                .param("tel", update.getTel()));

        result.andExpect(status().isMovedTemporarily())
                .andExpect(view().name("redirect:/customers"))
                .andExpect(redirectedUrl("/customers"));

        Map<String, Object> viewObjects = result.andReturn().getModelAndView().getModel();

        Customer modelCust = (Customer) viewObjects.get("customer");
        assertEquals(update.getId(), modelCust.getId());
        assertEquals(update.getAddress(), modelCust.getAddress());
        assertEquals(update.getName(), modelCust.getName());
        assertEquals(update.getTel(), modelCust.getTel());

        ArgumentCaptor<Customer> formObjectArgument = ArgumentCaptor.forClass(Customer.class);
        verify(customerServiceMock, never()).addCustomer(formObjectArgument.capture());
        verify(customerServiceMock, times(1)).updateCustomer(formObjectArgument.capture());
        verifyNoMoreInteractions(customerServiceMock);

        Customer them = formObjectArgument.getValue();
        assertEquals(update.getId(), them.getId());
        assertEquals(update.getAddress(), them.getAddress());
        assertEquals(update.getName(), them.getName());
        assertEquals(update.getTel(), them.getTel());
    }
}
