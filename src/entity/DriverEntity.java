package entity;

import java.sql.*;
import java.util.Properties;
import java.util.Scanner;
import java.util.logging.Logger;

public class DriverEntity extends Person implements Driver {
    public static int AUTO_ID = 10000;
    private int id;
    private String level;

    private static final String LEVEL_A = "Loại A";
    private static final String LEVEL_B = "Loại B";
    private static final String LEVEL_C = "Loại C";
    private static final String LEVEL_D = "Loại D";
    private static final String LEVEL_E = "Loại E";
    private static final String LEVEL_F = "Loại F";

    public DriverEntity() {
    }

    public DriverEntity(int id, String name, String address, String phoneNumber, String level) {
        super(name, address, phoneNumber);
        this.id = id;
        this.level = level;
    }

    public static int getAutoId() {
        return AUTO_ID;
    }

    public static void setAutoId(int autoId) {
        AUTO_ID = autoId;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public static String getLevelA() {
        return LEVEL_A;
    }

    public static String getLevelB() {
        return LEVEL_B;
    }

    public static String getLevelC() {
        return LEVEL_C;
    }

    public static String getLevelD() {
        return LEVEL_D;
    }

    public static String getLevelE() {
        return LEVEL_E;
    }

    public static String getLevelF() {
        return LEVEL_F;
    }

    @Override
    public void inputInfo() {
        this.setId(DriverEntity.AUTO_ID);

        super.inputInfo();
        System.out.println("Nhập trình độ lái xe: ");
        System.out.println("1. Loại A");
        System.out.println("2. Loại B");
        System.out.println("3. Loại C");
        System.out.println("4. Loại D");
        System.out.println("5. Loại E");
        System.out.println("6. Loại F");
        System.out.println("Nhập sự lựa chọn của bạn: ");
        boolean check = true;
        do {
            int choice;
            try {
                choice = new Scanner(System.in).nextInt();
                check = true;
            } catch (Exception e) {
                System.out.print("Không được nhập ký tự khác ngoài số! Vui lòng thử lại: ");
                check = false;
                continue;
            }
            if (choice <= 0 || choice > 6) {
                System.out.print("Nhập số trong khoảng từ 1 đến 6! Vui lòng thử lại: ");
                check = false;
            }
            switch (choice) {
                case 1:
                    this.setLevel(DriverEntity.LEVEL_A);
                    check = true;
                    break;
                case 2:
                    this.setLevel(DriverEntity.LEVEL_B);
                    check = true;
                    break;
                case 3:
                    this.setLevel(DriverEntity.LEVEL_C);
                    check = true;
                    break;
                case 4:
                    this.setLevel(DriverEntity.LEVEL_D);
                    check = true;
                    break;
                case 5:
                    this.setLevel(DriverEntity.LEVEL_E);
                    check = true;
                    break;
                case 6:
                    this.setLevel(DriverEntity.LEVEL_F);
                    check = true;
                    break;
                default:
                    System.out.print("Trình độ vừa chọn không hợp lệ, vui lòng chọn lại: ");
                    check = false;
                    break;
            }
        } while (!check);
        DriverEntity.AUTO_ID++;
    }

    @Override
    public String toString() {
        return  super.toString() + "Driver {" +
                " id = " + id +
                ", level = '" + level + '\'' +
                '}';
    }

    @Override
    public Connection connect(String s, Properties properties) throws SQLException {
        return null;
    }

    @Override
    public boolean acceptsURL(String s) throws SQLException {
        return false;
    }

    @Override
    public DriverPropertyInfo[] getPropertyInfo(String s, Properties properties) throws SQLException {
        return new DriverPropertyInfo[0];
    }

    @Override
    public int getMajorVersion() {
        return 0;
    }

    @Override
    public int getMinorVersion() {
        return 0;
    }

    @Override
    public boolean jdbcCompliant() {
        return false;
    }

    @Override
    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        return null;
    }
}
