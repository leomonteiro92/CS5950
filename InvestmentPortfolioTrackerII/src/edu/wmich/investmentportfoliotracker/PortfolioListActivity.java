package edu.wmich.investmentportfoliotracker;

import android.support.v4.app.Fragment;

public class PortfolioListActivity extends SingleFragmentActivity{

	@Override
	protected Fragment createFragment() {
		return new PortfolioListFragment();
	}

	

}
