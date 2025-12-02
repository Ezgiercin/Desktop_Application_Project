import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ClassroomReader {

    public List<Classroom> readClassrooms(String filePath) {

        List<Classroom> result = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {

            String line;
            boolean isHeader = true;

            while ((line = br.readLine()) != null) {
                if (isHeader) {
                    isHeader = false;
                    continue;
                }
                line = line.trim();
                if (line.isEmpty()) continue;

                String[] parts = line.split(";"); // split with;

                if (parts.length < 2) {
                    System.err.println("Hatalı satır (skip): " + line);
                    continue;
                }

                String name = parts[0].trim();
                int capacity;
                try {
                    capacity = Integer.parseInt(parts[1].trim());
                } catch (NumberFormatException e) {
                    System.err.println("Hatalı kapasite değeri: " + parts[1] + " satır: " + line);
                    continue;
                }

                result.add(new Classroom(name, capacity));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }
}
