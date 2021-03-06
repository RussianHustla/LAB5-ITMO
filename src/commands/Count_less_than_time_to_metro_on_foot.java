package commands;

import app.InvalidInputException;
import collection.CollectionManager;
import collection.Flat;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;

public class Count_less_than_time_to_metro_on_foot extends Command {

    public Count_less_than_time_to_metro_on_foot() {
        command = "count_less_than_time_to_metro_on_foot";
        description = "Вывести количество элементов, значение поля timeToMetroOnFoot которых меньше заданного";
    }


    @Override
    public void execute(CollectionManager collection, String[] args) throws IOException, ParserConfigurationException, TransformerException {
        if (args.length < 1) {
            throw new InvalidInputException("У вызываемой команды отсутствует аргумент");
        }
        Double referenceTime;
        try {
            referenceTime = Double.valueOf(args[0]);
        } catch (NumberFormatException e) {
            throw new InvalidInputException("У вызываемой команды некорректный аргумент (требуется число)");
        }
        int count = 0;
        for (Flat flat : collection.toList()) {
            if (flat.getTimeToMetroOnFoot() < referenceTime) count++;
        }
        System.out.println("Количество элементов, значение поля timeToMetroOnFoot которых меньше заданного = " + count);

    }
}
