package us.jasonh.fragmentschallenge;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Window;

public class DetailActivity extends FragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		//Remove title bar
		requestWindowFeature(Window.FEATURE_NO_TITLE);

		// Add the Detail fragment and pass Intent Extras
		DetailFragment detailFragment = new DetailFragment();
		detailFragment.setArguments(getIntent().getExtras());
		getSupportFragmentManager()
				.beginTransaction()
				.add(android.R.id.content, detailFragment)
				.commit();
	}
}



