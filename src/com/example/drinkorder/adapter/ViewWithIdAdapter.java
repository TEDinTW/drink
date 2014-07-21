package com.example.drinkorder.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.drinkorder.adapter.bean.ViewWithIdAdapterElement;

public class ViewWithIdAdapter<T extends ViewWithIdAdapterElement> extends android.widget.BaseAdapter {
	Context context = null;
	List<T> elements = null;
	int resource;

	public ViewWithIdAdapter(Context context, int resource, List<T> objects) {
		this.context = context;
		this.elements = objects;
		this.resource = resource;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		return createViewFromElementBean(position, convertView, parent, elements);
	}

	protected View createViewFromElementBean(int position, View convertView, ViewGroup parent, List<T> elements) {
		T element = elements.get(position);

		View v = convertView;
		if (v == null) {
			LayoutInflater li = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = li.inflate(resource, null);
		}

		if (v instanceof TextView) {
			((TextView) v).setText(element.getText());
		}
		v.setTag(element.getElementId());

		return v;
	}

	@Override
	public int getCount() {
		return elements.size();
	}

	@Override
	public Object getItem(int arg0) {
		return elements.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return elements.get(arg0).getElementId();
	}
}
