package com.company.models;

public class User {
    private int id;
    private String name;
    private String surname;
    private String gender;

    public User() {}

    public User(String name, String surname, String gender) {
        this.name = name;
        this.surname = surname;
        this.gender = gender;
    }

    public User(int id, String name, String surname, String gender) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.gender = gender;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getGender() {
        return gender;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", gender='" + gender + '\'' +
                '}';
    }
}
