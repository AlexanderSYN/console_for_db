package console;

import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import static DB.util.get_connection;

public class sql_console  {

    private static Scanner in = new Scanner(System.in);
    private static boolean isRun = false;
    protected static int rows_affected = 0;

    public static void sql_mode(boolean isRun) {
        while (isRun) {
            try {
                System.out.println("SQL MODE (please enter sql query): ");
                String sql_query = in.nextLine();

                if (sql_query.equals("ex") || sql_query.equals("exit")) {
                    isRun = false;
                    break;
                }

                // main variables for sql queries
                Connection con = get_connection();
                Statement statement = con.createStatement();

                if (sql_query.length() < 6) {
                    System.out.println("please enter correctly sql query!");
                }
                //======================
                // commands via SELECT
                //======================
                else if (sql_query.toLowerCase().substring(0, 6).equals("select")) {
                    long start_time = System.nanoTime();

                    // executing sql query
                    ResultSet result_set = statement.executeQuery(sql_query);
                    ResultSetMetaData meta_data = result_set.getMetaData();
                    int columnCount = meta_data.getColumnCount();

                    // get duration
                    long end_time = System.nanoTime();
                    long duration = end_time - start_time;

                    while (result_set.next()) {
                        for (int i = 1; i <= columnCount; i++) {
                            System.out.println(meta_data.getColumnName(i) + " - " + result_set.getString(i) + "\t");
                        }
                        System.out.println("\n");
                    }
                    System.out.printf("запрос занял: %d мс\n", TimeUnit.NANOSECONDS.toMillis(duration));
                }

                //=====================
                // commands via INSERT
                //=====================
                else if (sql_query.toLowerCase().substring(0, 6).equals("insert")) {
                    long start_time = System.nanoTime();

                    rows_affected = statement.executeUpdate(sql_query);

                    // get duration
                    long end_time = System.nanoTime();
                    long duration = end_time - start_time;

                    System.out.println("Добавлено строк: " + rows_affected);

                    System.out.printf("запрос занял: %d мс \n", TimeUnit.NANOSECONDS.toMillis(duration));
                }


                //=====================
                // commands via DELETE
                //=====================
                else if (sql_query.toLowerCase().substring(0, 6).equals("delete")) {
                    long start_time = System.nanoTime();

                    rows_affected = statement.executeUpdate(sql_query);

                    // get duration
                    long end_time = System.nanoTime();
                    long duration = end_time - start_time;

                    System.out.println("Удален строк: " + rows_affected);

                    System.out.printf("запрос занял: %d мс \n", TimeUnit.NANOSECONDS.toMillis(duration));

                }

                //=====================
                // commands via UPDATE
                //=====================
                else if (sql_query.toLowerCase().substring(0, 6).equals("update")) {
                    long start_time = System.nanoTime();

                    rows_affected = statement.executeUpdate(sql_query);

                    // get duration
                    long end_time = System.nanoTime();
                    long duration = end_time - start_time;

                    System.out.println("обновлено строк: " + rows_affected);

                    System.out.printf("запрос занял: %d мс \n", TimeUnit.NANOSECONDS.toMillis(duration));

                }

            } catch (Exception e){
                System.err.println("ERROR: " + e);
            }
        }

        System.out.println("BYE");
    }

}
