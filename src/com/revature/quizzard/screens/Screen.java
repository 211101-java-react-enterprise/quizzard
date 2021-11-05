package com.revature.quizzard.screens;

import java.io.BufferedReader;

/**
 * Key differences between abstract classes and interfaces:
 *
 *  -Abstract classes can have instance fields (that are not implicitly final)
 *  -Abstract classes can have methods with implementation that are not declared as default
 *  -Abstract class method stubs must be explicitly declared as abstract
 *
 */
public abstract class Screen {
    protected String name;
    protected String route;
    protected BufferedReader consoleReader;
    protected ScreenRouter router;

    public Screen(String name, String route, BufferedReader consoleReader, ScreenRouter router) {
        this.name = name;
        this.route = route;
        this.consoleReader = consoleReader;
    }

    public String getName() {
        return name;
    }

    public String getRoute() {
        return route;
    }

    public abstract void render() throws Exception;
}
