package main;

import driverManagement.Driving;
import entity.BusLineEntity;
import entity.DriverEntity;
import repository.DriverRepo;
import repository.DrivingRepo;
import repository.RouteRepo;
import util.CollectionUtil;

import java.sql.Driver;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static List<DriverEntity> drivers = new ArrayList<>();
    public static List<BusLineEntity> busLines = new ArrayList<>();
    public static List<Driving> drivings = new ArrayList<>();

    public static final DriverRepo driverRepo = new DriverRepo();
    public static final RouteRepo routeRepo = new RouteRepo();
    public static final DrivingRepo drivingRepo = new DrivingRepo();

    private static final DriverCreator driverCreator = new DriverCreator();
    private static final RouteCreator routeCreator = new RouteCreator();
    private static final DrivingTimeCreator drivingTimeCreator = new DrivingTimeCreator();
    private static final DrivingTimeSorter_Calculator sortDrivingTable = new DrivingTimeSorter_Calculator();

    public static void main(String[] args) {
        System.out.println("Program is initializing ....");
        init();
        System.out.println("Program is ready!");
        menu();
    }

    public static void menu() {
        do {
            int functionChoice = functionChoice();
            switch (functionChoice) {
                case 1:
                    createNewDriver();
                    printDriver();
                    break;
                case 2:
                    createNewRoute();
                    printRoute();
                    break;
                case 3:
                    createNewDrivingTable();
                    printDriving();
                    break;
                case 4:
                    sortDrivingTable.sortDrivingTable();
                    break;
                case 5:
                    sortDrivingTable.distanceDriving();
                    break;
                case 6:
                    System.exit(0);
            }
        } while (true);
    }

    private static void init() {
        drivers = !CollectionUtil.isEmpty(driverRepo.getDrivers()) ? driverRepo.getDrivers() : new ArrayList<>();
        if (CollectionUtil.isEmpty(drivers)) {
            DriverEntity.AUTO_ID = 10000;
        } else {
            drivers.sort(Comparator.comparing(DriverEntity::getId));
            DriverEntity.AUTO_ID = drivers.get(drivers.size() - 1).getId() + 1;
        }
        busLines = !CollectionUtil.isEmpty(routeRepo.getRoute()) ? routeRepo.getRoute() : new ArrayList<>();
        if (CollectionUtil.isEmpty(busLines)) {
            BusLineEntity.AUTO_ID = 100;
        } else {
            busLines.sort(Comparator.comparing(BusLineEntity::getId));
            BusLineEntity.AUTO_ID = busLines.get(drivers.size() - 1).getId() + 1;
        }
        drivings = !CollectionUtil.isEmpty(drivingRepo.getDrivingTime()) ? drivingRepo.getDrivingTime() : new ArrayList<>();
    }

    public static int functionChoice() {
        System.out.println("-----QUẢN LÝ PHÂN CÔNG LÁI XE BUS-------");
        System.out.println("1. Nhập danh sách lái xe");
        System.out.println("2. Nhập danh sách tuyến");
        System.out.println("3. Nhập danh sách phân công lái xe");
        System.out.println("4. Sắp xếp danh sách phân công ");
        System.out.println("5. Lập bảng thống kê tổng khoảng cách");
        System.out.println("6. Thoát");
        System.out.print("Nhập sự lựa chọn của bạn: ");
        int functionChoice = 0;
        boolean checkFunction = true;
        do {
            try {
                functionChoice = new Scanner(System.in).nextInt();
                checkFunction = true;
            } catch (Exception e) {
                System.out.println("Không được nhập ký tự khác ngoài số! Nhập lại: ");
                continue;
            }
            if (functionChoice <= 0 || functionChoice > 6) {
                System.out.print("Nhập số trong khoảng từ 1 đến 6! Nhập lại: ");
                checkFunction = false;
            } else break;
        } while (!checkFunction);
        return functionChoice;
    }

    public static void createNewDriver() {
        driverCreator.createNewDriver();
    }

    public static void printDriver() {
        drivers.forEach(System.out::println);
    }

    public static void createNewRoute() {
        routeCreator.createNewRoute();
    }

    public static void printRoute() {
        busLines.forEach(System.out::println);
    }

    public static void createNewDrivingTable() {
        drivingTimeCreator.createDrivingTable();
    }

    public static void printDriving() {
        drivings.forEach(System.out::println);
    }
}