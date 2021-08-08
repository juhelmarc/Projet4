package fr.marc.mareu.model;

import java.util.Date;

public class Meeting {
    //todo model date (à mettre en place) faire la verif au moment du booking
    //todo regarder pour les mails l'autocomplete il faut un model users pour le faire (pour proposer une liste de name à selectionner)
    private Date date;

    private Date endDate;

    private String place;

    private String subjectMeeting;

    private String mailsList;

    //todo créer un modèle users et mettre firstname et email // remplacer year, month, day, hour à remplacer par date
    //todo utiliser date plutôt que long
    public Meeting (Date date, Date endDate, String room, String subjectMeeting, String mailsList) {
        this.date = date;
        this.endDate = date;
        this.place = room;
        this.subjectMeeting = subjectMeeting;
        this.mailsList = mailsList;

    }
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getMailsList() {
        return mailsList;
    }

    public void setMailsList(String mailsList) {
        this.mailsList = mailsList;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getSubjectMeeting() {
        return subjectMeeting;
    }

    public void setSubjectMeeting(String subjectMeeting) {
        this.subjectMeeting = subjectMeeting;
    }

    public String getMails() {
        return mailsList;
    }

    public void setMails(String mails) {
        this.mailsList = mails;
    }


}
