package com.uma.astropandith.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.uma.astropandith.Model.CallHistory;
import com.uma.astropandith.R;


import java.util.List;

import static android.graphics.Color.rgb;


public class CallLogAdapter extends RecyclerView.Adapter<CallLogAdapter.ViewHolder> {


    private List<CallHistory> mData;
    private LayoutInflater mInflater;
    private Context mContext;
    private Animation myAnim;


    // data is passed into the constructor
    public CallLogAdapter(Context mContext, List<CallHistory> data) {
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

        View view = inflater.inflate(R.layout.call_log_list, parent, false);


        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder,  int position)  {

         String pname = mData.get(position).getPname();
         String chduration = mData.get(position).getCaduration();
         String chdate = mData.get(position).getCatime();
         String amount = mData.get(position).getPanditGetingAmount();
         String uname = mData.get(position).getUname();
         String callStatus = mData.get(position).getCallStatus();
         String cadate = mData.get(position).getCadate();
        String rating = mData.get(position).getRating();
        String feedback = mData.get(position).getFeedback();

        String customerStatus = mData.get(position).getCustomerStatus();



        String couponCode = mData.get(position).getCouponCode();
        String couponCodeStatus = mData.get(position).getCouponCodeStatus();

        if(couponCodeStatus.equals("1")){

            holder._LL_Coupon.setVisibility(View.VISIBLE);
            holder._couponCODETV.setText(couponCode);

        } else {

            holder._LL_Coupon.setVisibility(View.GONE);
            holder._couponCODETV.setText(couponCode);
        }


        if(customerStatus!=null) {

            if (customerStatus.equals("1")) {

                holder._customer_status.setText("NEW USER");
                holder._customer_status.setBackgroundResource(R.drawable.customer_statusnew);


            } else {

                holder._customer_status.setText("REPEATED USER");

            }

        }



        final String Id = mData.get(position).getId();

        if(rating!=null){

            holder._ratingbarcall.setRating(Float.parseFloat(rating));

        }

        if (feedback != null) {

            if (!feedback.isEmpty()) {

                holder._feedback_call_log.setText(feedback);
                holder._feedback_call_log.setVisibility(View.VISIBLE);
                holder._feedCT.setVisibility(View.VISIBLE);

            }
        }

        holder._name_call_log.setText(uname.toUpperCase());
        holder._secns_call_log.setText(chduration+" Sec");
        holder._date_time_call_log.setText(cadate+" "+chdate);
        holder._amount_call_log.setText("\u20B9 "+amount);
        if(callStatus.equals("Completed")){
            holder._secns_call_state_log.setTextColor(rgb(0,128,0));
            holder._sendCallSMS.setVisibility(View.GONE);

        }else if(callStatus.equals("Not Connected")){
            holder._secns_call_state_log.setTextColor(rgb(128,0,0));
            holder._sendCallSMS.setVisibility(View.VISIBLE);

        }
        holder._secns_call_state_log.setText(callStatus);

        holder._sendCallSMS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                holder._smsSentTV.setVisibility(View.VISIBLE);
                holder._sendCallSMS.setVisibility(View.GONE);
            }
        });


//
//        if(img!=null)
//            Picasso.get().load("http://49creativeworks.com/pavan/astro/Astratalk/admin/"+ img).into(holder._cat_image);
//        else holder._cat_image.setImageResource(R.drawable.ic_broken_image_black_24dp);

      //  holder.itemView.startAnimation(myAnim);


    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder  {


        private final TextView _name_call_log,_secns_call_log,_amount_call_log,_date_time_call_log,_secns_call_state_log,_feedCT,_feedback_call_log;
        private final RatingBar _ratingbarcall;
        private final Button _sendCallSMS;
        private LinearLayout _LL_Coupon;
        private  TextView _couponTV,_couponCODETV,_customer_status,_smsSentTV;


        ViewHolder(View itemView) {
            super(itemView);

            _name_call_log = (TextView) itemView.findViewById(R.id.name_call_log);
            _secns_call_log = (TextView) itemView.findViewById(R.id.secns_call_log);
            _date_time_call_log = (TextView) itemView.findViewById(R.id.date_time_call_log);
            _amount_call_log = (TextView) itemView.findViewById(R.id.amount_call_log);
            _secns_call_state_log = (TextView) itemView.findViewById(R.id.secns_call_state_log);
            _feedCT = (TextView) itemView.findViewById(R.id.feedCT);
            _ratingbarcall = (RatingBar) itemView.findViewById(R.id.ratingbarcall);

            _feedback_call_log = (TextView) itemView.findViewById(R.id.feedback_call_log);


            _couponTV = itemView.findViewById(R.id.couponTV);
            _couponCODETV = itemView.findViewById(R.id.couponCOETV);
            _LL_Coupon = itemView.findViewById(R.id.LL_Coupon);
            _customer_status = itemView.findViewById(R.id.customer_status);
            _sendCallSMS = itemView.findViewById(R.id.sendCallSMS);
            _smsSentTV = itemView.findViewById(R.id.smsSENT);



        }

    }


}
