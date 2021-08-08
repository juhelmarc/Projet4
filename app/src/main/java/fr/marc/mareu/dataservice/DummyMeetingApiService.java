package fr.marc.mareu.dataservice;

import java.util.List;

import fr.marc.mareu.model.Meeting;
import fr.marc.mareu.model.User;

public class DummyMeetingApiService implements MeetingApiService {



    private  List<Meeting> meetingList = DummyMeetingApiGenerator.generateMeetings();
    private List<User> userList =  DummyMeetingApiGenerator.generateUser();

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



    @Override
    public List<User> getUserList() {
        return userList;
    }


}
