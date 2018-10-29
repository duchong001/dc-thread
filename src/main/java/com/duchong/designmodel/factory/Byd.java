package com.duchong.designmodel.factory;

/**
 * 比亚迪
 *
 * @author DUCHONG
 * @since 2018-10-29 11:22
 **/
public class Byd implements Car {

    private String carName;
    private String carColor;

    public String getCarName() {
        return carName;
    }

    public void setCarName(String carName) {
        this.carName = carName;
    }

    public String getCarColor() {
        return carColor;
    }

    public void setCarColor(String carColor) {
        this.carColor = carColor;
    }

    public Byd() {

    }
    public Byd(String carColor, String carName) {
        this.carColor = carColor;
        this.carName = carName;
    }

    @Override
    public String toString() {
        return "Byd{" +
                "carName='" + carName + '\'' +
                ", carColor='" + carColor + '\'' +
                '}';
    }

    @Override
    public void run() {
        System.out.println("Byd------beginRun........");
    }
}
