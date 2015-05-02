package edu.wmich.cs5950.lmc.finalproject;

import android.support.v4.app.Fragment;

public class MainActivity extends SingleFragmentActivity {

	@Override
	protected Fragment createFragment() {
		return new TicTacToeFragment();
	}

    
}
