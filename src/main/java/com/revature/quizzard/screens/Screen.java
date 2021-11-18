package com.revature.quizzard.screens;

import com.revature.quizzard.util.ScreenRouter;
import com.revature.quizzard.util.logging.Logger;

import java.io.BufferedReader;

/**
 * Key Differences between Abstract Classes and Interfaces:
 *
 *      - Abstract classes can have instance fields (that are not implicitly public, static, final)
 *      - Abstract classes can have methods with implementation that are not declared as default
 *      - Abstract class method stubs must be explicitly declared as "abstract"
 */
public abstract class Screen {

    protected Logger logger = Logger.getLogger();
    protected String name;
    protected String route;
    protected BufferedReader consoleReader;
    protected ScreenRouter router;

    public Screen(String name, String route, BufferedReader consoleReader, ScreenRouter router) {
        this.name = name;
        this.route = route;
        this.consoleReader = consoleReader;
        this.router = router;
    }

    public final String getName() {
        return name;
    }

    public final String getRoute() {
        return route;
    }

    public abstract void render() throws Exception;

}
