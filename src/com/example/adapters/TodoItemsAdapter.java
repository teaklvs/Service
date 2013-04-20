package com.example.adapters;

import java.util.ArrayList;
import java.util.List;

//import android.R;
import com.example.service.R;
import com.example.model.TodoItem;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.example.model.TodoItem;

public class TodoItemsAdapter extends BaseAdapter implements
		OnItemClickListener {


	private List<TodoItem> items;
	private Context ctx;
	private LayoutInflater inflater;

	public TodoItemsAdapter(Context ctx) {
		items = new ArrayList<TodoItem>();
		this.ctx = ctx;
		inflater = (LayoutInflater) ctx
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	public TodoItemsAdapter(List<TodoItem> items, Context ctx) {
		this.items = items;
		this.ctx = ctx;
		inflater = (LayoutInflater) this.ctx
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		return items.size();
	}

	@Override
	public Object getItem(int position) {
		return items.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	class TodoHoler {
		public RelativeLayout itemLayout;
		public TextView name;
		public TextView url;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		TodoItem item = items.get(position);
		TodoHoler holder = null;
		if (convertView == null) {
			holder = new TodoHoler();
			holder.itemLayout = (RelativeLayout) inflater.inflate(
					R.layout.item_todo, null);

			holder.name = (TextView) holder.itemLayout
					.findViewById(R.id.todoName);
			holder.url = (TextView) holder.itemLayout
					.findViewById(R.id.todoUrl);
			convertView = holder.itemLayout;
			convertView.setTag(holder);

		}

		holder = (TodoHoler) convertView.getTag();

		holder.name.setText(item.getName());
		holder.url.setText(item.getURL().toString());


		return convertView;
	}

	public void add(TodoItem item) {
		items.add(item);
		notifyDataSetChanged();
	}

	public void addAll(List<TodoItem> items) {
		this.items.addAll(items);
		notifyDataSetChanged();
	}

	public void clear(){
		items.clear();
		notifyDataSetInvalidated();
	}


	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		TodoItem item = items.get(position);

		notifyDataSetChanged();
	}


}
