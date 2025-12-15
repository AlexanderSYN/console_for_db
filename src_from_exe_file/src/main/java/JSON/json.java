package JSON;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

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
                Scanner in = new Scanner(System.in);
                System.out.print("Are you sure want to change a command (y/n): ");
                String choice = in.nextLine();
                if (choice.toLowerCase().equals("y") || choice.toLowerCase().equals("yes")) {
                    data = objectMapper.readValue(configFile,
                            new TypeReference<Map<String, String>>() {
                            });
                } else return false;

            } else {
                data = new HashMap<>();
            }

            // Adding/updating data
            data.put(name_command, command);

            // Writing it back to the file
            objectMapper.writeValue(configFile, data);

            return true;
        } catch (Exception e) {
            System.err.println("[ERROR_JSON] " + e);
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
            while (!jParser.isClosed()) {
                JsonToken jToken = jParser.nextToken();

                if (JsonToken.FIELD_NAME.equals(jToken)) {
                    String name_command = jParser.getValueAsString();

                    jToken = jParser.nextToken();
                    System.out.println("name command -> command");
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

}