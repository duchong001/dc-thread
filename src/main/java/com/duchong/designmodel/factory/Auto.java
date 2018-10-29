package com.duchong.designmodel.factory;

/**
 * 大众
 *
 * @author DUCHONG
 * @since 2018-10-29 11:22
 **/
public class Auto implements  Car{

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

    public Auto() {

    }
    public Auto(String carColor, String carName) {
        this.carColor = carColor;
        this.carName = carName;
    }

    @Override
    public String toString() {
        return "Auto{" +
                "carName='" + carName + '\'' +
                ", carColor='" + carColor + '\'' +
                '}';
    }

    @Override
    public void run() {
        System.out.println("Auto------beginRun.......");
    }
}
