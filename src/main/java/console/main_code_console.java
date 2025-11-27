package console;

import java.util.Scanner;

public class main_code_console {

    private static Scanner in = new Scanner(System.in);

    protected static boolean isRun = true;
    protected static String input_from_console;


    public static void start_console() {
        while (isRun) {
            System.out.println("\033[H\033[2J"); // cleared console
            System.out.println("for help type help");
            System.out.print("# ");
            input_from_console = in.nextLine().toLowerCase();

            if (input_from_console.equals("help")) {
                System.out.println("HELP");
            }


        }
    }

}
