package appdevelopement.max.hangman;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Switch;

public class HomeFragment extends Fragment {

    SaveStateViewModel model;
    private Switch switchButton;
    private Button aboutButton;
    private Button playButton;
    static String themePictures;

    public HomeFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, final Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        createHomeFragmentToolBar(view);

        switchButton = view.findViewById(R.id.switchButton);
        aboutButton = view.findViewById(R.id.aboutButton);
        playButton = view.findViewById(R.id.playButton);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        themePictures = getString(R.string.defaultTheme);
        switchButton.setOnCheckedChangeListener((compoundButton, bChecked) -> {
            if (switchButton.isChecked()) {
                themePictures = getString(R.string.halloweenTheme);
            } else {
                themePictures = getString(R.string.defaultTheme);
            }
        });

       // if (isActive) {
      //      aboutButton.setText(getString(R.string.gameContinue));
      //  }

        aboutButton.setOnClickListener(v -> getFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, new InfoFragment(), null).addToBackStack(null).commit());

        playButton.setOnClickListener(v -> getFragmentManager().beginTransaction().replace(R.id.fragment_container,
                new GameFragment(), null).addToBackStack(null).commit());
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.info_action:
                getFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new InfoFragment()).addToBackStack(null).commit();
                break;
            case R.id.play_action:
                getFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new GameFragment()).addToBackStack(null).commit();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void createHomeFragmentToolBar(View view) {
    Toolbar toolbar = view.findViewById(R.id.app_bar);
    AppCompatActivity activity = (AppCompatActivity) getActivity();
    activity.setSupportActionBar(toolbar);
    activity.getSupportActionBar().setIcon(R.drawable.ic_hangman);
    setHasOptionsMenu(true);

    }
}

