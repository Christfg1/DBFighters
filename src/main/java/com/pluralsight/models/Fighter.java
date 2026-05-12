package com.pluralsight.models;

public class Fighter {

    private String name;
    private int powerLevel;
    private int health;
    private int maxHealth;

    public Fighter(String name, int powerLevel, int health) {

        this.name = name;
        this.powerLevel = powerLevel;
        this.health = health;
        this.maxHealth = health;
    }

    public void attack() {

        System.out.println(
                name +
                        " attacks with power level " +
                        powerLevel +
                        "!"
        );
    }

    public void takeDamage(int damage) {

        health -= damage;

        if (health < 0) {
            health = 0;
        }

        System.out.println(
                name +
                        " takes " +
                        damage +
                        " damage!"
        );

        System.out.println(
                "Health left: " +
                        health
        );

        if (!isAlive()) {

            System.out.println(
                    name +
                            " has been defeated!"
            );
        }
    }

    public void heal(int amount) {

        health += amount;

        if (health > maxHealth) {
            health = maxHealth;
        }

        System.out.println(
                name +
                        " heals for " +
                        amount
        );
    }

    public boolean isAlive() {

        return health > 0;
    }

    public void showStats() {

        System.out.println("\n=== Fighter Stats ===");

        System.out.println("Name: " + name);
        System.out.println("Power Level: " + powerLevel);
        System.out.println("Health: " + health);
    }

    public String getName() {
        return name;
    }

    public int getPowerLevel() {
        return powerLevel;
    }

    public void setPowerLevel(int powerLevel) {
        this.powerLevel = powerLevel;
    }

    public int getHealth() {
        return health;
    }

    public int getMaxHealth() {
        return maxHealth;
    }
}