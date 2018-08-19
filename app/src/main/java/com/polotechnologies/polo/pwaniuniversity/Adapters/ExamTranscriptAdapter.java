package com.polotechnologies.polo.pwaniuniversity.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.polotechnologies.polo.pwaniuniversity.Data.Transcript;
import com.polotechnologies.polo.pwaniuniversity.R;

import java.util.List;

public class ExamTranscriptAdapter extends RecyclerView.Adapter<ExamTranscriptAdapter.ResultViewHolder> {

    //Member Variable for OnClickListener
    final private ExamTranscriptClickListener mOnClickListener;
    //Member Variable for Lst
    private List<Transcript> examTranscriptLists;
    //Member Variable for Context
    private Context mContext;

    public ExamTranscriptAdapter(Context mContext, ExamTranscriptClickListener listener) {
        this.mContext = mContext;
        this.mOnClickListener = listener;
    }

    @NonNull
    @Override
    public ResultViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the exam to a view
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.card_student_transcript, parent, false);

        return new ResultViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ResultViewHolder holder, int position) {

        final Transcript examTranscript = examTranscriptLists.get(position);

        holder.mResultAcademicYear.setText(examTranscript.getmAcademicYear());
    }

    @Override
    public int getItemCount() {
        return examTranscriptLists.size();
    }

    public List<Transcript> swapList(List<Transcript> examTranscriptList) {

        List<Transcript> temp = examTranscriptLists;
        this.examTranscriptLists = examTranscriptList; // new examResultList value assigned

        return temp;
    }

    //Interface defining our listener
    public interface ExamTranscriptClickListener {
        /**
         * Method that takes in 2 parameters that is: academic year, pdf url
         * to be passed to the new Activity
         */
        void onTranscriptItemClick(String academic_year, String transcript_url);
    }

    public class ResultViewHolder extends RecyclerView.ViewHolder implements
            View.OnClickListener {

        TextView mResultAcademicYear;

        public ResultViewHolder(View itemView) {
            super(itemView);
            mResultAcademicYear = (TextView) itemView.findViewById(R.id.text_transcript_academic_year);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            //get the position of clicked item
            int clickedPosition = getAdapterPosition();

            final Transcript examTranscript = examTranscriptLists.get(clickedPosition);

            String mAcademicYear = Transcript.getmAcademicYear();
            String mTranscriptUrl = Transcript.getmTranscript();

            mOnClickListener.onTranscriptItemClick(mAcademicYear, mTranscriptUrl);
        }
    }
}
