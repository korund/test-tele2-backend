package tele2;

import java.util.Map;

public interface JokeGetterInterface {
    String requestJoke(String domain, String path, Map<String, String> params);
}
