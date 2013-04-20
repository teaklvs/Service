package com.example.tasks;

import java.util.List;

import com.example.service.R;
import com.example.service.TodoList;
import com.example.adapters.TodoItemsAdapter;
import com.example.database.ToDoDao;
import com.example.model.TodoItem;
import com.example.utils.Downloader;
import com.example.utils.OnContentDownloaded;
import com.example.utils.OnToDoItemsDownloaded;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.widget.Toast;

public class DownloadTask extends AsyncTask<String, Void, List<TodoItem>> {

	public static final String ITEMS_DOWNLOADED_ACTION = "com.example.ITEMS_DOWNLOADED_ACTION";
	private Exception exception = null;
	protected Context context;

	public DownloadTask(Context context) {
		this.context = context;
	}

	@Override
	protected List<TodoItem> doInBackground(String... params) {
		if (params.length < 1) {
			exception = new IllegalArgumentException(
					"At least one argument for the download url expected. ");
			return null;
		} else {

			String url = params[0];
			OnContentDownloaded<List<TodoItem>> handler = new OnToDoItemsDownloaded();
			try {
				Downloader.getFromUrl(url, handler);
				publishProgress(null);
				return handler.getResult();
			} catch (Exception ex) {
				exception = ex;
				return null;
			}
		}
	}

	@Override
	protected void onPostExecute(List<TodoItem> result) {
		super.onPostExecute(result);
		if (exception != null) {
			Toast.makeText(context, "Error: " + exception.getMessage(),
					Toast.LENGTH_LONG).show();
		} else {

			ToDoDao dao = new ToDoDao(context);
			dao.open();

			for (TodoItem item : result) {
				dao.insert(item);
			}
			dao.close();
			Intent intent=new Intent(ITEMS_DOWNLOADED_ACTION);
			context.sendBroadcast(intent);

		}
	}

}