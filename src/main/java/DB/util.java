package DB;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class util extends helper_for_util{
    private static String URL = get_url();
    private static String USER = get_user();
    private static String PASSWORD = get_password();

    private static String old_url = "";
    private static String old_user = "";
    private static String old_pass = "";

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

        } catch (Exception e) {
            System.err.println("ERROR: " + e);
        }
    }

    public static void set_data_for_bd(String url, String user, String password) throws IOException {
        if (url.equals("old_url") && user.equals("old_user") && password.equals("old_pass")) {
            set_url(old_url);
            set_user(old_user);
            set_password(old_pass);
            save_config();
            return;
        }

        old_url = URL;
        old_user = USER;
        old_pass = PASSWORD;

        set_url(url);
        set_user(user);
        set_password(password);
        save_config();
        URL = get_url();
        USER = get_user();
        PASSWORD = get_password();
    }

    /**
     * if you do not have data in database.properties, then you will need to write
     * data about the database, otherwise you will be asked to skip writing data or not.
     *
     * @return if has a data without null in properties then true otherwise false
     */
    public static boolean is_data_has_for_bd() {
        return URL.isEmpty() && USER.isEmpty();
    }

    public static Connection get_connection() throws SQLException {
        URL = get_url();
        USER = get_user();
        PASSWORD = get_password();
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

}
