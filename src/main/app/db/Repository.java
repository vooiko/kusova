package main.app.db;

import main.app.TransportApp;
import main.extensions.Extensions;
import main.model.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.UUID;

import static main.Settings.*;

public class Repository {

    public static ArrayList<Train> selectAllTrains() {
        ArrayList<Train> trains = new ArrayList<>();
        String sqlRequest = "SELECT * FROM train_db.train;";

        ResultSet result = executeQuery(sqlRequest);
        if (result != null) {
            try {
                while (result.next()) {
                    UUID trainId = Extensions.parseId(result.getString("id"));
                    if (trainId != null) {
                        trains.add(new Train(trainId, result.getString("name"), result.getString("code")));
                    }
                }
                TransportApp.logger.info("Finished adding trains from result set!");
                result.close();
            } catch (SQLException e) {
                TransportApp.logger.severe("SQLException! " + e.getMessage());
            }
        } else {
            TransportApp.logger.warning("Result set was null!");
        }
        return trains;
    }

    public static ArrayList<Wagon> selectAllWagons() {
        ArrayList<Wagon> wagons = new ArrayList<>();
        String sqlRequest = "SELECT * FROM train_db.wagon;";

        ResultSet result = executeQuery(sqlRequest);
        if (result != null) {
            try {
                while (result.next()) {
                    UUID id = Extensions.parseId(result.getString("id"));
                    ComfortTypes comfortTypes = ComfortTypes.valueOf(result.getString("type"));
                    if (id != null) {
                        wagons.add(new Wagon(id, new WagonType(comfortTypes, result.getInt("seats"), result.getFloat("weight"))));
                    }
                }
                TransportApp.logger.info("Finished adding wagons from result set!");
                result.close();
            } catch (SQLException e) {
                TransportApp.logger.severe("SQLException! " + e.getMessage());
            }
        } else {
            TransportApp.logger.warning("Result set was null!");
        }
        return wagons;
    }

    public static ArrayList<AssignedWagon> selectAllAssignedWagons(ArrayList<Wagon> wagons) {
        ArrayList<AssignedWagon> assignedWagons = new ArrayList<>();
        String sqlRequest = "SELECT * FROM train_db.assigned_wagon;";

        ResultSet result = executeQuery(sqlRequest);
        if (result != null) {
            try {
                while (result.next()) {
                    UUID wagonId = Extensions.parseId(result.getString("wagon_id"));
                    if (wagonId != null) {
                        for (Wagon wagon: wagons) {
                            if (wagon.getWagonId().compareTo(wagonId) == 0) {
                                UUID assignedWagonId = Extensions.parseId(result.getString("id"));
                                UUID trainId = Extensions.parseId(result.getString("train_id"));
                                if (assignedWagonId != null && trainId != null) {
                                    assignedWagons.add(new AssignedWagon(assignedWagonId, wagon, trainId, result.getInt("number")));
                                }
                            }
                        }
                    }
                }
                TransportApp.logger.info("Finished adding wagons from result set!");
                result.close();
            } catch (SQLException e) {
                TransportApp.logger.severe("SQLException! " + e.getMessage());
            }
        } else {
            TransportApp.logger.warning("Result set was null!");
        }


        return assignedWagons;
    }

    public static void deleteTrain(UUID trainId) {
        TransportApp.logger.info("method 'deleteTrain' in Repository was executed!");
        String sqlQuery = "DELETE FROM train_db.train WHERE (id = '" + trainId + "');";
        execute(sqlQuery);
    }

    public static void deleteWagon(UUID wagonId) {
        TransportApp.logger.info("method 'deleteWagon' in Repository was executed!");
        String sqlQuery = "DELETE FROM train_db.wagon WHERE (id = '" + wagonId + "');";
        execute(sqlQuery);
    }

    public static void deleteAllAssignedWagon(UUID trainId) {
        TransportApp.logger.info("method 'deleteAllAssignedWagon' in Repository was executed!");
        String sqlQuery = "DELETE FROM train_db.assigned_wagon WHERE (train_id = '" + trainId + "');";
        execute(sqlQuery);
    }

    public static void deleteAssignedWagon(UUID assignedId) {
        TransportApp.logger.info("method 'deleteAssignedWagon' in Repository was executed!");
        String sqlQuery = "DELETE FROM train_db.assigned_wagon WHERE (id = '" + assignedId + "');";
        execute(sqlQuery);
    }

    public static void insertTrain(Train train) {
        TransportApp.logger.info("method 'insertTrain' in Repository was executed!");
        String sqlQuery = "INSERT INTO train_db.train VALUES " + train.toSqlValues() + ";";
        execute(sqlQuery);
    }

    public static void insertWagon(Wagon wagon) {
        TransportApp.logger.info("method 'insertWagon' in Repository was executed!");
        String sqlQuery = "INSERT INTO train_db.wagon VALUES " + wagon.toSqlValues() + ";";
        execute(sqlQuery);
    }

    public static void insertAssignedWagon(AssignedWagon assignedWagon) {
        TransportApp.logger.info("method 'insertAssignedWagon' in Repository was executed!");
        String sqlQuery = "INSERT INTO train_db.assigned_wagon VALUES " + assignedWagon.toSqlValues() + ";";
        execute(sqlQuery);
    }

    private static ResultSet executeQuery(String sqlRequest) {
        TransportApp.logger.info("Executing query: " + sqlRequest);
        try {
            // set connection
            Connection connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
            // get data from db & return result
            return connection.createStatement().executeQuery(sqlRequest);
        } catch (SQLException e) {
            TransportApp.logger.severe("ERROR WILE EXECUTING SQL QUERY!");
            TransportApp.logger.severe(e.getMessage());
            return null;
        }
    }

    private static void execute(String sqlQuery) {
        TransportApp.logger.info("Executing query: " + sqlQuery);
        try {
            // set connection
            Connection connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
            connection.createStatement().execute(sqlQuery);
        } catch (SQLException e) {
            TransportApp.logger.severe("ERROR WILE EXECUTING SQL QUERY!");
            TransportApp.logger.severe(e.getMessage());
        }
    }
}
