package com.test.sybo;

public class HelperClass {

    String deviceid, devicename, cpucores, memory, os;

    public String getID() {
        return deviceid;
    }

    public void setName(String deviceid) {
        this.deviceid = deviceid;
    }

    public String getDevicename() {
        return devicename;
    }

    public void setDevicename(String devicename) {
        this.devicename = devicename;
    }

    public String getCpucores() {
        return cpucores;
    }

    public void setCpucores(String cpucores) {
        this.cpucores = cpucores;
    }

    public String getMemory() {
        return memory;
    }

    public void setMemory(String memory) {
        this.memory = memory;
    }


    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os;
    }



    public HelperClass(String deviceid, String devicename, String cpucores, String memory, String os) {

        this.deviceid = deviceid;
        this.devicename = devicename;
        this.cpucores = cpucores;
        this.memory = memory;
        this.os = os;
    }

    public HelperClass() {
    }
}
