package AG;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;


import java.io.FileWriter;
import java.io.IOException;

public class ExporterFile {

    private ObjectMapper obj;

    public ExporterFile() {
        this.obj = new ObjectMapper();

    }

    public void exportAvg(String filePath, Object toExport) throws IOException {
        if (obj == null) {
            throw new IOException("Object is null");
        }
        JsonNode jsonNode = this.obj.valueToTree(toExport);

        FileWriter file = new FileWriter(filePath);
        ;
        try {

            file.write(jsonNode.toString());

        } catch (IOException e) {
            e.printStackTrace();

        } finally {

            try {
                file.flush();
                file.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void exportBest(String filePath, Object toExport) throws IOException {
        if (obj == null) {
            throw new IOException("Object is null");
        }
        JsonNode jsonNode = this.obj.valueToTree(toExport);

        FileWriter file = new FileWriter(filePath);

        try {
            file.write(jsonNode.toString());
        } catch (IOException e) {
            e.printStackTrace();

        } finally {

            try {
                file.flush();
                file.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
