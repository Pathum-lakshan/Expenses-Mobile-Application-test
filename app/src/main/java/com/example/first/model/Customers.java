package com.example.first.model;



import java.util.Arrays;

public class Customers {
    private int id;
    private String name;
    private String address;
    private String email;
    private String contact;
    private String gender;
    private byte[] phto;
    private String path;

    public Customers(int id, String name, String address, String email, String contact, String gender, byte[] phto, String path) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.email = email;
        this.contact = contact;
        this.gender = gender;
        this.phto = phto;
        this.path = path;
    }

    public Customers(String name, String address, String email, String contact, String gender, byte[] phto, String path) {
        this.name = name;
        this.address = address;
        this.email = email;
        this.contact = contact;
        this.gender = gender;
        this.phto = phto;
        this.path = path;
    }

    public Customers(int id, String name, String address, String email, String contact, String gender, byte[] phto) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.email = email;
        this.contact = contact;
        this.gender = gender;
        this.phto = phto;
    }

    public Customers(String name, String address, String email, String contact, String gender, byte[] phto) {
        this.name = name;
        this.address = address;
        this.email = email;
        this.contact = contact;
        this.gender = gender;
        this.phto = phto;
    }


    public Customers(String name, String address, String email, String contact) {
        this.name = name;
        this.address = address;
        this.email = email;
        this.contact = contact;
    }

    public Customers() {
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public byte[] getPhto() {
        return phto;
    }

    public void setPhto(byte[] phto) {
        this.phto = phto;
    }


    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public String toString() {
        return "Customers{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", email='" + email + '\'' +
                ", contact='" + contact + '\'' +
                ", gender='" + gender + '\'' +
                ", phto=" + Arrays.toString(phto) +
                ", path='" + path + '\'' +
                '}';
    }
}
