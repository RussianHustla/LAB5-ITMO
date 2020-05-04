package commands;

import app.InvalidInputException;
import collection.CollectionManager;
import collection.Flat;
import collection.Furnish;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;

public class Filter_by_furnish extends Command {

    public Filter_by_furnish() {
        command = "filter_by_furnish";
        description = "Вывести элементы, значение поля furnish которых равно заданному";
    }

    @Override
    public void execute(CollectionManager collection, String[] args) throws IOException, ParserConfigurationException, TransformerException {
        if (args.length < 1) {
            throw new InvalidInputException("У вызываемой команды отсутствует аргумент");
        }
        Furnish referenceFurnish;
        try {
            referenceFurnish = Furnish.valueOf(args[0].toUpperCase());
        } catch ( NumberFormatException e ) {
            throw new InvalidInputException("У вызываемой команды некорректный аргумент (требуется число)");
        } catch (IllegalArgumentException e) {
            throw  new InvalidInputException("Несуществующее значение furnish");
        }
        for (Flat flat : collection.toList()) {
            if (flat.getFurnish().equals(referenceFurnish)) {
                System.out.println(flat);
            }
        }
        }
}
