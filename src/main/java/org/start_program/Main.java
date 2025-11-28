package org.start_program;

import java.io.IOException;
import java.sql.Connection;
import java.util.Scanner;

import static DB.util.*;
import static console.main_code_console.*;

public class Main {
    public static Scanner in = new Scanner(System.in);
    private static boolean isRun = true;

    public static void start_program() throws IOException {
        System.out.println("please connect to your bd");
        System.out.println("example url -> jdbc:mysql://localhost:3306/dbname");
        System.out.print("url: ");
        String tmp_url = in.nextLine();
        if (tmp_url.equals("ex") || tmp_url.equals("exit")) isRun = false;

        System.out.print("user: ");
        String tmp_user = in.nextLine();
        if (tmp_user.equals("ex") || tmp_user.equals("exit")) isRun = false;

        System.out.print("password: ");
        String tmp_password = in.nextLine();
        if (tmp_password.equals("ex") || tmp_password.equals("exit")) isRun = false;

        set_data_for_bd(tmp_url, tmp_user, tmp_password);

        try {
            try (Connection conn = get_connection()) {
                System.out.println("Connection to DB successfully");
                start_console();
            }
        } catch (Exception e) {
            set_data_for_bd("old_url", "old_user", "old_pass"); // old_url and etc is mean save old config for bd
            System.err.println("Connection FAILED");
            System.err.println("ERROR: " + e);
        }
    }

    public static void main(String[] args) throws IOException {
        Scanner in = new Scanner(System.in);

        while (isRun) {
            try {
                System.out.println("_Welcome to the console for bd program_");

                if (is_data_has_for_bd()) {
                    start_program();
                } else {
                    System.out.print("Do you want to use old data? (y/n): ");
                    String is_use_old_data = in.nextLine();

                    if (is_use_old_data.equals("yes") || is_use_old_data.equals("y")) {
                        try {
                            try (Connection conn = get_connection()) {
                                System.out.println("Connection to DB successfully");
                                start_console();
                                isRun = false;
                            }
                        } catch (Exception e) {
                            set_data_for_bd("old_url", "old_user", "old_pass");
                            System.err.println("Connection FAILED");
                            System.err.println("ERROR: " + e);
                        }
                    } else {
                        start_program();
                    }
                }
            } catch (Exception e) {
                System.err.println("ERROR: " + e);
            }
        }
    }
}