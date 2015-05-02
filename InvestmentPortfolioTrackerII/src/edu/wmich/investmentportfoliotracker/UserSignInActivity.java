package edu.wmich.investmentportfoliotracker;

import android.support.v4.app.Fragment;

public class UserSignInActivity extends SingleFragmentActivity{

	@Override
	protected Fragment createFragment() {
		return new UserSignInFragment();
	}

}
