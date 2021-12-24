package com.revature.quizzard.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.quizzard.auth.dtos.requests.LoginRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
public class AuthControllerIntegrationTest {

    private MockMvc mockMvc;
    private final WebApplicationContext context;

    private final ObjectMapper mapper;
    private final TokenValidator tokenValidator;

    @Autowired
    public AuthControllerIntegrationTest(WebApplicationContext context, ObjectMapper mapper, TokenValidator tokenValidator) {
        this.context = context;
        this.mapper = mapper;
        this.tokenValidator = tokenValidator;
    }

    @BeforeEach
    public void setup() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    public void test_authenticate_returnsRequestWithBodyContainingPrincipalAndAuthorizationHeaderContainingValidJwt_givenValidCredentials() throws Exception {

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("test");
        loginRequest.setPassword("test");

        String requestPayload = mapper.writeValueAsString(loginRequest);
        MvcResult mvcResult = mockMvc.perform(post("/auth").contentType("application/json").content(requestPayload))
                                     .andDo(print())
                                     .andExpect(status().is(200))
                                     .andExpect(jsonPath("$.id").isNotEmpty())
                                     .andExpect(jsonPath("$.username").isNotEmpty())
                                     .andExpect(jsonPath("$.role").isNotEmpty())
                                     .andExpect(header().exists("Authorization"))
                                     .andReturn();

        String token = mvcResult.getResponse().getHeader("Authorization");
        assertTrue(tokenValidator.parseToken(token).isPresent());
    }

    @Test
    public void test_authenticate_returnsRequestWithBodyContainingErrorResponse_givenInvalidCredentials() throws Exception {

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("incorrect");
        loginRequest.setPassword("incorrect");

        String requestPayload = mapper.writeValueAsString(loginRequest);
        mockMvc.perform(post("/auth").contentType("application/json").content(requestPayload))
               .andDo(print())
               .andExpect(status().is(401))
               .andExpect(jsonPath("$.statusCode").isNotEmpty())
               .andExpect(jsonPath("$.message").isNotEmpty())
               .andExpect(jsonPath("$.cause").isNotEmpty())
               .andExpect(jsonPath("$.datetime").isNotEmpty())
               .andExpect(header().doesNotExist("Authorization"))
               .andReturn();

    }

    @Test
    public void test_authenticate_returnsRequestWithBodyContainingErrorResponse_givenInvalidBody() throws Exception {

        mockMvc.perform(post("/auth").contentType("application/json").content(""))
               .andDo(print())
               .andExpect(status().is(400))
               .andExpect(jsonPath("$.statusCode").isNotEmpty())
               .andExpect(jsonPath("$.message").isNotEmpty())
               .andExpect(jsonPath("$.cause").isNotEmpty())
               .andExpect(jsonPath("$.datetime").isNotEmpty())
               .andExpect(header().doesNotExist("Authorization"))
               .andReturn();

    }

}
