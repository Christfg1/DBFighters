package com.pluralsight.models;

public class Saiyan extends Fighter {

    private boolean transformed;
    private String transformationName;

    public Saiyan(
            String name,
            int powerLevel,
            int health,
            String transformationName
    ) {

        super(name, powerLevel, health);

        this.transformationName = transformationName;

        transformed = false;
    }

    public void transform() {

        if (transformed) {

            System.out.println(
                    getName() +
                            " is already transformed!"
            );

            return;
        }

        int newPowerLevel =
                getPowerLevel() * 2;

        setPowerLevel(newPowerLevel);

        transformed = true;

        System.out.println(
                getName() +
                        " transforms into "
                        + transformationName
                        + "!"
        );

        System.out.println(
                "New power level: "
                        + getPowerLevel()
        );
    }

    public boolean isTransformed() {
        return transformed;
    }

    public String getTransformationName() {
        return transformationName;
    }
}