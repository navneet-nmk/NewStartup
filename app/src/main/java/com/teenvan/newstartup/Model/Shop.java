package com.teenvan.newstartup.Model;

/**
 * Created by navneet on 10/02/16.
 */
public class Shop {

    // Declaration of member variables
     String name;
     String address;
     Double distance;
     String image;
     int points;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getImageUrl(){
        return image;
    }

    public String getName() {
        return name;
    }

    public int getPoints(){
        return points;
    }

    public void setPoints(int p){
        points = p;
    }

    public void setName(String name) {
        this.name = name;
    }


    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }
}
