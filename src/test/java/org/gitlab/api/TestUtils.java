package org.gitlab.api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

public class TestUtils {
    public static String readDataFromResource(String path) throws IOException {
        InputStream inputStream = TestUtils.class.getClassLoader().getResourceAsStream(path);
        assert inputStream != null;
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            return reader.lines().collect(Collectors.joining("\n"));
        }
    }

}
