package cs.wmich.cs5950.weatherapp;

import android.support.v4.app.Fragment;


public class WeatherActivity extends SingleFragmentActivity {

	@Override
	protected Fragment createFragment() {
		return new WeatherFragment();
	}
}
