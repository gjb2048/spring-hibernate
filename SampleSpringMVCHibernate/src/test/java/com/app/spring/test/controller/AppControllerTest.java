package com.app.spring.test.controller;

import com.app.spring.config.WebConfig;
import com.app.spring.test.config.TestConfig;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

/**
 *
 * @author Gareth
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestConfig.class, WebConfig.class})
@WebAppConfiguration
public class AppControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Before
    public void setUp() {

        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        // Ref: http://www.zjhzxhz.com/2014/05/unit-testing-of-spring-mvc-controllers/
        //MockitoAnnotations.initMocks(this);
        //mockMvc = MockMvcBuilders.standaloneSetup(customerController).build();
    }

    @Test
    public void unknownUrlTest() throws Exception {

        System.out.println("unknownUrlTest() - start.");
        ResultActions result = mockMvc.perform(get("/rimmer"));

        result.andExpect(status().isOk()); // Odd, should be 404 and is when tested in browser with app.  Should this really test the AppController?

        Collection<String> headers = result.andReturn().getResponse().getHeaderNames();
        Iterator<String> it = headers.iterator();
        List<Object> values;
        while (it.hasNext()) {
            values = result.andReturn().getResponse().getHeaderValues(it.next());
            Iterator<Object> itv = values.iterator();
            while (itv.hasNext()) {
                System.out.println(itv.next());
            }
        }
        System.out.println("unknownUrlTest() - end.");
    }

}
