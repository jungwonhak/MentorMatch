package com.codepath.mentormatch.adapters;

import java.util.Date;
import java.util.List;

import android.content.Context;
import android.graphics.Paint;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.codepath.mentormatch.R;
import com.codepath.mentormatch.models.Status;
import com.codepath.mentormatch.models.parse.Task;

public class TasksAdapter extends ArrayAdapter<Task> {

	private TextView tvTaskDescr;
	private TextView tvCreatedBy;
	private TextView tvCreateDate;
	
	public TasksAdapter(Context context, List<Task> objects) {
		super(context, 0, objects);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if(convertView == null) {
			LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.task_item, null);	
		}
		Task task = getItem(position);
		tvTaskDescr = (TextView) convertView.findViewById(R.id.tvTaskDescr);
		tvCreatedBy = (TextView) convertView.findViewById(R.id.tvCreatedBy);
		tvCreateDate = (TextView) convertView.findViewById(R.id.tvCreateDate);
		
		tvTaskDescr.setText(task.getDescription());
		if(Status.CLOSED.equals(task.getStatus())) {
			tvTaskDescr.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
		}
		tvCreatedBy.setText(task.getCreatedBy().getFullName());
		Date taskDate = task.getLocalizedCreateDate();
		java.text.DateFormat timeFormat = DateFormat.getTimeFormat(getContext());
		String time = timeFormat.format(taskDate);
		java.text.DateFormat dateFormat = DateFormat.getDateFormat(getContext());
		String date = dateFormat.format(taskDate);
//		dateFormat.format(task.getLocalizedCreateDate());
		tvCreateDate.setText(date + " " + time); //DateUtils.getRelativeDateTimeString(getContext(), task.getLocalizedCreateDate().t.getTime(), DateUtils.MINUTE_IN_MILLIS, DateUtils.DAY_IN_MILLIS, 0).toString()); 
		
		return convertView;
	}
	
}
