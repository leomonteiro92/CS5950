package edu.wmich.investmentportfoliotracker;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.ListFragment;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import edu.wmich.investmentportfoliotracker.AddDialogFragment.AddDialogCallback;
import edu.wmich.investmentportfoliotracker.db.DatabaseHelper;
import edu.wmich.investmentportfoliotracker.db.PortfolioManager;
import edu.wmich.investmentportfoliotracker.model.Portfolio;
import edu.wmich.investmentportfoliotracker.network.PriceFetcher;

public class PortfolioListFragment extends ListFragment implements
		AddDialogCallback {

	private DatabaseHelper helper;
	private PortfolioManager pm;
	private long userId;
	private String mSymbol;
	private PortfolioAdapter adapter;
	private Double total = 0.0;
	private TextView txtTotal;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
		setRetainInstance(true);
		helper = new DatabaseHelper(getActivity());
		pm = new PortfolioManager(helper);
		userId = getActivity().getIntent().getExtras()
				.getLong(AddDialogFragment.USER_ID);
		refreshOrCreateList();
	}

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = super.onCreateView(inflater, container, savedInstanceState);

		ListView listView = (ListView) view.findViewById(android.R.id.list);
		
		View footer = (View) inflater.inflate(R.layout.footer_list, null);
		txtTotal = (TextView) footer.findViewById(R.id.txt_total_value);
		
		listView.addFooterView(footer);
		
		registerForContextMenu(listView);

		return view;
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		getActivity().getMenuInflater().inflate(R.menu.delete_portfolio_menu,
				menu);
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		AdapterContextMenuInfo info = (AdapterContextMenuInfo) item
				.getMenuInfo();
		int position = info.position;
		PortfolioAdapter adapter = (PortfolioAdapter) getListAdapter();
		Portfolio p = adapter.getItem(position);

		switch (item.getItemId()) {
		case R.id.menu_delete_portfolio:
			pm.deletePortfolio(p);
			refreshOrCreateList();
			return true;
		}
		return super.onContextItemSelected(item);
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		super.onCreateOptionsMenu(menu, inflater);
		inflater.inflate(R.menu.add_portfolio, menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_add_portfolio:
			long userId = getActivity().getIntent().getExtras()
					.getLong(AddDialogFragment.USER_ID);
			FragmentManager fm = getActivity().getSupportFragmentManager();
			AddDialogFragment dialog = AddDialogFragment.newInstance(userId);
			dialog.setTargetFragment(this, 0);
			dialog.show(fm, "AddPortfolioDialog");
			return true;
		case R.id.menu_log_out:
			Intent logOutIntent = new Intent(getActivity(), LoginActivity.class);
			startActivity(logOutIntent);
			return true;
		case R.id.menu_refresh:
			refreshOrCreateList();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	private class PortfolioAdapter extends ArrayAdapter<Portfolio> {

		public PortfolioAdapter(ArrayList<Portfolio> p) {
			super(getActivity(), 0, p);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = getActivity().getLayoutInflater().inflate(
						R.layout.list_item_portfolio, null);
			}
			Portfolio p = getItem(position);

			final TextView itemSymbol = (TextView) convertView
					.findViewById(R.id.txt_item_symbol);
			mSymbol = p.getSymbol();
			itemSymbol.setText(mSymbol);

			final TextView itemNoShares = (TextView) convertView
					.findViewById(R.id.txt_item_no_shares);
			itemNoShares.setText(Integer.toString(p.getNumberOfShares()));

			final TextView itemPrice = (TextView) convertView
					.findViewById(R.id.txt_item_price);

			final TextView itemTotal = (TextView) convertView
					.findViewById(R.id.txt_item_total);
			new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						final String price = new PriceFetcher(itemSymbol
								.getText().toString()).fetchPrice();

						itemPrice.post(new Runnable() {

							@Override
							public void run() {
								itemPrice.setText(price);
								Double mtotal = Double.parseDouble(price)
										* Integer.parseInt(itemNoShares
												.getText().toString());
								itemTotal.setText(mtotal.toString());
								total += mtotal;
								txtTotal.setText(total.toString());
							}
						});
					} catch (Exception e) {

					}
				}

			}).start();
			return convertView;
		}
	}

	@Override
	public void onResume() {
		super.onResume();
		((PortfolioAdapter) getListAdapter()).notifyDataSetChanged();
	}

	@Override
	public void detachDialog() {
		refreshOrCreateList();
	}

	public void refreshOrCreateList() {
		ArrayList<Portfolio> portfolios = pm.getPortfoliosByUser(userId);
		if (adapter == null) {
			adapter = new PortfolioAdapter(portfolios);
			setListAdapter(adapter);
		} else {
			adapter.clear();
			adapter = new PortfolioAdapter(portfolios);
			setListAdapter(adapter);
		}
	}

}
