package com.codepath.mentormatch.adapters;

import java.util.List;

import android.content.Context;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.codepath.mentormatch.R;
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
		tvCreatedBy.setText(task.getCreatedBy().getFullName());
		tvCreateDate.setText(		DateUtils.getRelativeDateTimeString(getContext(), task.getLocalizedCreateDate().getTime(), DateUtils.MINUTE_IN_MILLIS, DateUtils.DAY_IN_MILLIS, 0).toString()); 
		
		return convertView;
	}
	
}
