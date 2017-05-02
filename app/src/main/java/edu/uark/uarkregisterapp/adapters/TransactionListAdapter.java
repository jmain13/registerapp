package edu.uark.uarkregisterapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;
import java.util.Locale;

import edu.uark.uarkregisterapp.R;
import edu.uark.uarkregisterapp.models.api.TransactionEntry;

public class TransactionListAdapter extends ArrayAdapter<TransactionEntry> {
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = convertView;
		if (view == null) {
			LayoutInflater inflater = LayoutInflater.from(this.getContext());
			view = inflater.inflate(R.layout.list_view_item_transaction_entry, parent, false);
		}

		TransactionEntry entry = this.getItem(position);
		if (entry != null) {
			TextView lookupCodeTextView = (TextView) view.findViewById(R.id.list_view_item_transaction_entry_lookup_code);
			if (lookupCodeTextView != null) {
				lookupCodeTextView.setText(entry.getLookupCode());
			}

			TextView quantityTextView = (TextView) view.findViewById(R.id.list_view_item_transaction_entry_quantity);
			if (quantityTextView != null) {
				quantityTextView.setText(String.format(Locale.getDefault(), "%d", entry.getQuantity()));
			}

			TextView priceTextView = (TextView) view.findViewById(R.id.list_view_item_transaction_entry_price);
			if (priceTextView != null) {
				priceTextView.setText("$" + String.format(Locale.getDefault(), "%.2f", entry.getPrice()));
			}
		}

		return view;
	}

	public TransactionListAdapter(Context context, List<TransactionEntry> entries) {
		super(context, R.layout.list_view_item_transaction_entry, entries);
	}
}
