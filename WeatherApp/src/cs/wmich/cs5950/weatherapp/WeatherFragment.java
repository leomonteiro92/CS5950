package cs.wmich.cs5950.weatherapp;

import cs.wmich.cs5950.weatherapp.network.WeatherFetchr;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class WeatherFragment extends Fragment {

	private TextView currentWeather;
	private Button btnCheckWeather;
	private EditText inputCity;
	private String weather;
	private String city;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRetainInstance(true);
	}

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		ViewGroup view = (ViewGroup) inflater.inflate(R.layout.main_fragment,
				container, false);

		inputCity = (EditText) view.findViewById(R.id.input_city);
		btnCheckWeather = (Button) view.findViewById(R.id.btn_check_weather);
		currentWeather = (TextView) view.findViewById(R.id.txt_weather);

		btnCheckWeather.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				city = inputCity.getText().toString();
				new FetchWeatherTask().execute();
				if (weather != null) {
					currentWeather.setText(weather + "\u00b0 F");
				}
			}
		});

		return view;
	}

	private class FetchWeatherTask extends AsyncTask<Void, Void, String> {

		@Override
		protected String doInBackground(Void... params) {
			return new WeatherFetchr(city).fetchWeather();
		}

		@Override
		protected void onPostExecute(String result) {
			weather = result;
		}

	}

}
