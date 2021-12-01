package com.revature.quizzard.web.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.quizzard.daos.AppUserDAO;
import com.revature.quizzard.daos.FlashcardDAO;
import com.revature.quizzard.services.FlashcardService;
import com.revature.quizzard.services.UserService;
import com.revature.quizzard.web.controllers.AuthController;
import com.revature.quizzard.web.controllers.FlashcardController;
import com.revature.quizzard.web.controllers.TestController;
import com.revature.quizzard.web.controllers.UserController;
import com.revature.quizzard.web.servlets.DispatcherServlet;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

public class ContextLoaderListener implements ServletContextListener {

    private final static Logger logger = LogManager.getLogger();

    @Override
    public void contextInitialized(ServletContextEvent sce) {

        try {
            System.out.println("Initializing application");

    //        logger.info("Initializing application");

            ObjectMapper objectMapper = new ObjectMapper();

            AppUserDAO userDAO = new AppUserDAO();
            UserService userService = new UserService(userDAO);

            FlashcardDAO cardDAO = new FlashcardDAO();
            FlashcardService cardService = new FlashcardService(cardDAO);

            TestController testController = new TestController();
            Method test = TestController.class.getMethod("test", HttpServletRequest.class, HttpServletResponse.class);

            FlashcardController flashcardController = new FlashcardController(cardService, objectMapper);
            Method getCards = FlashcardController.class.getMethod("getCards", HttpServletRequest.class, HttpServletResponse.class);
            Method addNewCard = FlashcardController.class.getMethod("addNewCard", HttpServletRequest.class, HttpServletResponse.class);

            UserController userController = new UserController(userService, objectMapper);
            Method registerNewUser = UserController.class.getMethod("registerNewUser", HttpServletRequest.class, HttpServletResponse.class);

            AuthController authController = new AuthController(userService, objectMapper);
            Method login = AuthController.class.getMethod("login", HttpServletRequest.class, HttpServletResponse.class);
            Method logout = AuthController.class.getMethod("logout", HttpServletRequest.class, HttpServletResponse.class);

            HandlerMapping handlerMapping = new HandlerMapping();
            handlerMapping.addHandler(new RequestMapping("GET", "test"), new RequestHandle(testController, test));
            handlerMapping.addHandler(new RequestMapping("GET", "flashcards"), new RequestHandle(flashcardController, getCards));
            handlerMapping.addHandler(new RequestMapping("POST", "flashcards"), new RequestHandle(flashcardController, addNewCard));
            handlerMapping.addHandler(new RequestMapping("POST", "users"), new RequestHandle(userController, registerNewUser));
            handlerMapping.addHandler(new RequestMapping("POST", "auth"), new RequestHandle(authController, login));
            handlerMapping.addHandler(new RequestMapping("DELETE", "auth"), new RequestHandle(authController, logout));

            DispatcherServlet dispatcherServlet = new DispatcherServlet(handlerMapping, objectMapper);


            ServletContext context = sce.getServletContext();
            context.addServlet("DispatcherServlet", dispatcherServlet).addMapping("/*");

            System.out.println("Application initialized!");
    //        logger.info("Application initialized!");

        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        logger.info("Shutting down Quizzard web application!");
    }
}
