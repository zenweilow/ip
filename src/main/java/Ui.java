public class Ui {
    private static final String LINE = "____________________________________________________________";

    public void showGreeting() {
        printLine();
        System.out.println(" Welcome my GOAT, Hogrider here!");
        System.out.println(" u need help ah?");
        printLine();
    }

    public void showExit() {
        printLine();
        System.out.println(" Bye GOAT!");
        printLine();
    }

    public void showError(String message) {
        printLine();
        System.out.println(" " + message);
        printLine();
    }

    public void showTaskAdded(Task task, int taskCount) {
        printLine();
        System.out.println(" Got it. I've added this task:");
        System.out.println("   " + task);
        System.out.println(" Now you have " + taskCount + " tasks in the list.");
        printLine();
    }

    public void showTaskDeleted(Task task, int taskCount) {
        printLine();
        System.out.println(" Noted. I've removed this task:");
        System.out.println("   " + task);
        System.out.println(" Now you have " + taskCount + " tasks in the list.");
        printLine();
    }

    public void showTaskMarked(Task task) {
        printLine();
        System.out.println(" Nice! I've marked this task as done:");
        System.out.println("   " + task);
        printLine();
    }

    public void showTaskUnmarked(Task task) {
        printLine();
        System.out.println(" OK, I've marked this task as not done yet:");
        System.out.println("   " + task);
        printLine();
    }

    public void showList(TaskList tasks) {
        printLine();

        if (tasks.isEmpty()) {
            System.out.println(" nothing here leh");
        } else {
            System.out.println(" Here are the tasks in your list:");
            for (int i = 0; i < tasks.size(); i++) {
                System.out.println(" " + (i + 1) + "." + tasks.getTask(i));
            }
        }

        printLine();
    }

    private void printLine() {
        System.out.println(LINE);
    }
}
