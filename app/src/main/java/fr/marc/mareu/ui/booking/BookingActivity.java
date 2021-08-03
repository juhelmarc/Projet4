package fr.marc.mareu.ui.booking;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.MultiAutoCompleteTextView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import fr.marc.mareu.DI.DI;
import fr.marc.mareu.R;
import fr.marc.mareu.dataservice.MeetingApiService;
import fr.marc.mareu.model.Meeting;
import fr.marc.mareu.model.Users;

public class BookingActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    @BindView(R.id.datePickerButton)
    Button mDateButton;
    @BindView( R.id.hour )
    EditText mHour;
    @BindView( R.id.duration )
    EditText mDuration;
    @BindView( R.id.resum_date )
    TextView mResumDate;
    @BindView( R.id.mail_autocomplete )
    MultiAutoCompleteTextView mMail;
    @BindView( R.id.room_spinner )
    Spinner mMeetingRoom;
    @BindView( R.id.subject )
    EditText mSubject;
    @BindView( R.id.book )
    Button mBookButton;

    private Meeting meetingToBook;
    private MeetingApiService mApiService;
    private String[] userList = {"alexandre@mareu.com", "toto@mareu.com", "benoit@mareu.com", "qsdqsd@mareu.com", "cdsf@mareu.com", "zer@mareu.com", "oper@mareu.com"};

    private String[] meetingRoomList = {"Room A ", "Room B ", "Room C","Room D", "Room E", "Room F", "Room G", "Room H", "Room I", "Room J" };

    private Long currentDatePicker;
    private Long endCurrentDate;

    private Long datePickedMilli;
    private Long endDatePickedMilli;
    private Long duration;

    private String currentDateFormated;
    private String currentTimeFormated;
    private String endCurrentTimeFormated;
    private String datePickedFormated;
    private String timePickedFormated;
    private String endTimePickedFormated;

    private DatePickerDialog mDatePickerDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_booking );
        ButterKnife.bind( this );
        mApiService = DI.getMeetingApiService();
        //userList = mApiService.getUserList();


//
       // ArrayAdapter userListAdapter = new ArrayAdapter( this, android.R.layout.simple_list_item_1, userList );
       // mMail.setAdapter( userListAdapter );
       // mMail.setTokenizer( new MultiAutoCompleteTextView.CommaTokenizer() );
       // mMail.setThreshold( 1 );
       // // MeetingRoom spinner
       // ArrayAdapter<CharSequence> meetingRoomListAdapter = new ArrayAdapter( this, android.R.layout.simple_spinner_item, meetingRoomList );
       // meetingRoomListAdapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item );
       // mMeetingRoom.setAdapter( meetingRoomListAdapter );
       // mMeetingRoom.setOnItemSelectedListener( this );



        currentDatePicker = new Date().getTime();
        currentDateFormated = formatDate( currentDatePicker );
        mDateButton.setText( currentDateFormated );
        currentTimeFormated = formatHour( currentDatePicker );
        mHour.setText( currentTimeFormated );
        // Default value 45 min
        mDuration.setText("45");
        duration = Long.parseLong( mDuration.getText().toString() ) * 60L * 1000L;
        endCurrentDate = currentDatePicker + duration;
        endCurrentTimeFormated = formatHour( endCurrentDate );
        mResumDate.setText( "Date : " + currentDateFormated + " Start : " + currentTimeFormated + " End : " + endCurrentTimeFormated );

        Calendar calendar = Calendar.getInstance();
        int y = calendar.get( Calendar.YEAR );
        int m = calendar.get( Calendar.MONTH );
        int d = calendar.get( Calendar.DAY_OF_MONTH );
        int h = calendar.get(Calendar.HOUR_OF_DAY);
        int min = calendar.get(Calendar.MINUTE);

        mDateButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        //C'est au niveau du onDateSet que nous récupérons les entrées de l'utilisateur et qu'il faut les set à notre calendar pour pouvoir exploiter les valeurs
                        //set value to calendar
                        calendar.set(Calendar.DAY_OF_MONTH, view.getDayOfMonth());
                        calendar.set(Calendar.MONTH, view.getMonth());
                        calendar.set(Calendar.YEAR, view.getYear());
                        //récupérer la date de clalendar en milliseconde
                        datePickedMilli = calendar.getTimeInMillis() ;
                        duration = Long.parseLong( mDuration.getText().toString()  ) * 60 * 1000;
                        endDatePickedMilli = datePickedMilli + duration ;

                        datePickedFormated = formatDate( datePickedMilli );
                        timePickedFormated = formatHour( datePickedMilli );
                        endTimePickedFormated = formatHour(endDatePickedMilli);

                        mDateButton.setText( datePickedFormated );
                        mHour.setText(timePickedFormated);
                        mResumDate.setText( "Date : " + datePickedFormated + " Start : " + timePickedFormated + " End : " + endTimePickedFormated );
                    }
                };
                // valorisation de mDatePickerDialog par l'instance de DatePickerDialog avec en paramètre l'activité courrante, le dateSetListner et l'année, le mois et le jour
                mDatePickerDialog = new DatePickerDialog( BookingActivity.this, dateSetListener, y, m, d );
                mDatePickerDialog.getDatePicker().setMinDate( new Date().getTime() );
                mDatePickerDialog.show();
            }
        } );
        mHour.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {
                    @RequiresApi(api = Build.VERSION_CODES.M)
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        calendar.set(Calendar.HOUR_OF_DAY, view.getHour());
                        calendar.set(Calendar.MINUTE, view.getMinute());
                        datePickedMilli = calendar.getTimeInMillis();
                        duration = Long.parseLong( mDuration.getText().toString()  ) * 60 * 1000;
                        endDatePickedMilli = datePickedMilli + duration;
                        timePickedFormated = formatHour( datePickedMilli );
                        endTimePickedFormated = formatHour(endDatePickedMilli);
                        mHour.setText( timePickedFormated );
                        mResumDate.setText( "Date : " + datePickedFormated + " Start : " + timePickedFormated + " End : " + endTimePickedFormated );
                    }
                };
                TimePickerDialog timePickerDialog = new TimePickerDialog(BookingActivity.this, timeSetListener, h, min, true);
                timePickerDialog.show();
            }
        } );
        mDuration.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String textDuration = mDuration.getText().toString();
                mDuration.setText( textDuration  );
                duration = Long.parseLong( textDuration ) * 60L * 1000L;
                endDatePickedMilli = calendar.getTimeInMillis() + duration;
                datePickedFormated = formatDate( calendar.getTimeInMillis() );
                timePickedFormated = formatHour(calendar.getTimeInMillis() );
                endTimePickedFormated = formatHour( endDatePickedMilli );
                mResumDate.setText( "Date : " + datePickedFormated + " Start : " + timePickedFormated + " End : " + endTimePickedFormated );
            }

        } );




    }
    // method for format our dateMillisecond in string with SimpleDateFormat
    public String formatDate(Long dateMilli) {
        String format = "MMM dd.yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format, Locale.FRANCE);
        return simpleDateFormat.format( dateMilli );
    }
    public String formatHour(Long timeMilli) {
        String format = "kk:mm";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format, Locale.FRANCE);
        return simpleDateFormat.format(timeMilli);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: {
                finish();
                return true;
            }
        }
        return super.onOptionsItemSelected( item );
    }


    //TODO récupérer les valeurs du date picker et set cela au bouton (avec par défaut la date du jour)
    //TODO ajouter le timePicker et transformer toutes les données en Long date

    //TODO utiliser  / simpledateformat / dateFormat à la place de makeDateString() et getFormatMonth()

  @OnClick(R.id.book)
    public void setMeetingToBook() {
        //TODO :Enable when all input fields are complete
        Calendar calendar = Calendar.getInstance();
        meetingToBook = new Meeting(
                datePickedMilli,
                endDatePickedMilli,
                mMeetingRoom.getSelectedItem().toString(),
                mSubject.getText().toString(),
                mMail.getText().toString()
        );
        mApiService.bookMeeting(meetingToBook);
        Toast.makeText( this,  "MeetingList = " + mApiService.getMeetingList().size(), Toast.LENGTH_SHORT ).show();

        finish();
    }
    public static void navigate(FragmentActivity activity) {
        Intent intent = new Intent(activity, BookingActivity.class);
        ActivityCompat.startActivity(activity, intent, null);
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String text = parent.getItemAtPosition( position ).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}