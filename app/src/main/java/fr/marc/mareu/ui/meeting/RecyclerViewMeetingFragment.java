package fr.marc.mareu.ui.meeting;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import fr.marc.mareu.DI.DI;
import fr.marc.mareu.R;
import fr.marc.mareu.dataservice.MeetingApiService;
import fr.marc.mareu.events.DeleteMeetingEvent;
import fr.marc.mareu.model.Meeting;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RecyclerViewMeetingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */


public class RecyclerViewMeetingFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private MeetingApiService mApiService;
    private boolean isFiltered = false;
    private List<Meeting> mMeetingList;

    private static final String FILTER_KEY= "filter_key";

    private boolean mParam1;


    public RecyclerViewMeetingFragment() {
        // Required empty public constructor
    }
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param isFiltered Parameter 1.
     * @return A new instance of fragment MeetingFragment.
     */

    public static RecyclerViewMeetingFragment newInstance(boolean isFiltered) {
        RecyclerViewMeetingFragment fragment = new RecyclerViewMeetingFragment();
        Bundle bundle = new Bundle();
        bundle.putBoolean( FILTER_KEY , isFiltered );
        fragment.setArguments( bundle );
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        mApiService = DI.getMeetingApiService();
        isFiltered = getArguments().getBoolean( FILTER_KEY );
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate( R.layout.activity_meeting, container, false );
        Context context = view.getContext();
        mRecyclerView = (RecyclerView) view;
        mRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        initList();
        return view;
    }
    private void initList(){
        if(!isFiltered){
            mMeetingList = mApiService.getMeetingList();
        } else {
            mMeetingList = mApiService.getMeetingListFiltered();
        }
        mRecyclerView.setAdapter( new MyMeetingRecyclerViewAdapter( mMeetingList ));
    }



}