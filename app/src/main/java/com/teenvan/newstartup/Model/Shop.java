package com.teenvan.newstartup.Model;

/**
 * Created by navneet on 10/02/16.
 */
public class Shop {

    // Declaration of member variables
    private String shopName;
    private String shopAddress;
    private int walkinPoints;
    private Double storeDistance;

    public String getShopAddress() {
        return shopAddress;
    }

    public void setShopAddress(String shopAddress) {
        this.shopAddress = shopAddress;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }


    public int getWalkinPoints() {
        return walkinPoints;
    }

    public void setWalkinPoints(int walkinPoints) {
        this.walkinPoints = walkinPoints;
    }


    public Double getStoreDistance() {
        return storeDistance;
    }

    public void setStoreDistance(Double storeDistance) {
        this.storeDistance = storeDistance;
    }
}
