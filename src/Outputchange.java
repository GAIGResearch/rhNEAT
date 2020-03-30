import java.io.*;

/**
 * Created by Raluca on 22-Oct-17.
 */
public class Outputchange {

    public static void main(String[] args) {
        try {
            File dir = new File("results_diver");
            File[] directoryListing = dir.listFiles();
            if (directoryListing != null) {
                for (File dirc : directoryListing) {
                    for (File child : dirc.listFiles()) {
                        FileReader fileReader = new FileReader(child);
                        BufferedReader bufferedReader = new BufferedReader(fileReader);
                        StringBuffer stringBuffer = new StringBuffer();
                        String line;
                        while ((line = bufferedReader.readLine()) != null) {
                            String[] parts = line.split(" ");
                            String part1 = parts[3].split(":")[1].split(",")[0];
                            String part2 = parts[4].split(":")[1].split(",")[0];
                            String part3 = parts[5].split(":")[1];
                            line = part1 + " " + part2 + " " + part3;
                            stringBuffer.append(line);
                            stringBuffer.append("\n");
                        }
                        fileReader.close();
                        PrintWriter writer = new PrintWriter(child, "UTF-8");
                        writer.print(stringBuffer.toString());
                        writer.close();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
