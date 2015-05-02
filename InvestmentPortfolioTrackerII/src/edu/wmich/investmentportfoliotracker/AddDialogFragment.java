package edu.wmich.investmentportfoliotracker;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;
import edu.wmich.investmentportfoliotracker.db.DatabaseHelper;
import edu.wmich.investmentportfoliotracker.db.PortfolioManager;
import edu.wmich.investmentportfoliotracker.model.Portfolio;

@SuppressLint("InflateParams")
public class AddDialogFragment extends DialogFragment {

	private EditText companySymbol;
	private EditText numberOfShares;
	private DatabaseHelper helper;
	private PortfolioManager pm;
	private long user;
	public static final String USER_ID = "user.id";

	public interface AddDialogCallback {
		public void detachDialog();
	}

	private AddDialogCallback callback;

	public static AddDialogFragment newInstance(long userId) {
		AddDialogFragment f = new AddDialogFragment();
		Bundle args = new Bundle();
		args.putLong(USER_ID, userId);
		f.setArguments(args);
		return f;
	}

	@Override
	@NonNull
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		LayoutInflater inflater = getActivity().getLayoutInflater();

		ViewGroup view = (ViewGroup) inflater.inflate(R.layout.add_portfolio,
				null);
		companySymbol = (EditText) view.findViewById(R.id.company_symbol);
		numberOfShares = (EditText) view.findViewById(R.id.number_of_shares);

		user = (Long) getArguments().getLong(USER_ID);

		builder.setView(view)
				.setPositiveButton(R.string.add,
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								helper = new DatabaseHelper(getActivity());
								pm = new PortfolioManager(helper);

								Portfolio p = new Portfolio();
								p.setSymbol(companySymbol.getText().toString());
								p.setNumberOfShares(Integer
										.parseInt(numberOfShares.getText()
												.toString()));
								p.setUser(user);
								pm.insertPortfolio(p);

								Toast.makeText(getActivity(),
										R.string.success,
										Toast.LENGTH_SHORT).show();
							}
						})
				.setNegativeButton(R.string.cancel_portfolio,
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								AddDialogFragment.this.getDialog().cancel();
							}
						});

		return builder.create();
	}

	@Override
	public void onDetach() {
		super.onDetach();
		try {
			callback = (AddDialogCallback) getTargetFragment();
			callback.detachDialog();
		} catch (ClassCastException e) {
			Log.e("IP", e.toString());
		}
	}

	

}
