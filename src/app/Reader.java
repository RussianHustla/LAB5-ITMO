package app;

import collection.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;

import static java.lang.Math.random;

public class Reader {



    public static String request() throws IOException {
        String answer;
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        answer = reader.readLine();
        if (answer.equals("")) {
            return null;
        }
        return answer.trim();
    }

    public static String request(String message) throws IOException {
        System.out.println(message);
        String answer = request();
        return answer;
    }

    public static String request(String message, boolean nullable) throws IOException {
        String answer = request(message);
        if (nullable) {
            return answer;
        }
        else if (answer == null){
            do {
                System.err.println("Значение не может быть null");
                answer = request(message);
            } while (answer == null);
        }
        return answer;
    }

    public static String request(String message, int min, int max) throws IOException {
        String answer;
        try {
            answer = request(message, false);
            if (!checkNumber(Double.parseDouble(answer), min, max)) {
                System.err.println("Некорректные границы числа, повторите ввод");
                return request(message, min, max);
            }
        } catch (NumberFormatException e) {
            System.err.println("Некорректное число, повторите ввод");
            return request(message, min, max);
        }
        return answer;
    }

    public static Flat requestForFlat() throws IOException {
        String name = request("Введите название квартиры: ", false);
        Coordinates coordinates = requestForCoordinates();
        Date creationDate = new Date();
        Double area = Double.valueOf(request("Введите жилую площадь (число, большее 0): ", 1, -1));
        Long numberOfRooms = Long.valueOf(request("Введите количество комнат (целое число, большее 0): ", 1, -1));
        int kitchenArea = Integer.parseInt(request("Введите площадь кухни (целое число, большее 0): ", 1, -1));
        Double timeToMetroOnFoot = Double.valueOf(request("Введите время до метро пешком (число, большее 0): ", 1, -1));
        Furnish furnish = requestForFurnish();
        House house = requestForHouse();
        int id = generateId(1000);
        return new Flat(id, name, coordinates, creationDate, area, numberOfRooms, kitchenArea, timeToMetroOnFoot, furnish, house);
    }

    public static Coordinates requestForCoordinates() throws IOException {
        System.out.println("Введите координаты квартиры: ");
        double x;
        double y;
        try {
            x = Double.parseDouble(request("Введите расположение квартиры по X (число): ", false));
            y = Double.parseDouble(request("Введите расположение квартиры по Y (число): ", false));
        } catch (NumberFormatException e) {
            System.err.println("Некорректные координаты, повторите ввод");
            return requestForCoordinates();
        }
        return new Coordinates(x, y);
    }

    public static Furnish requestForFurnish() throws IOException {
        Furnish furnish;
        try {
            String answer = request("Введите состояние квартиры КАПСОМ (DESIGNER,NONE,FINE,BAD,LITTLE) : ", false);
            furnish = Furnish.valueOf(answer.toUpperCase());
        } catch (IllegalArgumentException e) {
            System.err.println("Введите один из доступных вариантов");
            return requestForFurnish();
        }
        return furnish;
    }

    public static House requestForHouse() throws IOException {
        System.out.println("Введите данные о доме: ");
        String name = request("Введите называние дома: ", false);
        Integer year = Integer.valueOf(request("Введите возраст дома (целое число, большее 0): ", 1, -1));
        long numberOfFlatsOnFloor = Long.parseLong(request("Введите количество квартир на этаже (целое число, большее 0): ", 1, -1));
        return new House(name, year, numberOfFlatsOnFloor);
    }

    public static boolean checkNumber(double s, int min, int max) {
        return ((min < 0 || s >= min) && (max < 0 || s <= max));
    }

    public static int generateId(int max) {
        final int min = 1;
        int generatedId;
        do {
            max -= min;
            generatedId = (int) (Math.random() * ++max) + min;
        } while (!CollectionManager.getInstance().isIdFree(generatedId));
        return generatedId;
    }
}
