package repository;

import constan.Data;
import util.CollectionUtil;
import util.DataConectionUtil;
import util.ObjUtil;
import entity.DriverEntity;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DriverRepo {
    public static final String DRIVER_TABLE_NAME = "driver";
    public static final String ID = "id";
    public static final String NAME = "name";
    public static final String ADDRESS = "address";
    public static final String PHONE_NUMBER = "phone_number";
    public static final String LEVEL = "driver_level";
    private static final Connection connection;

    static {
        connection = DataConectionUtil.openConnection(Data.DRIVER_STRING, Data.URL, Data.USERNAME, Data.PASSWORD);
    }

    public List<DriverEntity> getDrivers() {
        List<Driver> drivers = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            String query = "SELECT * FROM " + DRIVER_TABLE_NAME;
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            drivers = new ArrayList<>();

            while (resultSet.next()) {
                int id = resultSet.getInt(ID);
                String name = resultSet.getString(NAME);
                String address = resultSet.getString(ADDRESS);
                String phoneNumber = resultSet.getString(PHONE_NUMBER);
                String level = resultSet.getString(LEVEL);
                DriverEntity driver = new DriverEntity(id, name, address, phoneNumber, level);

                if (ObjUtil.isEmpty(driver)) {
                    continue;
                }
                drivers.add(driver);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            DataConectionUtil.closeConnection(resultSet, preparedStatement, null);
        }
        return drivers;
    }

    public void insertNewDriver(DriverEntity driverEntity) {
        if (ObjUtil.isEmpty(driverEntity)) {
            return;
        }

        PreparedStatement preparedStatement = null;
        try {
            String query = "INSERT INTO " + DRIVER_TABLE_NAME + " VALUES (?, ?, ?, ?, ?)";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, driverEntity.getId());
            preparedStatement.setString(2, driverEntity.getName());
            preparedStatement.setString(3, driverEntity.getAddress());
            preparedStatement.setString(4, driverEntity.getPhoneNumber());
            preparedStatement.setString(5, driverEntity.getLevel());
            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            DataConectionUtil.closeConnection(null, preparedStatement, null);
        }
    }

    public void insertNewDriver(List<DriverEntity> drivers) {
        if (CollectionUtil.isEmpty(drivers)) {
            return;
        }
        drivers.forEach(this::insertNewDriver);
    }
}