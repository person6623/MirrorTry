package com.mirror.mirrortry.alladdress;

/**
 * Created by dllo on 16/6/24.
 */
public class AllAddressBean {
    private String name,address,number;

    public AllAddressBean(String name, String address, String number) {
        this.name = name;
        this.address = address;
        this.number = number;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
