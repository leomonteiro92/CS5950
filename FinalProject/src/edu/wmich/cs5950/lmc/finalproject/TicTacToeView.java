package edu.wmich.cs5950.lmc.finalproject;

import edu.wmich.cs5950.lmc.finalproject.model.Cell;
import edu.wmich.cs5950.lmc.finalproject.model.EmptyCell;
import edu.wmich.cs5950.lmc.finalproject.model.OCell;
import edu.wmich.cs5950.lmc.finalproject.model.XCell;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Message;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

public class TicTacToeView extends View {

	private Cell[][] cells;
	private boolean lastUsed = false;
	private int winnerPlayer = 3;
	private Paint cellPaint;

	/* ******************************************************** */
	public TicTacToeView(Context context) {
		super(context);

		cellPaint = new Paint();

		cellPaint.setColor(Color.DKGRAY);
		cellPaint.setStrokeWidth(5);

		cells = new Cell[3][3];

		int xss = getWidth() / 3;
		int yss = getHeight() / 3;

		for (int z = 0; z < 3; z++) {
			for (int i = 0; i < 3; i++) {
				cells[z][i] = new EmptyCell(xss * i, z * yss);
			}
		}
	}

	/* ******************************************************** */
	@SuppressLint("ClickableViewAccessibility")
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		int offsetX = (int) (event.getX() / (getWidth() / 3));
		int offsetY = (int) (event.getY() / (getHeight() / 3));
		drawImage(offsetX, offsetY);
		return super.onTouchEvent(event);
	}

	/* ******************************************************** */
	@Override
	protected void onDraw(Canvas canvas) {
		for (int i = 0; i < cells.length; i++) {
			for (int j = 0; j < cells[0].length; j++) {
				cells[i][j].draw(canvas, getResources(), j, i, (getWidth() + 3)
						/ cells.length, getHeight() / cells[0].length);
			}
		}

		int xs = getWidth() / 3;
		int ys = getHeight() / 3;
		for (int i = 0; i <= 3; i++) {
			canvas.drawLine(xs * i, 0, xs * i, getHeight(), cellPaint);
		}
		for (int i = 0; i <= 3; i++) {
			canvas.drawLine(0, ys * i, this.getWidth(), ys * i, cellPaint);
		}

		super.onDraw(canvas);
	}

	/* ******************************************************** */
	public void drawImage(int offsetX, int offsetY) {
		Cell cel = null;
		if (lastUsed) {
			cel = new XCell(cells[offsetX][offsetY].x,
					cells[offsetX][offsetY].y);
			lastUsed = false;
		} else {
			cel = new OCell(cells[offsetX][offsetY].x,
					cells[offsetX][offsetY].y);
			lastUsed = true;
		}

		cells[offsetY][offsetX] = cel;

		handler.sendMessage(Message.obtain(handler, 0));

		if (checkWinOrTie()) {
			if (lastUsed) {
				handler.sendMessage(Message.obtain(handler, 1));
			} else {
				handler.sendMessage(Message.obtain(handler, 2));
			}
			newMatch();
		} else if (isFull()) {
			handler.sendMessage(Message.obtain(handler, 3));
			newMatch();
		}
	}

	/* ******************************************************** */
	private boolean checkWinOrTie() {
		int counter = 0;
		Cell previous = null;

		for (int i = 0; i < cells.length; i++) {
			for (int j = 0; j < cells[0].length; j++) {
				System.out.print(cells[i][j]);
				if (!cells[i][j].equals(previous)
						|| cells[i][j] instanceof EmptyCell) {

					previous = cells[i][j];
					counter = 0;
				} else {
					counter++;
				}
				if (counter >= winnerPlayer - 1) {
					return true;
				}

			}
			previous = null;
			counter = 0;
		}

		previous = null;
		for (int j = 0; j < cells[0].length; j++) {
			for (int i = 0; i < cells.length; i++) {
				System.out.print(cells[i][j]);
				if (!cells[i][j].equals(previous)
						|| cells[i][j] instanceof EmptyCell) {
					previous = cells[i][j];
					counter = 0;
				} else {
					counter++;
				}

				if (counter >= winnerPlayer - 1) {
					return true;
				}

			}
			previous = null;
			counter = 0;
		}

		previous = null;
		for (int j = cells[0].length - 1; j >= 0; j--) {
			int yau = 0;
			for (int z = j; z < cells[0].length; z++) {
				if (!cells[yau][z].equals(previous)
						|| cells[yau][z] instanceof EmptyCell) {
					previous = cells[yau][z];
					counter = 0;
				} else {
					counter++;
				}

				if (counter >= winnerPlayer - 1) {
					return true;
				}
				yau++;
			}
			counter = 0;
			previous = null;
		}

		previous = null;
		for (int j = 0; j < cells[0].length; j++) {
			int yau = 0;
			for (int z = j; z >= 0; z--) {
				if (!cells[yau][z].equals(previous)
						|| cells[yau][z] instanceof EmptyCell) {
					previous = cells[yau][z];
					counter = 0;
				} else {
					counter++;
				}

				if (counter >= winnerPlayer - 1) {
					return true;
				}

				yau++;
			}
			counter = 0;
			previous = null;
		}
		return false;
	}

	/* ************************************************************* */
	public boolean isFull() {
		for (int i = 0; i < cells.length; i++) {
			for (int j = 0; j < cells[0].length; j++) {
				if (cells[i][j] instanceof EmptyCell) {
					return false;
				}
			}
		}
		return true;
	}

	/* ************************************************************* */

	public void newMatch() {
		cells = new Cell[3][3];

		int xss = getWidth() / 3;
		int yss = getHeight() / 3;

		for (int z = 0; z < 3; z++) {
			for (int i = 0; i < 3; i++) {
				cells[z][i] = new EmptyCell(xss * i, z * yss);
			}
		}
		handler.sendMessage(Message.obtain(handler, 0));
	}
	
	/* ******************************************************** */
	@SuppressLint("HandlerLeak")
	Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0:
				invalidate();
				break;
			case 1:
				Toast.makeText(getContext(), R.string.o_won, Toast.LENGTH_SHORT)
						.show();
				break;
			case 2:
				Toast.makeText(getContext(), R.string.x_won, Toast.LENGTH_SHORT)
						.show();
				break;
			case 3:
				Toast.makeText(getContext(), R.string.tie, Toast.LENGTH_SHORT)
						.show();
				break;
			default:
				break;
			}
		}
	};

	/* ************************************************************* */
}
