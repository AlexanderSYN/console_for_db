package DB;

import java.io.*;
import java.util.Properties;

public class helper_for_util {
    private static final Properties PROOPS = new Properties();
    private static final String CONFIG_PATH = "config/database.properties";
    private static boolean propertiesLoaded = false;

    static {
        load_properties();
    }

    /**
     * to avoid writing props.load() and unnecessary things every time
     **/
    private static void load_properties() {
        if (propertiesLoaded) return;

        try (InputStream in = new FileInputStream(CONFIG_PATH)) {

            if (in == null) {
                throw new RuntimeException("Sorry, unable to find database.properties: " + CONFIG_PATH);
            }
            PROOPS.load(in);
            propertiesLoaded = true;

        } catch (IOException ioe) {
            throw new RuntimeException("Failed to load database properties", ioe);
        }
        catch (Exception e) {
            System.err.println("ERROR: " + e);
        }
    }

    //===============================
    // set url and get url
    //===============================
    protected static String get_url() { return PROOPS.getProperty("URL"); }
    protected static void set_url(String url) { PROOPS.setProperty("URL", url); }

    //===============================
    // set user and get user
    //===============================
    protected static String get_user() { return PROOPS.getProperty("USER");}
    protected static void set_user(String user) { PROOPS.setProperty("USER", user);}

    //===============================
    // set password and get password
    //===============================
    protected static String get_password() { return PROOPS.getProperty("PASSWORD"); }
    protected static void set_password(String password) { PROOPS.setProperty("PASSWORD", password); }

    protected static void save_config() throws IOException {
        try {
            String file_path = "config/database.properties";
            try (OutputStream output = new FileOutputStream(file_path)) {
                PROOPS.store(output, "Updated database configuration");
            }
        } catch (IOException ioe) {
            throw new RuntimeException("Failed to save properties: " + ioe);
        } catch (Exception e) {
            System.err.println("ERROR: " + e);
        }
    }

}
