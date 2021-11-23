package com.revature.quizzard.web.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.quizzard.daos.AppUserDAO;
import com.revature.quizzard.daos.FlashcardDAO;
import com.revature.quizzard.services.FlashcardService;
import com.revature.quizzard.services.UserService;
import com.revature.quizzard.web.servlets.FlashcardServlet;
import com.revature.quizzard.web.servlets.UserServlet;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class ContextLoaderListener implements ServletContextListener {

    private final static Logger logger = LogManager.getLogger();

    @Override
    public void contextInitialized(ServletContextEvent sce) {

        System.out.println("Initializing application");

//        logger.info("Initializing application");

        ObjectMapper objectMapper = new ObjectMapper();

        AppUserDAO userDAO = new AppUserDAO();
        UserService userService = new UserService(userDAO);

        FlashcardDAO cardDAO = new FlashcardDAO();
        FlashcardService cardService = new FlashcardService(cardDAO, userService);

        FlashcardServlet cardServlet = new FlashcardServlet(cardService, objectMapper);
        UserServlet userServlet = new UserServlet(userService, objectMapper);

        ServletContext context = sce.getServletContext();
        context.addServlet("FlashcardServlet", cardServlet).addMapping("/flashcards");
        context.addServlet("UserServlet", userServlet).addMapping("/users/*");

        System.out.println("Application initialized!");
//        logger.info("Application initialized!");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        logger.info("Shutting down Quizzard web application!");
    }
}
