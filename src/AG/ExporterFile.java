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
        if (toExport == null) {
            throw new IOException("Object to export is null");
        }
        JsonNode jsonNode = this.obj.valueToTree(toExport);

        try (FileWriter file = new FileWriter(filePath)) {
            file.write(jsonNode.toString());
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        }
    }

    public void exportBest(String filePath, Object toExport) throws IOException {
        if (toExport == null) {
            throw new IOException("Object to export is null");
        }
        JsonNode jsonNode = this.obj.valueToTree(toExport);


        try (FileWriter file = new FileWriter(filePath)) {
            file.write(jsonNode.toString());
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        }
    }

}
