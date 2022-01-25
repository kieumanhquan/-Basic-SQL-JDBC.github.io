package main;

import entity.BusLineEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class RouteCreator {
    public void createNewRoute(){
        System.out.println("Nhập số lượng tuyến muốn thêm mới: ");
        int routeQuantity = 0;
        boolean isValidRouteQuantity = true;
        do {
            try {
                routeQuantity = new Scanner(System.in).nextInt();
                isValidRouteQuantity = true;
            } catch (Exception e) {
                System.out.println("Không được nhập ký tự khác ngoài số! Nhập lại: ");
                isValidRouteQuantity = false;
                continue;
            }

            if (routeQuantity <= 0) {
                System.out.print("Số lượng tuyến đường không được nhỏ hơn 0! Nhập lại: ");
                isValidRouteQuantity = false;
            }
        } while (!isValidRouteQuantity);

        List<BusLineEntity> tempBusLine = new ArrayList<>();
        for (int i = 0; i < routeQuantity; i++) {
            BusLineEntity busLine = new BusLineEntity();
            busLine.inputInfo();
            tempBusLine.add(busLine);
        }
        Main.busLines.addAll(tempBusLine);
        Main.routeRepo.insertNewRoute(tempBusLine);
    }
}