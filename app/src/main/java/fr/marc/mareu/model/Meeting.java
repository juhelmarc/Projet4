package fr.marc.mareu.model;

public class Meeting {
    //todo model date (à mettre en place) faire la verif au moment du booking
    //todo regarder pour les mails l'autocomplete il faut un model users pour le faire (pour proposer une liste de name à selectionner)
    private Long date;

    private Long endDate;

    private String place;

    private String subjectMeeting;

    private String mailsList;

    //todo créer un modèle users et mettre firstname et email // remplacer year, month, day, hour à remplacer par date
    public Meeting (long date, long endDate, String room, String subjectMeeting, String mailsList) {
        this.date = date;
        this.endDate = date;
        this.place = room;
        this.subjectMeeting = subjectMeeting;
        this.mailsList = mailsList;

    }
    public Long getDate() {
        return date;
    }

    public void setDate(Long date) {
        this.date = date;
    }
    public Long getEndDate() {
        return endDate;
    }

    public void setEndDate(Long endDate) {
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
