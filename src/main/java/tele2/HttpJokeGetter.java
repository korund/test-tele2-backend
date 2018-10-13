package tele2;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

public class HttpJokeGetter implements JokeGetterInterface {
    private final String USER_AGENT = "Mozilla/5.0";
    private final String method = "GET";

    @Override
    public String requestJoke(String domain, String path, Map<String, String> params) {
        String response = null;
        try {
            response = request(constructUrl(domain, path, params), method);
        } catch (Exception e) {
            String errorMessage = "Error message:";
            if (e.getCause() != null) {
                errorMessage += "\nCause: " + e.getCause();
            }
            if (e.getLocalizedMessage() != null) {
                errorMessage += "\nBody: " + e.getLocalizedMessage();
            }
            System.out.println(errorMessage);
        }
        if (response == null) {
            return "\nSorry, couldn't get joke for you this time :(";
        }

        return parseJson(response);
    }

    private String constructUrl(String domain, String path, Map<String, String> params) {
        if (params == null) {
            return domain+path;
        }

        String paramString = params.entrySet()
                .stream()
                .filter(item -> item.getValue() != null)
                .map(item -> item.getKey() + "=" + item.getValue())
                .collect(Collectors.joining("&"));

        return domain+path+"?"+paramString;
    }

    private String request(String url, String method) throws Exception {
        URL obj = new URL(url);
        java.net.HttpURLConnection con = (java.net.HttpURLConnection) obj.openConnection();
        con.setRequestMethod(method);
        con.setRequestProperty("User-Agent", USER_AGENT);

        int responseCode = con.getResponseCode();
        System.out.println("\nSending 'GET' request to URL : " + url);
        System.out.println("Response Code : " + responseCode + "\n");

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        return response.toString();
    }

    private String parseJson(String stringifiedJson) {

        JsonObject rootElement = new JsonParser().parse(stringifiedJson).getAsJsonObject();
        JsonElement valueElement = rootElement.get("value");
        if (valueElement.isJsonArray()) {
            JsonArray valueArray = rootElement.getAsJsonArray("value");
            valueElement = valueArray.get(new Random().nextInt(valueArray.size()));
        }

        String parsed = ((JsonObject) valueElement)
                .get("joke")
                .getAsString();
        if (parsed == null) {
            return "We could not find a joke in response";
        }
        return parsed;
    }
}