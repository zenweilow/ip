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
            String input = scanner.nextLine();

            if (input.equals(CMD_BYE)) {
                showExit();
                break;
            }
            else if (input.equals(CMD_LIST)) {
                showList();
            } else if (input.startsWith(CMD_MARK + " ")) {
                markTask(input);
            } else if (input.startsWith(CMD_UNMARK + " ")) {
                unmarkTask(input);
            } else if (input.startsWith(CMD_TODO + " ")) {
                addTodo(input);
            } else if (input.startsWith(CMD_DEADLINE + " ")) {
                addDeadline(input);
            } else if (input.startsWith(CMD_EVENT + " ")) {
                addEvent(input);
            } else {
                showUnknownCommand();
            }
        }

        scanner.close();
    }

    private void printLine() {
        System.out.println(LINE);
    }

    private void addTodo(String input) {
        String description = input.substring(CMD_TODO.length()).trim();
        addTask(new Todo(description));
    }

    private void addDeadline(String input) {
        String rest = input.substring(CMD_DEADLINE.length()).trim();
        int byIndex = rest.indexOf(DEADLINE_BY);

        if (byIndex == -1) {
            printLine();
            System.out.println(" format wrong leh. Use: deadline <desc> /by <when>");
            printLine();
            return;
        }

        String description = rest.substring(0, byIndex).trim();
        String by = rest.substring(byIndex + DEADLINE_BY.length()).trim();

        addTask(new Deadline(description, by));
    }

    private void addEvent(String input) {
        String rest = input.substring(CMD_EVENT.length()).trim();
        int fromIndex = rest.indexOf(EVENT_FROM);
        int toIndex = rest.indexOf(EVENT_TO);

        if (fromIndex == -1 || toIndex == -1 || toIndex < fromIndex) {
            printLine();
            System.out.println(" format wrong leh. Use: event <desc> /from <start> /to <end>");
            printLine();
            return;
        }

        String description = rest.substring(0, fromIndex).trim();
        String from = rest.substring(fromIndex + EVENT_FROM.length(), toIndex).trim();
        String to = rest.substring(toIndex + EVENT_TO.length()).trim();

        addTask(new Event(description, from, to));
    }




    private void addTask(Task task) {
        printLine();

        if (items.size() >= MAX_ITEMS) {
            System.out.println(" eh bro cannot add already, max 100 items ");
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

    private void markTask(String input) {
        Integer index = parseIndex(input, CMD_MARK);
        if (index == null) {
            return;
        }

        Task task = items.get(index);
        task.mark();

        printLine();
        System.out.println(" Nice! I've marked this task as done:");
        System.out.println("   " + task);
        printLine();
    }

    private void unmarkTask(String input) {
        Integer index = parseIndex(input, CMD_UNMARK);
        if (index == null) {
            return;
        }

        Task task = items.get(index);
        task.unmark();


        System.out.println(" OK, I've marked this task as not done yet:");
        System.out.println("   " + task);
        System.out.println("____________________________________________________________");
    }


    private Integer parseIndex(String input, String commandWord) {
        String[] parts = input.trim().split("\\s+");
        if (parts.length != 2) {
            printLine();
            System.out.println(" format wrong leh. Use: " + commandWord + " <taskNumber>");
            printLine();
            return null;
        }

        int oneBased;
        try {
            oneBased = Integer.parseInt(parts[1]);
        } catch (NumberFormatException e) {
            printLine();
            System.out.println(" task number must be a number leh.");
            printLine();
            return null;
        }

        int zeroBased = oneBased - 1;
        if (zeroBased < 0 || zeroBased >= items.size()) {
            printLine();
            System.out.println(" no such task number leh.");
            printLine();
            return null;
        }

        return zeroBased;
    }

    private void showUnknownCommand() {
        printLine();
        System.out.println(" i only understand: todo, deadline, event, list, mark, unmark, bye");
        printLine();
    }


    private void showExit() {
        printLine();
        System.out.println(" Bye GOAT!");
        printLine();
    }
}
