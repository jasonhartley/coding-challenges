package us.jasonh.fragmentschallenge;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class DetailFragment extends Fragment {

	private static final String KEY_MESSAGE = "message";

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.fragment_detail, container, false);
		TextView textView = (TextView) view.findViewById(R.id.message);

		Bundle args = getArguments();
		String message = (args != null) ? args.getString(KEY_MESSAGE) : "";

		textView.setText(message);

		return view;
	}

}
