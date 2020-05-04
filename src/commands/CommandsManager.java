package commands;

import app.NoSuchCommandException;
import app.Reader;
import collection.CollectionManager;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class CommandsManager {
    //Invoker

    private static CommandsManager instance;

    public static CommandsManager getInstance() {
        if (instance == null) {
            instance = new CommandsManager();
        }
        return instance;
    }

    private Map<String, Command> commands = new HashMap<>();
    private ArrayList<Command> history = new ArrayList<>();

    public CommandsManager() {
        addCommand(new Add());
        addCommand(new Exit());
        addCommand(new Clear());
        addCommand(new Help());
        addCommand(new Remove_first());
        addCommand(new Show());
        addCommand(new Save());
        addCommand(new History());
        addCommand(new Remove_by_id());
        addCommand(new Info());
        addCommand(new Update());
        addCommand(new Count_less_than_time_to_metro_on_foot());
        addCommand(new Filter_by_furnish());
        addCommand(new Count_greater_than_house());
        addCommand(new Add_if_min());
        addCommand(new Execute_script());

    }

    private void addCommand(Command command) {
        commands.put(command.getCommand(), command);
    }

    public Command getCommand(String s) throws NoSuchCommandException {
        if (!commands.containsKey(s)) {
            throw new NoSuchCommandException();
        }
        return commands.getOrDefault(s, null);
    }

    public List<Command> getAllCommands() {
        return commands.keySet().stream().map(x -> (commands.get(x))).collect(Collectors.toList());
    }

    public List<Command> getHistory(int count) {
        ArrayList<Command> requestedHistory = new ArrayList<>();
        if (count > history.size()) count = history.size();
        for (int i = history.size() - count; i < history.size(); i++) {
            requestedHistory.add(history.get(i));
        }
        return requestedHistory;
    }

    public void executeCommand(CollectionManager collection, String s) throws IOException, ParserConfigurationException, TransformerException {
        String[] parsedCommand = parseCommand(s);
        Command command = getCommand(parsedCommand[0]);
        String[] args = Arrays.copyOfRange(parsedCommand, 1, parsedCommand.length);
        history.add(command);
        command.execute(collection, args);
    }

    public boolean confirmExecution(String question) throws IOException {
        String answer = Reader.request(question, false).toLowerCase();
        if (answer.equals("y") || answer.equals("д") || answer.equals("+") || answer.equals("yes") || answer.equals("да")) {
            return true;
        } else return false;
    }

    public String[] parseCommand(String line) {
        return line.split(" ");
    }
}
