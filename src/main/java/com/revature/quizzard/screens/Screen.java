package com.revature.quizzard.screens;

import com.revature.quizzard.util.ScreenRouter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;

/**
 * Key Differences between Abstract Classes and Interfaces:
 *
 *      - Abstract classes can have instance fields (that are not implicitly public, static, final)
 *      - Abstract classes can have methods with implementation that are not declared as default
 *      - Abstract class method stubs must be explicitly declared as "abstract"
 */
public abstract class Screen implements View {

    protected Logger logger = LogManager.getLogger();
    protected String name;
    protected BufferedReader consoleReader;
    protected ScreenRouter router;

    public Screen(BufferedReader consoleReader, ScreenRouter router) {
        this.consoleReader = consoleReader;
        this.router = router;
    }

}
