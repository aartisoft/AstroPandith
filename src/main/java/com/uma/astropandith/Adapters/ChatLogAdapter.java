package com.uma.astropandith.Adapters;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;


import com.uma.astropandith.Activitys.ConverstionHistory;
import com.uma.astropandith.Activitys.CoverstionActivity;
import com.uma.astropandith.Activitys.WebViewActivity;
import com.uma.astropandith.Activitys.WebviewHistoryActivity;
import com.uma.astropandith.Model.ChatHistory;
import com.uma.astropandith.R;

import java.util.List;

import static android.graphics.Color.rgb;


public class ChatLogAdapter extends RecyclerView.Adapter<ChatLogAdapter.ViewHolder> {



    private List<ChatHistory> mData;
    private LayoutInflater mInflater;
    private Context mContext;
    private Animation myAnim;
    private ProgressDialog progressBar;


    // data is passed into the constructor
    public ChatLogAdapter(Context mContext, List<ChatHistory> data) {
        this.mInflater = LayoutInflater.from(mContext);
        this.mData = data;
        this.mContext = mContext;

    }



    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)  {

        myAnim = AnimationUtils.loadAnimation(mContext, R.anim.push_left_in);

        Context context = parent.getContext();

        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(R.layout.chat_log_list, parent, false);

        progressBar = new ProgressDialog(mContext);
        progressBar.setMessage("Loading");
        progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressBar.setCancelable(true);

        return new ViewHolder(view);

    }

    // binds the data to the TextView in each row
    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(ViewHolder holder,  int position)  {

         String pname = mData.get(position).getUname();
         String chduration = mData.get(position).getChduration();
         String chdate = mData.get(position).getTime();
         String amount = mData.get(position).getPanditGetingAmount();
         String pid = mData.get(position).getPid();
         String uid = mData.get(position).getUid();
         String uname = mData.get(position).getUname();
         String date = mData.get(position).getDate();
        String chatStatusValue = mData.get(position).getChatStatusValue();
        String chatStatus = mData.get(position).getChatStatus();
        String customerStatus = mData.get(position).getCustomerStatus();




        String chatId = mData.get(position).getId();

        String feedback = mData.get(position).getFeedback();
        String rating = mData.get(position).getRating();


        String couponCode = mData.get(position).getCouponCode();
        String couponCodeStatus = mData.get(position).getCouponCodeStatus();


        if(couponCodeStatus.equals("1")){

            holder._LL_Coupon.setVisibility(View.VISIBLE);
            holder._couponCODETV.setText(couponCode);

        } else {

            holder._LL_Coupon.setVisibility(View.GONE);
            holder._couponCODETV.setText(couponCode);
        }

        if(chatStatusValue!=null) {

            if (chatStatusValue.equals("4")) {

//                holder._cStatus.setText("No Response");
//                holder._cStatus.setTextColor(rgb(128, 0, 0));

                holder._chatsendSMS.setVisibility(View.VISIBLE);


            } else {

                holder._chatsendSMS.setVisibility(View.GONE);

            }
        }

        holder._chatsendSMS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                holder._smsSent.setVisibility(View.VISIBLE);
                holder._chatsendSMS.setVisibility(View.GONE);

            }
        });

//            else if (chatStatusValue.equals("3")) {
//
//                holder._cStatus.setText("Chat Rejected By Astrologer");
//                holder._cStatus.setTextColor(rgb(128, 0, 0));
//
//            }
//        }


//
//            } else if(chatStatusValue.equals("5")){
//
//                holder._cStatus.setText("Chat Completed");
//                holder._cStatus.setTextColor(rgb(0,128,0));
//
//
//
//            } else if(chatStatusValue.equals("51")){
//
//                holder._cStatus.setText("Chat Completed");
//                holder._cStatus.setTextColor(rgb(0,128,0));
//
//
//
//            }else if(chatStatusValue.equals("9")){
//
//                holder._cStatus.setText("Chat Rejected By Astrologer");
//                holder._cStatus.setTextColor(rgb(128,0,0));
//
//
//
//            }else if(chatStatusValue.equals("8")){
//
//                holder._cStatus.setText("Chat Rejected By User");
//                holder._cStatus.setTextColor(rgb(128,0,0));
//
//
//
//            }
//        }


        if(customerStatus!=null) {

            if (customerStatus.equals("1")) {

                holder._customer_status.setText("NEW USER");
                holder._customer_status.setBackgroundResource(R.drawable.customer_statusnew);

            } else {

                holder._customer_status.setText("REPEATED USER");

            }
        }



        if(rating.equals("0")){

            holder._ratingbar.setVisibility(View.GONE);
        }
        holder._ratingbar.setRating(Float.parseFloat(rating));
        holder._cStatus.setText(chatStatus);

        holder._name_call_log.setText(uname.toUpperCase());
        holder._secns_call_log.setText(chduration+" Mins");
        holder._date_time_call_log.setText(date+", "+chdate);
        holder._amount_call_log.setText("\u20B9 "+amount);
        holder._customer_id.setText(uid);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String chatStatusValue = mData.get(position).getChatStatusValue();

               // progressBar.show();

                String s = mData.get(position).getCustomerStatus();

//                Toast.makeText(mContext, " "+ s, Toast.LENGTH_LONG).show();

                assert chatStatusValue != null;

                if (chatStatusValue.equals("5") || chatStatusValue.equals("51")|| chatStatusValue.equals("61")) {


                    int ccid = Integer.parseInt(chatId);

                    if(ccid>2975){

                        Intent intent = new Intent(mContext, ConverstionHistory.class);
                        intent.putExtra("uname", uname);
                        intent.putExtra("pname", pname);
                        intent.putExtra("pid", pid);
                        intent.putExtra("uid", uid);
                        intent.putExtra("chatId", chatId);
                        intent.putExtra("feedback", feedback);
                        intent.putExtra("rating", rating);
                        mContext.startActivity(intent);
                    }else {

                        Intent intent = new Intent(mContext, WebviewHistoryActivity.class);
                        intent.putExtra("uname", uname);
                        intent.putExtra("pname", pname);
                        intent.putExtra("pid", pid);
                        intent.putExtra("uid", uid);
                        intent.putExtra("chatId", chatId);
                        intent.putExtra("feedback", feedback);
                        intent.putExtra("rating", rating);
                        mContext.startActivity(intent);

                    }

                }
                else {

                    Toast.makeText(mContext, "No Data Found", Toast.LENGTH_SHORT).show();
                }
            }
        });

      //  holder.itemView.startAnimation(myAnim);

//
//        if(img!=null)
//            Picasso.get().load("http://49creativeworks.com/pavan/astro/Astratalk/admin/"+ img).into(holder._cat_image);
//        else holder._cat_image.setImageResource(R.drawable.ic_broken_image_black_24dp);



    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder  {

        private final RatingBar _ratingbar;

        private final TextView _name_call_log,_secns_call_log,_amount_call_log,_date_time_call_log;
        private final TextView _customer_id,_cStatus;
        private final Button _chatsendSMS;
        private LinearLayout _LL_Coupon;
        private  TextView _couponTV,_couponCODETV,_customer_status,_smsSent;


        ViewHolder(View itemView) {
            super(itemView);

            _name_call_log = (TextView) itemView.findViewById(R.id.name_call_log);
            _secns_call_log = (TextView) itemView.findViewById(R.id.secns_call_log);
            _date_time_call_log = (TextView) itemView.findViewById(R.id.date_time_call_log);
            _amount_call_log = (TextView) itemView.findViewById(R.id.amount_call_log);
            _customer_id = (TextView) itemView.findViewById(R.id.customer_id);
            _ratingbar = itemView.findViewById(R.id.ratingbar);
            _cStatus = (TextView) itemView.findViewById(R.id.cStatus);


            _couponTV = itemView.findViewById(R.id.couponTV);
            _couponCODETV = itemView.findViewById(R.id.couponCOETV);
            _LL_Coupon = itemView.findViewById(R.id.LL_Coupon);
            _customer_status = itemView.findViewById(R.id.customer_status);
            _chatsendSMS = itemView.findViewById(R.id.chatsendSMS);
            _smsSent = itemView.findViewById(R.id.smsSENT);



        }

    }


}
