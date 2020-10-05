import database.InputPointRepository;
import database.PipelineSystemRepository;
import dijkstras.Graph;
import dijkstras.Vertex;
import java.io.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class Main {
private final static File OUTPUT_FILE = new File("D:\\Java_Projects\\dbbest_water_pipeline_system\\src\\main\\resources\\output.csv");
    public static void main(String[] args) throws IOException {
        OUTPUT_FILE.delete();
        try (PrintWriter fileWriter = new PrintWriter(new FileOutputStream(OUTPUT_FILE, true));
             Connection connection = PipelineSystemRepository.getConnection()) {
            PipelineSystemRepository pipelineSystemRepository = new PipelineSystemRepository(connection);
            InputPointRepository inputPointRepository = new InputPointRepository(connection);
            pipelineSystemRepository.createTableWaterPipelineSystem();
            inputPointRepository.createTableInputPoint();
            Graph graph = new Graph(pipelineSystemRepository.getAllDataFromPipelineSystem());
            pipelineSystemRepository.getAllDataFromPipelineSystem();
            List<Vertex> vertices1 = inputPointRepository.getFirstInputPoint();
            List<Vertex> vertices2 = inputPointRepository.getSecondInputPoint();
            fileWriter.write("ROUTE EXISTS; MIN LENGTH \n");
            for (int i = 0; i < vertices1.size(); i++) {
                graph.dijkstra(vertices1.get(i).getPipeNumber(), vertices2.get(i).getPipeNumber());
                graph.writeCSV(vertices2.get(i).getPipeNumber(), fileWriter);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}