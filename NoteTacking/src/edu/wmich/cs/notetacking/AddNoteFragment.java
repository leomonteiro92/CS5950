package edu.wmich.cs.notetacking;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import edu.wmich.cs.notetacking.model.Note;
import edu.wmich.cs.notetacking.model.NoteSingleton;

public class AddNoteFragment extends Fragment {

	public static final String TAG = "NoteFrag";
	public static final String EXTRA_NOTE_ID = "edu.wmich.edu.note.get_id";
	private Note myNote;
	private EditText subject;
	private EditText body;
	private Button btnSave;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		ViewGroup view = (ViewGroup) inflater.inflate(R.layout.note_fragment,
				container, false);

		subject = (EditText) view.findViewById(R.id.edit_subject);
		body = (EditText) view.findViewById(R.id.edit_body);
		btnSave = (Button) view.findViewById(R.id.btn_save);

		btnSave.setOnClickListener(new OnClickListener() {
			@SuppressLint("ShowToast")
			@Override
			public void onClick(View arg0) {
				myNote = new Note();
				String subj = subject.getText().toString();
				String bdy = body.getText().toString();
				
				if(!subj.trim().equals("") || !subj.trim().equals("")){
					myNote.setSubject(subj);
					myNote.setBody(bdy);
					Log.d(TAG, "AddNoteFragment - " + myNote.toString());
					NoteSingleton.getInstance(getActivity()).addNote(myNote);
					if (NoteSingleton.getInstance(getActivity()).saveNotes()) {
						if(NavUtils.getParentActivityName(getActivity())!= null){
							NavUtils.navigateUpFromSameTask(getActivity());
						}
					} else {
						Toast.makeText(getActivity(), "Cannot save!",
								Toast.LENGTH_SHORT);
					}
				} else {
					Toast.makeText(getActivity(), "Subject or body are empty!",
							Toast.LENGTH_SHORT);
				}//End if subject and body are null
				
			}
		});

		return view;
	}

	@Override
	public void onPause() {
		super.onPause();
		Log.d(TAG, "AddNoteFragment - O OnPause foi chamado");
		NoteSingleton.getInstance(getActivity()).saveNotes();
	}

}
