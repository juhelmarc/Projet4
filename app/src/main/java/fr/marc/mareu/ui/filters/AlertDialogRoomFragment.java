package fr.marc.mareu.ui.filters;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

import fr.marc.mareu.DI.DI;
import fr.marc.mareu.R;
import fr.marc.mareu.dataservice.MeetingApiService;
import fr.marc.mareu.model.Room;
import fr.marc.mareu.ui.meeting.MeetingActivity;
import fr.marc.mareu.ui.meeting.MyMeetingRecyclerViewAdapter;

public class AlertDialogRoomFragment extends DialogFragment {


    public static AlertDialogRoomFragment newInstance(int num) {
        AlertDialogRoomFragment dialogFragment = new AlertDialogRoomFragment();
        //Bundle bundle = new Bundle();
        //bundle.putInt("NUM", num);
        //dialogFragment.setArguments( bundle );
        return dialogFragment;
    }

    private static final String ROOM_IS_PICKED = "ROOM_IS_PICKED";
    private MeetingApiService mApiService;
    private List<Room> roomList;
    private List<String> roomName;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        mApiService = DI.getMeetingApiService();
        roomList = mApiService.getRoomList();
        roomName = mApiService.getRoomNameList();

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle( R.string.chose_meeting_rooms)
                .setSingleChoiceItems( roomName.toArray(new String[roomName.size()]),-1, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // The 'which' argument contains the index position
                        // of the selected item
                        mApiService.applyRoomFilter( which );
                        //Intent intent = new Intent();
                        //intent.putExtra(ROOM_IS_PICKED, true  );
                        //registerForActivityResult( Fragment.SavedState, intent );
                        //listener.onDialogPositiveClick(AlertDialogRoomFragment.this);

                        dialog.dismiss();
                    }
        });
        builder.setNegativeButton( "Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //listener.onDialogNegativeClick( AlertDialogRoomFragment.this);
                //Intent intent = new Intent();
                //intent.putExtra(ROOM_IS_PICKED, false  );
                dialog.dismiss();
            }
        } );
        return builder.create();
    }

//public interface NoticeDialogListener {
    //    boolean onDialogPositiveClick(DialogFragment dialog );
    //    void onDialogNegativeClick(DialogFragment dialog);
    //}
    //NoticeDialogListener listener;

    // Override the Fragment.onAttach() method to instantiate the NoticeDialogListener
    //@Override
    //public void onAttach(Context context) {
    //    super.onAttach(context);
    //    // Verify that the host activity implements the callback interface
    //    try {
    //        // Instantiate the NoticeDialogListener so we can send events to the host
    //        listener = (NoticeDialogListener) context;
    //    } catch (ClassCastException e) {
    //        // The activity doesn't implement the interface, throw exception
    //        throw new ClassCastException("MeetingActivity"
    //                + " must implement NoticeDialogListener");
    //    }
    //}
}
