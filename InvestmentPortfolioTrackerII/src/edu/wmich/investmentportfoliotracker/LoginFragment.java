package edu.wmich.investmentportfoliotracker;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import edu.wmich.investmentportfoliotracker.db.DatabaseHelper;
import edu.wmich.investmentportfoliotracker.db.UserManager;
import edu.wmich.investmentportfoliotracker.model.User;

public class LoginFragment extends Fragment {

	private DatabaseHelper helper;
	private UserManager manager;
	private EditText inputUsername;
	private EditText inputPassword;
	private Button btnLogin;
	private TextView lnkSignIn;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		helper = new DatabaseHelper(getActivity());
		manager = new UserManager(helper);
	}

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		ViewGroup view = (ViewGroup) inflater.inflate(R.layout.login_fragment,
				container, false);

		inputUsername = (EditText) view.findViewById(R.id.username);
		inputPassword = (EditText) view.findViewById(R.id.password);
		btnLogin = (Button) view.findViewById(R.id.btnLogin);
		lnkSignIn = (TextView) view.findViewById(R.id.lnkSigIn);
		lnkSignIn.setPaintFlags(lnkSignIn.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);

		btnLogin.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				String usernameTemp = inputUsername.getText().toString();
				String passwordTemp = inputPassword.getText().toString();

				User temp = manager.getUserByUsername(usernameTemp);
				
				if (temp != null) {
					if (passwordTemp.equals(temp.getPassword())) {
						Intent i = new Intent(getActivity(),
								PortfolioListActivity.class);
						i.putExtra(AddDialogFragment.USER_ID,
								temp.getId());
						Log.d("IP", AddDialogFragment.USER_ID + " = " +temp.getId());
						startActivity(i);
					} else {
						Toast.makeText(getActivity(), R.string.error,
								Toast.LENGTH_SHORT).show();
					}
				}
			}
		});
		
		lnkSignIn.setOnClickListener(new View.OnClickListener() {	
			@Override
			public void onClick(View v) {
				Intent fwdToSignInPageIntent = new Intent(getActivity(), UserSignInActivity.class);
				startActivity(fwdToSignInPageIntent);
			}
		});

		return view;
	}

}
