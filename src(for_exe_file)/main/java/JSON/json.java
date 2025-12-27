package JSON;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class json {
    private static final String CONFIG_PATH = "config/commands.json";
    private static final ObjectMapper objectMapper = new ObjectMapper()
            .enable(SerializationFeature.INDENT_OUTPUT); // для красивого форматирования


    /**
     *
     * write name command and command in json
     *
     * @param name_command
     * @param command
     * @return
     */
    public static boolean write_to_json(String name_command, String command) {
        try {
            // Creating a directory if it does not exist
            File configFile = new File(CONFIG_PATH);
            configFile.getParentFile().mkdirs();

            Map<String, String> data;

            // We read the existing data if the file exists.
            if (configFile.exists() && configFile.length() > 0) {
                if (exists_key_in_json(name_command)) {
                    Scanner in = new Scanner(System.in);
                    System.out.print("[INFO] Are you sure want to change a command (y/n): ");
                    String choice = in.nextLine();
                    if (choice.toLowerCase().equals("y") || choice.toLowerCase().equals("yes")) {
                        data = objectMapper.readValue(configFile,
                                new TypeReference<Map<String, String>>() {
                                });
                    } else return false;
                }

                data = objectMapper.readValue(configFile,
                        new TypeReference<Map<String, String>>() {
                        });


            } else data = new HashMap<>();

            // Adding/updating data
            data.put(name_command, command);

            // Writing it back to the file
            objectMapper.writeValue(configFile, data);

            return true;
        } catch (Exception e) {
            System.err.println("[ERROR_JSON][method->write_to_json] " + e);
            return false;
        }
    }

    /**
     *
     * output all commands from json file
     *
     * @throws IOException
     */
    public static void output_commands_from_json() throws IOException {
        try {
            File configFile = new File(CONFIG_PATH);

            JsonFactory jFactory = new JsonFactory();
            JsonParser jParser = jFactory.createParser(configFile);
            int count = 1;
            System.out.println("[HINT] name command -> command");
            while (!jParser.isClosed()) {
                JsonToken jToken = jParser.nextToken();

                if (JsonToken.FIELD_NAME.equals(jToken)) {
                    String name_command = jParser.getValueAsString();

                    jToken = jParser.nextToken();
                    System.out.println(count + ") " + name_command + " -> " + jParser.getValueAsString());
                    count++;
                }
            }
        } catch (Exception e) {
            System.err.println("[ERROR_JSON][method->output_commands_from_json]: " + e);
        }
    }

    /**
     * @param name_command - which in json file
     * to get command for name command
     *
     * if don't found a name command return None
     * if error return ERROR
     */
    public static String get_command_from_json(String name_command)  {
        try {
            File configFile = new File(CONFIG_PATH);

            JsonFactory jFactory = new JsonFactory();
            JsonParser jParser = jFactory.createParser(configFile);
            while (!jParser.isClosed()) {
                JsonToken jToken = jParser.nextToken();
                if (JsonToken.FIELD_NAME.equals(jToken)) {
                    String name_command_from_json = jParser.getValueAsString();
                    jToken = jParser.nextToken();

                    if (name_command_from_json.trim().equals(name_command)) {
                        return jParser.getValueAsString();
                    }
                }
            }

            return "None";
        } catch (Exception e) {
            System.err.println("[ERROR_JSON][method->get_command_from_json]: " + e);
            return "ERROR";
        }
    }

    /**
     * deletes the command and the name command from json
     *
     * @param name_command - the name of the command that is in json
     * @return boolean
     */
    public static boolean remove_command_from_json(String name_command) {
        try {
            File config_file = new File(CONFIG_PATH);

            if (!config_file.exists() || config_file.length() == 0) {
                System.out.println("[WARNING] JSON file is empty or doesn't exists");
                return false;
            }

            // read the json file into a map
            Map<String, String> commands = objectMapper.readValue(
                    config_file,
                    new TypeReference<Map<String, String>>() {}
            );

            // check if the command exists
            if (!commands.containsKey(name_command)) {
                System.out.println("[WARNING] command [" + name_command + "] not found in JSON");
                return false;
            }

            // get the value before removing
            String removed_command = commands.get(name_command);

            commands.remove(name_command);

            // Ask for confirmation if you want
            Scanner scanner = new Scanner(System.in);
            System.out.print("[INFO] Are you sure you want to remove command [" + name_command + "]? (y/n): ");
            String choice = scanner.nextLine().trim().toLowerCase();

            if (!choice.equals("y") && !choice.equals("yes")) {
                System.out.println("[INFO] Removal cancelled");
                return false;
            }

            // write the updated map back to the file
            objectMapper.writeValue(config_file, commands);

            System.out.println("[SUCCESS] Command '" + name_command + "' removed successfully");
            System.out.println("[INFO] Removed command: " + removed_command);

            return true;

        } catch (Exception e) {
            System.err.println("[ERROR_JSON][method->remove_command_from_json]: " + e.getMessage());
            return false;
        }
    }

    /**
     * checking if the required text is in the json file.
     *
     * @param text name of the text being checked
     * @return if the text is in the key in the json file then return true otherwise false
     */
    public static boolean exists_key_in_json(String text) {
        try {
            File config_file = new File(CONFIG_PATH);

            if (!config_file.exists() || config_file.length() == 0) {
                System.out.println("[WARNING] JSON file is empty or doesn't exists");
                return false;
            }

            Map<String, String> commands = objectMapper.readValue(
                    config_file,
                    new TypeReference<Map<String, String>>() {}
            );

            if (commands.containsKey(text)) return true;
            else return false;


        } catch (Exception e) {
            System.err.println("[ERROR][method->exists_text_in_json] " + e);
            return false;
        }
    }

}