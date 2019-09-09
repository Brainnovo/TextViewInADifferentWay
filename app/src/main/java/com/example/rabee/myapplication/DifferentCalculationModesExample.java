package com.example.rabee.myapplication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatSeekBar;
import androidx.fragment.app.Fragment;

public class DifferentCalculationModesExample extends Fragment {

    private AppCompatSeekBar sb;
    public static final String DIFFERENT_CALCULATION_MODES_EXAMPLE = "different_calculation_modes_example";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = (View) inflater.inflate(R.layout.different_calculation_modes_example, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        View text_view_in_a_different_way_calculation_mode_single_line = (View) view.findViewById(R.id.text_view_in_a_different_way_calculation_mode_single_line);
        TextViewInADifferentWay textViewInADifferentWay = new TextViewInADifferentWay(getContext(), text_view_in_a_different_way_calculation_mode_single_line, TextViewInADifferentWay.CALCULATION_MODE_SINGLE_LINE);
        View text_view_in_a_different_way_calculation_mode_lines_total_height = (View) view.findViewById(R.id.text_view_in_a_different_way_calculation_mode_lines_total_height);
        TextViewInADifferentWay textViewInADifferentWay_ = new TextViewInADifferentWay(getContext(), text_view_in_a_different_way_calculation_mode_lines_total_height, TextViewInADifferentWay.CALCULATION_MODE_LINES_TOTAL_HEIGHT);
        sb = (AppCompatSeekBar) view.findViewById(R.id.sb);
        sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                TextView tv_variable = (TextView) text_view_in_a_different_way_calculation_mode_single_line.findViewById(R.id.tv_variable);
                tv_variable.setTextSize(i);
                TextView tv_variable_ = (TextView) text_view_in_a_different_way_calculation_mode_lines_total_height.findViewById(R.id.tv_variable);
                tv_variable_.setTextSize(i);
                textViewInADifferentWay.calculate();
                textViewInADifferentWay_.calculate();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }


}
