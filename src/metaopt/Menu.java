package metaopt;

import java.util.logging.Level;
import java.util.logging.Logger;
import metaopt.utils.MenuUtils;

/**
 * Menu manager.
 * @author joseja
 */
public class Menu {

    /**
     * List of all possible algorithms used to resolve problems.
     */
    public enum Algorithm {
        GT, BL, BT, ES, AGG, AGE
    }

    /**
     * File containing the problem to resolve.
     */
    String fileToLoad;

    /**
     * Chosen algorithm to resolve the problem.
     */
    Algorithm algorithm;

    /**
     * Number of times the problem will be resolved with the specified
     * algorithm.
     */
    int numberOfIterations;

    /**
     * Public constructor; the default parameters are initialized.
     */
    public Menu() {
        // Default params.
        fileToLoad = "la01.txt";
        algorithm = Algorithm.AGE;
        numberOfIterations = 1;
    }

    public void mainMenu() {
        while (true) {
            int option;
            System.out.println("MENU PRINCIPAL");
            System.out.print("\n");
            System.out.println("1.  Seleccion del archivo (actual: " + fileToLoad + ")");
            System.out.println("2.  Seleccion del algoritmo (actual: " + algorithm + ")");
            System.out.println("3.  Seleccion del numero de iteraciones (actual: " + numberOfIterations + ")");
            System.out.println("4.  Obtener media");
            System.out.println("5.  Obtener desviación típica");
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
                    median();
                    break;
                case 5:
                    deviation();
                    break;
                default:
                    System.exit(0);
            }
        }
    }

    public void selectFile() {
        int option;
        System.out.println("SELECCION DEL ARCHIVO");
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
                fileToLoad = "abz07.txt";
                break;
            case 2:
                fileToLoad = "abz08.txt";
                break;
            case 3:
                fileToLoad = "abz09.txt";
                break;
            case 4:
                fileToLoad = "la01.txt";
                break;
            case 5:
                fileToLoad = "la02.txt";
                break;
            case 6:
                fileToLoad = "la03.txt";
                break;
            case 7:
                fileToLoad = "la04.txt";
                break;
            case 8:
                fileToLoad = "la05.txt";
                break;
            case 9:
                fileToLoad = "mt06.txt";
                break;
            case 10:
                fileToLoad = "swv01.txt";
                break;
        }
    }

    public void selectAlgorithm() {
        int option;
        System.out.println("SELECCION DEL ALGORITMO");
        System.out.print("\n");
        System.out.println("1.  GT");
        System.out.println("2.  BL");
        System.out.println("3.  BT");
        System.out.println("4.  ES");
        System.out.println("5.  AGG");
        System.out.println("6.  AGE");
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
        }
    }

    public void selectNumberOfIterations() {
        System.out.println("SELECCION DEL NUMERO DE ITERACIONES");
        System.out.print("Introduzca un numero del 1 al 100 (-1 para cancelar): ");
        numberOfIterations = MenuUtils.getIntInput();
        try {
            MenuUtils.cleanConsole();
        } catch (InterruptedException ex) {
            Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void median() {
        if (areOptionsValid()) {
            ResolverFramework resolver = new ResolverFramework(fileToLoad, algorithm);
            double averageMakespan = resolver.getAverageMakespan(numberOfIterations);
            System.out.println("Average MAX_SPAN: " + averageMakespan + "\n");
        }
    }

    public void deviation() {
        if (areOptionsValid()) {
            ResolverFramework resolver = new ResolverFramework(fileToLoad, algorithm);
            double makespanDeviation = resolver.getDeviation(numberOfIterations);
            System.out.print("Makespan deviation: " + makespanDeviation + "\n");
        }
    }

    private boolean areOptionsValid() {
        if (numberOfIterations <= 0) {
            try {
                MenuUtils.cleanConsole();
                System.out.println("Numero de iteraciones no valido");
                MenuUtils.waitSeconds(2);
                MenuUtils.cleanConsole();
            } catch (InterruptedException ex) {
                Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
            }
            return false;
        } else if (numberOfIterations > 100) {
            try {
                MenuUtils.cleanConsole();
                System.out.println("Numero de iteraciones demasiado alto");
                MenuUtils.waitSeconds(2);
                MenuUtils.cleanConsole();
            } catch (InterruptedException ex) {
                Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
            }
            return false;
        }
        return true;
    }

}
