package appdevelopement.max.hangman;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

public class InfoFragment extends Fragment {

    public InfoFragment() {}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_info, container, false);
        createInfoFragmentToolbar(view);
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.back_action:
                getFragmentManager().popBackStack();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        MenuItem playaction = menu.findItem(R.id.play_action);
        MenuItem infoaction = menu.findItem(R.id.info_action);
        MenuItem backaction = menu.findItem(R.id.back_action);
        playaction.setVisible(false);
        infoaction.setVisible(false);
        backaction.setVisible(true);
    }

    private void createInfoFragmentToolbar(View view) {
        Toolbar toolbar = view.findViewById(R.id.app_bar);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setIcon(R.drawable.ic_hangman);
        activity.getSupportActionBar().setTitle(R.string.title_about);
        setHasOptionsMenu(true);
    }
}

