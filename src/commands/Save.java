package commands;

import collection.CollectionManager;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

public class Save extends Command {
    private static final String path = "flats.xml"; //нужно брать путь с командной строки!

    public Save() {
        command = "save";
        description = "Сохранить коллекцию в файл";
    }

    @Override
    public void execute(CollectionManager collection, String[] args) throws IOException, ParserConfigurationException {
        if (CommandsManager.getInstance().confirmExecution("Файл с коллекцией будет перезаписан. Продолжить? y/n")) {
            String str = collection.toXml();
            collection.save(str.replaceFirst("UTF-16", "UTF-8"), path);
            System.out.println("Коллекция успешно сохранена в файл " + path);
            collection.setHasUnsavedChanges(false);
            File temp = new File("temp.xml");
            temp.delete();
        }
    }
}
