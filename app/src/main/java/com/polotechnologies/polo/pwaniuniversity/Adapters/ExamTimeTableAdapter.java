package com.polotechnologies.polo.pwaniuniversity.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.polotechnologies.polo.pwaniuniversity.Data.ExamTimeTable;
import com.polotechnologies.polo.pwaniuniversity.R;

import java.util.List;

public class ExamTimeTableAdapter extends RecyclerView.Adapter<ExamTimeTableAdapter.ExamTimeTableViewHolder> {

    //Member Variable for OnClickListener
    final private ExamTimeTableClickListener mOnClickListener;
    //Member Variable for Lst
    private List<ExamTimeTable> examTimeTableLists;
    //Member Variable for Context
    private Context mContext;

    public ExamTimeTableAdapter(ExamTimeTableClickListener mOnClickListener, Context mContext) {
        this.mOnClickListener = mOnClickListener;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ExamTimeTableViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the exam to a view
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.card_exam_time_table, parent, false);

        return new ExamTimeTableViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ExamTimeTableViewHolder holder, int position) {

        final ExamTimeTable examTimeTable = examTimeTableLists.get(position);

        holder.mComment.setText(examTimeTable.getsComment());
        holder.mSemester.setText(examTimeTable.getsSemester());
        holder.mYear.setText(examTimeTable.getsYear());
    }

    @Override
    public int getItemCount() {
        return examTimeTableLists.size();
    }

    public List<ExamTimeTable> swapList(List<ExamTimeTable> examTimetableList) {

        List<ExamTimeTable> temp = examTimeTableLists;
        this.examTimeTableLists = examTimetableList; // new examTimeTableList value assigned

        return temp;
    }

    //Interface defining our listener
    public interface ExamTimeTableClickListener {
        /**
         * Method that takes in 3 parameters that is: semester, year, timetable url
         * to be passed to the new Activity
         */
        void onExamTimeTableClick(String comment, String semester, String year, String url);
    }

    public class ExamTimeTableViewHolder extends RecyclerView.ViewHolder implements
            View.OnClickListener {
        TextView mComment;
        TextView mSemester;
        TextView mYear;

        public ExamTimeTableViewHolder(View itemView) {
            super(itemView);
            mComment = (TextView) itemView.findViewById(R.id.text_exam_time_table_comment);
            mSemester = (TextView) itemView.findViewById(R.id.text_exam_time_table_semester);
            mYear = (TextView) itemView.findViewById(R.id.text_exam_time_table_year);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            //get the position of clicked item
            int clickedPosition = getAdapterPosition();

            final ExamTimeTable examTimeTable = examTimeTableLists.get(clickedPosition);

            String mComment = examTimeTable.getsComment();
            String mSemester = examTimeTable.getsSemester();
            String mYear = examTimeTable.getsYear();
            String mTeachingTimeTableUrl = examTimeTable.getsTimeTableUrl();

            mOnClickListener.onExamTimeTableClick(mComment, mSemester, mYear, mTeachingTimeTableUrl);
        }
    }
}
