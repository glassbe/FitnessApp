package com.example.fitnessapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.fragment.app.Fragment;

import com.example.fitnessapp.db.Entity.User;


/**
 * A simple {@link Fragment} subclass.
 */
public class _FragmentStartYourDataSetData extends Fragment {


    private EditText mFirstName;
    private EditText mSurName;
    private EditText mBirthdate;
    private Spinner mGender;
    private EditText mWeight;
    private EditText mHeight;
    private EditText mEnergyLevel;

    private Button mSetData;




    public _FragmentStartYourDataSetData() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout._fragment_start_your_data_set_data, container, false);



        mFirstName = view.findViewById(R.id.et_input_firstname);
        mSurName = view.findViewById(R.id.et_input_surname);
        mBirthdate = view.findViewById(R.id.et_input_birthdate);
        mGender = view.findViewById(R.id.s_input_gender);
        mWeight = view.findViewById(R.id.et_input_weight);
        mHeight = view.findViewById(R.id.et_input_height);
        mEnergyLevel = view.findViewById(R.id.sb_energyLevel);

        mSetData = view.findViewById(R.id.btn_setData);
        mSetData.setOnClickListener(v -> setData());

        // set selection of Spinner
        String[] arraySpinner = new String[] {
                "männlich",
                "weiblich",
                "divers"
        };
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item, arraySpinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mGender.setAdapter(adapter);

        return view;
    }

    private View.OnClickListener setData() {
        return null;
    }
}
