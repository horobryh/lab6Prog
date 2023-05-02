package client.builders;

import general.models.*;

import java.util.Date;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.Set;

/**
 * Ticket object creation class
 */
public class TicketBuilder implements Builderable<Ticket> {
    private Scanner scanner;
    @Override
    public Ticket buildObject() {
        System.out.println("▶ ▶ ▶   Объект билета   ◀ ◀ ◀");
        Ticket resObj = new Ticket();
        String obj = null;

        boolean check = false;
        boolean checkCond;

        do {
            System.out.println("Введите " + "название" + ": ");
            try {
                obj = scanner.nextLine();
            } catch (NoSuchElementException e) {
                System.out.println("Введен символ завершения ввода. Выполнение программы завершено.");
                System.exit(0);
            }

            checkCond = Ticket.checkAllConditionsByKey(obj, "name");
        } while (!checkCond);
        String name = obj;

        check = false;
        Coordinates coords = new CoordinatesBuilder(scanner).buildObject();

        Date creationDate = new Date();

        check = false;
        do {
            System.out.println("Введите " + "цену" + ": ");
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
                System.out.println(e + " Введенный аргумент не число.");
                continue;
            }
            checkCond = Ticket.checkAllConditionsByKey(obj, "price");
        } while (!check || !checkCond);
        Integer price = Integer.parseInt(obj);

        check = false;
        do {
            System.out.println("Введите " + "скидку" + ": ");
            try {
                obj = scanner.nextLine();
            } catch (NoSuchElementException e) {
                System.out.println("Введен символ завершения ввода. Выполнение программы завершено.");
                System.exit(0);
            }
            try {
                Long.parseLong(obj);
                check = true;
            } catch (NumberFormatException e) {
                System.out.println(e + "Введенный аргумент не число.");
                continue;
            }
            checkCond = Ticket.checkAllConditionsByKey(obj, "discount");
        } while (!check || !checkCond);
        Long discount = Long.parseLong(obj);

        do {
            System.out.println("Введите " + "комментарий" + ": ");
            try {
                obj = scanner.nextLine();
            } catch (NoSuchElementException e) {
                System.out.println("Введен символ завершения ввода. Выполнение программы завершено.");
                System.exit(0);
            }
            checkCond = Ticket.checkAllConditionsByKey(obj, "comment");
        } while (!checkCond);
        String comment = obj;

        check = false;
        do {
            System.out.println("Введите " + "тип билета из вариантов (VIP, USUAL, BUDGETARY, CHEAP)" + ": ");
            try {
                obj = scanner.nextLine();
            } catch (NoSuchElementException e) {
                System.out.println("Введен символ завершения ввода. Выполнение программы завершено.");
                System.exit(0);
            }
            Set<String> types = Set.of("VIP", "USUAL", "BUDGETARY", "CHEAP");

            if (!types.contains(obj)) {
                System.out.println("Вы ввели несуществующий тип билета.");
            } else {
                check = true;
            }
            checkCond = Ticket.checkAllConditionsByKey(obj, "type");
        } while (!check || !checkCond);
        TicketType ticketType = TicketType.valueOf(obj);

        Event event = new EventBuilder(scanner).buildObject();

        resObj.setName(name);
        resObj.setCoordinates(coords);
        resObj.setCreationDate(creationDate);
        resObj.setPrice(price);
        resObj.setDiscount(discount);
        resObj.setComment(comment);
        resObj.setType(ticketType);
        resObj.setEvent(event);

        return resObj;
    }

    public TicketBuilder(Scanner scanner) {
        this.scanner = scanner;
    }
}
