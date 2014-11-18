package us.jasonh.fragmentschallenge;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends FragmentActivity implements InputFragment.InputFragmentInterface {
	private static final String KEY_MESSAGE = "message";
	private static final String KEY_NAME = "name";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

		//Remove title bar
		requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.layout_main);

		if (savedInstanceState != null) {
			String name = savedInstanceState.getString(KEY_NAME);
			EditText editText = (EditText) findViewById(R.id.editText);
			editText.setText(name);
		}
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);

		EditText editText = (EditText) findViewById(R.id.editText);
		String name = editText.getText().toString();
		outState.putString(KEY_NAME, name);
	}

	@Override
	public void onButtonClicked(String number) {
		EditText editText = (EditText) findViewById(R.id.editText);
		String name = editText.getText().toString();
		String message = "Hello, " + name + "!  You tapped Button " + number;

		// Rather than detect orientation, just detect if Detail fragment is currently displayed
		View detailFrame = findViewById(R.id.detail);
		boolean isDual = detailFrame != null && detailFrame.getVisibility() == View.VISIBLE;

		// If in landscape mode, change the message
		if (isDual) {
			TextView textView = (TextView) findViewById(R.id.message);
			textView.setText(message);
		}
		// If in portrait mode, create an Intent
		else {
			Intent intent = new Intent();
			intent.setClass(this, DetailActivity.class);
			intent.putExtra(KEY_MESSAGE, message);
			startActivity(intent);
		}

	}

	@Override
	public LinearLayout getLayoutContainer(int id) {
		return (LinearLayout) findViewById(R.id.ll_container);
	}
}
