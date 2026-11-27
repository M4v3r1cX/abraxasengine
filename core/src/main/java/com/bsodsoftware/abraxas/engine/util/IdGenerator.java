package com.bsodsoftware.abraxas.engine.util;

public class IdGenerator {
    private static int id = 0;

    public static int getId() {
        return id++;
    }
}
