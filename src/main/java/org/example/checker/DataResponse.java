package org.example.checker;

public class DataResponse {
    private double nightData;
    private double dayData;
    private double tempData;

    public DataResponse(){}
    public DataResponse(double nightData, double dayData, double tempData){
        this.setNightData(nightData);
        this.setDayData(dayData);
        this.setTempData(tempData);
    }

    public double getNightData() {
        return nightData;
    }

    public void setNightData(double nightData) {
        this.nightData = nightData;
    }

    public double getDayData() {
        return dayData;
    }

    public void setDayData(double dayData) {
        this.dayData = dayData;
    }

    public double getTempData() {
        return tempData;
    }

    public void setTempData(double tempData) {
        this.tempData = tempData;
    }

    @Override
    public String toString(){
        return "nightData: " + nightData + ", dayData: " + dayData + ", tempData: " + tempData;
    }
}
