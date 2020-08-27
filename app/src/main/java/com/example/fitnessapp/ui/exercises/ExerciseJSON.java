package com.example.fitnessapp.ui.exercises;

import java.util.ArrayList;
import java.util.List;

public class ExerciseJSON {

    private int id;
    private String license_author;
    private String status;
    private String description;
    private String name;
    private String name_original;
    private String creation_date;
    private String uuid;
    private int license;
    private int category;
    private int language;
    private List<String> muscles;
    private List<String> muscles_secondary;
    private List<String> equipment;

//    private String firstName;
//    private int age;
//    private String mail;

//    public ExerciseJSON(String firstName, int age, String mail) {
//        this.firstName = firstName;
//        this.age = age;
//        this.mail = mail;
//    }


    public ExerciseJSON(
            int id,
            String license_author,
            String status,
            String description,
            String name,
            String name_original,
            String creation_date,
            String uuid,
            int license,
            int category,
            int language,
            List<String> muscles,
            List<String> muscles_secondary,
            List<String> equipment) {
        this.id = id;
        this.license_author = license_author;
        this.status = status;
        this.description = description;
        this.name = name;
        this.name_original = name_original;
        this.creation_date = creation_date;
        this.uuid = uuid;
        this.license = license;
        this.category = category;
        this.language = language;
        this.muscles = muscles;
        this.muscles_secondary = muscles_secondary;
        this.equipment = equipment;
    }
}

//{
//        "id":827,
//        "license_author":"test+++",
//        "status":"1",
//        "description":"Pentru brate mari si tari Sa dai in cap la dusmani Sa nu ai mila de niciunu Sa lovesti mai rau ca tunu",
//        "name":"",
//        "name_original":"",
//        "creation_date":"2020-08-12",
//        "uuid":"f685333b-4c27-4942-8245-c575ea25fac6",
//        "license":2,
//        "category":9,
//        "language":2,
//        "muscles":[],
//        "muscles_secondary":[],
//        "equipment":[]
//        }