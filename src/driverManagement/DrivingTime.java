package driverManagement;

import entity.BusLineEntity;
import repository.DrivingRepo;

public class DrivingTime extends DrivingRepo {
    private BusLineEntity busLine;
    private int roundTripNumber;

    public DrivingTime() {
    }

    public DrivingTime(BusLineEntity busLine, int roundTripNumber) {
        this.busLine = busLine;
        this.roundTripNumber = roundTripNumber;
    }

    public BusLineEntity getRoute() {
        return busLine;
    }

    public void setBusLine(BusLineEntity busLine) {
        this.busLine = busLine;
    }

    public int getRoundTripNumber() {
        return roundTripNumber;
    }

    public void setRoundTripNumber(int roundTripNumber) {
        this.roundTripNumber = roundTripNumber;
    }

    @Override
    public String toString() {
        return "DrivingTime {" +
                " busLine = " + busLine +
                ", roundTripNumber = " + roundTripNumber +
                '}';
    }
}