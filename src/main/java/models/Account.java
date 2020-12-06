package models;

public class Account {
    private int id;
    private String name;
    private String phone;
    private int hall;

    public Account() {
    }

    public Account(String name, String phone, int hall) {
        this.name = name;
        this.phone = phone;
        this.hall = hall;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getHall() {
        return hall;
    }

    public void setHall(int hall) {
        this.hall = hall;
    }
}
