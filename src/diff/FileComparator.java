package diff;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

public class FileComparator {

    public List<String> compareFiles(String sourceFilePath, String targetFilePath) throws IOException {
        List<String> differences = new ArrayList<>();
        
        try (BufferedReader sourceReader = new BufferedReader(new FileReader(sourceFilePath));
             BufferedReader targetReader = new BufferedReader(new FileReader(targetFilePath))) {

            String sourceLine;
            String targetLine;
            int lineNumber = 1;
            
            while ((sourceLine = sourceReader.readLine()) != null || (targetLine = targetReader.readLine()) != null) {
                if (sourceLine == null) {
                    differences.add("Line " + lineNumber + " only in target: " + targetLine);
                } else if (targetLine == null) {
                    differences.add("Line " + lineNumber + " only in source: " + sourceLine);
                } else if (!sourceLine.equals(targetLine)) {
                    differences.add("Line " + lineNumber + " differs: Source: " + sourceLine + " | Target: " + targetLine);
                }
                lineNumber++;
            }
        }
        return differences;
    }
}
