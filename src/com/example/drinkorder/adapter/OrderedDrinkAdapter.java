package com.example.drinkorder.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.drinkorder.R;
import com.example.drinkorder.adapter.bean.OrderedDrinkAdapterElement;

public class OrderedDrinkAdapter extends ViewWithIdAdapter<OrderedDrinkAdapterElement> {
	public OrderedDrinkAdapter(Context context, int resource, List<OrderedDrinkAdapterElement> objects) {
		super(context, resource, objects);
		this.context = context;
		this.elements = objects;
	}

	@Override
	protected View createViewFromElementBean(int position, View convertView, ViewGroup parent, List<OrderedDrinkAdapterElement> elements) {
		OrderedDrinkAdapterElement element = elements.get(position);

		View v = convertView;
		if (v == null) {
			LayoutInflater li = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = li.inflate(resource, null);
		}
		v.setTag(element.getElementId());

		if (v instanceof ViewGroup) {

			TextView tvDrinkName = (TextView) v.findViewById(R.id.tvDrinkName);
			TextView tvIceLevel = (TextView) v.findViewById(R.id.tvIceLevel);
			TextView tvSugarLevel = (TextView) v.findViewById(R.id.tvSugarLevel);
			TextView tvQuantity = (TextView) v.findViewById(R.id.tvQuantity);
			TextView tvSubTotal = (TextView) v.findViewById(R.id.tvSubTotal);
			
			tvDrinkName.setText(element.getDrinkName());
			tvIceLevel.setText(element.getIceLevel());
			tvSugarLevel.setText(element.getSugarLevel());
			tvQuantity.setText(String.valueOf(element.getQuantity()));
			tvSubTotal.setText(String.valueOf(element.getSubTotal()));
		}
		return v;
	}
}
