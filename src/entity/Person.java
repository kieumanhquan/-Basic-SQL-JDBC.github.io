package entity;

import java.util.Scanner;

public class Person {
    private String name;
    private String address;
    private String phoneNumber;

    public Person() {
    }

    public Person(String name, String address, String phoneNumber) {
        this.name = name;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void inputInfo(){
        System.out.println("Nhập tên: ");
        this.name = new Scanner(System.in).nextLine();
        System.out.println("Nhập địa chỉ: ");
        this.address = new Scanner(System.in).nextLine();
        System.out.println("Nhập số điện thoại: ");
        this.phoneNumber = new Scanner(System.in).nextLine();
    }

    @Override
    public String toString() {
        return "Person {" +
                " name = '" + name + '\'' +
                ", address = '" + address + '\'' +
                ", phoneNumber = '" + phoneNumber + '\'' +
                '}';
    }
}