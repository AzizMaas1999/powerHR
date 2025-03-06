package tn.esprit.powerHR.models;


public class Holiday {

    private String name;
    private String date;

    public Holiday(String name, String date) {
        this.name = name;
        this.date = date;
    }

    public String getName() {
        return name;
    }


    public String getDate() {
        return date;
    }

}

