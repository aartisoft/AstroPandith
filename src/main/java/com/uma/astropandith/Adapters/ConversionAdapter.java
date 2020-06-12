package com.uma.astropandith.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.uma.astropandith.Model.Chat;
import com.uma.astropandith.R;

import java.util.List;

public class ConversionAdapter extends RecyclerView.Adapter<ConversionAdapter.ViewHolder> {

    public static  final int MSG_TYPE_LEFT = 0;
    public static  final int MSG_TYPE_RIGHT = 1;

    private Context mContext;
    private List<Chat> mChat;
    private String myId;


    public ConversionAdapter(Context mContext, List<Chat> mChat,String myId){
        this.mChat = mChat;
        this.mContext = mContext;
        this.myId = myId;
    }

    @NonNull
    @Override
    public ConversionAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == MSG_TYPE_RIGHT) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.chat_item_right, parent, false);
            return new ConversionAdapter.ViewHolder(view);
        } else {
            View view = LayoutInflater.from(mContext).inflate(R.layout.chat_item_left, parent, false);
            return new ConversionAdapter.ViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull ConversionAdapter.ViewHolder holder, int position) {

        Chat chat = mChat.get(position);

        holder.show_message.setText(chat.getMessage());



        if (position == mChat.size()-1){
//            if (chat.isIsseen()){
//                holder.txt_seen.setText("Seen");
//            } else {
//                holder.txt_seen.setText("Delivered");
//            }
        } else {
            holder.txt_seen.setVisibility(View.GONE);
        }

    }

    @Override
    public int getItemCount() {
        return mChat.size();
    }

    public  class ViewHolder extends RecyclerView.ViewHolder{

        public TextView show_message;
        public ImageView profile_image;
        public TextView txt_seen;

        public ViewHolder(View itemView) {
            super(itemView);

            show_message = itemView.findViewById(R.id.show_message);
            profile_image = itemView.findViewById(R.id.profile_image);
            txt_seen = itemView.findViewById(R.id.txt_seen);
        }
    }

    @Override
    public int getItemViewType(int position) {


        if (mChat.get(position).getSender().equals(myId)){
            return MSG_TYPE_RIGHT;
        } else {
            return MSG_TYPE_LEFT;
        }
    }
}