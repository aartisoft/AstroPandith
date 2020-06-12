package com.uma.astropandith.Adapters;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.uma.astropandith.Model.TutorialListModel;
import com.uma.astropandith.R;

import java.util.ArrayList;


public class TutorialListAdapter extends RecyclerView.Adapter<TutorialListAdapter.ViewHolder> {


    private ArrayList<TutorialListModel> mData;

    // data is passed into the constructor
    public TutorialListAdapter(ArrayList<TutorialListModel> data) {
        this.mData = data;

    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)  {

        Context context = parent.getContext();

        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(R.layout.tutorialtitle_list, parent, false);

        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position)  {


        String date = mData.get(position).getDate();
        String time = mData.get(position).getTime();
        String topicTitle = mData.get(position).getTopicTitle();


        holder.TutorialTitle.setText(topicTitle);


    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder  {

        private  TextView TutorialTitle;


        ViewHolder(View itemView) {
            super(itemView);

            TutorialTitle = itemView.findViewById(R.id.tutorial_Title);


        }
    }
}
