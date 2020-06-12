package com.uma.astropandith.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;


import com.uma.astropandith.Model.WalletHistory;
import com.uma.astropandith.R;

import java.util.List;


public class WalletHistoryAdapter extends RecyclerView.Adapter<WalletHistoryAdapter.ViewHolder> {


    private List<WalletHistory> mData;
    private LayoutInflater mInflater;
    private Context mContext;
    private Intent intent;
    private Animation myAnim;


    // data is passed into the constructor
    public WalletHistoryAdapter(Context mContext, List<WalletHistory> data) {
        this.mData = data;
        this.mInflater = LayoutInflater.from(mContext);
        this.mContext = mContext;

    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)  {

        myAnim = AnimationUtils.loadAnimation(mContext, R.anim.push_left_in);


        Context context = parent.getContext();

        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(R.layout.wallet_list, parent, false);

        return new ViewHolder(view);


    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position)  {



        String date = mData.get(position).getDate();
        String walletOrderid = mData.get(position).getUserName();
        String orderStatus = mData.get(position).getOrderStatus();
        String walletAmount = mData.get(position).getWalletAmount();
        String transactionAmount = mData.get(position).getTotalTransactionAmount();
        String time = mData.get(position).getTime();
        String astrotype = mData.get(position).getAstrotype();
        String paymentThrough = mData.get(position).getPaymentThrough();




        holder._date_wallet.setText(date);
        holder._Order_Id.setText(walletOrderid.toUpperCase());
        holder._satus_wallet.setText(orderStatus.toUpperCase());
        holder._closing_balance.setText("\u20B9 "+walletAmount);
        holder._amount_wallet.setText("\u20B9 "+transactionAmount);
        holder._time_wallet.setText(time);
        holder._service_type.setText(astrotype);
        holder._comment.setText(paymentThrough);



//        holder._chat_bt.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                intent = new Intent(mContext, ConfirmChatActivity.class);
//                intent.putExtra("pid", _pid);
//                intent.putExtra("pname", name);
//                intent.putExtra("languages", languages);
//                intent.putExtra("experiance", experiance);
//                intent.putExtra("chatpriceMin", chatpriceMin);
//                intent.putExtra("astrotype", astrotype);
//                mContext.startActivity(intent);
//
//            }
//        });

     //   holder.itemView.startAnimation(myAnim);



    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder  {

        private  TextView _comment;
        private  TextView _time_wallet;
        private  TextView _amount_wallet;
        private  TextView _Order_Id;
        private  TextView _satus_wallet;
        private  TextView _closing_balance;
        private  TextView _date_wallet;
        private  TextView _service_type;


        ViewHolder(View itemView) {
            super(itemView);

            _date_wallet = itemView.findViewById(R.id.date_wallet);
            _Order_Id = itemView.findViewById(R.id.Order_Id);
            _satus_wallet = itemView.findViewById(R.id.satus_wallet);
            _closing_balance = itemView.findViewById(R.id.closing_balance);
            _amount_wallet = itemView.findViewById(R.id.amount_wallet);
            _time_wallet = itemView.findViewById(R.id.time_wallet);
            _service_type = itemView.findViewById(R.id.service_type);
            _comment = itemView.findViewById(R.id.Comment);



        }
    }
}
