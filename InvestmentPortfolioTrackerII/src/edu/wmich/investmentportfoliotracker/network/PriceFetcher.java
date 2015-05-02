package edu.wmich.investmentportfoliotracker.network;

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

public class PriceFetcher {

	private static final String ENDPOINT = "http://finance.yahoo.com/webservice/v1/symbols";
	private static final String FEATURES = "quote";
	private static final String FORMAT = "format";
	private static final String FORMAT_TYPE = "xml";
	private static final String XML_FIELD = "field";
	private static final String XML_PRICE = "price";
	private String symbol;
	private String price;

	public PriceFetcher(String symbol) {
		Log.d("IP", "The symbol is " + symbol);
		this.symbol = symbol;
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
	}

	public String fetchPrice() {
		try {
			String url = Uri.parse(ENDPOINT).buildUpon()
					.appendEncodedPath(this.symbol).appendEncodedPath(FEATURES)
					.appendQueryParameter(FORMAT, FORMAT_TYPE).toString();
			Log.d("IP", "fetching: " + url);
			String xmlString = getUrl(url);
			XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
			XmlPullParser parser = factory.newPullParser();
			parser.setInput(new StringReader(xmlString));
			parseItems(parser);
		} catch (IOException e) {
			Log.e("IP", "Failed to fecth price ", e);
		} catch (XmlPullParserException xppe) {
			Log.e("IP", "Failed to parse items", xppe);
		}
		return price;
	}

	void parseItems(XmlPullParser parser) throws XmlPullParserException,
			IOException {

		int eventType = parser.next();

		while (eventType != XmlPullParser.END_DOCUMENT) {
			if (eventType == XmlPullParser.START_TAG
					&& XML_FIELD.equals(parser.getName())) {
				if (parser.getAttributeValue(0).equals(XML_PRICE)) {
					price = parser.nextText();
				}
			}
			eventType = parser.next();
		}
	}

}
