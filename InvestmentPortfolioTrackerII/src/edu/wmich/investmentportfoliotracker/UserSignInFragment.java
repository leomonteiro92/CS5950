package edu.wmich.investmentportfoliotracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import edu.wmich.investmentportfoliotracker.db.DatabaseHelper;
import edu.wmich.investmentportfoliotracker.db.UserManager;
import edu.wmich.investmentportfoliotracker.model.User;

public class UserSignInFragment extends Fragment {

	private DatabaseHelper helper;
	private UserManager manager;
	
	private EditText inputUsername;
	private EditText inputPwd;
	private EditText inputConfirmPwd;
	private Button btnSignUp;
	private User newUser;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		helper = new DatabaseHelper(getActivity());
		manager = new UserManager(helper);
		newUser = new User();
	}

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		ViewGroup view = (ViewGroup) inflater.inflate(R.layout.add_user,
				container, false);

		inputUsername = (EditText) view.findViewById(R.id.edit_username);
		inputPwd = (EditText) view.findViewById(R.id.edit_password);
		inputConfirmPwd = (EditText) view.findViewById(R.id.confirm_pwd);
		btnSignUp = (Button) view.findViewById(R.id.btn_register);
		
		btnSignUp.setOnClickListener(new View.OnClickListener(){

			@Override
			public void onClick(View arg0) {
				String passwordTemp = inputPwd.getText().toString();
				String passwordConf = inputConfirmPwd.getText().toString();
				if(passwordTemp.equals(passwordConf)){
					newUser.setUsername(inputUsername.getText().toString());
					newUser.setPassword(passwordTemp);
					manager.insertUser(newUser);
					Intent fwdToLoginPage = new Intent(getActivity(), LoginActivity.class);
					startActivity(fwdToLoginPage);
					Toast.makeText(getActivity(), R.string.success, Toast.LENGTH_SHORT)
					.show();
				} else {
					Toast.makeText(getActivity(), R.string.error_add_user, Toast.LENGTH_SHORT)
					.show();
				}
			}
			
		});

		return view;
	}

}
