package com.example.service;

import java.util.Date;
import java.util.List;

import com.example.adapters.TodoItemsAdapter;

import com.example.database.ToDoDao;
import com.example.model.TodoItem;
import com.example.tasks.DownloadTask;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class TodoList extends Activity {

	

	static final String ACTION_TODO_DETAILS = "com.examples.ACTION_TODO_DETAILS";

	private EditText mItemName;

	private ListView mTodoItemsList;
	private ToDoDao mDao;
	private TodoItemsAdapter mAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_todo_list);
		loadViews();
		initList();
	}

	@Override
	protected void onResume() {
		super.onResume();
		mDao = new ToDoDao(this);
		mDao.open();
		loadData();
	}

	@Override
	protected void onPause() {
		super.onPause();
		mDao.close();
	}

	private void loadData() {

		List<TodoItem> res = mDao.getAllItems();
		mAdapter.clear();
		mAdapter.addAll(res);
	}

	/**
	 * Inflates the views from the xml layout
	 */
	private void loadViews() {
		mItemName = (EditText) findViewById(R.id.todoName);

		mTodoItemsList = (ListView) findViewById(R.id.todoItems);
	}

	private void initList() {
		mAdapter = new TodoItemsAdapter(this);
		mTodoItemsList.setAdapter(mAdapter);
		mTodoItemsList.setOnItemClickListener(mAdapter);
		mTodoItemsList
				.setOnItemLongClickListener(new OnItemLongClickListener() {

					@Override
					public boolean onItemLongClick(AdapterView<?> arg0,
							View parent, int position, long id) {
						Toast.makeText(TodoList.this, "Item long click",
								Toast.LENGTH_LONG).show();
						return true;
					}

				});

	}
	
	public void onClick(View v) {
		EditText txtPrebaraj=(EditText)findViewById(R.id.todoName);
		String src= txtPrebaraj.getText().toString();
		String down="http://api.europeana.eu/api/opensearch.json?searchTerms="+src+"&wskey=zaNguG5d7&callback=com.example.showResults";
		IntentFilter filter = new IntentFilter(down);

		 registerReceiver(new OnDownloadRefreshReceiver(), filter);
		 startService(new Intent(this, DownloadService.class));
		
	}


	private ProgressDialog loadingDialog;

	private void createDialog() {
		loadingDialog = new ProgressDialog(this);
		loadingDialog.setTitle(this.getResources().getString(
				R.string.download_title));
		loadingDialog.setMessage(this.getResources().getString(
				R.string.download_description));
		loadingDialog.setIndeterminate(true);
		loadingDialog.setCancelable(false);
	}

	public void explicit(View view) {
		startActivity(new Intent(this, TodoDetails.class));
	}





	class OnDownloadRefreshReceiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {

			loadData();

			if (loadingDialog != null && loadingDialog.isShowing()) {
				loadingDialog.dismiss();
			}

			TodoList.this.unregisterReceiver(this);

		}
	}

}
