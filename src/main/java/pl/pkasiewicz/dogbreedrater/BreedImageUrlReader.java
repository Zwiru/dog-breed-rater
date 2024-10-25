package pl.pkasiewicz.dogbreedrater;

import org.apache.commons.io.IOUtils;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class BreedImageUrlReader {
    public static String getJson(URL url) {
        String json = null;
        try {
            json = IOUtils.toString(url, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        JSONObject jsonObject = new JSONObject(json);
        return jsonObject.get("message").toString();
    }
}
