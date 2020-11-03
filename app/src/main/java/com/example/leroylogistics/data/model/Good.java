package com.example.leroylogistics.data.model;

public class Good {
    private int id;
    private String code;
    private String name;
    private String location;
    private String quantity;
    private String minimalRemain;

    public Good(int id, String code, String name, String location, String quantity, String minimalRemain) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.location = location;
        this.quantity = quantity;
        this.minimalRemain = minimalRemain;
    }

    public Good(String code, String name, String location, String quantity, String minimalRemain) {
        this.code = code;
        this.name = name;
        this.location = location;
        this.quantity = quantity;
        this.minimalRemain = minimalRemain;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public void setMinimalRemain(String minimalRemain) {
        this.minimalRemain = minimalRemain;
    }

    public int getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }

    public String getQuantity() {
        return quantity;
    }

    public String getMinimalRemain() {
        return minimalRemain;
    }
}
