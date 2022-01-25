package repository;

import constan.Data;
import entity.BusLineEntity;
import util.CollectionUtil;
import util.DataConectionUtil;
import util.ObjUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RouteRepo {
    public static final String ROUTE_TABLE_NAME = "bus_line";

    public static final String ID = "id";
    public static final String RANGE = "distance";
    public static final String STOP_NUMBER = "bus_stop_number";

    private static final Connection connection;

    static {
        connection = DataConectionUtil.openConnection(Data.DRIVER_STRING, Data.URL, Data.USERNAME, Data.PASSWORD);
    }

    public List<BusLineEntity> getRoute() {
        List<BusLineEntity> busLines = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            String query = "SELECT * FROM " + ROUTE_TABLE_NAME;
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            busLines = new ArrayList<>();
            while (resultSet.next()) {
                int id = resultSet.getInt(ID);
                float range = resultSet.getFloat(RANGE);
                int stopNumber = resultSet.getInt(STOP_NUMBER);
                BusLineEntity busLine = new BusLineEntity(id, range, stopNumber);
                if (ObjUtil.isEmpty(busLine)) {
                    continue;
                }
                busLines.add(busLine);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            DataConectionUtil.closeConnection(resultSet, preparedStatement, null);
        }
        return busLines;
    }

    public void insertNewRoute(BusLineEntity busLine) {
        if (ObjUtil.isEmpty(busLine)) {
            return;
        }
        PreparedStatement preparedStatement = null;
        try {
            String query = "INSERT INTO " + ROUTE_TABLE_NAME + " VALUES (?, ?, ?)";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, busLine.getId());
            preparedStatement.setFloat(2, busLine.getDistance());
            preparedStatement.setInt(3, busLine.getStopNumber());
            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            DataConectionUtil.closeConnection(null, preparedStatement, null);
        }
    }

    public void insertNewRoute(List<BusLineEntity> subjects) {
        if (CollectionUtil.isEmpty(subjects)) {
            return;
        }
        subjects.forEach(this::insertNewRoute);
    }
}