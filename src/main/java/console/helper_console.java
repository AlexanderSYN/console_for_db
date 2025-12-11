package console;

import java.sql.*;
import java.util.concurrent.TimeUnit;

import static DB.util.*;

public class helper_console {

    /**
     *
     * for executing sql queries (for !one line)
     *
     * @param sql_queries the array where all sql queries are stored
     * @param count_request  number of requests
     *
     */
    public static void exec_sql_queries(String[] sql_queries, int count_request) {

       try {
           // main variables for sql queries
           Connection con = get_connection();
           Statement statement = con.createStatement();

           int rows_affected = 0;

           for (int i = 0; i <= count_request; i++) {
               if (sql_queries[i] == null) continue;
               else if (sql_queries[i].isEmpty()) continue;
               System.out.println("name query: " + sql_queries[i] + "\nnumber: " + i);

               if (sql_queries[i].length() < 6) {
                   System.out.println("please enter correctly sql query!");
               }
               //======================
               // commands via SELECT
               //======================
               else if (sql_queries[i].toLowerCase().substring(0, 6).equals("select")) {
                   long start_time = System.nanoTime();

                   // executing sql query
                   ResultSet result_set = statement.executeQuery(sql_queries[i]);
                   ResultSetMetaData meta_data = result_set.getMetaData();
                   int columnCount = meta_data.getColumnCount();

                   // get duration
                   long end_time = System.nanoTime();
                   long duration = end_time - start_time;

                   while (result_set.next()) {
                       for (int j = 1; j <= columnCount; j++) {
                           System.out.println(meta_data.getColumnName(j) + " - " + result_set.getString(j) + "\t");
                       }
                       System.out.println("\n");
                   }
                   System.out.printf("запрос занял: %d мс\n", TimeUnit.NANOSECONDS.toMillis(duration));
               }

               //=====================
               // commands via INSERT
               //=====================
               else if (sql_queries[i].toLowerCase().substring(0, 6).equals("insert")) {
                   long start_time = System.nanoTime();

                   rows_affected = statement.executeUpdate(sql_queries[i]);

                   // get duration
                   long end_time = System.nanoTime();
                   long duration = end_time - start_time;

                   System.out.println("Добавлено строк: " + rows_affected);

                   System.out.printf("запрос занял: %d мс \n", TimeUnit.NANOSECONDS.toMillis(duration));
               }


               //=====================
               // commands via DELETE
               //=====================
               else if (sql_queries[i].toLowerCase().substring(0, 6).equals("delete")) {
                   long start_time = System.nanoTime();

                   rows_affected = statement.executeUpdate(sql_queries[i]);

                   // get duration
                   long end_time = System.nanoTime();
                   long duration = end_time - start_time;

                   System.out.println("Удален строк: " + rows_affected);

                   System.out.printf("запрос занял: %d мс \n", TimeUnit.NANOSECONDS.toMillis(duration));

               }

               //=====================
               // commands via UPDATE
               //=====================
               else if (sql_queries[i].toLowerCase().substring(0, 6).equals("update")) {
                   long start_time = System.nanoTime();

                   rows_affected = statement.executeUpdate(sql_queries[i]);

                   // get duration
                   long end_time = System.nanoTime();
                   long duration = end_time - start_time;

                   System.out.println("обновлено строк: " + rows_affected);

                   System.out.printf("запрос занял: %d мс \n", TimeUnit.NANOSECONDS.toMillis(duration));

               }
               //=====================
               // commands via CREATE
               //=====================
               else if (sql_queries[i].toLowerCase().substring(0, 6).equals("create")) {
                   long start_time = System.nanoTime();

                   rows_affected = statement.executeUpdate(sql_queries[i]);

                   // get duration
                   long end_time = System.nanoTime();
                   long duration = end_time - start_time;

                   System.out.println("Таблица успешно создана!");
                   System.out.printf("запрос занял: %d мс \n", TimeUnit.NANOSECONDS.toMillis(duration));

               }

               else {
                   long start_time = System.nanoTime();

                   rows_affected = statement.executeUpdate(sql_queries[i]);

                   // get duration
                   long end_time = System.nanoTime();
                   long duration = end_time - start_time;

                   System.out.println("Запрос успешно завершился: " + rows_affected);

                   System.out.printf("запрос занял: %d мс \n", TimeUnit.NANOSECONDS.toMillis(duration));

               }

               System.out.println();

           }

       } catch (Exception e) {
           System.err.println("[ERROR]: " + e);
       }
    }

    /**
     * the method of getting the name command from mkcommand
     *
     * @param text - here is the text of the user with the mkcommand command
     * @return command name
     */
    public static String get_name_command(String text) {
        int count_space = 0;
        StringBuilder tmp_name_command = new StringBuilder();

        for (int i = 0; i <= text.length(); i++) {
            if (count_space == 1) tmp_name_command.append(text.charAt(i));
            if (count_space == 2) return tmp_name_command.toString();
            if (text.charAt(i) == ' ') count_space++;

        }

        return tmp_name_command.toString();
    }

    /**
     * the method of getting the command from mkcommand
     *
     * @param text - here is the text of the user with the mkcommand command
     * @return command
     */
    public static String get_command(String text) {
        int count_space = 0;
        StringBuilder tmp_command = new StringBuilder();

        for (int i = 0; i <= text.length(); i++) {
            if (count_space == 2) return text.substring(i);
            if (text.charAt(i) == ' ') count_space++;
        }

        return tmp_command.toString();
    }


}
