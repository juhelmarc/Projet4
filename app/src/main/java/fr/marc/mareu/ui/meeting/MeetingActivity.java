package fr.marc.mareu.ui.meeting;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;


import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import fr.marc.mareu.DI.DI;
import fr.marc.mareu.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import fr.marc.mareu.dataservice.MeetingApiService;
import fr.marc.mareu.events.DeleteMeetingEvent;
import fr.marc.mareu.model.Meeting;
import fr.marc.mareu.ui.booking.BookingActivity;

public class MeetingActivity extends AppCompatActivity {

    @BindView(R.id.filterView)
    ImageView mFilterView;
    @BindView(R.id.toolbar2)
    Toolbar mToolbar;
    @BindView(R.id.list_meeting)
    RecyclerView mRecyclerView;

    private MeetingApiService mApiService;
    private List<Meeting> mMeetingList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_meeting );

        ButterKnife.bind( this );

        setSupportActionBar( mToolbar );
        mApiService = DI.getMeetingApiService();
        mMeetingList = mApiService.getMeetingList();

        MyMeetingRecyclerViewAdapter mAdapter = new MyMeetingRecyclerViewAdapter( mMeetingList );
        mRecyclerView.setAdapter( mAdapter );
        mRecyclerView.setLayoutManager( new LinearLayoutManager( this ) );
        mRecyclerView.addItemDecoration( new DividerItemDecoration( getApplicationContext(), DividerItemDecoration.VERTICAL ) );
    }


    @OnClick(R.id.floatingActionButton2)
    void startBookingActivity() {
        BookingActivity.navigate( this );
        //Intent intent = new Intent(this, BookingActivity.class );
        //ActivityCompat.startActivity( this, intent, null );
        //startActivity(intent);

    }
    //Todo : récupérer une référence de MyMeetingRecuclerViewAdapter + .notifyDataSetChange et ajouter la nouvelle liste dans l'adapter. Créer une fonction pour insérer une nouvelle liste
    private void initList() {
        mMeetingList = mApiService.getMeetingList();
        mRecyclerView.setAdapter( new MyMeetingRecyclerViewAdapter( mMeetingList ) );
    }

    @Override
    public void onResume() {
        super.onResume();
        initList();
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
    /**
     * param event
     */
    @Subscribe
    public void onDeleteMeetingEvent(DeleteMeetingEvent event) {
        mApiService.deleteMeeting(event.meeting);
        initList();
    }
}