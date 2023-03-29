package client.builders;

import general.models.Event;
import general.models.EventType;

import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.Set;

/**
 * Event object creation class
 */
public class EventBuilder implements Builderable<Event> {
    /**
     * Scanner object
     */
    private Scanner scanner;

    @Override
    public Event buildObject() {
        System.out.println("▶ ▶ ▶   Объект события   ◀ ◀ ◀");
        Event resObj = new Event();
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
            checkCond = Event.checkAllConditionsByKey(obj, "name");
        } while (!checkCond);
        String name = obj;

        do {
            System.out.println("Введите " + "дату в формате YYYY-MM-DD" + ": ");
            try {
                obj = scanner.nextLine();
            } catch (NoSuchElementException e) {
                System.out.println("Введен символ завершения ввода. Выполнение программы завершено.");
                System.exit(0);
            }
            obj += "T00:00:00";
            try {
                LocalDateTime.parse(obj);
                check = true;
            } catch (DateTimeException e) {
                System.out.println(e.getClass() + "Вы ввели дату в неверном формате.");
            }
            checkCond = Event.checkAllConditionsByKey(obj, "date");
        } while (!check || !checkCond);
        LocalDateTime date = LocalDateTime.parse(obj);

        check = false;
        do {
            System.out.println("Введите " + "минимальный возраст" + ": ");
            try {
                obj = scanner.nextLine();
            } catch (NoSuchElementException e) {
                System.out.println("Введен символ завершения ввода. Выполнение программы завершено.");
                System.exit(0);
            }
            if (obj.equals("")) {
                obj = null;
                check = true;
            } else {
                try {
                    Long.parseLong(obj);
                    check = true;
                } catch (NumberFormatException e) {
                    System.out.println(e + "Введенный аргумент не число.");
                }
                checkCond = Event.checkAllConditionsByKey(obj, "minAge");
            }
        } while (!check || !checkCond);
        Long minAge = null;
        if (obj != null) {
            minAge = Long.parseLong(obj);
        }

        check = false;
        do {
            System.out.println("Введите " + "описание" + ": ");
            try {
                obj = scanner.nextLine();
            } catch (NoSuchElementException e) {
                System.out.println("Введен символ завершения ввода. Выполнение программы завершено.");
                System.exit(0);
            }
            checkCond = Event.checkAllConditionsByKey(obj, "description");
        } while (!checkCond);
        String description = obj;

        check = false;
        do {
            System.out.println("Введите " + "тип события из вариантов (E_SPORTS, FOOTBALL, EXPOSITION)" + ": ");
            try {
                obj = scanner.nextLine();
            } catch (NoSuchElementException e) {
                System.out.println("Введен символ завершения ввода. Выполнение программы завершено.");
                System.exit(0);
            }
            Set<String> types = Set.of("E_SPORTS", "FOOTBALL", "EXPOSITION");

            if (!types.contains(obj)) {
                System.out.println("Вы ввели несуществующий тип события.");
            } else {
                check = true;
            }
            checkCond = Event.checkAllConditionsByKey(obj, "eventType");
        } while (!check || !checkCond);
        EventType eventType = EventType.valueOf(obj);

        resObj.setName(name);
        resObj.setDate(date);
        resObj.setMinAge(minAge);
        resObj.setDescription(description);
        resObj.setEventType(eventType);

        return resObj;
    }

    public EventBuilder(Scanner scanner) {
        this.scanner = scanner;
    }
}
