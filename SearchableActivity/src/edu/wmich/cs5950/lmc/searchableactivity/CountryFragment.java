package edu.wmich.cs5950.lmc.searchableactivity;

import java.util.ArrayList;

import android.app.Activity;
import android.app.SearchManager;
import android.app.SearchableInfo;
import android.content.ComponentName;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.SearchView;

public class CountryFragment extends ListFragment {

	private DatabaseHelper helper;
	private CountryManager cm;
	private ArrayList<Country> countries;
	private ArrayAdapter<Country> adapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		helper = new DatabaseHelper(getActivity());
		cm = new CountryManager(helper);

		updateItems();

		setHasOptionsMenu(true);
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		super.onCreateOptionsMenu(menu, inflater);
		inflater.inflate(R.menu.search, menu);

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			// Pull out the SearchView
			MenuItem searchItem = menu.findItem(R.id.menu_item_search);
			SearchView searchView = (SearchView) searchItem.getActionView();
			// Get the data from our searchable.xml as a SearchableInfo
			SearchManager searchManager = (SearchManager) getActivity()
					.getSystemService(Context.SEARCH_SERVICE);
			ComponentName name = getActivity().getComponentName();
			SearchableInfo searchInfo = searchManager.getSearchableInfo(name);
			searchView.setSearchableInfo(searchInfo);
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_item_search:
			getActivity().onSearchRequested();
			return true;
		case R.id.menu_item_clear:
			PreferenceManager.getDefaultSharedPreferences(getActivity())
			 .edit()
			 .putString(MainActivity.SEARCH_QUERY, null)
			 .commit();
			 updateItems();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	public void updateItems() {

		Activity a = getActivity();
		if (a == null)
			countries = new ArrayList<Country>();

		String query = PreferenceManager.getDefaultSharedPreferences(a)
				.getString(MainActivity.SEARCH_QUERY, null);
		if (query != null) {
			countries = cm.getCountryByName(query);
			Log.d("SA", countries.toString());
		} else {
			countries = cm.getCountries();
		}

		adapter = new ArrayAdapter<Country>(getActivity(),
				android.R.layout.simple_list_item_1, countries);
		setListAdapter(adapter);
	}

}
