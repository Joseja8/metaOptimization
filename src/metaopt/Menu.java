/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metaopt;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import metaopt.utils.MenuUtils;
import metaopt.utils.RandomStatic;

/**
 *
 * @author joseja
 */
public class Menu {

    public enum Algorithm {
        GT, BL, BT, ES, AGG, AGE
    }

    String file;
    Algorithm algorithm;
    int numberOfIterations;
    long seed;

    public Menu() {
        // Default params.
        file = "abz07.txt";
        algorithm = Algorithm.GT;
        numberOfIterations = 1;
    }

    public void mainMenu() {
        while (true) {
            int option;
            System.out.println("MENU PRINCIPAL");
            System.out.print("\n");
            System.out.println("1.  Seleccion del archivo (actual: " + file + ")");
            System.out.println("2.  Seleccion del algoritmo (actual: " + algorithm + ")");
            System.out.println("3.  Seleccion del numero de iteraciones (actual: " + numberOfIterations + ")");
            System.out.println("4.  Resolver el problema");
            System.out.println("5.  Resetear el RNG");
            System.out.print("\n");
            System.out.print("Introduzca un numero (-1 para salir): ");
            option = MenuUtils.getIntInput();

            try {
                MenuUtils.cleanConsole();
            } catch (InterruptedException ex) {
                Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
            }

            switch (option) {
                case 1:
                    selectFile();
                    break;
                case 2:
                    selectAlgorithm();
                    break;
                case 3:
                    selectNumberOfIterations();
                    break;
                case 4:
                    problemResolver();
                    break;
                case 5:
                    RandomStatic.resetRandom();
                    break;
                default:
                    System.exit(0);
            }
        }
    }

    public void selectFile() {
        int option;
        System.out.println("SELECCION DE ARCHIVO");
        System.out.print("\n");
        System.out.println("1.  abz07");
        System.out.println("2.  abz08");
        System.out.println("3.  abz09");
        System.out.println("4.  la01");
        System.out.println("5.  la02");
        System.out.println("6.  la03");
        System.out.println("7.  la04");
        System.out.println("8.  la05");
        System.out.println("9.  mt06");
        System.out.println("10. swv01");
        System.out.print("\n");
        System.out.print("Introduzca un numero (-1 para cancelar): ");
        option = MenuUtils.getIntInput();

        try {
            MenuUtils.cleanConsole();
        } catch (InterruptedException ex) {
            Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
        }

        switch (option) {
            case 1:
                file = "abz07.txt";
                break;
            case 2:
                file = "abz08.txt";
                break;
            case 3:
                file = "abz09.txt";
                break;
            case 4:
                file = "la01.txt";
                break;
            case 5:
                file = "la02.txt";
                break;
            case 6:
                file = "la03.txt";
                break;
            case 7:
                file = "la04.txt";
                break;
            case 8:
                file = "la05.txt";
                break;
            case 9:
                file = "mt06.txt";
                break;
            case 10:
                file = "swv01.txt";
                break;
            default:
                return;
        }
    }

    public void selectAlgorithm() {
        int option;
        System.out.println("SELECCION DE ALGORITMO");
        System.out.print("\n");
        System.out.println("1.  GT");
        System.out.println("2.  BL");
        System.out.println("3.  BT");
        System.out.println("4.  ES");
        System.out.println("5.  AGG");
        System.out.println("5.  AGE");
        System.out.print("\n");
        System.out.print("Introduzca un numero (-1 para cancelar): ");
        option = MenuUtils.getIntInput();

        try {
            MenuUtils.cleanConsole();
        } catch (InterruptedException ex) {
            Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
        }

        switch (option) {
            case 1:
                algorithm = Algorithm.GT;
                break;
            case 2:
                algorithm = Algorithm.BL;
                break;
            case 3:
                algorithm = Algorithm.BT;
                break;
            case 4:
                algorithm = Algorithm.ES;
                break;
            case 5:
                algorithm = Algorithm.AGG;
                break;
            case 6:
                algorithm = Algorithm.AGE;
                break;
            default:
                return;
        }
    }

    public void selectNumberOfIterations() {
        System.out.println("SELECCION DE NUMERO DE ITERACIONES");
        System.out.print("\n");
        System.out.print("Introduzca un numero (max 100 iteraciones) (-1 para cancelar): ");
        numberOfIterations = MenuUtils.getIntInput();
        try {
            MenuUtils.cleanConsole();
        } catch (InterruptedException ex) {
            Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void problemResolver() {
        if (numberOfIterations <= 0) {
            System.out.print("Numero de iteraciones no valido");
            try {
                MenuUtils.cleanConsole();
            } catch (InterruptedException ex) {
                Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
            }
            return;
        } else if (numberOfIterations > 100) {
            System.out.print("Numero de iteraciones demasiado alto");
            try {
                MenuUtils.cleanConsole();
            } catch (InterruptedException ex) {
                Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
            }
            return;
        }

        ArrayList<Double> results = new ArrayList<>();
        ProblemResolver problemResolver = new ProblemResolver(file, algorithm, numberOfIterations);
        results = problemResolver.getResults();
        
        System.out.print("MAXSPAN   : " + results.get(1) + "\n");
        System.out.print("TIME      : " + results.get(0) + "\n");  
        System.out.print("DEVIATION : " + results.get(2) + "\n");        
    }

}
