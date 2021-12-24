package com.revature.quizzard.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.quizzard.user.dtos.requests.NewRegistrationRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class UserControllerIntegrationTest {

    private MockMvc mockMvc;
    private final WebApplicationContext context;
    private final ObjectMapper mapper;

    @Autowired
    public UserControllerIntegrationTest(WebApplicationContext context, ObjectMapper mapper) {
        this.context = context;
        this.mapper = mapper;
    }

    @BeforeEach
    public void setup() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    public void test_checkUsernameAvailability_returns204_givenThatProvidedUsernameIsNotTaken() throws Exception {
        mockMvc.perform(get("/users/username?username=totally-not-taken-username"))
               .andDo(print())
               .andExpect(status().is(204))
               .andReturn();
    }

    @Test
    public void test_checkUsernameAvailability_returns409_givenThatProvidedUsernameIsNotTaken() throws Exception {
        mockMvc.perform(get("/users/username?username=test"))
               .andDo(print())
               .andExpect(status().is(409))
               .andReturn();
    }

    @Test
    public void test_register_returns201_givenValidNewRegistrationRequest() throws Exception {

        NewRegistrationRequest newRegistrationRequest = new NewRegistrationRequest();
        newRegistrationRequest.setFirstName("Bob");
        newRegistrationRequest.setLastName("Bailey");
        newRegistrationRequest.setEmail("bob.bailey@revature.com");
        newRegistrationRequest.setUsername("bbailey");
        newRegistrationRequest.setPassword("p4$$word");

        String requestPayload = mapper.writeValueAsString(newRegistrationRequest);

        MvcResult result = mockMvc.perform(post("/users").contentType("application/json").content(requestPayload))
                                  .andDo(print())
                                  .andExpect(status().is(201))
                                  .andExpect(jsonPath("$.id").isNotEmpty())
                                  .andReturn();

        assertEquals("application/json", result.getResponse().getContentType());
    }

}
