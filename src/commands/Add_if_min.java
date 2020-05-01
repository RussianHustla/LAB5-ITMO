package commands;

import app.Reader;
import collection.CollectionManager;
import collection.Flat;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.util.Collections;

public class Add_if_min extends Command {

    public Add_if_min() {
        command = "add_if_min";
        description = "Добавить новый элемент в коллекцию, если его значение меньше, чем у наименьшего элемента этой коллекции";
    }

    @Override
    public void execute(CollectionManager collection, String[] args) throws IOException, ParserConfigurationException, TransformerException {
        Flat flat = Reader.requestForFlat();
        if (collection.getMinimal().compareTo(flat) > 0) {
            collection.add(flat);
        }
    }
}
