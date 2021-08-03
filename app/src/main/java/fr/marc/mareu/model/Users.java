package fr.marc.mareu.model;

public class Users {


    private String firstName;

    private String eMail;



    public Users(String firstName, String eMail) {
        this.firstName = firstName;
        this.eMail = eMail;
    }


    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String geteMail() {
        return eMail;
    }

    public void seteMail(String eMail) {
        this.eMail = eMail;
    }



}

