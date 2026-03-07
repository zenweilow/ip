import java.util.Scanner;

public class Hogrider {

    private static final String FILE_PATH = "data/hogrider.txt";

    private final Storage storage;
    private final TaskList tasks;
    private final Ui ui;
    private final Parser parser;

    private static final String CMD_BYE = "bye";
    private static final String CMD_LIST = "list";
    private static final String CMD_MARK = "mark";
    private static final String CMD_UNMARK = "unmark";
    private static final String CMD_TODO = "todo";
    private static final String CMD_DEADLINE = "deadline";
    private static final String CMD_EVENT = "event";
    private static final String CMD_DELETE = "delete";
    private static final String CMD_FIND = "find";

    public Hogrider(String filePath) {
        ui = new Ui();
        parser = new Parser();
        storage = new Storage(filePath);

        TaskList loadedTasks;
        try {
            loadedTasks = new TaskList(storage.load());
        } catch (HogriderException e) {
            ui.showError(e.getMessage());
            loadedTasks = new TaskList();
        }

        tasks = loadedTasks;
    }

    public static void main(String[] args) {
        new Hogrider(FILE_PATH).run();
    }

    public void run() {
        Scanner scanner = new Scanner(System.in);
        ui.showGreeting();

        while (true) {
            String input = scanner.nextLine().trim();

            try {
                boolean shouldContinue = handleCommand(input);
                if (!shouldContinue) {
                    break;
                }
            } catch (HogriderException e) {
                ui.showError(e.getMessage());
            }
        }

        scanner.close();
    }

    private boolean handleCommand(String input) throws HogriderException {
        String commandWord = parser.parseCommandWord(input);

        switch (commandWord) {
        case CMD_BYE:
            ui.showExit();
            return false;

        case CMD_LIST:
            ui.showList(tasks);
            return true;

        case CMD_MARK:
            markTask(input);
            return true;

        case CMD_UNMARK:
            unmarkTask(input);
            return true;

        case CMD_DELETE:
            deleteTask(input);
            return true;

        case CMD_TODO:
            addTodo(input);
            return true;

        case CMD_DEADLINE:
            addDeadline(input);
            return true;

        case CMD_EVENT:
            addEvent(input);
            return true;

        case CMD_FIND:
            findTask(input);
            return true;

        case "":
            throw new HogriderException("you never type anything leh");

        default:
            throw new HogriderException(
                    "aiyo i don't understand leh. Try: todo, deadline, event, list, mark, unmark, delete, bye");
        }
    }


    private void addTodo(String input) throws HogriderException {
        String description = parser.parseTodo(input);
        Task task = new Todo(description);

        tasks.addTask(task);
        storage.save(tasks.getAllTasks());
        ui.showTaskAdded(task, tasks.size());
    }

    private void addDeadline(String input) throws HogriderException {
        String[] deadlineParts = parser.parseDeadline(input);
        Task task = new Deadline(deadlineParts[0], deadlineParts[1]);

        tasks.addTask(task);
        storage.save(tasks.getAllTasks());
        ui.showTaskAdded(task, tasks.size());
    }

    private void addEvent(String input) throws HogriderException {
        String[] eventParts = parser.parseEvent(input);
        Task task = new Event(eventParts[0], eventParts[1], eventParts[2]);

        tasks.addTask(task);
        storage.save(tasks.getAllTasks());
        ui.showTaskAdded(task, tasks.size());
    }

    private void markTask(String input) throws HogriderException {
        int index = parser.parseIndex(input, CMD_MARK, tasks.size());
        tasks.markTask(index);
        storage.save(tasks.getAllTasks());
        ui.showTaskMarked(tasks.getTask(index));
    }

    private void unmarkTask(String input) throws HogriderException {
        int index = parser.parseIndex(input, CMD_UNMARK, tasks.size());
        tasks.unmarkTask(index);
        storage.save(tasks.getAllTasks());
        ui.showTaskUnmarked(tasks.getTask(index));
    }

    private void deleteTask(String input) throws HogriderException {
        int index = parser.parseIndex(input, CMD_DELETE, tasks.size());
        Task removedTask = tasks.deleteTask(index);
        storage.save(tasks.getAllTasks());
        ui.showTaskDeleted(removedTask, tasks.size());
    }

    private void findTask(String input) throws HogriderException {
        String keyword = parser.parseFind(input);
        TaskList matchingTasks = tasks.findTasks(keyword);
        ui.showMatchingTasks(matchingTasks);
    }
}


