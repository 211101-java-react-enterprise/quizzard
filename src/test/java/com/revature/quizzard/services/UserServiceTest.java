package com.revature.quizzard.services;

import com.revature.quizzard.user.UserRepository;
import com.revature.quizzard.common.exceptions.InvalidRequestException;
import com.revature.quizzard.common.exceptions.ResourcePersistenceException;
import com.revature.quizzard.user.AppUser;
import com.revature.quizzard.user.UserService;
import com.revature.quizzard.user.dtos.requests.NewRegistrationRequest;
import com.revature.quizzard.user.dtos.responses.RegistrationSuccessResponse;
import org.junit.*;

import static org.mockito.Mockito.*;

public class UserServiceTest {

    UserService sut;
    UserRepository mockUserDAO;

    @Before
    public void testCaseSetup() {
        mockUserDAO = mock(UserRepository.class);
        sut = new UserService(mockUserDAO);
    }

    @After
    public void testCaseCleanUp() {
        sut = null;
    }

    @Test
    public void test_isUserValid_returnsTrue_givenValidUser() {

        // Arrange
        AppUser validUser = new AppUser("valid", "valid", "valid", "valid", "valid");

        // Act
        boolean actualResult = sut.isUserValid(validUser);

        // Assert
        Assert.assertTrue("Expected user to be considered valid", actualResult);

    }

    @Test
    public void test_isUserValid_returnsFalse_givenUserWithInvalidFirstName() {

        // Arrange
        AppUser invalidUser_1 = new AppUser(null, "valid", "valid", "valid", "valid");
        AppUser invalidUser_2 = new AppUser("", "valid", "valid", "valid", "valid");
        AppUser invalidUser_3 = new AppUser("             ", "valid", "valid", "valid", "valid");

        // Act
        boolean actualResult_1 = sut.isUserValid(invalidUser_1);
        boolean actualResult_2 = sut.isUserValid(invalidUser_2);
        boolean actualResult_3 = sut.isUserValid(invalidUser_3);

        // Assert
        Assert.assertFalse("Expected user to be considered false.", actualResult_1);
        Assert.assertFalse("Expected user to be considered false.", actualResult_2);
        Assert.assertFalse("Expected user to be considered false.", actualResult_3);

    }

    @Ignore
    @Test
    public void test_registerNewUser_returnsTrue_givenValidUser() {

        // Arrange
        NewRegistrationRequest validUser = new NewRegistrationRequest("valid", "valid", "valid", "valid", "valid");
        when(mockUserDAO.findUserByUsername(validUser.getUsername())).thenReturn(null);
        when(mockUserDAO.findUserByEmail(validUser.getEmail())).thenReturn(null);
        when(mockUserDAO.save(any())).thenReturn(any());

        // Act
        RegistrationSuccessResponse successResponse = sut.registerNewUser(validUser);

        // Assert
        Assert.assertNotNull(successResponse);
        verify(mockUserDAO, times(1)).save(any());

    }

    @Test(expected = ResourcePersistenceException.class)
    public void test_registerNewUser_throwsResourcePersistenceException_givenValidUserWithTakenUsername() {

        // Arrange
        NewRegistrationRequest validUser = new NewRegistrationRequest("valid", "valid", "valid", "valid", "valid");
        when(mockUserDAO.findUserByUsername(validUser.getUsername())).thenReturn(new AppUser());
        when(mockUserDAO.findUserByEmail(validUser.getEmail())).thenReturn(null);
        when(mockUserDAO.save(any())).thenReturn(any());

        // Act
        try {
            sut.registerNewUser(validUser);
        } finally {
            // Assert
            verify(mockUserDAO, times(0)).save(any());
        }

    }

    @Test(expected = ResourcePersistenceException.class)
    public void test_registerNewUser_throwsResourcePersistenceException_givenValidUserWithTakenEmail() {

        // Arrange
        NewRegistrationRequest validUser = new NewRegistrationRequest("valid", "valid", "valid", "valid", "valid");
        when(mockUserDAO.findUserByUsername(validUser.getUsername())).thenReturn(null);
        when(mockUserDAO.findUserByEmail(validUser.getEmail())).thenReturn(new AppUser());
        when(mockUserDAO.save(any())).thenReturn(any());

        // Act
        try {
            sut.registerNewUser(validUser);
        } finally {
            // Assert
            verify(mockUserDAO, times(0)).save(any());
        }

    }

    @Ignore
    @Test(expected = InvalidRequestException.class)
    public void test_registerNewUser_throwsInvalidRequestException_givenInvalidUser() {
        sut.registerNewUser(null);
    }

    // TODO implement test case
    @Test
    public void test_registerNewUser_throwsInvalidRequestException_givenUserWithDuplicatedEmailOrUsername() {

        // Arrange

        // Act

        // Assert

    }

}
