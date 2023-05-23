package com.example.restservice.model;

import com.example.restservice.entity.ReaderEntity;


public class ReaderModel {
    private Long id;
    private String name;
    private String surname;
    private String secondName;

    public static ReaderModel toModel(ReaderEntity entity) {
        ReaderModel model = new ReaderModel();
        model.setId(entity.getId());
        model.setName(entity.getName());
        model.setSurname(entity.getSurname());
        model.setSecondName(entity.getSecondName());
        return model;
    }

    public ReaderModel() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

}
