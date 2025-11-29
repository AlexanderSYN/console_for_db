package console;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.Scanner;

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

                if (input_from_console.equals("help")) {
                    System.out.println("help -> for help");
                    System.out.println("cls / clear -> for clean console");
                    System.out.println("ex / exit -> for exit from console");
                } else if (input_from_console.equals("cls") || input_from_console.equals("clear")) {
                    System.out.println("\033[H\033[2J");
                } else if (input_from_console.equals("sql")) {

                    System.out.println("SQL MODE (please enter sql query): ");
                    sql_query = in.nextLine();

                    Connection con = get_connection();
                    Statement statement = con.createStatement();

                    ResultSet resultSet = statement.executeQuery(sql_query);
                    ResultSetMetaData metaData = resultSet.getMetaData();
                    int columnCount = metaData.getColumnCount();

                    while (resultSet.next()) {
                        for (int i = 1; i <= columnCount; i++) {
                            System.out.println(metaData.getColumnName(i) + " - " + resultSet.getString(i) + "\t");
                        }
                        System.out.println("\n");
                    }

                } else if (input_from_console.equals("ex") || input_from_console.equals("exit")) {
                    isRun = false;
                }
            }
        } catch (Exception e) {
            System.err.println("ERROR: " + e);
            System.out.println("!for help type help!");
            System.out.print("# ");
        }
    }
}
