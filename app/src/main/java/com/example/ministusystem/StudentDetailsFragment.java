package com.example.ministusystem;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class StudentDetailsFragment extends Fragment {

    TextView mNameView, mAgeView;
    Button mSaveButton;
    CheckBox mathsC, englishC, computerC, musicC, paintingC;
    ArrayList<Students> mStudents = new ArrayList<Students>();
    private int final_current, checked = 0;

    private static final String STUDENT_DETAILS = "array";
    private static final String CURRENT_TEMP = "temp";
    private static final String TAG = "curr";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            final_current = bundle.getInt(CURRENT_TEMP, 0);
            mStudents = bundle.getParcelableArrayList(STUDENT_DETAILS);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_student_details, container, false);
        if (savedInstanceState != null) {
            final_current = savedInstanceState.getInt(CURRENT_TEMP, 0);
            mStudents = savedInstanceState.getParcelableArrayList(STUDENT_DETAILS);
        }

        mNameView = (TextView) v.findViewById(R.id.current_student_name);
        mAgeView = (TextView) v.findViewById(R.id.student_age);
        mathsC = (CheckBox) v.findViewById(R.id.mathsC);
        englishC = (CheckBox) v.findViewById(R.id.englishC);
        computerC = (CheckBox) v.findViewById(R.id.computerC);
        musicC = (CheckBox) v.findViewById(R.id.musicC);
        paintingC = (CheckBox) v.findViewById(R.id.paintingC);


        mNameView.setText(mStudents.get(final_current % mStudents.size()).getName());
        mAgeView.setText(String.valueOf(mStudents.get(final_current % mStudents.size()).getAge()));

        mathsC.setChecked(mStudents.get(final_current % mStudents.size()).isMathsC());
        englishC.setChecked(mStudents.get(final_current % mStudents.size()).isEnglishC());
        computerC.setChecked(mStudents.get(final_current % mStudents.size()).isComputerC());
        musicC.setChecked(mStudents.get(final_current % mStudents.size()).isMusicC());
        paintingC.setChecked(mStudents.get(final_current % mStudents.size()).isPaintingC());

        mSaveButton = (Button) v.findViewById(R.id.save_button);
        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mathsC.isChecked()) checked++;
                if (englishC.isChecked()) checked++;
                if (computerC.isChecked()) checked++;
                if (musicC.isChecked()) checked++;
                if (paintingC.isChecked()) checked++;

                mStudents.get(final_current % mStudents.size()).save_curriculums(
                        mathsC.isChecked(), englishC.isChecked(), computerC.isChecked(),
                        musicC.isChecked(), paintingC.isChecked());
                mStudents.get(final_current % mStudents.size()).setCc(checked);
                Toast.makeText(getContext(), "Changes Saved!", Toast.LENGTH_SHORT).show();
                MainFragment nextFragment = new MainFragment();
                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList(STUDENT_DETAILS, mStudents);
                bundle.putInt(CURRENT_TEMP, final_current);
                nextFragment.setArguments(bundle);
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, nextFragment, "LocateFragment")
                        .addToBackStack(null)
                        .commit();

            }
        });
        return v;
    }

    public static ArrayList<Students> newList(Intent result) {
        return result.getParcelableArrayListExtra(STUDENT_DETAILS);
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState){
        super.onSaveInstanceState(savedInstanceState);
        Log.i(TAG,"onSaveInstanceState");
        savedInstanceState.putInt(CURRENT_TEMP,final_current);
        savedInstanceState.putParcelableArrayList(STUDENT_DETAILS,mStudents);
    }

    @Override
    public void onStart(){
        super.onStart();
        Log.d(TAG,"onStart() called");
    }
    @Override
    public void onResume(){
        super.onResume();
        Log.d(TAG,"onResume Called");
    }
    @Override
    public void onPause(){
        super.onPause();
        Log.d(TAG,"onPause Called");
    }
    @Override
    public void onStop(){
        super.onStop();
        Log.d(TAG,"onStop Called");
    }
    @Override
    public void onDestroy(){
        super.onDestroy();
        Log.d(TAG,"onDestroy Called");
    }


}

