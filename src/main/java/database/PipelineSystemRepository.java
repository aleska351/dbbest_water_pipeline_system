package database;

import dijkstras.Edge;
import dijkstras.Vertex;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;


public class PipelineSystemRepository {

    private final Connection connection;

    public PipelineSystemRepository(Connection connection) {
        this.connection = connection;
    }

    /**
     * @return
     * @throws SQLException
     */
    public static Connection getConnection() throws SQLException, IOException {
        Properties properties = new Properties();
        try (InputStream in = Files.newInputStream(Paths.get("database.properties"))) {
            properties.load(in);
            String url = properties.getProperty("url");
            String username = properties.getProperty("username");
            String password = properties.getProperty("password");
            System.out.println("Подключение успешно");
            return DriverManager.getConnection(url, username, password);

        }
    }

    /**
     * Create table from csv file for the water pipeline  system
     */
    public void createTableWaterPipelineSystem() {
        try (Statement stmt = connection.createStatement()) {
            stmt.execute("drop table if exists waterPipelineSystem");
            stmt.execute("CREATE TABLE WaterPipelineSystem AS SELECT * FROM CSVREAD('D:\\Java_Projects\\dbbest_water_pipeline_system\\src\\main\\resources\\waterPipelineSystem.csv', null,'fieldSeparator=;')");
            ResultSet rs = stmt.executeQuery("select * from waterPipelineSystem");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }


    /**
     * Get data from the database waterPipelineSystem.
     *
     * @return List of Edge.
     */

    public List<Edge> getAllDataFromPipelineSystem() {
        try (Statement statement = connection.createStatement();
             ResultSet cursor = statement.executeQuery("SELECT IDX, IDY, LENGTH FROM PIPELINE.PUBLIC.waterPipelineSystem")) {
            List<Edge> edges = new ArrayList<>();
            while (cursor.next()) {
                Edge edge = createEdgeFromCursor(cursor);
                edges.add(edge);
            }
            return edges;
        } catch (SQLException e) {
            System.out.println("Cannot ");
            throw new RuntimeException(e);
        }
    }


    private Edge createEdgeFromCursor(ResultSet cursor) throws SQLException {
        return new Edge(cursor.getInt("IDX"), cursor.getInt("IDY"), cursor.getInt("LENGTH"));
    }

}
