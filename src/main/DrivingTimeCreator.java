package main;

import driverManagement.Driving;
import driverManagement.DrivingTime;
import entity.BusLineEntity;
import util.CollectionUtil;
import util.ObjUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DrivingTimeCreator {
    private final List<Integer> checkID = new ArrayList<>();

    public boolean isValidSubjectAndTeacher() {
        return !CollectionUtil.isEmpty(Main.drivers) && !CollectionUtil.isEmpty(Main.busLines);
    }

    public void createDrivingTable() {
        if (!isValidSubjectAndTeacher()) {
            System.out.println("Bạn cần nhập lái xe và tuyến đường trước khi phân công ");
            return;
        }
        boolean check = true;
        List<Driving> tempDrivings = new ArrayList<>();
        for (int i = 0; i < Main.drivers.size(); i++) {
            String driverName = Main.drivers.get(i).getName();
            System.out.println("------Phân công cho lái xe " + driverName + " ---------");
            System.out.println("Nhập số tuyến đường mà lái xe " + driverName + " được phân công: ");
            int drivingRouteNumber = inputDrivingRouteNumber();
            if (drivingRouteNumber == 0){
                continue;
            }
            List<DrivingTime> drivingTime = new ArrayList<>();
            for (int j = 0; j < drivingRouteNumber; j++) {
                System.out.println("Nhập id tuyến đường thứ " + (j + 1) + " mà lái xe " + driverName + " được phân công: ");
                BusLineEntity busLine = inputRouteId();
                System.out.println(busLine);
                System.out.println("Nhập số lượt lái xe " + driverName + " đi tuyến đường này: ");
                int drivingTurnNumber = inputTurnNumber(drivingTime);
                drivingTime.add(new DrivingTime(busLine, drivingTurnNumber));
            }
            Driving driving = new Driving(Main.drivers.get(i), drivingTime);
            tempDrivings.add(driving);
            driving.setTotalBusLineNumber(drivingRouteNumber);
            Main.drivings.add(driving);
        }
        Main.drivingRepo.insertNewDrivingTime(tempDrivings);
    }

    private int inputTurnNumber(List<DrivingTime> drivingTime) {
        int drivingTurnNumber = 0;
        boolean isValidTurnNumber = true;
        do {
            try {
                drivingTurnNumber = new Scanner(System.in).nextInt();
                isValidTurnNumber = true;
            } catch (Exception e) {
                System.out.println("Không được có ký tự khác ngoài số! Nhập lại: ");
                isValidTurnNumber = false;
                continue;
            }
            if (drivingTurnNumber <= 0) {
                System.out.print("Số lượt lái phải lớn hơn 0! Nhập lại: ");
                isValidTurnNumber = false;
                continue;
            }
            int currentTotalTurn = calculateTotalTurn(drivingTime);
            if (currentTotalTurn + drivingTurnNumber > 15) {
                System.out.println("Tổng lượt lái của lái xe đang là " + currentTotalTurn + ", lớn hơn 15! Nhập lại: ");
                isValidTurnNumber = false;
            }
        } while (!isValidTurnNumber);
        return drivingTurnNumber;
    }

    private int calculateTotalTurn(List<DrivingTime> drivingTime) {
        return CollectionUtil.isEmpty(drivingTime) ? 0 : drivingTime.stream().mapToInt(DrivingTime::getRoundTripNumber).sum();
    }

    private int inputDrivingRouteNumber() {
        int drivingRouteNumber = 0;
        boolean isValidRouteNumber = true;
        do {
            try {
                drivingRouteNumber = new Scanner(System.in).nextInt();
                isValidRouteNumber = true;
            } catch (Exception e) {
                System.out.println("Không được nhập ký tự khác ngoài số! Nhập lại: ");
                isValidRouteNumber = false;
                continue;
            }
            if (drivingRouteNumber < 0 || drivingRouteNumber > Main.busLines.size()) {
                System.out.print("Tuyến đường không được nhỏ hơn 0 và lớn hơn tổng số tuyến! Nhập lại: ");
                isValidRouteNumber = false;
            }
        } while (!isValidRouteNumber);
        return drivingRouteNumber;
    }

    private BusLineEntity inputRouteId() {
        int routeId = 0;
        boolean isValidRouteId = true;
        do {
            try {
                routeId = new Scanner(System.in).nextInt();
                isValidRouteId = true;
            } catch (Exception e) {
                System.out.println("không được có ký tự khác ngoài số! Nhập lại: ");
                isValidRouteId = false;
                continue;
            }

            BusLineEntity busLine = searchRouteId(routeId);
            if (ObjUtil.isEmpty(busLine)) {
                System.out.print("Không có id tuyến đường vừa nhập! Nhập lại: ");
                isValidRouteId = false;
            }
            else return busLine;

            for (Integer integer : checkID) {
                if (integer == routeId) {
                    System.out.println("Tuyến đường đã tồn tại! Nhập lại: ");
                    isValidRouteId = false;
                    break;
                }
            }
            checkID.add(routeId);
        } while (!isValidRouteId);
        System.out.println("sai ở 2");
        return null;
    }

    public static BusLineEntity searchRouteId(int id) {
        System.out.println(Main.busLines.size());
        for (int i = 0; i < Main.busLines.size(); i++) {
            if (Main.busLines.get(i).getId() == id) {
                return Main.busLines.get(i);
            }
        }
        return null;
    }
}