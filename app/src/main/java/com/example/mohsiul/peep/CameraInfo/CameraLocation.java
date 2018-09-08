package com.example.mohsiul.peep.CameraInfo;

import com.google.android.gms.maps.model.LatLng;

public class CameraLocation {
    String title;
    String cameraNo;
    double lat;
    double lng;
    boolean selection;
    int groupId;
    int camId;

    public CameraLocation() {
    }

    public CameraLocation(String title, double lat, double lng) {
        this.title = title;
        this.lat = lat;
        this.lng = lng;
    }

    public CameraLocation(String title, String cameraNo, double lat, double lng,int groupId) {
        this.title = title;
        this.cameraNo = cameraNo;
        this.lat = lat;
        this.lng = lng;
        this.groupId=groupId;

    }

    public CameraLocation(String title, String cameraNo, double lat, double lng, boolean selection,int groupId,int camId) {
        this.title = title;
        this.cameraNo = cameraNo;
        this.lat = lat;
        this.lng = lng;
        this.selection = selection;
        this.groupId=groupId;
        this.camId=camId;

    }

    public boolean isSelection() {
        return selection;
    }

    public void setSelection(boolean selection) {
        this.selection = selection;
    }

    public String getCameraNo() {
        return cameraNo;
    }

    public void setCameraNo(String cameraNo) {
        this.cameraNo = cameraNo;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public LatLng getLatLng(){
        return new LatLng(lat,lng);

    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public int getCamId() {
        return camId;
    }

    public void setCamId(int camId) {
        this.camId = camId;
    }
}
