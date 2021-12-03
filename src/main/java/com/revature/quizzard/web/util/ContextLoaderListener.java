package com.revature.quizzard.web.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.quizzard.daos.AppUserDAO;
import com.revature.quizzard.daos.FlashcardDAO;
import com.revature.quizzard.services.FlashcardService;
import com.revature.quizzard.services.UserService;
import com.revature.quizzard.util.datasource.ConnectionFactory;
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

        logger.info("Initializing application");

        ObjectMapper objectMapper = new ObjectMapper();

        ConnectionFactory connFactory = ConnectionFactory.getInstance();
        AppUserDAO userDAO = new AppUserDAO(connFactory);
        UserService userService = new UserService(userDAO);

        FlashcardDAO cardDAO = new FlashcardDAO(connFactory);
        FlashcardService cardService = new FlashcardService(cardDAO);

        TestController testController = new TestController();
        FlashcardController flashcardController = new FlashcardController(cardService, objectMapper);
        UserController userController = new UserController(userService, objectMapper);
        AuthController authController = new AuthController(userService, objectMapper);

        HandlerMapping handlerMapping = new HandlerMapping();
        handlerMapping.addHandler(testController);
        handlerMapping.addHandler(flashcardController);
        handlerMapping.addHandler(userController);
        handlerMapping.addHandler(authController);

        DispatcherServlet dispatcherServlet = new DispatcherServlet(handlerMapping, objectMapper);

        ServletContext context = sce.getServletContext();
        context.addServlet("DispatcherServlet", dispatcherServlet).addMapping("/*");

        logger.info("Application initialized!");

    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        logger.info("Shutting down Quizzard web application!");
    }
}
