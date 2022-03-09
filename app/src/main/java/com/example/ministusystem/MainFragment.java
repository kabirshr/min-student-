package com.example.ministusystem;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class MainFragment extends Fragment {

    private static final String TAG = "StudentSystem";
    private static final String KEY_INDEX = "temp";
    private static final String COUNT = "count";
    private static final String STUDENTS = "array";
    private static final int REQUEST_CODE = 0;

    Button mAppendButton, mPrevButton, mNextButton, mDelButton, mEditButton;
    TextView mTotal, mCurrent, name_id, age_id, cc;
    EditText edit_name, edit_age;
    String newName, newAge;
    AlertDialog.Builder builder;
    int finalAge, f_total = 0, temp = -1;
    ArrayList<Students> mStudents = new ArrayList<>();
//    public ArrayList<Students> second_array = new ArrayList<Students>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = this.getArguments();
        if (getArguments() != null) {
            temp = bundle.getInt(KEY_INDEX,0);
            mStudents = bundle.getParcelableArrayList(STUDENTS);
            f_total = mStudents.size();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_main, container, false);

        mAppendButton = (Button) v.findViewById(R.id.append_button);
        mPrevButton = v.findViewById(R.id.prev_button);
        mNextButton = (Button) v.findViewById(R.id.next_button);
        mDelButton = (Button) v.findViewById(R.id.del_button);
        edit_age = (EditText) v.findViewById(R.id.edit_age);
        edit_name = (EditText) v.findViewById(R.id.edit_name);
        mCurrent = (TextView) v.findViewById(R.id.current_student);
        mTotal = (TextView) v.findViewById(R.id.total);
        name_id = (TextView) v.findViewById(R.id.view_name);
        age_id = (TextView) v.findViewById(R.id.view_age);
        cc = (TextView) v.findViewById(R.id.cc);
        builder = new AlertDialog.Builder(getContext());
        mEditButton = (Button) v.findViewById(R.id.edit_button);
        Log.d(TAG, "onCreate called");

        if(!mStudents.isEmpty()) {
            f_total = mStudents.size();
            mCurrent.setText(mStudents.get(temp%f_total).getName() + "," + mStudents.get(temp%f_total).getAge());
            mTotal.setText("Total: " + f_total);
            cc.setText(mStudents.get(temp % mStudents.size()).getCurr() + "");
        }

        if (savedInstanceState != null) {
            temp = savedInstanceState.getInt(KEY_INDEX, 0);
            f_total = savedInstanceState.getInt(COUNT, 0);
            mStudents = savedInstanceState.getParcelableArrayList(STUDENTS);
            if (temp < 0) {
                temp = f_total -1;
//                mCurrent.setText(mStudents.get(temp % f_total).getName() + "," + mStudents.get(temp % f_total).getAge());
            } else if (f_total == 0) {
                mCurrent.setText("");
            } else {
                mCurrent.setText(mStudents.get(temp % f_total).getName() + "," + mStudents.get(temp % f_total).getAge());
                mTotal.setText("Total: " + f_total);
            }

        }

        mAppendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edit_name.getText().toString().matches("") || edit_age.getText().toString().matches("")) {
                    Toast.makeText(getContext(),
                            "Both Text fields have to be filled!",
                            Toast.LENGTH_SHORT).show();
                } else {
                    newName = edit_name.getText().toString();
                    newAge = edit_age.getText().toString();
                    finalAge = Integer.parseInt(newAge);

                    edit_name.setText("");
                    edit_age.setText("");
                    mStudents.add(new Students(finalAge, newName));
                    mCurrent.setText(mStudents.get(f_total).getName() + " , " + mStudents.get(f_total).getAge());
                    f_total++;

                    temp = f_total;
                    mTotal.setText(f_total + " ");
                    cc.setText(mStudents.get(temp%f_total).getCurr()+ "");
                }
            }
        });


        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                next();
            }
        });


        mPrevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prev();
            }
        });

        mDelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(getContext())
                        .setTitle("Confirm")
                        .setMessage("Are you sure you  want to delete [" + mStudents.get(temp % mStudents.size()).getName() + "," + mStudents.get(temp % mStudents.size()).getAge() + "]")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                if (temp == mStudents.size()) {
                                    mStudents.remove(temp  % mStudents.size());
                                    prev();
                                    mTotal.setText(mStudents.size() + " ");
                                } else if (mStudents.size() != 0) {
                                    mStudents.remove(temp % mStudents.size());
                                    prev();
                                    mTotal.setText(mStudents.size() + " ");
                                } else {
                                    mTotal.setText("");
                                    mTotal.setText(mStudents.size() + " ");
                                }
                            }
                        })

                        .setNegativeButton(android.R.string.no, null)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }
        });


        mEditButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCurrent.getText().toString().equals("")) {
                    Toast.makeText(getContext(),
                            "Nothing to edit!",
                            Toast.LENGTH_SHORT).show();
                } else {

//               Intent intent = new Intent(MainActivity.this, StudentDetails.class);
//               Intent intent = StudentDetails.newIntent(getContext(), temp, mStudents);
//               startActivityForResult(intent, REQUEST_CODE);
                    StudentDetailsFragment nextFragment = new StudentDetailsFragment();
                    Bundle bundle = new Bundle();
                    bundle.putParcelableArrayList(STUDENTS, mStudents);
                    bundle.putInt(KEY_INDEX, temp);
                    nextFragment.setArguments(bundle);
                    getActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment_container, nextFragment, "LocateFragment")
                            .addToBackStack(null)
                            .commit();
                }
            }

        });

        return v;
    }


    public void prev() {
        int messageResId = 0;
        if (!mStudents.isEmpty()) {
            temp--;
            if (temp < 0) {
                temp = mStudents.size() - 1;
                mCurrent.setText(mStudents.get(temp % mStudents.size()).getName() + " , " + mStudents.get(temp % mStudents.size()).getAge());
                cc.setText(mStudents.get(temp%f_total).getCurr()+ "");
            } else {
                mCurrent.setText(mStudents.get(temp % mStudents.size()).getName() + " , " + mStudents.get(temp % mStudents.size()).getAge());
                cc.setText(mStudents.get(temp%f_total).getCurr()+ "");
            }

        } else {
            Toast.makeText(getContext(),
                    "Entries Empty!",
                    Toast.LENGTH_SHORT).show();
        }
    }

    public void next() {
        if (!mStudents.isEmpty()) {
            temp++;
            if (temp < mStudents.size()) {
                mCurrent.setText(mStudents.get(temp % mStudents.size()).getName() + " , " + mStudents.get(temp % mStudents.size()).getAge());
                cc.setText(mStudents.get(temp%f_total).getCurr()+ "");

            } else {
                temp = 0;
                mCurrent.setText(mStudents.get(temp % mStudents.size()).getName() + " , " + mStudents.get(temp % mStudents.size()).getAge());
                cc.setText(mStudents.get(temp%f_total).getCurr()+ "");

            }

        } else {
            Toast.makeText(getContext(),
                    "Entries Empty!",
                    Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        Log.i(TAG, "onSaveInstanceState");
        savedInstanceState.putInt(KEY_INDEX, temp);
        savedInstanceState.putInt(COUNT, f_total);
        savedInstanceState.putParcelableArrayList(STUDENTS, mStudents);
    }
}