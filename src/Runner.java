import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

public class Runner {

    private static int passed;

    public static void main(String[] args) {
        runTest(new File(System.getProperty("user.dir")
                        + "\\out\\production\\Practice\\com\\prakharmittal\\list"),
                "com.prakharmittal.list");
        runTest(new File(System.getProperty("user.dir")
                        + "\\out\\production\\Practice\\com\\prakharmittal\\linear"),
                "com.prakharmittal.linear");
        runTest(new File(System.getProperty("user.dir")
                        + "\\out\\production\\Practice\\com\\prakharmittal\\tree"),
                "com.prakharmittal.tree");
        runTest(new File(System.getProperty("user.dir")
                        + "\\out\\production\\Practice\\com\\prakharmittal\\hashing"),
                "com.prakharmittal.hashing");
        runTest(new File(System.getProperty("user.dir")
                        + "\\out\\production\\Practice\\com\\prakharmittal\\algo"),
                "com.prakharmittal.algo");
        System.out.println("Passed " + passed + " Tests");
    }

    private static void runTest(File directory, String packageName) {
        File[] files = directory.listFiles();
        assert files != null;
        for (File file: files) {
            if (file.getName().contains("Test") && !file.getName().contains("$")) {
                Runtime runTime = Runtime.getRuntime();
                try {
                    String fileName = file.getName().replace(".class", "");
                    String command = "java -cp \"" + System.getProperty("user.dir")
                            + "\\out\\production\\Practice\";"
                            + "\"C:\\Program Files\\JetBrains\\IntelliJ IDEA 2020.3.1\\lib\\*\" "
                            + "org.junit.runner.JUnitCore " + packageName + "." + fileName;
                    Process process = runTime.exec(command);
                    BufferedReader bufferedReader =
                            new BufferedReader(new InputStreamReader(process.getInputStream()));
                    String output = bufferedReader.lines().collect(Collectors.joining(System.lineSeparator()));
                    if (output.contains("FAILURES")) {
                        System.out.println("Failed " + fileName);
                    } else {
                        passed++;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
