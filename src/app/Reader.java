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
        String answer = null;
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        answer = reader.readLine();
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
                //throw new InvalidInputException("Значение не может быть null");
                System.err.println("Значение не может быть null");
                answer = request(message);
            } while (answer == null);
        }
        return answer;
    }

    public static String request(String message, int min, int max) throws IOException {
        String answer = null;
        do {
            try {
                answer = request(message, false);
            } catch (Exception e) {
                System.err.println("Введите число");
            }
        } while (!checkNumber(Double.parseDouble(answer), min, max));
        return answer;
    }

    public static Flat requestForFlat() throws IOException {
        String name = request("Введите название квартиры: ", false);
        Coordinates coordinates = requestForCoordinates();
        Date creationDate = new Date();
        Double area = Double.valueOf(request("Введите жилую площадь (целое число, большее 0): ", 0, -1));
        Long numberOfRooms = Long.valueOf(request("Введите количество комнат (целое число, большее 0): ", 0, -1));
        int kitchenArea = Integer.parseInt(request("Введите площадь кухни (целое число, большее 0): ", 0, -1));
        Double timeToMetroOnFoot = Double.valueOf(request("Введите время до метро пешком (целое число, большее 0): ", 0, -1));
        Furnish furnish = requestForFurnish();
        House house = requestForHouse();
        int id = generateId(1000);
        return new Flat(id, name, coordinates, creationDate, area, numberOfRooms, kitchenArea, timeToMetroOnFoot, furnish, house);
    }

    public static Coordinates requestForCoordinates() throws IOException {
        System.out.println("Введите координаты квартиры: ");
        double x = Double.parseDouble(request("Введите расположение квартиры по X (вещественное число): ", false));
        double y = Double.parseDouble(request("Введите расположение квартиры по Y (вещественное число): ", false));
        return new Coordinates(x, y);
    }

    public static Furnish requestForFurnish() throws IOException {
        Furnish furnish = Furnish.NONE;
        try {
            String answer = request("Введите состояние квартиры КАПСОМ (DESIGNER,NONE,FINE,BAD,LITTLE) : ", false);
            /*answer = answer.toUpperCase();
            System.out.println(answer);*/
            furnish = Furnish.valueOf(answer);
        } catch (IllegalArgumentException e) {
            System.err.println("Введите один из доступных вариантов");
            requestForFurnish();
        }
        return furnish;
    }

    public static House requestForHouse() throws IOException {
        System.out.println("Введите данные о доме: ");
        String name = request("Введите называние дома: ", false);
        Integer year = Integer.valueOf(request("Введите возраст дома (целое число, большее 0): ", 0, -1));
        long numberOfFlatsOnFloor = Long.parseLong(request("Введите количество квартир на этаже: ", 0, -1));
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
