package edu.wmich.cs5950.lmc.finalproject.model;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import edu.wmich.cs5950.lmc.finalproject.R;

public class XCell extends Cell{
	
	public XCell(int x, int y){
		super(x, y);
	}

	@Override
	public void draw(Canvas c, Resources r, int x, int y, int w, int h) {
		Bitmap image = BitmapFactory.decodeResource(r, R.drawable.cross);
		c.drawBitmap(image, null, new Rect(x * w, y * h, (x * w) + w, (y * h)
				+ h), new Paint());
	}
	
	@Override
	public boolean equals(Object o) {
		if (o instanceof XCell) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public String toString() {
		return "X";
	}

}
