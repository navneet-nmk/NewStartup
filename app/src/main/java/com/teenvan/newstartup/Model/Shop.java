package com.teenvan.newstartup.Model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by navneet on 10/02/16.
 */
public class Shop implements Parcelable {

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
        points =p;
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

    protected Shop(Parcel in) {
        name = in.readString();
        address = in.readString();
        distance = in.readByte() == 0x00 ? null : in.readDouble();
        image = in.readString();
        points = in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(address);
        if (distance == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeDouble(distance);
        }
        dest.writeString(image);
        dest.writeInt(points);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Shop> CREATOR = new Parcelable.Creator<Shop>() {
        @Override
        public Shop createFromParcel(Parcel in) {
            return new Shop(in);
        }

        @Override
        public Shop[] newArray(int size) {
            return new Shop[size];
        }
    };
}
