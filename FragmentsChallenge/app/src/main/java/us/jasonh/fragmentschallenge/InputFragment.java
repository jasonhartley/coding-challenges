package us.jasonh.fragmentschallenge;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

public class InputFragment extends Fragment {
	InputFragmentInterface mCallback;

	public interface InputFragmentInterface {
		public void onButtonClicked(String number);
		public LinearLayout getLayoutContainer(int id);
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);

		// This makes sure that the container activity has implemented
		// the callback interface. If not, it throws an exception.
		try {
			mCallback = (InputFragmentInterface) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString()
					+ " must implement OnButtonClickListener.");
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		return inflater.inflate(R.layout.fragment_input, container, false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		int buttonCount = 5;

		// Create layout to be used for each button
		int pad = dpAsPixels(40);
		int margin = dpAsPixels(5);
		LinearLayout layoutContainer = mCallback.getLayoutContainer(R.id.ll_container);
		LinearLayout.LayoutParams buttonParam = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.WRAP_CONTENT,
				LinearLayout.LayoutParams.WRAP_CONTENT
		);
		buttonParam.setMargins(margin, 0, 0, 0);

		// Create the buttons
		for (int i = 0; i < buttonCount; i++) {
			Button button = new Button(getActivity());
			button.setText("Button " + (i + 1));
			button.setLayoutParams(buttonParam);
			button.setPadding(pad, 0, pad, 0);

			layoutContainer.addView(button);

			final String number = String.valueOf(i + 1);

			button.setOnClickListener(new ButtonClickLister(number));
		}
	}

	private class ButtonClickLister implements View.OnClickListener {
		private String mNumber;

		public ButtonClickLister(String number) {
			mNumber = number;
		}

		@Override
		public void onClick(View view) {
			mCallback.onButtonClicked(mNumber);
		}
	}

	private int dpAsPixels(int dp) {
		float scale = getResources().getDisplayMetrics().density;
		return (int) (dp * scale + 0.5f);
	}
}
