import java.io.*;

public class InputHelper {
    public static final String RESOURCE_PATH = "/src/main/resources/input/";

    public static FileInputStream fis() throws IOException {

        StackTraceElement se = Thread.currentThread().getStackTrace()[2];
        String[] qualifiedClassName = se.getClassName().split("\\.");
        String className = qualifiedClassName[qualifiedClassName.length - 1];

        String input = new File("").getAbsolutePath()
                + RESOURCE_PATH
                + className;

        return new FileInputStream(new File(input));
    }
}
