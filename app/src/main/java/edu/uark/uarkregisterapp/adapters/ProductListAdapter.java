package edu.uark.uarkregisterapp.adapters;

import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;
import android.widget.Filterable;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import edu.uark.uarkregisterapp.ProductsListingActivity;
import edu.uark.uarkregisterapp.R;
import edu.uark.uarkregisterapp.models.api.Product;
import edu.uark.uarkregisterapp.models.api.services.ProductService;

public class ProductListAdapter extends ArrayAdapter<Product> implements Filterable{

	CustomFilter filter;
	List<Product> products;
	Context c;
	List<Product> filterList;

	public int getCount()
	{
		return products.size();
	}

	@Nullable
	@Override
	public Product getItem(int position) {
		return products.get(position);
	}

	@Override
	public long getItemId(int position) {
		return products.indexOf(getItem(position));
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		LayoutInflater inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		if(convertView == null)
		{
			convertView = inflater.inflate(R.layout.content_list_view_products_model, null);
		}

		Product product = this.getItem(position);
		if(product != null)
		{
			TextView nameTxt = (TextView) convertView.findViewById(R.id.productName);
			if(nameTxt != null)
			{
				nameTxt.setText(products.get(position).getLookupCode());
			}
		}

		return convertView;

		/*View view = convertView;
		if (view == null) {
			LayoutInflater inflater = LayoutInflater.from(this.getContext());
			view = inflater.inflate(R.layout.list_view_item_product, parent, false);
		}

		Product product = this.getItem(position);
		if (product != null) {
			TextView lookupCodeTextView = (TextView) view.findViewById(R.id.list_view_item_product_lookup_code);
			if (lookupCodeTextView != null) {
				lookupCodeTextView.setText(product.getLookupCode());
			}

			TextView quantityTextView = (TextView) view.findViewById(R.id.list_view_item_product_quantity);
			if (quantityTextView != null) {
				quantityTextView.setText(String.format(Locale.getDefault(), "%d", product.getQuantity()));
			}

			TextView priceTextView = (TextView) view.findViewById(R.id.list_view_item_product_price);
			if (priceTextView != null) {
				priceTextView.setText("$" + String.format(Locale.getDefault(), "%.2f", product.getPrice()));
			}

			TextView activeTextView = (TextView) view.findViewById(R.id.list_view_item_product_active);
			if (activeTextView != null) {
				if (product.getActive()) { activeTextView.setText("Active"); }
				else { activeTextView.setText("Inactive"); }
			}
		}

		return view;*/

	}

	public ProductListAdapter(Context context, List<Product> products) {
		super(context, R.layout.list_view_item_product, products);
		this.filterList = products;
		this.products = products;
		this.c = context;
	}

	@Override
	public Filter getFilter()
	{
		if(filter == null)
		{
			filter = new CustomFilter();
		}
		return filter;
	}

	private class CustomFilter extends Filter
	{
		@Override
		protected FilterResults performFiltering(CharSequence constraint) {

			FilterResults results = new FilterResults();

			if(constraint != null && constraint.length() > 0)
			{
				constraint = constraint.toString().toUpperCase();

				List<Product> filters = new ArrayList<Product>();

				for(int i = 0; i < filterList.size(); i++)
				{
					if(filterList.get(i).getLookupCode().toUpperCase().contains(constraint))
					{
						filters.add(filterList.get(i));
					}
				}
				results.count = filters.size();
				results.values = filters;
			}
			else
			{
				results.count = filterList.size();
				results.values = filterList;
			}

			return results;
		}

		@Override
		protected void publishResults(CharSequence constraint, FilterResults results) {
			products = (List<Product>) results.values;
			notifyDataSetChanged();
		}
	}
}
