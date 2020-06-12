package com.uma.astropandith.Adapters;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.uma.astropandith.Model.TutorialModel;
import com.uma.astropandith.R;

import java.util.ArrayList;


public class TutorialAdapter extends RecyclerView.Adapter<TutorialAdapter.ViewHolder> {


    private ArrayList<TutorialModel> mData;

    // data is passed into the constructor
    public TutorialAdapter(ArrayList<TutorialModel> data) {
        this.mData = data;

    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)  {

        Context context = parent.getContext();

        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(R.layout.tutorial_steps_list, parent, false);

        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position)  {


        String date = mData.get(position).getDate();
        String time = mData.get(position).getTime();
        String topicTitle = mData.get(position).getTopicTitle();
        String step1 = mData.get(position).getStep1();
        String step2 = mData.get(position).getStep2();
        String step3 = mData.get(position).getStep3();
        String step4 = mData.get(position).getStep4();
        String step5 = mData.get(position).getStep5();
        String step6 = mData.get(position).getStep6();
        String step7 = mData.get(position).getStep7();
        String step8 = mData.get(position).getStep8();
        String step9 = mData.get(position).getStep9();



        holder.TutorialTitle.setText(topicTitle);


        holder.steps.setText(step1);
        holder.steps1.setText(step2);
        holder.steps2.setText(step3);
        holder.steps3.setText(step4);
        holder.steps4.setText(step5);
        holder.steps5.setText(step6);
        holder.steps6.setText(step7);
        holder.steps7.setText(step8);
        holder.steps8.setText(step9);




    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder  {

        private  TextView steps1,steps2,steps3,steps4,steps5,steps6,steps7,steps8;
        private  TextView TutorialTitle,steps;


        ViewHolder(View itemView) {
            super(itemView);

            TutorialTitle = itemView.findViewById(R.id.steps_name);
            steps = itemView.findViewById(R.id.steps);
            steps1 = itemView.findViewById(R.id.steps1);
            steps2 = itemView.findViewById(R.id.steps2);
            steps3 = itemView.findViewById(R.id.steps3);
            steps4 = itemView.findViewById(R.id.steps4);
            steps5 = itemView.findViewById(R.id.steps5);
            steps6 = itemView.findViewById(R.id.steps6);
            steps7 = itemView.findViewById(R.id.steps7);
            steps8 = itemView.findViewById(R.id.steps8);


        }
    }
}
