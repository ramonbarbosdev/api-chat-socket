package app.chat.config;

import io.github.cdimascio.dotenv.Dotenv;

public class DotenvLoader {

    static {
        Dotenv dotenv = Dotenv.configure()
            .filename(".env")
            .ignoreIfMissing()
            .load();

        dotenv.entries().forEach(entry -> {
            System.setProperty(entry.getKey(), entry.getValue());
        });
    }

    public static void init() {
        // só para forçar o classloader
    }
}