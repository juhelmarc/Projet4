 package fr.marc.mareu.ui.meeting;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentContainerView;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.ClipData;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;


import com.google.android.material.tabs.TabLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import fr.marc.mareu.DI.DI;
import fr.marc.mareu.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import fr.marc.mareu.dataservice.MeetingApiService;
import fr.marc.mareu.events.DeleteMeetingEvent;
import fr.marc.mareu.model.Meeting;
import fr.marc.mareu.model.Room;
import fr.marc.mareu.ui.booking.BookingActivity;
import fr.marc.mareu.ui.filters.AlertDialogRoomFragment;

import static fr.marc.mareu.ui.meeting.RecyclerViewMeetingFragment.newInstance;

 //TODO : singlechoicepicker pour les rooms
//TODO : date datepicker exploiter le format date
public class MeetingActivity extends AppCompatActivity    {


    private static final int REQUEST_CODE_GAME_ACTIVITY = 42;
    private MeetingApiService mApiService;
    private List<Meeting> mMeetingList;
    //private Bundle idRoom;
    private boolean roomIsFiltered;
    private boolean dateIsFiltered;
    private int buttonClicked;
    private Date dateFilter;
    private MyMeetingRecyclerViewAdapter mMeetingRecyclerViewAdapter;
    private int idRoom;
    private TabLayout fakeTab;
    private boolean isFiltered;
    private LinearLayoutManager mLinearLayoutManager;

    @BindView(R.id.toolbar2)
    Toolbar mToolbar;
    @BindView( R.id.recyclerview )
    RecyclerView mRecyclerView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_meeting );
        ButterKnife.bind( this );
        setSupportActionBar( mToolbar );
        mApiService = DI.getMeetingApiService();
        mMeetingList = mApiService.getMeetingList();
        mLinearLayoutManager = new LinearLayoutManager( this );
        mLinearLayoutManager.setOrientation( RecyclerView.VERTICAL );
        mRecyclerView.setLayoutManager( mLinearLayoutManager );
        mMeetingRecyclerViewAdapter = new MyMeetingRecyclerViewAdapter( mMeetingList );
        mRecyclerView.setAdapter( mMeetingRecyclerViewAdapter );




    }

   @Override
   public boolean onCreateOptionsMenu(Menu menu) {
       super.onCreateOptionsMenu( menu );
       getMenuInflater().inflate( R.menu.menu_filter, menu );

       return true;
   }


     @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if( R.id.room_filter == item.getItemId()) {
            new AlertDialogRoomFragment().show( getSupportFragmentManager(), "AlertDialogRoomFragment" );
            //Intent intent = new Intent();
            isFiltered = true;
            //isFiltered = intent.getBooleanExtra( "ROOM_IS_PICKED", false );
            mMeetingList.clear();
            mMeetingList.addAll( mApiService.getMeetingListFiltered() );
            mMeetingRecyclerViewAdapter.notifyDataSetChanged();
            return true;

        } else if (R.id.date_filter  == item.getItemId()) {
            initDatePicker( item );
            isFiltered = true;
            if(dateFilter != null) {
                mApiService.applyDateFilter( dateFilter );
                mMeetingList.clear();
                mMeetingList.addAll( mApiService.getMeetingListFiltered() );
                mMeetingRecyclerViewAdapter.notifyDataSetChanged();
            } else {
                isFiltered = false;
            }

            return true;

        } else if (R.id.clear_filter == item.getItemId()) {
            item.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    isFiltered = false;
                    mMeetingList.clear();
                    mMeetingList.addAll( mApiService.getMeetingList() );
                    mMeetingRecyclerViewAdapter.notifyDataSetChanged();
                    return true;
                }
            });
        }
        return false;
    }

    @OnClick(R.id.floatingActionButton2)
    void startBookingActivity() {
        BookingActivity.navigate( this );

    }
    private void initDatePicker(MenuItem item) {
        Calendar calendar = Calendar.getInstance();
        int y = calendar.get( Calendar.YEAR );
        int m = calendar.get( Calendar.MONTH );
        int d = calendar.get( Calendar.DAY_OF_MONTH );

        item.setOnMenuItemClickListener( new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        calendar.set( Calendar.DAY_OF_MONTH, view.getDayOfMonth() );
                        calendar.set( Calendar.MONTH, view.getMonth() );
                        calendar.set( Calendar.YEAR, view.getYear() );
                        dateFilter = new Date( calendar.getTimeInMillis() );
                    }
                };
                DatePickerDialog datePickerDialog = new DatePickerDialog( MeetingActivity.this, dateSetListener, y, m, d );
                datePickerDialog.show();
                return true;
            }

        } );

    }
    //private void initRecyclerview() {
    //    mLinearLayoutManager = new LinearLayoutManager( this );
    //    mLinearLayoutManager.setOrientation( RecyclerView.VERTICAL );
    //    mRecyclerView.setLayoutManager( mLinearLayoutManager );
    //    mMeetingRecyclerViewAdapter = new MyMeetingRecyclerViewAdapter( mMeetingList );
    //    mRecyclerView.setAdapter( mMeetingRecyclerViewAdapter );
    //    mMeetingRecyclerViewAdapter.notifyDataSetChanged();
//
    //    }
    private void initData(Boolean isFiltered) {

        mMeetingList = mApiService.getMeetingList();
        mLinearLayoutManager = new LinearLayoutManager( this );
        mLinearLayoutManager.setOrientation( RecyclerView.VERTICAL );
        mRecyclerView.setLayoutManager( mLinearLayoutManager );
        mMeetingRecyclerViewAdapter = new MyMeetingRecyclerViewAdapter( mMeetingList );
        mRecyclerView.setAdapter( mMeetingRecyclerViewAdapter );
        mMeetingRecyclerViewAdapter.notifyDataSetChanged();
    }
     /**
      * param event
      */
     @Subscribe
     public void onDeleteMeetingEvent(DeleteMeetingEvent event) {
         mApiService.deleteMeeting(event.meeting);
         initData(isFiltered);
     }

     @Override
     public void onResume() {
         super.onResume();
         initData(isFiltered);
     }

     @Override
     public void onStart() {
         super.onStart();
         EventBus.getDefault().register( this );

     }

     @Override
     public void onStop() {
         super.onStop();
         EventBus.getDefault().unregister( this );


     }




     //Todo : récupérer une référence de MyMeetingRecuclerViewAdapter + .notifyDataSetChange et ajouter la nouvelle liste dans l'adapter. Créer une fonction pour insérer une nouvelle liste

}