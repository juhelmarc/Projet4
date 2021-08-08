package fr.marc.mareu.dataservice;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import fr.marc.mareu.model.Meeting;
import fr.marc.mareu.model.User;

public abstract class DummyMeetingApiGenerator {

    private Date endDate = new Date(1628084345889L + 45 * 60 * 1000);


    public static List<Meeting> DUMMY_MEETING = Arrays.asList(
            new Meeting( new Date(1628084345889L ), new Date (1628084345889L + 45 * 60 * 1000),"Réunion C", "Peach", "test@test.com mail@mail.com testmail@testmail.com"  )

    );

    static List<Meeting> generateMeetings() {
        return new ArrayList<>(DUMMY_MEETING);
    }

    public static List<User> DUMMY_USERS = Arrays.asList(
            new User ("Alexandre" , "alexandre@mareu.com"),
            new User ("Alexandra" , "alexandra@mareu.com"),
            new User ("Clément" , "clément@mareu.com"),
            new User ("Clémentine" , "clémentine@mareu.com"),
            new User ("Damien" , "damien@mareu.com"),
            new User ("Dorine" , "dorine@mareu.com")
    );
    static List<User> generateUser() {
        return new ArrayList<>(DUMMY_USERS);
    }

}
