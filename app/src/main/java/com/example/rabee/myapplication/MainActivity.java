package com.example.rabee.myapplication;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private final String TAG = MainActivity.class.getSimpleName();
    private RecyclerView rv_before;
    private RecyclerView rv_after;
    private Button b;
    private List<String> one = new ArrayList<>();
    private List<String> two = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        one.add("Hello, how is work?");
        two.add("Hello, everything is fine, thank you for asking.");
        one.add("?");
        two.add("How are you today?");
        one.add("I not feeling well!");
        two.add("Are you sick, you seem extremely tired?");
        one.add("I've got flu");
        two.add("Go home and please stay there until you feel better. " +
                "You don't want to spread your infection!");

        rv_before = (RecyclerView) findViewById(R.id.rv_before);
        rv_before.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManagerB = new LinearLayoutManager(MainActivity.this);
        rv_before.setLayoutManager(linearLayoutManagerB);

        rv_after = (RecyclerView) findViewById(R.id.rv_after);
        rv_after.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManagerA = new LinearLayoutManager(MainActivity.this);
        rv_after.setLayoutManager(linearLayoutManagerA);

        CustomRecyclerViewAdapter customRecyclerViewAdapterB = new CustomRecyclerViewAdapter(MainActivity.this, true, one, two);
        rv_before.setAdapter(customRecyclerViewAdapterB);

        CustomRecyclerViewAdapter customRecyclerViewAdapterA = new CustomRecyclerViewAdapter(MainActivity.this, false, one, two);
        rv_after.setAdapter(customRecyclerViewAdapterA);

        b = (Button) findViewById(R.id.b);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<String> fixed = new ArrayList<>();
                List<String> variable = new ArrayList<>();
                for (int i = 0; i < one.size(); i++) {
                    int oneI = new Random().nextInt(one.size());
                    fixed.add(one.get(oneI));
                    variable.add(two.get(oneI));
                }
                customRecyclerViewAdapterB.changeText(fixed, variable);
                customRecyclerViewAdapterA.changeText(fixed, variable);
            }
        });
    }

    private class CustomRecyclerViewAdapter extends RecyclerView.Adapter<CustomRecyclerViewAdapter.CustomViewHolder> {

        private final Context context;
        private List<String> fixed;
        private List<String> variable;
        private final boolean before;

        public CustomRecyclerViewAdapter(Context context, boolean before,
                                         List<String> fixed, List<String> variable) {
            super();
            this.context = context;
            this.before = before;
            this.fixed = fixed;
            this.variable = variable;
        }

        @NonNull
        @Override
        public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View layout = LayoutInflater.from(context).inflate(R.layout.recycler_view_item, parent, false);
            return new CustomViewHolder(layout);
        }

        @Override
        public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
            if(!before) { // after
                holder.textViewInADifferentWay.setText(fixed.get(position), variable.get(position));
            }else{ // before
                TextView tv_fixed = (TextView) holder.text_view_in_a_different_way.findViewById(R.id.tv_fixed);
                TextView tv_variable = (TextView) holder.text_view_in_a_different_way.findViewById(R.id.tv_variable);
                tv_fixed.setText(fixed.get(position));
                tv_variable.setText(variable.get(position));
            }
            holder.tv.setText("Item " + position + " top");
            holder.tv1.setText("Item " + position + " bottom");
        }

        @Override
        public int getItemCount() {
            return fixed.size();
        }

        public class CustomViewHolder extends RecyclerView.ViewHolder {

            private TextViewInADifferentWay textViewInADifferentWay;
            private final View text_view_in_a_different_way;
            private final TextView tv;
            private final TextView tv1;

            public CustomViewHolder(@NonNull View itemView) {
                super(itemView);
                text_view_in_a_different_way = (View)
                        itemView.findViewById(R.id.text_view_in_a_different_way);
                if(!before) { // after
                    textViewInADifferentWay = new TextViewInADifferentWay(context,
                            text_view_in_a_different_way);
                }
                tv = (TextView) itemView.findViewById(R.id.tv);
                tv1 = (TextView) itemView.findViewById(R.id.tv1);
            }
        }

        public void changeText(List<String> fixed, List<String> variable) {
            this.fixed = fixed;
            this.variable = variable;
            this.notifyDataSetChanged();
        }
    }

}
