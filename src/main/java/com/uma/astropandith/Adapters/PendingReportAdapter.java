package com.uma.astropandith.Adapters;

import android.content.Context;
import android.content.Intent;

import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.uma.astropandith.Activitys.PendingReportDetailsActivity;
import com.uma.astropandith.Model.ReportPendingList;
import com.uma.astropandith.R;

import java.util.ArrayList;


public class PendingReportAdapter extends RecyclerView.Adapter<PendingReportAdapter.ViewHolder> {

    private ArrayList<ReportPendingList> mData;
    private LayoutInflater mInflater;
    private Context mContext;
    private String _reportType;
    private String _namePd;
    private String _phonePd;
    private String _genderPd;
    private String _dobPd;
    private String _tobPd;
    private String _pobcityPd;
    private String _pobstatePd;
    private String _pobcountryPd;
    private String _martialstatusPd;
    private String _occupationPd;
    private String _topicConcrenPd;
    private String _pnamePd;
    private String _pdobPd;
    private String _ptobPd;
    private String _ppobPd;
    private String _ppobcityPd;
    private String _ppobstatePd;
    private String _ppobscountryPd;
    private String _pcommentPd;
    private String _pobPd;
    private String _rID;
    private String _pID;
    private String _userID;
    private String pid;


    // data is passed into the constructor
    public PendingReportAdapter(Context mContext,ArrayList<ReportPendingList> data,String pid) {
        this.mData = data;
        this.mInflater = LayoutInflater.from(mContext);
        this.mContext = mContext;
        this.pid = pid;

    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)  {

        Context context = parent.getContext();

        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(R.layout.report_list, parent, false);

        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position)  {

        String name = mData.get(position).getName();
        String rtype = mData.get(position).getRtype();
        String anycomments = mData.get(position).getAnycomments();
        String reportAFinalPrice = mData.get(position).getPandit_geting_amount();
        String date = mData.get(position).getDate();
        String time = mData.get(position).getTime();
        String langAnsured = mData.get(position).getLangAnsured();


        holder._name.setText(name.toUpperCase());
        holder._pendingType.setText(rtype);
        holder._comment.setText(anycomments);
        holder._answer_language.setText(langAnsured);
        holder._creationDateTime.setText(date+" "+time);
        holder._report_price.setText(reportAFinalPrice);
        holder._pending_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                _reportType = mData.get(position).getRtype();
                _namePd = mData.get(position).getName();
                _phonePd = mData.get(position).getPhone();
                _genderPd = mData.get(position).getGender();
                _dobPd = mData.get(position).getDob();
                _tobPd = mData.get(position).getTob();
                _pobPd = mData.get(position).getPdob();
                _pobcityPd = mData.get(position).getPobcity();
                _pobstatePd = mData.get(position).getPobstate();
                _pobcountryPd = mData.get(position).getPobcountry();
                _martialstatusPd = mData.get(position).getMaritalstatus();
                _occupationPd = mData.get(position).getOccupation();
                _topicConcrenPd = mData.get(position).getTopicofconcern();

                _pnamePd = mData.get(position).getPname();
                _pdobPd = mData.get(position).getPdob();
                _ptobPd = mData.get(position).getPtob();
                _ppobPd = mData.get(position).getPpob();
                _ppobcityPd = mData.get(position).getPpobcity();
                _ppobstatePd = mData.get(position).getPpobstate();
                _ppobscountryPd = mData.get(position).getPpobcountry();

                _pcommentPd = mData.get(position).getAnycomments();


                _rID = mData.get(position).getId();
                _userID = mData.get(position).getUserid();
                _pID = pid;


                Intent intent = new Intent(mContext.getApplicationContext(), PendingReportDetailsActivity.class);

                intent.putExtra("_reportType", _reportType);
                intent.putExtra("_namePd", _namePd);
                intent.putExtra("_phonePd", _phonePd);
                intent.putExtra("_genderPd", _genderPd);
                intent.putExtra("_dobPd", _dobPd);
                intent.putExtra("_tobPd", _tobPd);
                intent.putExtra("_pobPd", _pobPd);
                intent.putExtra("_pobcityPd", _pobcityPd);
                intent.putExtra("_pobstatePd", _pobstatePd);
                intent.putExtra("_pobcountryPd", _pobcountryPd);
                intent.putExtra("_martialstatusPd", _martialstatusPd);
                intent.putExtra("_occupationPd", _occupationPd);
                intent.putExtra("_topicConcrenPd", _topicConcrenPd);


                intent.putExtra("_pnamePd", _pnamePd);
                intent.putExtra("_pdobPd", _pdobPd);
                intent.putExtra("_ptobPd", _ptobPd);
                intent.putExtra("_ppobPd", _ppobPd);
                intent.putExtra("_ppobcityPd", _ppobcityPd);
                intent.putExtra("_ppobstatePd", _ppobstatePd);
                intent.putExtra("_ppobscountryPd", _ppobscountryPd);

                intent.putExtra("_pcommentPd", _pcommentPd);


                intent.putExtra("_rID", _rID);
                intent.putExtra("_userID", _userID);
                intent.putExtra("_pID", _pID);


                mContext.startActivity(intent);


            }
        });

    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder  {

        private LinearLayout _pending_list;
        private  TextView _pendingType,_comment,_answer_language,_creationDateTime,_report_price;
        private  TextView _name;


        ViewHolder(View itemView) {
            super(itemView);
            _name = itemView.findViewById(R.id.name_pendingreport);
            _pendingType = itemView.findViewById(R.id.report_pendingreport);
            _comment = itemView.findViewById(R.id.comment_pendingcomment);
            _answer_language = itemView.findViewById(R.id.answer_language);
            _creationDateTime = itemView.findViewById(R.id.report_creationtime);
            _report_price = itemView.findViewById(R.id.report_price);
            _pending_list = itemView.findViewById(R.id.pending_list);

        }
    }
}
