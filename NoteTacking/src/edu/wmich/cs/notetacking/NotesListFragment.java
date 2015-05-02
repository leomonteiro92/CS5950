package edu.wmich.cs.notetacking;

import java.util.ArrayList;
import edu.wmich.cs.notetacking.model.Note;
import edu.wmich.cs.notetacking.model.NoteSingleton;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class NotesListFragment extends ListFragment {

	public static final String TAG = "NoteFrag";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
		setRetainInstance(true);
		ArrayList<Note> listNotes = NoteSingleton.getInstance(getActivity())
				.getNotes();
		NotesAdapter adapter = new NotesAdapter(listNotes);
		setListAdapter(adapter);
		Log.d(TAG, "NotesListFragment - on Create");
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = super.onCreateView(inflater, container, savedInstanceState);

		ListView listView = (ListView) view.findViewById(android.R.id.list);
		registerForContextMenu(listView);

		return view;
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		Note n = ((NotesAdapter) getListAdapter()).getItem(position);
		Intent i = new Intent(getActivity(), NotesPagerActivity.class);
		i.putExtra(NotesDetailsFragment.EXTRA_NOTE_ID, n.getId());
		startActivity(i);
	}

	@Override
	public void onResume() {
		super.onResume();
		((NotesAdapter) getListAdapter()).notifyDataSetChanged();
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		super.onCreateOptionsMenu(menu, inflater);
		inflater.inflate(R.menu.notes_list, menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_add_note:
			Intent i = new Intent(getActivity(), AddNoteActivity.class);
			startActivityForResult(i, 0);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		getActivity().getMenuInflater().inflate(R.menu.note_context, menu);
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		AdapterContextMenuInfo info = (AdapterContextMenuInfo) item
				.getMenuInfo();
		int position = info.position;
		NotesAdapter adapter = (NotesAdapter) getListAdapter();
		Note note = adapter.getItem(position);

		switch (item.getItemId()) {
		case R.id.menu_item_delete_crime:
			NoteSingleton.getInstance(getActivity()).remove(note);
			adapter.notifyDataSetChanged();
			return true;
		case R.id.menu_item_send_to_sms:
			Intent smsIntent = new Intent(Intent.ACTION_VIEW);
			smsIntent.setType("vnd.android-dir/mms-sms");
			smsIntent.putExtra("sms_body",
					note.getSubject() + " : " + note.getBody());
			startActivity(smsIntent);
		case R.id.menu_item_send_to_email:
			Intent emailIntent = new Intent(Intent.ACTION_SEND);
			emailIntent.setType("text/plain");
			emailIntent.putExtra(Intent.EXTRA_SUBJECT, note.getSubject());
			emailIntent.putExtra(Intent.EXTRA_TEXT, note.getBody());
			startActivity(emailIntent);
		}

		return super.onContextItemSelected(item);
	}

	private class NotesAdapter extends ArrayAdapter<Note> {
		public NotesAdapter(ArrayList<Note> notes) {
			super(getActivity(), 0, notes);
		}

		@SuppressLint("InflateParams")
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = getActivity().getLayoutInflater().inflate(
						R.layout.single_note_item, null);
			}
			Note note = getItem(position);
			TextView operationButton = (TextView) convertView
					.findViewById(R.id.subject);
			operationButton.setText(note.getSubject());
			return convertView;
		}
	}

}
