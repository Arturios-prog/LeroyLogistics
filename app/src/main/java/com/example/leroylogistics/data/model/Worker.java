package com.example.leroylogistics.data.model;
/**
 * Данный класс является объектом сотрудника. Содержит в себе 4 поля: id, код, ФИО, а также
 * уровень доступа
 */
public class Worker {

    private int id;
    private String code;
    private String name;
    private String level;

    public Worker(){

    }

    public Worker(int id, String code, String name, String level) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.level = level;
    }

    public Worker(String code, String name, String level) {
        this.code = code;
        this.name = name;
        this.level = level;
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

    public String getLevel() {
        return level;
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

    public void setLevel(String level) {
        this.level = level;
    }
}
