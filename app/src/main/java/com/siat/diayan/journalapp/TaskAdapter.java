package com.siat.diayan.journalapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.siat.diayan.journalapp.database.TaskEntry;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskAdapterViewHolder> {

    final private ItemClickListener mItemClickListener;
    private static final String DATE_FORMAT = "dd/MM/yyy";
    private static final String TIME_FORMAT = "hh:mm";
    private SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT, Locale.getDefault());
    private SimpleDateFormat timeFormat = new SimpleDateFormat(TIME_FORMAT, Locale.getDefault());

    private List<TaskEntry> mTaskEntries;
    private Context mContext;

    public TaskAdapter(Context context, ItemClickListener listener) {
        mContext = context;
        mItemClickListener = listener;
    }


    @NonNull
    @Override
    public TaskAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.item_task, parent, false);

        return new TaskAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskAdapterViewHolder holder, int position) {

        TaskEntry taskEntry = mTaskEntries.get(position);

        String description = taskEntry.getTaskDescription();
        String updateOn = dateFormat.format(taskEntry.getUpdateDate());
        String updateTimeStamp = timeFormat.format(taskEntry.getUpdateTime());

        holder.taskDescriptionView.setText(description);
        holder.upDatedAtView.setText(updateOn);
        holder.timeStamView.setText(updateTimeStamp);

    }

    @Override
    public int getItemCount() {

        if (mTaskEntries == null) {
            return 0;
        }

        return mTaskEntries.size();
    }

    public List<TaskEntry> getTasks() {
        return mTaskEntries;
    }

    public void setTasks(List<TaskEntry> taskEntries) {
        mTaskEntries = taskEntries;
        notifyDataSetChanged();
    }

    public interface ItemClickListener {
        void onItemClickListener(int itemId);
    }

    class TaskAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView taskDescriptionView;
        TextView timeStamView;
        TextView upDatedAtView;

        public TaskAdapterViewHolder(View itemView) {
            super(itemView);

            taskDescriptionView = (TextView) itemView.findViewById(R.id.tv_task_description);
            timeStamView = (TextView) itemView.findViewById(R.id.tv_time_stamp);
            upDatedAtView = (TextView) itemView.findViewById(R.id.tv_task_updatedOn);
        }

        @Override
        public void onClick(View v) {
            int elementId = mTaskEntries.get(getAdapterPosition()).getId();
            mItemClickListener.onItemClickListener(elementId);
        }
    }
}
