package fr.marc.mareu.dataservice;

import java.util.List;

import fr.marc.mareu.model.Meeting;
import fr.marc.mareu.model.Users;

public class DummyMeetingApiService implements MeetingApiService {



    private  List<Meeting> meetingList = DummyMeetingApiGenerator.generateMeetings();

    @Override
    public List<Meeting> getMeetingList() {
        return meetingList;
    }

    @Override
    public void deleteMeeting(Meeting meeting) {
        meetingList.remove(meeting);
    }

    @Override
    public void bookMeeting(Meeting meeting) {
        meetingList.add(meeting);
    }


    public List<Users> userList =  DummyMeetingApiGenerator.generateUsers();

    @Override
    public List<Users> getUserList() {
        return userList;
    }


}
