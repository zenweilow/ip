import java.util.Scanner;

public class Hogrider {

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

            showEcho(input);
        }

        scanner.close();
    }

    private void showGreeting() {
        System.out.println("____________________________________________________________");
        System.out.println(" Welcome my GOAT, Hogrider here!");
        System.out.println(" u need help ah?");
        System.out.println("____________________________________________________________");
    }

    private void showEcho(String input) {
        System.out.println("____________________________________________________________");
        System.out.println(" " + input);
        System.out.println("____________________________________________________________");
    }

    private void showExit() {
        System.out.println("____________________________________________________________");
        System.out.println(" Bye GOAT!");
        System.out.println("____________________________________________________________");
    }
}
