package com.pluralsight.ui;

import com.pluralsight.models.Fighter;
import com.pluralsight.models.Namekian;
import com.pluralsight.models.Saiyan;
import com.pluralsight.services.BattleService;
import com.pluralsight.services.SoundService;

import java.util.ArrayList;
import java.util.Scanner;

public class GameConsoleUI {

    private Scanner scanner;
    private BattleService battleService;
    private ArrayList<Fighter> team;
    private ArrayList<Fighter> roster;

    private ArrayList<Fighter> transformedFighters;
    private boolean epicFightPlayed;

    public GameConsoleUI() {
        scanner = new Scanner(System.in);
        battleService = new BattleService();
        team = new ArrayList<>();
        roster = new ArrayList<>();
        transformedFighters = new ArrayList<>();
        epicFightPlayed = false;

        loadDefaultRoster();
    }

    public void start() {
        SoundService.playLoop("MenuSound");

        boolean running = true;

        while (running) {
            showMainMenu();

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    SoundService.playSound("UIclick");
                    createSaiyan();
                    break;

                case 2:
                    SoundService.playSound("UIclick");
                    createNamekian();
                    break;

                case 3:
                    SoundService.playSound("UIclick");
                    addExistingFighter();
                    break;

                case 4:
                    SoundService.playSound("UIclick");
                    viewTeam();
                    break;

                case 5:
                    SoundService.playSound("UIclick");
                    calculateTeamPower();
                    break;

                case 6:
                    SoundService.playSound("UIclick");
                    startBattle();
                    break;

                case 0:
                    SoundService.stopAllSounds();
                    System.out.println("Exiting game...");
                    running = false;
                    break;

                default:
                    System.out.println("Invalid option.");
            }
        }
    }

    private void loadDefaultRoster() {
        roster.add(new Saiyan("Goku", 9000, 100, "Ultra Instinct"));
        roster.add(new Saiyan("Vegeta", 8800, 100, "Super Saiyan Blue"));
        roster.add(new Saiyan("Broly", 12000, 150, "Legendary Super Saiyan"));
        roster.add(new Namekian("Piccolo", 7000, 120));
        roster.add(new Fighter("Frieza", 9500, 110));
    }

    private void showMainMenu() {
        System.out.println("\n=== DBZ Battle Arena ===");
        System.out.println("1) Create Saiyan");
        System.out.println("2) Create Namekian");
        System.out.println("3) Add Existing Fighter");
        System.out.println("4) View Team");
        System.out.println("5) Calculate Total Team Power");
        System.out.println("6) Start Battle");
        System.out.println("0) Exit");
        System.out.print("Choose option: ");
    }

    private void createSaiyan() {
        System.out.print("Enter Saiyan name: ");
        String name = scanner.nextLine();

        System.out.print("Enter power level: ");
        int powerLevel = scanner.nextInt();

        System.out.print("Enter health: ");
        int health = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Enter transformation name: ");
        String transformation = scanner.nextLine();

        Saiyan saiyan = new Saiyan(name, powerLevel, health, transformation);
        team.add(saiyan);

        System.out.println(name + " has been added to your team!");
    }

    private void createNamekian() {
        System.out.print("Enter Namekian name: ");
        String name = scanner.nextLine();

        System.out.print("Enter power level: ");
        int powerLevel = scanner.nextInt();

        System.out.print("Enter health: ");
        int health = scanner.nextInt();
        scanner.nextLine();

        Namekian namekian = new Namekian(name, powerLevel, health);
        team.add(namekian);

        System.out.println(name + " has been added to your team!");
    }

    private void addExistingFighter() {
        System.out.println("\n=== Existing Fighters ===");

        for (int i = 0; i < roster.size(); i++) {
            System.out.println((i + 1) + ") " + roster.get(i).getName());
        }

        System.out.print("Choose fighter to add: ");
        int choice = scanner.nextInt();
        scanner.nextLine();

        if (choice < 1 || choice > roster.size()) {
            System.out.println("Invalid choice.");
            return;
        }

        Fighter selected = roster.get(choice - 1);
        Fighter copy = copyFighter(selected);

        team.add(copy);

        System.out.println(copy.getName() + " has been added!");
    }

    private Fighter copyFighter(Fighter fighter) {
        if (fighter instanceof Saiyan) {
            Saiyan saiyan = (Saiyan) fighter;

            return new Saiyan(
                    saiyan.getName(),
                    saiyan.getPowerLevel(),
                    saiyan.getMaxHealth(),
                    saiyan.getTransformationName()
            );
        }

        if (fighter instanceof Namekian) {
            return new Namekian(
                    fighter.getName(),
                    fighter.getPowerLevel(),
                    fighter.getMaxHealth()
            );
        }

        return new Fighter(
                fighter.getName(),
                fighter.getPowerLevel(),
                fighter.getMaxHealth()
        );
    }

    private void viewTeam() {
        if (team.isEmpty()) {
            System.out.println("Your team is empty.");
            return;
        }

        System.out.println("\n=== Your Team ===");

        for (int i = 0; i < team.size(); i++) {
            System.out.println("\n" + (i + 1) + ") " + team.get(i).getName());
            team.get(i).showStats();
        }
    }

    private void calculateTeamPower() {
        int totalPower = 0;

        for (Fighter fighter : team) {
            totalPower += fighter.getPowerLevel();
        }

        System.out.println("Total Team Power: " + totalPower);
    }

    private void startBattle() {
        if (team.size() < 2) {
            System.out.println("You need at least 2 fighters to start a battle.");
            return;
        }

        transformedFighters.clear();
        epicFightPlayed = false;

        SoundService.stopSound("MenuSound");

        System.out.println("\nChoose Fighter 1:");
        Fighter fighterOne = chooseFighter();

        System.out.println("\nChoose Fighter 2:");
        Fighter fighterTwo = chooseFighter();

        if (fighterOne == fighterTwo) {
            System.out.println("A fighter cannot battle themselves.");
            SoundService.playLoop("MenuSound");
            return;
        }

        boolean battling = true;

        while (battling && !battleService.isBattleOver(fighterOne, fighterTwo)) {

            System.out.println("\n=== Battle Menu ===");
            System.out.println(fighterOne.getName() + " is fighting " + fighterTwo.getName());

            System.out.println("\nWhose turn is it?");
            System.out.println("1) " + fighterOne.getName());
            System.out.println("2) " + fighterTwo.getName());
            System.out.println("0) Quit Battle");
            System.out.print("Choose fighter: ");

            int turnChoice = scanner.nextInt();
            scanner.nextLine();

            Fighter attacker;
            Fighter target;

            if (turnChoice == 1) {
                attacker = fighterOne;
                target = fighterTwo;
            } else if (turnChoice == 2) {
                attacker = fighterTwo;
                target = fighterOne;
            } else if (turnChoice == 0) {
                SoundService.playSound("UIclick");
                battling = false;
                break;
            } else {
                System.out.println("Invalid choice.");
                continue;
            }

            boolean sameTurn = true;

            while (sameTurn && battling && !battleService.isBattleOver(fighterOne, fighterTwo)) {

                showActionMenu(attacker, target);

                int actionChoice = scanner.nextInt();
                scanner.nextLine();

                switch (actionChoice) {
                    case 1:
                        useAttack(attacker, target);
                        sameTurn = false;
                        break;

                    case 2:
                        useSpecialAbility(attacker);
                        break;

                    case 3:
                        SoundService.playSound("UIclick");
                        attacker.showStats();
                        target.showStats();
                        break;

                    case 4:
                        if (hasTransformed(attacker)) {
                            SoundService.playSound("UIclick");

                            System.out.println(
                                    attacker.getName()
                                            + " has already transformed into "
                                            + getTransformationName(attacker)
                                            + "!"
                            );
                        } else {
                            levelUpFighter(attacker);
                            markTransformed(fighterOne, fighterTwo, attacker);
                            askOtherFighterToTransform(fighterOne, fighterTwo, attacker);
                        }
                        break;

                    case 0:
                        SoundService.playSound("UIclick");
                        battling = false;
                        sameTurn = false;
                        break;

                    default:
                        System.out.println("Invalid option.");
                }
            }
        }

        battleService.showWinner(fighterOne, fighterTwo);
        SoundService.playLoop("MenuSound");
    }

    private void askOtherFighterToTransform(Fighter fighterOne, Fighter fighterTwo, Fighter transformedFighter) {
        Fighter otherFighter;

        if (transformedFighter == fighterOne) {
            otherFighter = fighterTwo;
        } else {
            otherFighter = fighterOne;
        }

        if (hasTransformed(otherFighter)) {
            return;
        }

        System.out.println();
        System.out.println(
                transformedFighter.getName()
                        + " has transformed into "
                        + getTransformationName(transformedFighter)
                        + "!"
        );

        System.out.println("Does " + otherFighter.getName() + " transform as well?");
        System.out.println("1) Yes");
        System.out.println("2) No");
        System.out.print("Choose option: ");

        int choice = scanner.nextInt();
        scanner.nextLine();

        if (choice == 1) {
            levelUpFighter(otherFighter);
            markTransformed(fighterOne, fighterTwo, otherFighter);
        } else {
            SoundService.playSound("UIclick");
            System.out.println(otherFighter.getName() + " stays in base form.");
        }
    }

    private void showActionMenu(Fighter attacker, Fighter target) {
        System.out.println("\n" + attacker.getName() + "'s turn.");
        System.out.println(attacker.getName() + " is facing " + target.getName());

        System.out.println("\nWhat would you like to do?");
        System.out.println("1) Attack");
        System.out.println("2) Use Special Ability");
        System.out.println("3) Show Stats");

        if (hasTransformed(attacker)) {
            System.out.println(
                    "4) "
                            + attacker.getName()
                            + " has transformed into "
                            + getTransformationName(attacker)
            );
        } else {
            System.out.println("4) Level Up / Transform");
        }

        System.out.println("0) Quit Battle");
        System.out.print("Choose option: ");
    }

    private Fighter chooseFighter() {
        for (int i = 0; i < team.size(); i++) {
            System.out.println((i + 1) + ") " + team.get(i).getName());
        }

        System.out.print("Enter choice: ");

        int choice = scanner.nextInt();
        scanner.nextLine();

        return team.get(choice - 1);
    }

    private void useAttack(Fighter attacker, Fighter target) {
        if (attacker.getName().equalsIgnoreCase("Goku")) {
            SoundService.playSound("gokukamehameha");
            System.out.println(attacker.getName() + " uses Kamehameha on " + target.getName() + "!");
        } else {
            SoundService.playSound("punch");
            System.out.println(attacker.getName() + " attacks " + target.getName() + "!");
        }

        battleService.attack(attacker, target);
    }

    private void useSpecialAbility(Fighter fighter) {
        if (fighter instanceof Namekian) {
            SoundService.playSound("piccoloauraforming");

            Namekian namekian = (Namekian) fighter;
            namekian.regenerate();

        } else if (fighter.getName().equalsIgnoreCase("Frieza")) {
            SoundService.playSound("friezatransforms");
            System.out.println("Frieza uses Death Beam!");

        } else {
            SoundService.playSound("UIclick");
            System.out.println(fighter.getName() + " has no special ability right now.");
        }
    }

    private void levelUpFighter(Fighter fighter) {
        if (fighter instanceof Saiyan) {
            playTransformationSound(fighter);

            Saiyan saiyan = (Saiyan) fighter;
            saiyan.transform();

        } else if (fighter instanceof Namekian) {
            playTransformationSound(fighter);

            fighter.setPowerLevel(fighter.getPowerLevel() + 1500);

            System.out.println(
                    fighter.getName()
                            + " transforms into "
                            + getTransformationName(fighter)
                            + "!"
            );

            System.out.println("New power level: " + fighter.getPowerLevel());

        } else if (fighter.getName().equalsIgnoreCase("Frieza")) {
            playTransformationSound(fighter);

            fighter.setPowerLevel(fighter.getPowerLevel() + 2500);

            System.out.println("Frieza transforms into Golden Frieza!");
            System.out.println("New power level: " + fighter.getPowerLevel());

        } else {
            SoundService.playSound("UIclick");

            fighter.setPowerLevel(fighter.getPowerLevel() + 1000);

            System.out.println(fighter.getName() + " leveled up!");
            System.out.println("New power level: " + fighter.getPowerLevel());
        }
    }

    private void playTransformationSound(Fighter fighter) {
        String soundName = getTransformationSoundName(fighter);

        System.out.println();
        System.out.println(fighter.getName() + " is transforming...");

        SoundService.stopTransformationSounds();
        SoundService.playSound(soundName);

        for (int i = 4; i >= 1; i--) {
            System.out.println("Transforming... " + i);

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                System.out.println("Transformation interrupted.");
            }
        }

        SoundService.stopSound(soundName);

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            System.out.println("Pause interrupted.");
        }
    }

    private String getTransformationSoundName(Fighter fighter) {
        String name = fighter.getName().toLowerCase();

        if (name.equals("goku")) {
            return "gokuultrainstinct";
        } else if (name.equals("vegeta")) {
            return "vegetatransforms";
        } else if (name.equals("broly")) {
            return "brolytransforms";
        } else if (name.equals("frieza")) {
            return "friezatransforms";
        } else if (name.equals("piccolo")) {
            return "piccolotransforms";
        } else {
            return "UIclick";
        }
    }

    private String getTransformationName(Fighter fighter) {
        if (fighter instanceof Saiyan) {
            Saiyan saiyan = (Saiyan) fighter;
            return saiyan.getTransformationName();
        }

        if (fighter instanceof Namekian) {
            return "Orange Piccolo";
        }

        if (fighter.getName().equalsIgnoreCase("Frieza")) {
            return "Golden Frieza";
        }

        return "Powered Up Form";
    }

    private boolean hasTransformed(Fighter fighter) {
        return transformedFighters.contains(fighter);
    }

    private void markTransformed(Fighter fighterOne, Fighter fighterTwo, Fighter fighter) {
        if (!transformedFighters.contains(fighter)) {
            transformedFighters.add(fighter);
        }

        boolean fighterOneTransformed = transformedFighters.contains(fighterOne);
        boolean fighterTwoTransformed = transformedFighters.contains(fighterTwo);

        if (fighterOneTransformed && fighterTwoTransformed && !epicFightPlayed) {
            SoundService.stopTransformationSounds();

            System.out.println("\n=================================");
            System.out.println("🔥 AN EPIC BATTLE HAS STARTED! 🔥");
            System.out.println("=================================");

            SoundService.playSound("epicfight");

            epicFightPlayed = true;
        }
    }
}