package edu.wmich.cs.notetacking;

import java.util.UUID;

import edu.wmich.cs.notetacking.model.Note;
import edu.wmich.cs.notetacking.model.NoteSingleton;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class NotesDetailsFragment extends Fragment{
	
	public static final String EXTRA_NOTE_ID = "edu.wmich.edu.notes.get_id";
	
	private Note mNote;
	private TextView noteSubject;
	private TextView noteBody;
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		UUID id = (UUID) getArguments().getSerializable(EXTRA_NOTE_ID);
		mNote = NoteSingleton.getInstance(getActivity()).getById(id);
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		ViewGroup view = (ViewGroup) inflater.inflate(R.layout.note_details_fragment,
				container, false);
		
		noteSubject = (TextView) view.findViewById(R.id.txt_subject_details);
		noteBody = (TextView) view.findViewById(R.id.txt_body_details);
		
		noteSubject.setText(mNote.getSubject());
		noteBody.setText(mNote.getBody());
		
		return view;
	}
	
	public static NotesDetailsFragment newInstance(UUID noteId){
		Bundle args = new Bundle();
		args.putSerializable(EXTRA_NOTE_ID, noteId);
		NotesDetailsFragment fragment = new NotesDetailsFragment();
		fragment.setArguments(args);
		return fragment;
	}

}
