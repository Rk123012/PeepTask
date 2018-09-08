package com.example.mohsiul.peep.CameraInfo;

import java.util.ArrayList;

public class CameraLocationData {


    public  static ArrayList<CameraLocation> getCameraLocation(){
        ArrayList<CameraLocation> cameraInfos=new ArrayList<>();

        cameraInfos.add(new CameraLocation("mirpur -1", "Camera - 1", 23.7956, 90.3537,true,1,1));
        cameraInfos.add(new CameraLocation("mirpur -1", "Camera - 2", 23.7958, 90.3535,true,1,2));
        cameraInfos.add(new CameraLocation("mirpur -1", "Camera - 3", 23.7954, 90.3533,true,1,3));
      //  cameraInfos.add(new CameraLocation("mirpur -1", "Camera - 4", 23.7950, 90.3531,true,1));
      //  cameraInfos.add(new CameraLocation("gazipur", "Camera - 1", 23.9999, 90.4203,true,2));
      //  cameraInfos.add(new CameraLocation("gazipur", "Camera - 2", 23.9997, 90.4205,true,2));
      //  cameraInfos.add(new CameraLocation("gazipur", "Camera - 3", 23.9995, 90.4207,true,2));
      //  cameraInfos.add(new CameraLocation("gazipur", "Camera - 4", 23.9993, 90.4209,true,2));
     //   cameraInfos.add(new CameraLocation("sylhet", "Camera - 1", 22.776340, 90.399450,true,3));
     //   cameraInfos.add(new CameraLocation("sylhet", "Camera - 2", 22.876352, 90.399453,true,3));
     //   cameraInfos.add(new CameraLocation("sylhet", "Camera - 3", 22.676365, 90.399456,true,3));
      //  cameraInfos.add(new CameraLocation("sylhet", "Camera - 4", 22.776338, 90.399459,true,3));

        return cameraInfos;
    }





}
