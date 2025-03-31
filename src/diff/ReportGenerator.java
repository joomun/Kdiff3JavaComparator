package diff;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class ReportGenerator {

    public void generateReport(String reportFilePath, List<String> differences) throws IOException {
        try (FileWriter writer = new FileWriter(reportFilePath)) {
            if (differences.isEmpty()) {
                writer.write("No differences found.");
            } else {
                for (String diff : differences) {
                    writer.write(diff + "\n");
                }
            }
        }
    }
}
