package util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class IO
{
    public static String read(File file) throws IOException
    {
        return Files.readString(file.toPath());
    }

    public static void write(String content, File file) throws IOException
    {
        Files.write(file.toPath(), content.getBytes());
    }
}
