package client.builders;

import general.models.Coordinates;

import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Coordinates object creation class
 */
public class CoordinatesBuilder implements Builderable<Coordinates> {
    /**
     * Scanner object
     */
    private Scanner scanner;

    @Override
    public Coordinates buildObject() {
        System.out.println("▶ ▶ ▶   Объект координат   ◀ ◀ ◀");
        Coordinates resObj = new Coordinates();
        String obj = null;

        boolean check = false;
        boolean checkCond;
        do {
            System.out.println("Введите " + "x" + ": ");
            try {
                obj = scanner.nextLine();
            } catch (NoSuchElementException e) {
                System.out.println("Введен символ завершения ввода. Выполнение программы завершено.");
                System.exit(0);
            }
            try {
                Integer.parseInt(obj);
                check = true;
            } catch (NumberFormatException e) {
                System.out.println(e.getClass() + ": Введенный аргумент не число.");
            }
            checkCond = Coordinates.checkAllConditionsByKey(obj, "x");

        } while (!check || !checkCond);
        resObj.setX(Integer.parseInt(obj));

        check = false;
        do {
            System.out.println("Введите " + "y" + ": ");
            try {
                obj = scanner.nextLine();
            } catch (NoSuchElementException e) {
                System.out.println("Введен символ завершения ввода. Выполнение программы завершено.");
                System.exit(0);
            }
            try {
                Integer.parseInt(obj);
                check = true;
            } catch (NumberFormatException e) {
                System.out.println(e.getClass() + ": Введенный аргумент не число.");
            }
            checkCond = Coordinates.checkAllConditionsByKey(obj, "y");
        } while (!check || !checkCond);
        resObj.setY(Float.parseFloat(obj));

        return resObj;
    }

    public CoordinatesBuilder(Scanner scanner) {
        this.scanner = scanner;
    }
}