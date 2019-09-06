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
    private RecyclerView rv;
    private Button b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rv = (RecyclerView) findViewById(R.id.rv);
        rv.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MainActivity.this);
        rv.setLayoutManager(linearLayoutManager);
        List<String> fixed = new ArrayList<>();
        List<String> variable = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            fixed.add(generateSentence(3, 3));
            variable.add(generateSentence(10, 20));
        }
        CustomRecyclerViewAdapter customRecyclerViewAdapter = new CustomRecyclerViewAdapter(MainActivity.this, fixed, variable);
        rv.setAdapter(customRecyclerViewAdapter);

        b = (Button) findViewById(R.id.b);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<String> fixed = new ArrayList<>();
                List<String> variable = new ArrayList<>();
                for (int i = 0; i < 30; i++) {
                    fixed.add(generateSentence(3, 3));
                    variable.add(generateSentence(10, 20));
                }
                customRecyclerViewAdapter.changeText(fixed, variable);
            }
        });
    }

    private String generateSentence(int maxWordSize, int maxSentenceWordCount) {
        char[] chars = new char[]{'a', 'b', 'c', 'd', ' ', 'e', 'f', 'g', 'h'};
        StringBuilder sentence = new StringBuilder();
        int word_size = new Random().nextInt(maxWordSize) + 1;
        int sentence_word_count = new Random().nextInt(maxSentenceWordCount) + 2;
        for (int i = 0; i < sentence_word_count; i++) {
            StringBuilder word = new StringBuilder();
            for (int j = 0; j < word_size; j++) {
                char c = chars[new Random().nextInt(chars.length)];
                word.append(c);
            }
            sentence.append(word);
            sentence.append(" ");
        }
        return sentence.toString();
    }

    private class CustomRecyclerViewAdapter extends RecyclerView.Adapter<CustomRecyclerViewAdapter.CustomViewHolder> {

        private final Context context;
        private List<String> fixed;
        private List<String> variable;

        public CustomRecyclerViewAdapter(Context context, List<String> fixed, List<String> variable) {
            super();
            this.context = context;
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
            holder.textViewInADifferentWay.setText(fixed.get(position), variable.get(position));
            holder.tv.setText("Item " + position + " top");
            holder.tv1.setText("Item " + position + " bottom");
        }

        @Override
        public int getItemCount() {
            return fixed.size();
        }

        public class CustomViewHolder extends RecyclerView.ViewHolder {

            private final TextViewInADifferentWay textViewInADifferentWay;
            private final TextView tv;
            private final TextView tv1;

            public CustomViewHolder(@NonNull View itemView) {
                super(itemView);
                View text_view_in_a_different_way = (View)
                        itemView.findViewById(R.id.text_view_in_a_different_way);
                textViewInADifferentWay = new TextViewInADifferentWay(context,
                        text_view_in_a_different_way);
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
