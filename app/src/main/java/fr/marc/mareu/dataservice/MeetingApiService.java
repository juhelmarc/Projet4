package fr.marc.mareu.dataservice;

import java.util.List;

import fr.marc.mareu.model.Meeting;
import fr.marc.mareu.model.User;

public interface MeetingApiService {

    List<Meeting> getMeetingList();

    void deleteMeeting(Meeting meeting);

    void bookMeeting(Meeting meeting);

    List<User> getUserList();

}
