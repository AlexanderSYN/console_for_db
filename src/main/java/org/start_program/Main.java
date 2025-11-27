package org.start_program;

import java.io.IOException;
import java.sql.Connection;
import java.util.Scanner;

import static DB.util.*;
import static console.main_code_console.*;

public class Main {
    public static Scanner in = new Scanner(System.in);

    public static void start_program() throws IOException {
        System.out.println("please connect to your bd");
        System.out.println("example url -> jdbc:mysql://localhost:3306/dbname");
        System.out.print("url: ");
        String tmp_url = in.nextLine();

        System.out.print("user: ");
        String tmp_user = in.nextLine();

        System.out.print("password: ");
        String tmp_password = in.nextLine();

        set_data_for_bd(tmp_url, tmp_user, tmp_password);

        try {
            try (Connection conn = get_connection()) {
                System.out.println("Connection to DB successfully");
                start_console();
            }
        } catch (Exception e) {
            System.err.println("Connection FAILED");
            System.err.println("ERROR: " + e);
        }
    }

    public static void main(String[] args) throws IOException {
        Scanner in = new Scanner(System.in);

        System.out.println("_Welcome to the console for bd program_");

        if (!is_has_data_for_bd()) {
            start_program();
        } else {
            System.out.print("Do you want to use old data? (y/n): ");
            String is_use_old_data = in.nextLine();

            if (is_use_old_data.equals("yes") || is_use_old_data.equals("y")) {
                try {
                    try (Connection conn = get_connection()) {
                        System.out.println("Connection to DB successfully");
                        start_console();
                    }
                } catch (Exception e) {
                    System.err.println("Connection FAILED");
                    System.err.println("ERROR: " + e);
                }
            } else {
                start_program();
            }
        }
    }
}