package com.revature.quizzard.auth;

import com.revature.quizzard.auth.dtos.responses.Principal;
import com.revature.quizzard.common.exceptions.InvalidRequestException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TokenServiceUnitTest {

    private TokenService sut;

    private TokenGenerator mockTokenGenerator;
    private TokenValidator mockTokenValidator;

    @BeforeEach
    public void setUp() {
        mockTokenGenerator = mock(TokenGenerator.class);
        mockTokenValidator = mock(TokenValidator.class);
        sut = new TokenService(mockTokenGenerator, mockTokenValidator);
    }

    @AfterEach
    public void tearDown() {
        reset(mockTokenGenerator, mockTokenValidator);
    }

    @Test
    public void test_generateToken_returnsToken_givenValidPrincipal() {
        when(mockTokenGenerator.createToken(any())).thenReturn("mocked-token-value");
        String token = sut.generateToken(new Principal("ee413ff2-928ced21-61fe22aa-00123abc", "stubbed-user", "BASIC"));
        assertNotNull(token);
    }

    @Test
    public void test_generateToken_throwsInvalidRequestException_givenInvalidPrincipal() {

        InvalidRequestException thrown = assertThrows(InvalidRequestException.class, () -> {
            sut.generateToken(null);
        });

        assertEquals("Invalid Principal object provided!", thrown.getMessage());

        thrown = assertThrows(InvalidRequestException.class, () -> {
            sut.generateToken(new Principal());
        });

        assertEquals("Invalid Principal object provided!", thrown.getMessage());

        thrown = assertThrows(InvalidRequestException.class, () -> {
            sut.generateToken(new Principal("valid", "", ""));
        });

        assertEquals("Invalid Principal object provided!", thrown.getMessage());

        thrown = assertThrows(InvalidRequestException.class, () -> {
            sut.generateToken(new Principal("valid", "valid", ""));
        });

        assertEquals("Invalid Principal object provided!", thrown.getMessage());

    }

    @Test
    public void test_isTokenValid_returnsFalse_givenNullOrEmptyTokenValue() {
        boolean nullResult = sut.isTokenValid(null);
        boolean emptyResult = sut.isTokenValid("");
        assertFalse(nullResult);
        assertFalse(emptyResult);
    }

    @Test
    public void test_isTokenValid_returnsTrue_givenValidToken() {
        when(mockTokenValidator.parseToken(any())).thenReturn(Optional.of(new Principal("ee413ff2-928ced21-61fe22aa-00123abc", "stubbed-user", "BASIC")));
        boolean result = sut.isTokenValid("stubbed-valid-token-value");
        assertTrue(result);
    }

    @Test
    public void test_extractTokenDetails_returnsPrincipal_givenValidToken() {
        Principal stubbedPrincipal = new Principal("ee413ff2-928ced21-61fe22aa-00123abc", "stubbed-user", "BASIC");
        when(mockTokenValidator.parseToken(any())).thenReturn(Optional.of(stubbedPrincipal));
        Principal result = sut.extractTokenDetails("stubbed-valid-token-value");
        assertEquals(stubbedPrincipal, result);
    }

    @Test
    public void test_extractTokenDetails_throwInvalidRequestException_givenNullOrEmptyToken() {
        assertThrows(InvalidRequestException.class, () -> {
            sut.extractTokenDetails(null);
        });

        assertThrows(InvalidRequestException.class, () -> {
            sut.extractTokenDetails("");
        });
    }

}
