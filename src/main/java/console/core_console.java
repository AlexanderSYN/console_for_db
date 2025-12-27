package console;

import DB.util;
import com.fasterxml.jackson.core.JsonToken;

import java.util.Scanner;

import static console.sql_console.*;
import static DB.util.*;
import static JSON.json.*;

public class core_console {

    private final static Scanner in = new Scanner(System.in);

    protected static boolean isRun = true;
    protected static String input_from_console;

    public static void start_console() {
        try {
            helper_console.clear_console();
            while (isRun) {
                System.out.println("for help type help");
                System.out.print("# ");
                input_from_console = in.nextLine().toLowerCase();

                try {
                    if (input_from_console.equals("help")) {
                        System.out.println("help -> for help");
                        System.out.println("info -> info about console");
                        System.out.println("db -> find out data about the database");
                        System.out.println("sql -> for sql mode");
                        System.out.println("mkcommand (name command) (command) -> for create your commands (for writing sql queries quickly)");
                        System.out.println("rmc (name command) -> to remove a command from quick access (json) ");
                        System.out.println("output-all-commands / all-commands -> output of all your commands");
                        System.out.println("run (name_your_command) -> to run your command");
                        System.out.println("clear data -> delete data for a quick login to your database");
                        System.out.println("cls / clear -> for clean console");
                        System.out.println("ex / exit -> for exit from console");
                    }

                    else if (input_from_console.equals("info")) {
                        System.out.println("Console for DataBase");
                        System.out.println("VERSION: 2.0 (Beta Test)");
                        System.out.println("___SYNEATION____");
                        System.out.println("programmer: Alexander Katin");
                    }

                    else if (input_from_console.equals("db")) {
                        util.get_data_about_db();
                    }

                    else if (input_from_console.equals("cls") || input_from_console.equals("clear")) {
                        helper_console.clear_console();
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

                    else if (input_from_console.substring(0,4).equals("run ")) {
                        helper_console.run_command(input_from_console.substring(4));
                    }

                    // delete command
                    else if (input_from_console.substring(0,4).toLowerCase().equals("rmc ")) {
                        System.out.println(remove_command_from_json(input_from_console.substring(4)));
                    }
                    // create command (mkcommand)
                    else if (input_from_console.substring(0, 10).equals("mkcommand ")) {
                        if (input_from_console.substring(0, 10).equals(" ") || input_from_console.substring(0,9).equals(" ")) {
                            System.out.println("[INFO][HELP] Please write like this -> mkcommand name_command command");
                        }

                        if (write_to_json(helper_console.get_name_command(input_from_console.trim()), helper_console.get_command(input_from_console).trim())) {
                            System.out.println("[INFO][SUCCESS] command has been saved!");
                        } else System.err.println("[ERROR][FAILED] command hasn't been saved!");

                    }

                    else if (input_from_console.equals("mkcommand")) {
                        System.out.println("[HINT] Please write like this -> mkcommand name_command command");
                    }
                    else if (input_from_console.equals("all-commands") || input_from_console.equals("output-all-commands")) {
                        output_commands_from_json();
                    }


                } catch (IndexOutOfBoundsException IOFBE) {
                    System.out.println("!for help type help!");
                    System.out.print("# ");
                    input_from_console = in.nextLine().toLowerCase();

                } catch (Exception e) {
                    System.err.println("[ERROR] " + e);
                    System.out.println("!for help type help!");
                    System.out.print("# ");
                    input_from_console = in.nextLine().toLowerCase();
                }
            }
        } catch (IndexOutOfBoundsException IOFBE) {
            System.out.println("!for help type help!");
            System.out.print("# ");
            input_from_console = in.nextLine().toLowerCase();

        } catch (Exception e) {
            System.err.println("[ERROR] " + e);
            System.out.println("!for help type help!");
            System.out.print("# ");
            input_from_console = in.nextLine().toLowerCase();
        }
    }
}