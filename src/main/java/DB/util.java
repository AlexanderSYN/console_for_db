package DB;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import java.util.Properties;

public class util extends helper_for_util{
    private static String URL = get_url();
    private static String USER = get_user();
    private static String PASSWORD = get_password();

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

        } catch (Exception e) {
            System.err.println("ERROR: " + e);
        }
    }

    public static void set_data_for_bd(String url, String user, String password) throws IOException {
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
    public static boolean is_has_data_for_bd() {
        return URL != null && USER != null;
    }

    public static Connection get_connection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

}
