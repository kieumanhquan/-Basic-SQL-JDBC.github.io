package repository;

import constan.Data;
import driverManagement.Driving;
import driverManagement.DrivingTime;
import entity.BusLineEntity;
import entity.DriverEntity;
import util.CollectionUtil;
import util.DataConectionUtil;
import util.ObjUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class DrivingRepo {
    private static final String DRIVING_TABLE_NAME = "driving_assignment";

    private static final String DRIVER_ID = "driver_id";
    private static final String ROUTE_ID = "bus_line_id";
    private static final String ROUND_TRIP_NUMBER = "round_trip_number";

    public static final Connection connection;

    static {
        connection = DataConectionUtil.openConnection(Data.DRIVER_STRING, Data.URL, Data.USERNAME, Data.PASSWORD);
    }

    public List<Driving> getDrivingTime() {
        List<Driving> drivings = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            String query = "select d.id driver_id, d.name, d.address, d.phone_number, d.driver_level, bl.id bus_line_id, bl.distance, bl.bus_stop_number, da.round_trip_number " +
                    "from " + DRIVING_TABLE_NAME + " da join " + DriverRepo.DRIVER_TABLE_NAME + " d on da.driver_Id = d.id join " + RouteRepo.ROUTE_TABLE_NAME + " bl on da.bus_line_id = bl.id";
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            drivings = new ArrayList<>();
            while (resultSet.next()) {
                int driverID = resultSet.getInt(DRIVER_ID);
                String name = resultSet.getString(DriverRepo.NAME);
                String address = resultSet.getString(DriverRepo.ADDRESS);
                String phoneNumber = resultSet.getString(DriverRepo.PHONE_NUMBER);
                String level = resultSet.getString(DriverRepo.LEVEL);
                DriverEntity driver = new DriverEntity(driverID, name, address, phoneNumber, level);

                int routeID = resultSet.getInt(ROUTE_ID);
                float range = resultSet.getFloat(RouteRepo.RANGE);
                int stopNumber = resultSet.getInt(RouteRepo.STOP_NUMBER);
                BusLineEntity busLine = new BusLineEntity(routeID, range, stopNumber);

                int turn = resultSet.getInt(ROUND_TRIP_NUMBER);

                DrivingTime drivingTime = new DrivingTime(busLine, turn);

                Driving tempDriving = searchDriver(drivings, driverID);

                if (ObjUtil.isEmpty(tempDriving)) {
                    Driving driving = new Driving((DriverEntity) drivings, Arrays.asList(drivingTime));
                    drivings.add(driving);
                } else {
                    int index = drivings.indexOf(tempDriving);
                    drivings.get(index).getDrivingTime().add(drivingTime);
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            DataConectionUtil.closeConnection(resultSet, preparedStatement, null);
        }
        return drivings;
    }

    private Driving searchDriver(List<Driving> drivings, int driverId) {

        List<Driving> collect = drivings.stream().filter(driving -> driving.getDriverEntity().getId() == driverId).collect(Collectors.toList());

        if (!CollectionUtil.isEmpty(collect)) {
            collect.get(0);
        }
        return null;
    }

    public void insertNewDriving(Driving driving) {
        if (ObjUtil.isEmpty(driving)) {
            return;
        }
        int driverID = driving.getDriverEntity().getId();
        driving.getDrivingTime().forEach(time -> {
            PreparedStatement preparedStatement = null;
            try {
                String query = "INSERT INTO " + DRIVING_TABLE_NAME + " VALUES (?, ?, ?)";
                preparedStatement = connection.prepareStatement(query);
                preparedStatement.setInt(1, driverID);
                preparedStatement.setInt(2, time.getRoute().getId());
                preparedStatement.setInt(3, time.getRoundTripNumber());
                preparedStatement.executeUpdate();
            } catch (SQLException ex) {
                ex.printStackTrace();
            } finally {
                DataConectionUtil.closeConnection(null, preparedStatement, null);
            }
        });
    }

    public void insertNewDrivingTime(List<Driving> drivings) {
        if (CollectionUtil.isEmpty(drivings)) {
            return;
        }
        drivings.forEach(this::insertNewDriving);
    }
}