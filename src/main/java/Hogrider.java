import java.util.ArrayList;
import java.util.Scanner;

public class Hogrider {

    private static final int MAX_ITEMS = 100;
    private final ArrayList<Task> items = new ArrayList<>();

    public static void main(String[] args) {
        new Hogrider().run();
    }

    public void run() {
        Scanner scanner = new Scanner(System.in);

        showGreeting();

        while (true) {
            String input = scanner.nextLine();

            if (input.equals("bye")) {
                showExit();
                break;
            }
            else if (input.equals("list")) {
                showList();
            } else if (input.startsWith("mark ")) {
                markTask(input);
            } else if (input.startsWith("unmark ")) {
                unmarkTask(input);
            } else {
                addItem(input);
            }
        }

        scanner.close();
    }

    private void addItem(String input) {
        System.out.println("____________________________________________________________");

        if (items.size() >= MAX_ITEMS) {
            System.out.println(" eh bro cannot add already, max 100 items ");
        } else {
            items.add(new Task(input));
            System.out.println(" added: " + input);
        }

        System.out.println("____________________________________________________________");
    }

    private void showGreeting() {
        System.out.println("____________________________________________________________");
        System.out.println(" Welcome my GOAT, Hogrider here!");
        System.out.println(" u need help ah?");
        System.out.println("____________________________________________________________");
    }

    private void showList() {
        System.out.println("____________________________________________________________");

        if (items.isEmpty()) {
            System.out.println(" nothing here leh");
        } else {
            for (int i = 0; i < items.size(); i++) {
                System.out.println(" " + (i + 1) + "." + items.get(i));
            }
        }

        System.out.println("____________________________________________________________");
    }

    private void markTask(String input) {
        int index = Integer.parseInt(input.substring(5)) - 1;

        Task task = items.get(index);
        task.mark();

        System.out.println("____________________________________________________________");
        System.out.println(" Nice! I've marked this task as done:");
        System.out.println("   " + task);
        System.out.println("____________________________________________________________");
    }

    private void unmarkTask(String input) {
        int index = Integer.parseInt(input.substring(7)) - 1;

        Task task = items.get(index);
        task.unmark();

        System.out.println("____________________________________________________________");
        System.out.println(" OK, I've marked this task as not done yet:");
        System.out.println("   " + task);
        System.out.println("____________________________________________________________");
    }


    private void showExit() {
        System.out.println("____________________________________________________________");
        System.out.println(" Bye GOAT!");
        System.out.println("____________________________________________________________");
    }
}
