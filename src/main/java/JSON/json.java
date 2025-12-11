package JSON;

import DB.helper_for_util;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class json {
    private static final String CONFIG_PATH = "src/main/resources/config/commands.json";
    private static final ObjectMapper objectMapper = new ObjectMapper()
            .enable(SerializationFeature.INDENT_OUTPUT); // для красивого форматирования

    public static boolean write_to_json(String name_command, String command) {
        try {
            // Создаем директорию, если она не существует
            File configFile = new File(CONFIG_PATH);
            configFile.getParentFile().mkdirs();

            Map<String, String> data;

            // Читаем существующие данные, если файл существует
            if (configFile.exists() && configFile.length() > 0) {
                data = objectMapper.readValue(configFile,
                        new TypeReference<Map<String, String>>() {});
            } else {
                data = new HashMap<>();
            }

            // Добавляем/обновляем данные
            data.put(name_command, command);

            // Записываем обратно в файл
            objectMapper.writeValue(configFile, data);

            return true;
        } catch (Exception e) {
            System.err.println("[ERROR_JSON] " + e);
            e.printStackTrace();
            return false;
        }


    }
}
