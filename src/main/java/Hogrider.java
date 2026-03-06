import java.util.ArrayList;
import java.util.Scanner;

public class Hogrider {

    private static final int MAX_ITEMS = 100;
    private final ArrayList<Task> items = new ArrayList<>();
    private static final String LINE = "____________________________________________________________";

    private static final String CMD_BYE = "bye";
    private static final String CMD_LIST = "list";
    private static final String CMD_MARK = "mark";
    private static final String CMD_UNMARK = "unmark";
    private static final String CMD_TODO = "todo";
    private static final String CMD_DEADLINE = "deadline";
    private static final String CMD_EVENT = "event";
    private static final String CMD_DELETE = "delete";

    private static final String DEADLINE_BY = " /by ";
    private static final String EVENT_FROM = " /from ";
    private static final String EVENT_TO = " /to ";

    public static void main(String[] args) {
        new Hogrider().run();
    }

    public void run() {
        Scanner scanner = new Scanner(System.in);

        showGreeting();

        while (true) {
            String input = scanner.nextLine().trim();
            try {
                if (input.equals(CMD_BYE)) {
                    showExit();
                    break;
                } else if (input.equals(CMD_LIST)) {
                    showList();
                } else if (input.startsWith(CMD_MARK + " ")) {
                    markTask(input);
                } else if (input.startsWith(CMD_UNMARK + " ")) {
                    unmarkTask(input);
                } else if (input.equals(CMD_TODO)) {
                    throw new HogriderException("todo cannot be empty leh. Use: todo <description>");
                } else if (input.startsWith(CMD_TODO + " ")) {
                    addTodo(input);
                } else if (input.equals(CMD_DEADLINE)) {
                    throw new HogriderException("deadline cannot be empty leh. Use: deadline <desc> /by <when>");
                } else if (input.startsWith(CMD_DEADLINE + " ")) {
                    addDeadline(input);
                } else if (input.equals(CMD_EVENT)) {
                    throw new HogriderException("event cannot be empty leh. Use: event <desc> /from <start> /to <end>");
                } else if (input.startsWith(CMD_EVENT + " ")) {
                    addEvent(input);
                } else if (input.startsWith(CMD_DELETE + " ")) {
                    deleteTask(input);
                } else if (input.isEmpty()) {
                    throw new HogriderException("you never type anything leh ");
                } else {
                    throw new HogriderException("aiyo i don't understand leh. Try: todo, deadline, event, list, mark, unmark, bye");
                }
            } catch (HogriderException e) {
                showError(e.getMessage());
            }
        }

        scanner.close();
    }

    private void printLine() {
        System.out.println(LINE);
    }

    private void showError(String message) {
        printLine();
        System.out.println(" " + message);
        printLine();
    }

    private void addTodo(String input) throws HogriderException {
        String description = input.substring(CMD_TODO.length()).trim();
        if (description.isEmpty()) {
            throw new HogriderException("todo cannot be empty leh. Use: todo <description>");
        }
        addTask(new Todo(description));
    }

    private void addDeadline(String input) throws HogriderException {
        String rest = input.substring(CMD_DEADLINE.length()).trim();
        int byIndex = rest.indexOf(DEADLINE_BY);

        if (byIndex == -1) {
            throw new HogriderException("format wrong leh. Use: deadline <desc> /by <when>");
        }

        String description = rest.substring(0, byIndex).trim();
        String by = rest.substring(byIndex + DEADLINE_BY.length()).trim();

        if (description.isEmpty()) {
            throw new HogriderException("deadline description cannot be empty leh. Use: deadline <desc> /by <when>");
        }
        if (by.isEmpty()) {
            throw new HogriderException("deadline /by cannot be empty leh. Use: deadline <desc> /by <when>");
        }

        addTask(new Deadline(description, by));
    }

    private void addEvent(String input) throws HogriderException {
        String rest = input.substring(CMD_EVENT.length()).trim();
        int fromIndex = rest.indexOf(EVENT_FROM);
        int toIndex = rest.indexOf(EVENT_TO);

        if (fromIndex == -1 || toIndex == -1 || toIndex < fromIndex) {
            throw new HogriderException("format wrong leh. Use: event <desc> /from <start> /to <end>");
        }

        String description = rest.substring(0, fromIndex).trim();
        String from = rest.substring(fromIndex + EVENT_FROM.length(), toIndex).trim();
        String to = rest.substring(toIndex + EVENT_TO.length()).trim();

        if (description.isEmpty()) {
            throw new HogriderException("event description cannot be empty leh. Use: event <desc> /from <start> /to <end>");
        }
        if (from.isEmpty() || to.isEmpty()) {
            throw new HogriderException("event /from and /to cannot be empty leh. Use: event <desc> /from <start> /to <end>");
        }

        addTask(new Event(description, from, to));
    }




    private void addTask(Task task) throws HogriderException {
        printLine();

        if (items.size() >= MAX_ITEMS) {
            throw new HogriderException("eh bro cannot add already, max 100 items");
        } else {
            items.add(task);
            System.out.println(" Got it. I've added this task:");
            System.out.println("   " + task);
            System.out.println(" Now you have " + items.size() + " tasks in the list.");

            printLine();
        }
    }

    private void showGreeting() {
        printLine();
        System.out.println(" Welcome my GOAT, Hogrider here!");
        System.out.println(" u need help ah?");
        printLine();
    }

    private void showList() {
        printLine();

        if (items.isEmpty()) {
            System.out.println(" nothing here leh");
        } else {
            System.out.println(" Here are the tasks in your list:");
            for (int i = 0; i < items.size(); i++) {
                System.out.println(" " + (i + 1) + "." + items.get(i));
            }
        }

        printLine();
    }

    private void markTask(String input) throws HogriderException {
        int index = parseIndex(input, CMD_MARK);

        Task task = items.get(index);
        task.mark();

        printLine();
        System.out.println(" Nice! I've marked this task as done:");
        System.out.println("   " + task);
        printLine();
    }

    private void unmarkTask(String input) throws HogriderException {
        int index = parseIndex(input, CMD_UNMARK);

        Task task = items.get(index);
        task.unmark();


        System.out.println(" OK, I've marked this task as not done yet:");
        System.out.println("   " + task);
        System.out.println("____________________________________________________________");
    }

    private void deleteTask(String input) throws HogriderException {
        int index = parseIndex(input, CMD_DELETE);
        Task removedTask = items.remove(index);

        printLine();
        System.out.println(" Noted. I've removed this task:");
        System.out.println("   " + removedTask);
        System.out.println(" Now you have " + items.size() + " tasks in the list.");
        printLine();
    }


    private int parseIndex(String input, String commandWord) throws HogriderException {
        String[] parts = input.trim().split("\\s+");
        if (parts.length != 2) {
            throw new HogriderException("format wrong leh. Use: " + commandWord + " <taskNumber>");
        }

        int oneBased;
        try {
            oneBased = Integer.parseInt(parts[1]);
        } catch (NumberFormatException e) {
            throw new HogriderException("task number must be a number leh.");
        }

        int zeroBased = oneBased - 1;
        if (zeroBased < 0 || zeroBased >= items.size()) {
            throw new HogriderException("no such task number leh.");
        }

        return zeroBased;
    }


    private void showExit() {
        printLine();
        System.out.println(" Bye GOAT!");
        printLine();
    }
}
