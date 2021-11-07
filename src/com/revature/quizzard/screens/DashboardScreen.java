package com.revature.quizzard.screens;

import com.revature.quizzard.util.ScreenRouter;

import java.io.BufferedReader;

//*************************************************

public class DashboardScreen extends Screen {

    //TTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTT

    public DashboardScreen(BufferedReader consoleReader, ScreenRouter router) {
        super("Dashboard Screen", "/dashboard", consoleReader, router);
    }

    //TTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTT

    //-------------------------------------------------

    @Override
    public void render() throws Exception {

    }

    //-------------------------------------------------

}
