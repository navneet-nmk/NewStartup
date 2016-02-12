package com.teenvan.newstartup.Model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by navneet on 10/02/16.
 */
public class Deal implements Parcelable {
    // Declaration of member variables
    private String dealStore;
    private String dealDescription;

    protected Deal(Parcel in) {
        dealStore = in.readString();
        dealDescription = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }

    public String getDealStore() {
        return dealStore;
    }

    public void setDealStore(String dealStore) {
        this.dealStore = dealStore;
    }

    public String getDealDescription() {
        return dealDescription;
    }

    public void setDealDescription(String dealDescription) {
        this.dealDescription = dealDescription;
    }

    public static final Parcelable.Creator<Deal> CREATOR = new Parcelable.Creator<Deal>() {
        @Override
        public Deal createFromParcel(Parcel in) {
            return new Deal(in);
        }

        @Override
        public Deal[] newArray(int size) {
            return new Deal[size];
        }
    };
}
