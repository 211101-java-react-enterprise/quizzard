package com.revature.quizzard.screens;

import com.revature.quizzard.util.ScreenRouter;

import java.io.BufferedReader;

//*************************************************

/**
 * Key differences between abstract classes and interfaces:
 *
 *  -Abstract classes can have instance fields (that are not implicitly public/static/final)
 *  -Abstract classes can have methods with implementation that are not declared as default
 *  -Abstract class method stubs must be explicitly declared as "abstract"
 *
 */
public abstract class Screen {

    //0000000000000000000000000000000000000000000000000

    protected String name;
    protected String route;
    protected BufferedReader consoleReader;
    protected ScreenRouter router;

    //0000000000000000000000000000000000000000000000000

    //TTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTT

    public Screen(String name, String route, BufferedReader consoleReader, ScreenRouter router) {
        this.name = name;
        this.route = route;
        this.consoleReader = consoleReader;
        this.router = router;

    }

    //TTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTT

    //-------------------------------------------------

    public final String getName() {
        return name;
    }

    //-------------------------------------------------

    public final String getRoute() {
        return route;
    }

    //-------------------------------------------------

    public abstract void render() throws Exception;

    //-------------------------------------------------

}
