package database;

import dijkstras.Vertex;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class InputPointRepository {
    private final Connection connection;

    public InputPointRepository(Connection connection) {
        this.connection = connection;
    }

    /**
     * Create table from csv file for set of points, between which need to find the route.
     */

    public void createTableInputPoint() {
        try (Statement stmt = connection.createStatement()) {
            stmt.execute("drop table if exists inputPoint");
            stmt.execute("CREATE TABLE inputPoint AS SELECT * FROM CSVREAD('D:\\Java_Projects\\dbbest_water_pipeline_system\\src\\main\\resources\\inputPoint.csv', null,'fieldSeparator=;')");
        } catch (SQLException throwables) {
            System.out.println("Table for input point does not create");
            new RuntimeException();
        }
    }

    /**
     * Get List of first point from the database inputPoint.
     *
     * @return List of Edge.
     */
    public List<Vertex> getFirstInputPoint() {
        try (Statement statement = connection.createStatement();
             ResultSet cursor = statement.executeQuery("SELECT IDA FROM inputPoint")) {
            List<Vertex> vertex = new ArrayList<>();
            while (cursor.next()) {
                vertex.add(createFirstPointFromCursor(cursor));

            }
            return vertex;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Get List of second point from the database inputPoint.
     *
     * @return List of Edge.
     */
    public List<Vertex> getSecondInputPoint() {
        try (Statement statement = connection.createStatement();
             ResultSet cursor = statement.executeQuery("SELECT IDB FROM inputPoint")) {
            List<Vertex> vertex = new ArrayList<>();
            while (cursor.next()) {
                vertex.add(createSecondPointFromCursor(cursor));
            }
            return vertex;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Vertex createFirstPointFromCursor(ResultSet cursor) throws SQLException {
        return new Vertex(cursor.getInt("IDA"));
    }

    private Vertex createSecondPointFromCursor(ResultSet cursor) throws SQLException {
        return new Vertex(cursor.getInt("IDB"));
    }
}
