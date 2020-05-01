package commands;

import collection.CollectionManager;
import collection.Flat;

import java.io.IOException;

public class Show extends  Command {

    public Show() {
        command = "show";
        description = "Вывести в стандартный поток вывода все элементы коллекции в строков представлении";
    }

    @Override
    public void execute(CollectionManager collection, String[] args) throws IOException {
        System.out.println("Элементов в коллекции: " + collection.size());
        for (Flat flat : collection.toList()) {
            System.out.println(flat);
            System.out.println();
        }
    }
}
