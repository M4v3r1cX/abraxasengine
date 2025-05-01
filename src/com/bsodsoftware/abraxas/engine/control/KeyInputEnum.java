package com.bsodsoftware.abraxas.engine.control;

public enum KeyInputEnum {
    UP_ARROW(38),
    DOWN_ARROW(40),
    LEFT_ARROW(37),
    RIGHT_ARROW(39),
    M(77),
    ESC(27),
    ENTER(10),
    A(65),
    D(68),
    W(87),
    S(83);

    private final int keycode;

    KeyInputEnum(int keycode) {
        this.keycode = keycode;
    }

    public int getValue() {
        return keycode;
    }
}
