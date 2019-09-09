package com.example.rabee.myapplication;

import android.os.Bundle;;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity {

    private final String TAG = MainActivity.class.getSimpleName();
    private FrameLayout fl;
    private Button b_recycler_view_example;
    private Button b_different_calculation_modes_example;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fl = (FrameLayout) findViewById(R.id.fl);
        b_recycler_view_example = (Button) findViewById(R.id.b_recycler_view_example);
        b_recycler_view_example.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                displayFragmentRecyclerViewExample();
            }
        });
        b_different_calculation_modes_example = (Button) findViewById(R.id.b_different_calculation_modes_example);
        b_different_calculation_modes_example.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DifferentCalculationModesExample differentCalculationModesExample = new DifferentCalculationModesExample();
                displayFragment(differentCalculationModesExample, DifferentCalculationModesExample.DIFFERENT_CALCULATION_MODES_EXAMPLE, fl.getId());
            }
        });
        displayFragmentRecyclerViewExample();
    }

    private void displayFragmentRecyclerViewExample(){
        RecyclerViewExample recyclerViewExample = new RecyclerViewExample();
        displayFragment(recyclerViewExample, RecyclerViewExample.RECYCLER_VIEW_EXAMPLE, fl.getId());
    }

    public void displayFragment(Fragment fragment, String name, int container_id) {
        try {
            if (fragment != null) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                fragmentTransaction.replace(container_id, fragment, name);
                fragmentTransaction.commit();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
