public class Hogrider {

    public static void main(String[] args) {
        new Hogrider().run();
    }

    public void run() {
        showGreeting();
        showExit();
    }

    private void showGreeting() {
        System.out.println("____________________________________________________________");
        System.out.println(" Hello! I'm Hogrider");
        System.out.println(" What can I do for you?");
        System.out.println("____________________________________________________________");
    }

    private void showExit() {
        System.out.println(" Bye. Hope to see you again soon!");
        System.out.println("____________________________________________________________");
    }
}
