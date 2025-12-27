package org.start_program;

import DB.util;

import java.io.IOException;
import java.sql.Connection;
import java.util.Scanner;

import static console.core_console.*;

public class Main extends util {
    public static Scanner in = new Scanner(System.in);
    private static boolean isRun = true;

    public static void start_program() throws IOException {
        System.out.print("please connect to your db\n"+
                "example url -> jdbc:mysql://<IP_ADDRESS_SERVER_OR_DOMEN>:<PORT>/<dbname>\n"+
                "you can write help!\n"+
                "url: ");
        String tmp_url = in.nextLine();
        if (tmp_url.equalsIgnoreCase("help")) {
            System.out.print("to connect on localhost server you can to write so: jdbc:mysql://localhost:3306/db_name\n"+
                    "to connect on your server mysql you need to write so: jdbc:mysql://your_ip_address_server_or_your_domen:your_port/db_name\n"+
                    "if it returns an error when connecting to the database via the host, then most likely you need to give access to your ip address\n"+
                    "url: ");
            tmp_url = in.nextLine();
        }

        if (tmp_url.equals("ex") || tmp_url.equals("exit")) {
            isRun = false;
            return;
        }

        System.out.print("user: ");
        String tmp_user = in.nextLine();
        if (tmp_user.equals("ex") || tmp_user.equals("exit")) {
            isRun = false;
            return;
        }

        System.out.print("password: ");
        String tmp_password = in.nextLine();
        if (tmp_password.equals("ex") || tmp_password.equals("exit")) {
            isRun = false;
            return;
        }
        System.out.println("[INFO] Connect to database...");
        set_data_for_bd(tmp_url, tmp_user, tmp_password);

        try {
            try (Connection conn = get_connection()) {
                System.out.println("[INFO] Connection to DB successfully");
                isRun = false;
                start_console();
            }
        } catch (Exception e) {
            set_data_for_bd("old_url", "old_user", "old_pass"); // old_url and etc is mean save old config for bd
            System.err.println("[ERROR] Connection FAILED");
            System.err.println("[ERROR] " + e);
        }
    }

    /**
     *
     * if your data is correct and the user has connected to the database,
     * then OK, otherwise set the value to old_... to get data from database.properties
     *
     * @param args
     */
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        while (isRun) {
            try {
                System.out.println("_Welcome to the console database_");

                if (is_data_has_for_bd()) {
                    start_program();
                } else {
                    System.out.println("[old_data] url: " +  util.URL);
                    System.out.println("[old_data] user: " + util.USER);
                    System.out.println("[old_data] password: " + util.PASSWORD);
                    System.out.print("[INFO] Do you want to use old data? (y/n): ");
                    String is_use_old_data = in.nextLine();

                    if (is_use_old_data.equalsIgnoreCase("yes") ||
                            is_use_old_data.equalsIgnoreCase("y")) {
                        try {
                            try (Connection conn = get_connection()) {
                                System.out.println("Connection to DB successfully");
                                start_console();
                                isRun = false;
                            }
                        } catch (Exception e) {
                            set_data_for_bd("old_url", "old_user", "old_pass");
                            System.err.println("[ERROR] Connection FAILED");
                            System.err.println("[ERROR] " + e);
                        }
                    } else {
                        start_program();
                    }
                }
            } catch (Exception e) {
                System.err.println("[ERROR] " + e);
                System.out.println("please click <any button> to continue");
                in.nextLine();
            }
        }
    }
}
