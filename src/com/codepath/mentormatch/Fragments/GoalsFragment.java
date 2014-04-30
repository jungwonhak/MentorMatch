package com.codepath.mentormatch.fragments;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.codepath.mentormatch.R;
import com.codepath.mentormatch.activities.ConnectionDetailActivity;
import com.codepath.mentormatch.adapters.TasksAdapter;
import com.codepath.mentormatch.helpers.ParseQueries;
import com.codepath.mentormatch.models.Status;
import com.codepath.mentormatch.models.parse.MatchRelationship;
import com.codepath.mentormatch.models.parse.Task;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

public class GoalsFragment extends Fragment {

	private List<Task> taskList;
	private TasksAdapter tasksAdapter;
	private ListView lvTasks;
	private EditText etTaskDescription;
	private Button btnAddTask;
	private MatchRelationship relationship;

	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		String objId = getActivity().getIntent().getStringExtra(ConnectionDetailActivity.RELATIONSHIP_ID_EXTRA);
		ParseQueries.getRelationshipById(objId, new RetrieveRelationshipCallback());
	}
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		taskList = new ArrayList<Task>();
		tasksAdapter = new TasksAdapter(getActivity(), taskList);

	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_goals, container, false);
		etTaskDescription = (EditText) view.findViewById(R.id.etTaskDescription);
		btnAddTask = (Button) view.findViewById(R.id.btnAddTask);
		lvTasks = (ListView) view.findViewById(R.id.lvTasks);
		lvTasks.setAdapter(tasksAdapter);
		setupListener();
		return view;
	}

	private void setupListener() {
		btnAddTask.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Task task = new Task();
				task.setDescription(etTaskDescription.getText().toString());
				task.setDueDate(new Date());
				task.setMatchRelationshipId(relationship);
				task.setStatus(Status.OPEN);
				task.setCreatedBy(ParseUser.getCurrentUser());

				task.saveInBackground();
				etTaskDescription.setText("");
				tasksAdapter.add(task);
			}
		});
	}
	
	private void retrieveTasks(){
		ParseQuery<Task> query = ParseQuery.getQuery("Task");
		query.whereEqualTo(Task.RELATIONSHIP_KEY, relationship);
		query.addDescendingOrder(Task.CREATED_AT_KEY);
		query.findInBackground(new FindCallback<Task>() {

			@Override
			public void done(List<Task> list, ParseException e) {
				if(e == null) {
					tasksAdapter.clear();
					tasksAdapter.addAll(list);
				} else {
					e.printStackTrace();
				}
			}
		}); 
			
	}
	
	private void populateExistingData() {
		retrieveTasks();
	}
	
	private class RetrieveRelationshipCallback extends GetCallback<MatchRelationship> {
		@Override
		public void done(MatchRelationship obj, ParseException e) {
			if(e == null) {
				relationship = obj;
				populateExistingData();
				btnAddTask.setEnabled(true);				
			} else {
				Log.d("DEBUG", "Error getting relationship");
				e.printStackTrace();
			}
		}		
	}
	
}
