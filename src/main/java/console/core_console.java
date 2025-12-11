package console;

import java.util.Scanner;

import static console.sql_console.*;
import static DB.util.*;

public class core_console {

    private final static Scanner in = new Scanner(System.in);

    protected static boolean isRun = true;
    protected static String input_from_console;
    protected static String sql_query;

    public static void start_console() {
        try {
            while (isRun) {
                System.out.println("\033[H\033[2J"); // cleared console
                System.out.println("for help type help");
                System.out.print("# ");
                input_from_console = in.nextLine().toLowerCase();

                try {
                    if (input_from_console.equals("help")) {
                        System.out.println("help -> for help");
                        System.out.println("sql -> for sql mode");
                        System.out.println("clear data -> delete data for a quick login to your database");
                        System.out.println("cls / clear -> for clean console");
                        System.out.println("ex / exit -> for exit from console");
                    }

                    else if (input_from_console.equals("cls") || input_from_console.equals("clear")) {
                        System.out.println("\033[H\033[2J");
                    }
                    else if (input_from_console.equals("clear data")) {
                        clear_all_data_from_db_prop();
                        break;
                    }

                    else if (input_from_console.equals("sql")) {
                        sql_mode(true);
                    }
                    else if (input_from_console.equals("ex") || input_from_console.equals("exit")) {
                        isRun = false;
                    }

                    // beta test
                    else if (input_from_console.substring(0, 10).equals("mkcommand ")) {
                        if (input_from_console.substring(0, 10).equals(" ") || input_from_console.substring(0,9).equals(" ")) {
                            System.out.println("Please write like this -> mkcommand name_command command");
                        }

                        String name_command = input_from_console.substring(10);
                        System.out.println(name_command);
                    }
                    else if (input_from_console.equals("mkcommand")) {
                        System.out.println("Please write like this -> mkcommand name_command command");
                    }



                } catch (Exception e) {
                    System.err.println("ERROR: " + e);
                    System.out.println("!for help type help!");
                    System.out.print("# ");
                    input_from_console = in.nextLine().toLowerCase();
                }
            }
        } catch (Exception e) {
            System.err.println("ERROR: " + e);
            System.out.println("!for help type help!");
            System.out.print("# ");
            input_from_console = in.nextLine().toLowerCase();
        }
    }
}
