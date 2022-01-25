package driverManagement;

import entity.DriverEntity;
import repository.DrivingRepo;

//import java.sql.Driver;
import java.util.ArrayList;
import java.util.List;

public class Driving  {
    private DriverEntity driverEntity;
    private List<DrivingTime> drivingTime = new ArrayList<>();
    private int totalBusLineNumber;
    private float totalDistance;

    public Driving(Driving driving, List<DrivingTime> drivingTime) {
    }

    public Driving(DriverEntity driverEntity, List<DrivingTime> drivingTime) {
        this.driverEntity = driverEntity;
        this.drivingTime = drivingTime;
    }

    public float getTotalDistance() {
        return totalDistance;
    }

    public void setTotalDistance(float totalDistance) {
        this.totalDistance = totalDistance;
    }

    public int getTotalBusLineNumber() {
        return totalBusLineNumber;
    }

    public void setTotalBusLineNumber(int totalBusLineNumber) {
        this.totalBusLineNumber = totalBusLineNumber;
    }

    public DriverEntity getDriverEntity() {
        return driverEntity;
    }

    public void setDriverEntity(DriverEntity driverEntity) {
        this.driverEntity = driverEntity;
    }

    public List<DrivingTime> getDrivingTime() {
        return drivingTime;
    }

    public void setDrivingTime(List<DrivingTime> drivingTime) {
        this.drivingTime = drivingTime;
    }

    @Override
    public String toString() {
        return "Driving {" +
                " driver = " + driverEntity +
                ", drivingTime = " + drivingTime +
                '}';
    }
}