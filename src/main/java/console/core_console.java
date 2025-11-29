package console;

import java.util.Scanner;

public class core_console {

    private final static Scanner in = new Scanner(System.in);

    protected static boolean isRun = true;
    protected static String input_from_console;


    public static void start_console() {
        while (isRun) {
            System.out.println("\033[H\033[2J"); // cleared console
            System.out.println("for help type help");
            System.out.print("# ");
            input_from_console = in.nextLine().toLowerCase();

            if (input_from_console.equals("help")) {
                System.out.println("help -> for help");
                System.out.println("cls / clear -> for clean console");
                System.out.println("ex / exit -> for exit from console");
            }
            else if (input_from_console.equals("cls") || input_from_console.equals("clear")) {
                System.out.println("\033[H\033[2J");
            }
            else if (input_from_console.equals("ex") || input_from_console.equals("exit")) {
                isRun = false;
            }

        }
    }
}
