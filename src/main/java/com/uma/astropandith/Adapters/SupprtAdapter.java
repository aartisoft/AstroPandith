package com.uma.astropandith.Adapters;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.uma.astropandith.Activitys.ViewImageActivity;
import com.uma.astropandith.Model.Chat;
import com.uma.astropandith.R;

import java.util.List;

import static android.content.Context.CLIPBOARD_SERVICE;

public class SupprtAdapter extends RecyclerView.Adapter<SupprtAdapter.ViewHolder> {

    public static  final int MSG_TYPE_LEFT = 0;
    public static  final int MSG_TYPE_RIGHT = 1;

    private Context mContext;
    private List<Chat> mChat;
    private String myId;


    public SupprtAdapter(Context mContext, List<Chat> mChat, String myId){
        this.mChat = mChat;
        this.mContext = mContext;
        this.myId = myId;
    }

    @NonNull
    @Override
    public SupprtAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == MSG_TYPE_RIGHT) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.chat_support_item_right, parent, false);
            return new SupprtAdapter.ViewHolder(view);
        } else {
            View view = LayoutInflater.from(mContext).inflate(R.layout.chat_support_item_left, parent, false);
            return new SupprtAdapter.ViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull SupprtAdapter.ViewHolder holder, int position) {

        Chat chat = mChat.get(position);

        String date = chat.getDate();
        String attatch_image = chat.getImage();
        String time = chat.getTime();
        String message = chat.getMessage();



        if (date != null) {

            holder.date.setText(date);
            holder.date.setVisibility(View.VISIBLE);

        }


        if(attatch_image!=null)

            if (!attatch_image.isEmpty()&& !attatch_image.equals("null")) {

                Picasso.get().load(attatch_image).into(holder.attachImage);
                holder.attachImage.setVisibility(View.VISIBLE);
                holder.time.setVisibility(View.VISIBLE);
                holder.time.setText(time);
                holder.show_message.setVisibility(View.GONE);
                holder.attachImage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Intent intent = new Intent(mContext, ViewImageActivity.class);
                       // intent.putExtra("attatch_image","https://meraastro.com/admin/"+attatch_image);
                        intent.putExtra("attatch_image","https://meraastro.in/admin/"+attatch_image);
                        mContext.startActivity(intent);

                    }
                });

            }


        if(message!=null)
            if(!message.isEmpty()&&!message.equals("null")){
                holder.show_message.setText(message);
                holder.time.setText(time);
                holder.show_message.setVisibility(View.VISIBLE);
                holder.time.setVisibility(View.VISIBLE);
                holder.attachImage.setVisibility(View.GONE);

                holder.show_message.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {


                        ClipboardManager manager = (ClipboardManager) mContext.getSystemService(CLIPBOARD_SERVICE);
                        ClipData clipData = ClipData.newPlainText("text",  holder.show_message.getText());
                        manager.setPrimaryClip(clipData);

                        Toast.makeText(mContext, "Text Copied", Toast.LENGTH_SHORT).show();

                        return false;
                    }
                });

            }



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
        public ImageView attachImage;
        public TextView txt_seen;
        public TextView date;
        public TextView time;

        public ViewHolder(View itemView) {
            super(itemView);

            show_message = itemView.findViewById(R.id.show_message);
            profile_image = itemView.findViewById(R.id.profile_image);
            attachImage = itemView.findViewById(R.id.attach_image);
            txt_seen = itemView.findViewById(R.id.txt_seen);
            date = itemView.findViewById(R.id.date);
            time = itemView.findViewById(R.id.time);

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