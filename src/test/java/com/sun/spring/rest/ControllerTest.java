package com.sun.spring.rest;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import org.springframework.test.web.servlet.MockMvc;

import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import org.springframework.web.context.WebApplicationContext;



@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = MyWebConfig.class)
public class ControllerTest {

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    
    @Before
    public void setup () {
        DefaultMockMvcBuilder builder = MockMvcBuilders.webAppContextSetup(this.wac);
        this.mockMvc = builder.build();
    }
    
    

    @Test
    public void testUserController () throws Exception {

    	//user1
        MockHttpServletRequestBuilder builder =
                            MockMvcRequestBuilders.post("/users/register")
                                                  .contentType(MediaType.APPLICATION_JSON)
                                                  .content(createUserInJson("sun",
                                                                            "sun@sun.com",
                                                                            "sunora"));
        this.mockMvc.perform(builder)
                    .andExpect(MockMvcResultMatchers.status()
                                                    .isCreated());
        
        

        //user2
        builder = MockMvcRequestBuilders.post("/users/register")
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .content(createUserInJson("ora",
                                                                  "ora@ora.com",
                                                                  "123"));
        this.mockMvc.perform(builder)
                    .andExpect(MockMvcResultMatchers.status()
                                                    .isCreated());
        
        

        // get all users
        builder = MockMvcRequestBuilders.get("/users")
                                        .accept(MediaType.APPLICATION_JSON);
        this.mockMvc.perform(builder)
                    .andExpect(MockMvcResultMatchers.status()
                                                    .isOk())
                    .andDo(MockMvcResultHandlers.print());

    }
    
    

    private static String createUserInJson (String name, String email, String password) {
        return "{ \"name\": \"" + name + "\", " +"\"emailAddress\":\"" + email + "\"," +"\"password\":\"" + password + "\"}";
    }
}



