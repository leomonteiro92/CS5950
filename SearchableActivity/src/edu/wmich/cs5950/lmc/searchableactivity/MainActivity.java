package edu.wmich.cs5950.lmc.searchableactivity;

import android.app.SearchManager;
import android.content.Intent;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;

public class MainActivity extends SingleFragmentActivity {

	public static final String SEARCH_QUERY = "searchQuery";
	
	@Override
	protected Fragment createFragment() {
		return new CountryFragment();
	}
	
	@Override
	protected void onNewIntent(Intent intent) {
		CountryFragment fragment = 
				(CountryFragment)
				getSupportFragmentManager()
				.findFragmentById(R.id.fragmentContainer);
		if(Intent.ACTION_SEARCH.equals(intent.getAction())){
			String query = intent.getStringExtra(SearchManager.QUERY);
			
			PreferenceManager.getDefaultSharedPreferences(this)
			.edit()
			.putString(SEARCH_QUERY, query)
			.commit();
		}
		fragment.updateItems();
	}

}
