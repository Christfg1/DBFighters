package com.pluralsight.models;

public class Namekian extends Fighter {

    public Namekian(String name, int powerLevel, int health) {

        super(name, powerLevel, health);
    }

    public void regenerate() {

        heal(20);

        System.out.println(
                getName() +
                        " regenerates health!"
        );
    }
}