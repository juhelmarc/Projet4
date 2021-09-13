package fr.marc.mareu.dataservice;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import fr.marc.mareu.model.Meeting;
import fr.marc.mareu.model.Room;
import fr.marc.mareu.model.User;

public class DummyMeetingApiService implements MeetingApiService {



    private List<Meeting> meetingList = DummyMeetingApiGenerator.generateMeetings();
    private List<User> userList =  DummyMeetingApiGenerator.generateUser();
    private List<Room> roomList = DummyMeetingApiGenerator.generateRoom();


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

    @Override
    public List<String> getEmailInThisUserList(List<User> thisUserList) {
        List<String> emailList = new ArrayList<>();
        for (User user : thisUserList) {
            emailList.add(user.geteMail());
        }
        return emailList;
    }

    @Override
    public List<User> getUserListMeeting (String emailString) {
        List<User> userInMeeting = new ArrayList<>();
        List<String> emailList = Arrays.asList( emailString.replace(",", " ").split( " " ) );
        for (String email : emailList) {
            for (User user : userList) {
                if (email.equals( user.geteMail() )) {
                    userInMeeting.add( user );
                }
            }
        }
        if (userInMeeting.size() == 0) {
            return null;
        }
        return userInMeeting;
    }

    @Override
    public List<Room> getRoomList() {
        return roomList;
    }
//créer méthode pour maj la liste en focntion des filtres

    @Override
    public List<Meeting> getMeetingListFiltered() {
        List<Meeting> filteredMeetingList = new ArrayList<>();
        for(Meeting meeting : meetingList) {
            if(meeting.getFiltered()){
                filteredMeetingList.add(meeting);
            }
        }
        return filteredMeetingList;
    }

    @Override
    public List<String> getRoomNameList() {
        List<String> roomNameList = new ArrayList<>();
        for(Room room : roomList) {
            roomNameList.add(room.getRoomName());
        }
        return roomNameList;
    }

    @Override
    public void applyRoomFilter(int index) {
        Room roomPicked = getRoomList().get( index );
        for(Meeting meeting : meetingList) {
            if(roomPicked == meeting.getRoom()) {
                meeting.setFiltered( true );
            } else {
                meeting.setFiltered( false );
            }
        }


    }
    @Override
    public void  applyDateFilter(Date date) {
        for(Meeting meeting : meetingList)
            if(meeting.getDate().after( date )) {
               meeting.setFiltered( true );
        } else {
                meeting.setFiltered( false );
            }

    }

}
