package fr.marc.mareu.dataservice;


import java.sql.ClientInfoStatus;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import fr.marc.mareu.model.Meeting;
import fr.marc.mareu.model.Users;

public abstract class DummyMeetingApiGenerator {



    public static List<Meeting> DUMMY_MEETING = Arrays.asList(
            new Meeting( 	1626825600000L, 1626827400000L,"Réunion C", "Peach", "test@test.com mail@mail.com testmail@testmail.com"  )

    );

    static List<Meeting> generateMeetings() {
        return new ArrayList<>(DUMMY_MEETING);
    }

    public static List<Users> DUMMY_USERS = Arrays.asList(
            new Users ("Alexandre" , "alexandre@mareu.com"),
            new Users ("Alexandra" , "alexandra@mareu.com"),
            new Users ("Clément" , "clément@mareu.com"),
            new Users ("Clémentine" , "clémentine@mareu.com"),
            new Users ("Damien" , "damien@mareu.com"),
            new Users ("Dorine" , "dorine@mareu.com")
    );
    static List<Users> generateUsers() {
        return new ArrayList<>(DUMMY_USERS);
    }

}
