package tele2;

public class JokeGetter {
    private static final String url = "https://api.icndb.com/";
    private static final String path = "jokes/random";

    public static void main(String[] args) {
        JokeGetterInterface jokeGetter = new HttpJokeGetter();
        System.out.println("Getting a fresh joke...");
        System.out.println(jokeGetter.requestJoke(url, path, null));
    }
}
