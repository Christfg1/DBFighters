package com.pluralsight.services;

import com.pluralsight.models.Fighter;
import com.pluralsight.models.Namekian;
import com.pluralsight.models.Saiyan;

public class BattleService {

    public void attack(Fighter attacker, Fighter target) {

        if (!attacker.isAlive()) {
            System.out.println(attacker.getName() + " cannot attack because they are defeated.");
            return;
        }

        if (!target.isAlive()) {
            System.out.println(target.getName() + " is already defeated.");
            return;
        }

        SoundService.playSound("punch");

        int damage = attacker.getPowerLevel() / 500;

        if (damage < 10) {
            damage = 10;
        }

        System.out.println();
        attacker.attack();

        target.takeDamage(damage);

        if (!target.isAlive()) {
            SoundService.playSound("victory");
        }
    }

    public void useSpecialAbility(Fighter fighter) {

        if (!fighter.isAlive()) {
            System.out.println(fighter.getName() + " cannot use a special ability because they are defeated.");
            return;
        }

        if (fighter instanceof Saiyan) {

            SoundService.playSound("transform");

            Saiyan saiyan = (Saiyan) fighter;
            saiyan.transform();

        } else if (fighter instanceof Namekian) {

            SoundService.playSound("heal");

            Namekian namekian = (Namekian) fighter;
            namekian.regenerate();

        } else {
            System.out.println(fighter.getName() + " has no special ability.");
        }
    }

    public boolean isBattleOver(Fighter fighterOne, Fighter fighterTwo) {

        return !fighterOne.isAlive() || !fighterTwo.isAlive();
    }

    public void showWinner(Fighter fighterOne, Fighter fighterTwo) {

        System.out.println();

        if (fighterOne.isAlive() && !fighterTwo.isAlive()) {
            System.out.println(fighterOne.getName() + " wins!");
        } else if (fighterTwo.isAlive() && !fighterOne.isAlive()) {
            System.out.println(fighterTwo.getName() + " wins!");
        } else {
            System.out.println("The battle ended with no winner.");
        }
    }
}