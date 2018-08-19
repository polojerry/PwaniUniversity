package com.polotechnologies.polo.pwaniuniversity.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.polotechnologies.polo.pwaniuniversity.Data.ExamResult;
import com.polotechnologies.polo.pwaniuniversity.R;

import java.util.List;

public class ExamResultAdapter extends RecyclerView.Adapter<ExamResultAdapter.ResultViewHolder> {

    //Member Variable for OnClickListener
    final private ExamResultClickListener mOnClickListener;
    //Member Variable for Lst
    private List<ExamResult> examResultLists;
    //Member Variable for Context
    private Context mContext;

    public ExamResultAdapter(Context mContext, ExamResultClickListener listener) {
        this.mContext = mContext;
        this.mOnClickListener = listener;
    }

    @NonNull
    @Override
    public ResultViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the exam to a view
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.card_student_exam_result, parent, false);

        return new ResultViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ResultViewHolder holder, int position) {

        final ExamResult examResult = examResultLists.get(position);

        holder.mResultAcademicYear.setText(examResult.getmAcademicYear());
        holder.mResultStudyStage.setText(examResult.getmStudyStage());
    }

    @Override
    public int getItemCount() {
        return examResultLists.size();
    }

    public List<ExamResult> swapList(List<ExamResult> examResultList) {

        List<ExamResult> temp = examResultLists;
        this.examResultLists = examResultList; // new examResultList value assigned

        return temp;
    }


    //Interface defining our listener
    public interface ExamResultClickListener {
        /**
         * Method that takes in 3 parameters that is: academic year, study stage, pdf url
         * to be passed to the new Fragment
         */
        void onExamItemClick(String academic_year, String study_stage, String result_url);
    }

    public class ResultViewHolder extends RecyclerView.ViewHolder implements
            View.OnClickListener {

        TextView mResultAcademicYear;
        TextView mResultStudyStage;

        public ResultViewHolder(View itemView) {
            super(itemView);
            mResultAcademicYear = (TextView) itemView.findViewById(R.id.text_result_academic_year);
            mResultStudyStage = (TextView) itemView.findViewById(R.id.text_result_study_stage);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            //get the position of clicked item
            int clickedPosition = getAdapterPosition();

            final ExamResult examResult = examResultLists.get(clickedPosition);

            String mAcademicYear = examResult.getmAcademicYear();
            String mStudyStage = examResult.getmStudyStage();
            String mResultUrl = examResult.getmExamResult();

            mOnClickListener.onExamItemClick(mAcademicYear, mStudyStage, mResultUrl);
        }
    }
}
