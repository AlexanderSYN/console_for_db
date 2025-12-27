package DB;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class util extends helper_for_util {
    protected static String URL = get_url();
    protected static String USER = get_user();
    protected static String PASSWORD = get_password();

    private static String old_url = "";
    private static String old_user = "";
    private static String old_pass = "";

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

        } catch (Exception e) {
            System.err.println("[ERROR] " + e);
        }
    }

    /**
     * set data for bd in database.properties
     *
     * if your data is correct and the user has connected to the database,
     * then OK, otherwise set the value to old_... to get data from database.properties
     *
     * @param url url server to writing like so: jdbc:mysql://<IP_ADDRESS_SERVER_OR_DOMEN>:<PORT>/<dbname>
     * @param user user from db mysql
     * @param password password from db mysql
     * @throws IOException
     */
    public static void set_data_for_bd(String url, String user, String password) throws IOException {
        if (url.equals("old_url") & user.equals("old_user") & password.equals("old_pass")) {
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
     *
     * clearing all data from the database.properties
     *
     */
    public static void clear_all_data_from_db_prop() {
        try {
            set_url("");
            set_user("");
            set_password("");
            save_config();
            System.out.println("[SUCCESS] the data for quick login to your database has been successfully deleted");
        } catch (Exception e) {
            System.err.println("[ERROR] " + e);
        }
    }

    /**
     * if you do not have data in database.properties, then you will need to write
     * data about the database, otherwise you will be asked to skip writing data or not.
     *
     * @return if has a data without null in properties then true otherwise false
     */
    public static boolean is_data_has_for_bd() {
        return get_url().isEmpty() & get_user().isEmpty();
    }

    public static Connection get_connection() throws SQLException {
        URL = get_url();
        USER = get_user();
        PASSWORD = get_password();

        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    /**
     * find out data about the database
     */
    public static void get_data_about_db() {
        System.out.println("url: " + URL);
        System.out.println("user: " + USER);
        System.out.println("password: " + PASSWORD);
    }

}
