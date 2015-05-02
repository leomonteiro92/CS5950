package cs.wmich.cs5950.weatherapp.network;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import android.net.Uri;
import android.util.Log;

public class WeatherFetchr {

	private static final String ENDPOINT = "http://api.wunderground.com/api/";
	private static final String API_KEY = "c136512e6e075837";
	private static final String FEATURES = "conditions";
	private static final String FORMAT = "xml";
	private static final String XML_TEMP = "temp_f";
	private String city;
	private String weather;

	public WeatherFetchr(String city) {
		this.city = city.replace(" ", "_").replace(",", "/");
		Log.d("WF", "The city is: " + city);
	}

	byte[] getUrlBytes(String urlSpec) throws IOException {
		URL url = new URL(urlSpec);
		HttpURLConnection con = (HttpURLConnection) url.openConnection();

		try {
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			InputStream in = con.getInputStream();

			if (con.getResponseCode() != HttpURLConnection.HTTP_OK) {
				return null;
			}

			int bytesRead = 0;
			byte[] buffer = new byte[1024];
			while ((bytesRead = in.read(buffer)) > 0) {
				out.write(buffer, 0, bytesRead);
			}
			out.close();
			return out.toByteArray();

		} finally {
			con.disconnect();
		}
	}

	public String getUrl(String urlSpec) throws IOException {
		return new String(getUrlBytes(urlSpec));
	}// End of method getUrl

	public String fetchWeather() {
		try {
			String url = Uri.parse(ENDPOINT).buildUpon()
					.appendEncodedPath(API_KEY)
					.appendEncodedPath(FEATURES)
					.appendEncodedPath("q")
					.appendEncodedPath(this.city + "." + FORMAT).build()
					.toString();
			Log.d("WF", "fetching: " + url);
			String xmlString = getUrl(url);
			XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
			XmlPullParser parser = factory.newPullParser();
			parser.setInput(new StringReader(xmlString));
			parseItems(parser);	
			Log.d("WF", "The weather is" + weather);
		} catch (IOException e) {
			Log.e("WF", "Failed to fecth weather ", e);
		} catch (XmlPullParserException xppe) {
			Log.e("WF", "Failed to parse items", xppe);
		}
		return weather;
	}

	void parseItems(XmlPullParser parser)
			throws XmlPullParserException, IOException {

		int eventType = parser.next();

		while (eventType != XmlPullParser.END_DOCUMENT) {
			if (eventType == XmlPullParser.START_TAG
					&& XML_TEMP.equals(parser.getName())) {
				weather = parser.nextText();
				Log.d("WF", "The weather is set to "+ weather);
			}
			eventType = parser.next();
		}
	}// End of method parseItems

}
