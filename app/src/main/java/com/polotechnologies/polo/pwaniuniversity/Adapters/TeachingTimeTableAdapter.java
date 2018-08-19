package com.polotechnologies.polo.pwaniuniversity.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.polotechnologies.polo.pwaniuniversity.Data.TeachingTimeTable;
import com.polotechnologies.polo.pwaniuniversity.R;

import java.util.List;

public class TeachingTimeTableAdapter extends RecyclerView.Adapter<TeachingTimeTableAdapter.TeachingTimeTableViewHolder> {

    //Member Variable for OnClickListener
    final private TeachingTimeTableClickListener mOnClickListener;
    //Member Variable for Lst
    private List<TeachingTimeTable> teachingTimeTableLists;
    //Member Variable for Context
    private Context mContext;

    public TeachingTimeTableAdapter(Context mContext, TeachingTimeTableClickListener mOnClickListener) {
        this.mOnClickListener = mOnClickListener;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public TeachingTimeTableViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the exam to a view
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.card_teaching_time_table, parent, false);

        return new TeachingTimeTableViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TeachingTimeTableViewHolder holder, int position) {

        final TeachingTimeTable teachingTimeTable = teachingTimeTableLists.get(position);

        holder.mSemester.setText(teachingTimeTable.getsSemester());
        holder.mYear.setText(teachingTimeTable.getsYear());

    }

    @Override
    public int getItemCount() {
        return teachingTimeTableLists.size();
    }

    public List<TeachingTimeTable> swapList(List<TeachingTimeTable> teachingTimetableList) {

        List<TeachingTimeTable> temp = teachingTimeTableLists;
        this.teachingTimeTableLists = teachingTimetableList; // new examResultList value assigned

        return temp;
    }

    //Interface defining our listener
    public interface TeachingTimeTableClickListener {
        /**
         * Method that takes in 3 parameters that is: semester, year, timetable url
         * to be passed to the new Activity
         */
        void onTeachingTimeTableClick(String semester, String year, String url);
    }

    public class TeachingTimeTableViewHolder extends RecyclerView.ViewHolder implements
            View.OnClickListener {
        TextView mSemester;
        TextView mYear;

        public TeachingTimeTableViewHolder(View itemView) {
            super(itemView);

            mSemester = (TextView) itemView.findViewById(R.id.text_teaching_time_table_semester);
            mYear = (TextView) itemView.findViewById(R.id.text_teaching_time_table_year);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            //get the position of clicked item
            int clickedPosition = getAdapterPosition();

            final TeachingTimeTable teachingTimeTable = teachingTimeTableLists.get(clickedPosition);

            String mSemester = teachingTimeTable.getsSemester();
            String mYear = teachingTimeTable.getsYear();
            String mTeachingTimeTableUrl = teachingTimeTable.getsTimeTableUrl();

            mOnClickListener.onTeachingTimeTableClick(mSemester, mYear, mTeachingTimeTableUrl);

        }
    }
}
