package com.uma.astropandith.Adapters;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.uma.astropandith.Activitys.ClarificationActivity;
import com.uma.astropandith.Activitys.EditReplyActivity;
import com.uma.astropandith.Activitys.PendingReportDetailsActivity;
import com.uma.astropandith.Activitys.ViewReplyActivity;
import com.uma.astropandith.Model.ReportCompletedList;
import com.uma.astropandith.R;

import java.util.ArrayList;


public class CompletedReportAdapter extends RecyclerView.Adapter<CompletedReportAdapter.ViewHolder> {


    private ArrayList<ReportCompletedList> mData;
    private LayoutInflater mInflater;
    private Context mContext;
    private String _replyMSG;
    private String _replyReportID;
    private String _userId;
    private String _pandithId;
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
    public CompletedReportAdapter(Context mContex, ArrayList<ReportCompletedList> data) {
        this.mData = data;
        this.mInflater = LayoutInflater.from(mContex);
        this.mContext = mContex;

    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)  {

        Context context = parent.getContext();

        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(R.layout.report_completed_list, parent, false);

        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position)  {

        String name = mData.get(position).getName();
        String reportAFinalPrice = mData.get(position).getPanditGetingAmount();
        String feedback = mData.get(position).getFeedback();
        String anycomments = mData.get(position).getAnycomments();
        String rtype = mData.get(position).getRtype();
        String date = mData.get(position).getDate();
        String time = mData.get(position).getTime();
        String rating = mData.get(position).getRating();
        String clarificationCount = mData.get(position).getClarificationCount();
        String clarificationIn = mData.get(position).getClarificationIn();


        String couponCode = mData.get(position).getCouponCode();
        String couponCodeStatus = mData.get(position).getCouponCodeStatus();
        String couponPer = mData.get(position).getCouponPer();

        if(couponCodeStatus.equals("1")){

            holder._LL_Coupon.setVisibility(View.VISIBLE);
            holder._couponCODETV.setText(couponCode +" "+ couponPer+ "%");

        } else {

            holder._LL_Coupon.setVisibility(View.GONE);
            holder._couponCODETV.setText(couponCode);
        }

        if(rating.equals("0")){

            holder._ratingbar.setVisibility(View.GONE);

        }

        holder._name.setText(name.toUpperCase());
        holder._amount.setText("\u20B9 "+reportAFinalPrice);

        if(feedback!=null) {

            if (!feedback.isEmpty()) {

                holder._feedBack.setText(feedback);

            } else {

                holder._feedBack.setVisibility(View.GONE);

                holder._feedTit.setVisibility(View.GONE);
            }
        }

        holder._report_Completedreport.setText(rtype);
        holder._comment.setText(anycomments);
        holder._completedTime.setText(date+" "+time);
        String edit_count = mData.get(position).getEditCount();


        if(clarificationCount.equals("1")){

            holder._clarification.setVisibility(View.VISIBLE);

        } else {

            holder._clarification.setVisibility(View.GONE);

        }


        int _ECount = Integer.parseInt(edit_count);

        if(_ECount>=2){

            holder._editReply.setVisibility(View.GONE);

        }

        holder._completedLT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               String _reportType = mData.get(position).getRtype();
                String _namePd = mData.get(position).getName();
                String _phonePd = mData.get(position).getPhone();
                String _genderPd = mData.get(position).getGender();
                String  _dobPd = mData.get(position).getDob();
                String _tobPd = mData.get(position).getTob();
                String  _pobPd = mData.get(position).getPdob();
                String _pobcityPd = mData.get(position).getPobcity();
                String  _pobstatePd = mData.get(position).getPobstate();
                String  _pobcountryPd = mData.get(position).getPobcountry();
                String  _martialstatusPd = mData.get(position).getMaritalstatus();
                String _occupationPd = mData.get(position).getOccupation();
                String _topicConcrenPd = mData.get(position).getTopicofconcern();

                String _pnamePd = mData.get(position).getPname();
                String  _pdobPd = mData.get(position).getPdob();
                String  _ptobPd = mData.get(position).getPtob();
                String  _ppobPd = mData.get(position).getPpob();
                String  _ppobcityPd = mData.get(position).getPpobcity();
                String  _ppobstatePd = mData.get(position).getPpobstate();
                String  _ppobscountryPd = mData.get(position).getPpobcountry();

                String  _pcommentPd = mData.get(position).getAnycomments();


                String rid = mData.get(position).getId();
                String userid = mData.get(position).getUserid();
                String replayMsg = mData.get(position).getReplayMsg();
                String clarificationIn = mData.get(position).getClarificationIn();
                String clarificationMsg = mData.get(position).getClarificationMsg();

                Intent intent = new Intent(mContext.getApplicationContext(), ViewReplyActivity.class);

                intent.putExtra("_rID", rid);
                intent.putExtra("_uid", userid);
                intent.putExtra("replayMsg", replayMsg);
                intent.putExtra("clarificationIn", clarificationIn);
                intent.putExtra("clarificationMsg", clarificationMsg);

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


                mContext.startActivity(intent);

            }
        });

        holder._clarification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String _reportType = mData.get(position).getRtype();
                String _namePd = mData.get(position).getName();
                String _phonePd = mData.get(position).getPhone();
                String _genderPd = mData.get(position).getGender();
                String  _dobPd = mData.get(position).getDob();
                String _tobPd = mData.get(position).getTob();
                String  _pobPd = mData.get(position).getPdob();
                String _pobcityPd = mData.get(position).getPobcity();
                String  _pobstatePd = mData.get(position).getPobstate();
                String  _pobcountryPd = mData.get(position).getPobcountry();
                String  _martialstatusPd = mData.get(position).getMaritalstatus();
                String _occupationPd = mData.get(position).getOccupation();
                String _topicConcrenPd = mData.get(position).getTopicofconcern();

                String _pnamePd = mData.get(position).getPname();
                String  _pdobPd = mData.get(position).getPdob();
                String  _ptobPd = mData.get(position).getPtob();
                String  _ppobPd = mData.get(position).getPpob();
                String  _ppobcityPd = mData.get(position).getPpobcity();
                String  _ppobstatePd = mData.get(position).getPpobstate();
                String  _ppobscountryPd = mData.get(position).getPpobcountry();

                String  _pcommentPd = mData.get(position).getAnycomments();


                _replyMSG = mData.get(position).getReplayMsg();
                _replyReportID = mData.get(position).getId();
                _userId = mData.get(position).getUserid();
                _pandithId = mData.get(position).getPanditid();
                String clarificationIn = mData.get(position).getClarificationIn();


                Intent intent = new Intent(mContext.getApplicationContext(), ClarificationActivity.class);
                intent.putExtra("_replyReportID", _replyReportID);
                intent.putExtra("_userId", _userId);
                intent.putExtra("_replyMSG", _replyMSG);
                intent.putExtra("_pandithId", _pandithId);
                intent.putExtra("rating", rating);
                intent.putExtra("feedback", feedback);
                intent.putExtra("clarificationIn", clarificationIn);


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

                mContext.startActivity(intent);

            }
        });

        holder._ratingbar.setRating(Float.parseFloat(rating));
        holder._editReply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String rid = mData.get(position).getId();
                String userid = mData.get(position).getUserid();
                String replayMsg = mData.get(position).getReplayMsg();
                String edit_count = mData.get(position).getEditCount();



                String _reportType = mData.get(position).getRtype();
                String _namePd = mData.get(position).getName();
                String _phonePd = mData.get(position).getPhone();
                String _genderPd = mData.get(position).getGender();
                String  _dobPd = mData.get(position).getDob();
                String _tobPd = mData.get(position).getTob();
                String  _pobPd = mData.get(position).getPdob();
                String _pobcityPd = mData.get(position).getPobcity();
                String  _pobstatePd = mData.get(position).getPobstate();
                String  _pobcountryPd = mData.get(position).getPobcountry();
                String  _martialstatusPd = mData.get(position).getMaritalstatus();
                String _occupationPd = mData.get(position).getOccupation();
                String _topicConcrenPd = mData.get(position).getTopicofconcern();

                String _pnamePd = mData.get(position).getPname();
                String  _pdobPd = mData.get(position).getPdob();
                String  _ptobPd = mData.get(position).getPtob();
                String  _ppobPd = mData.get(position).getPpob();
                String  _ppobcityPd = mData.get(position).getPpobcity();
                String  _ppobstatePd = mData.get(position).getPpobstate();
                String  _ppobscountryPd = mData.get(position).getPpobcountry();

                String  _pcommentPd = mData.get(position).getAnycomments();


                int _ECount = Integer.parseInt(edit_count);

                if(_ECount>=2){

                    Toast.makeText(mContext, "Can't Modify Report", Toast.LENGTH_SHORT).show();


                }else {

                    Intent intent = new Intent(mContext.getApplicationContext(), EditReplyActivity.class);

                    intent.putExtra("_rID", rid);
                    intent.putExtra("_uid", userid);
                    intent.putExtra("replayMsg", replayMsg);


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

                    mContext.startActivity(intent);

                }


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

        private final RatingBar _ratingbar;
        private  LinearLayout _feedTit,_LL_Coupon;
        private  LinearLayout _completedLT;
        public TextView _name,_amount,_feedBack,_comment,_completedTime,_editReply,_clarification,_report_Completedreport,_couponTV,_couponCODETV;


        ViewHolder(View itemView) {
            super(itemView);
            _name = itemView.findViewById(R.id.name_completedreport);
            _amount = itemView.findViewById(R.id.report_completedprice);
            _feedBack = itemView.findViewById(R.id.feedback_completedcomment);
            _comment = itemView.findViewById(R.id.comment_completed);
            _completedTime = itemView.findViewById(R.id.report_Completedtime);
            _editReply = itemView.findViewById(R.id.editReply);
            _ratingbar = itemView.findViewById(R.id.ratingbar);
            _completedLT = itemView.findViewById(R.id.completedLT);
            _clarification = itemView.findViewById(R.id.clarification);
            _report_Completedreport = itemView.findViewById(R.id.report_Completedreport);
            _feedTit = itemView.findViewById(R.id.feedTit);


            _couponTV = itemView.findViewById(R.id.couponTV);
            _couponCODETV = itemView.findViewById(R.id.couponCODETV);
            _LL_Coupon = itemView.findViewById(R.id.LL_Coupon);

        }
    }
}
