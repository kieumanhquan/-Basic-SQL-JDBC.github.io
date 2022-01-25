package main;

import util.CollectionUtil;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class DrivingTimeSorter_Calculator {
    public void sortDrivingTable() {
        if (CollectionUtil.isEmpty(Main.drivings)) {
            System.out.println("Nhập bảng phân công trước khi sắp xếp ");
            return;
        }

        do {
            System.out.println("----- Sắp xếp danh sách phân công -------");
            System.out.println("1. Sắp xếp theo họ tên lái xe");
            System.out.println("2. Sắp xếp theo số lượng tuyến");
            System.out.println("3. Thoát");
            System.out.println("Hãy nhập sự lựa chọn của bạn: ");
            int choice = 0;
            boolean checkchoice = true;

            do {
                try {
                    choice = new Scanner(System.in).nextInt();
                    checkchoice = true;
                } catch (Exception e) {
                    System.out.println("Không được chứa ký tự khác ngoài số! Nhập lại");
                    checkchoice = false;
                    continue;
                }

                if (choice <= 0 || choice > 3) {
                    System.out.println("Nhập số trong khoảng từ 1 đến 3! Nhập lại: ");
                    checkchoice = false;
                }
            } while (!checkchoice);

            switch (choice) {
                case 1:
                    sortByDriverName();
                    break;
                case 2:
                    sortByRouteNumber();
                    break;
                case 3:
                    return;
            }
        } while (true);
    }

    public void sortByDriverName() {
        Main.drivings.sort(Comparator.comparing(driving -> driving.getDriverEntity().getName()));
        Main.printDriving();
    }

    public void sortByRouteNumber() {
        Main.drivings.sort((o1, o2) -> o1.getTotalBusLineNumber() < o2.getTotalBusLineNumber() ? 1 : -1);
        Main.printDriving();
    }

    public void distanceDriving() {
        if (CollectionUtil.isEmpty(Main.drivings)) {
            System.out.println("Nhập bảng phân công trước khi sắp xếp ");
            return;
        }

        for (int i = 0; i < Main.drivings.size()-1; i++) {
            System.out.println("-------Tính tổng khoảng cách cho lái xe " + Main.drivers.get(i).getName() + " --------");
            List<Float> distanceTotal = new ArrayList<>();

            for (int j = 0; j < Main.drivings.get(i).getDrivingTime().size(); j++) {
                float tmp = Main.drivings.get(i).getDrivingTime().get(j).getRoute().getDistance() *
                        Main.drivings.get(i).getDrivingTime().get(j).getRoundTripNumber();
                distanceTotal.add(tmp);
            }

            int tempTotal = 0;
            for (Float aFloat : distanceTotal) {
                tempTotal += aFloat;
            }
            Main.drivings.get(i).setTotalDistance(tempTotal);
            System.out.println(tempTotal);
        }
    }
}