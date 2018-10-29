package appdevelopement.max.hangman;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;

public class HomeFragment extends Fragment {

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

        themePictures = getString(R.string.defaultTheme);
        switchButton = view.findViewById(R.id.switchButton);
        switchButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean bChecked) {
                if (switchButton.isChecked()) {
                    themePictures = getString(R.string.halloweenTheme);
                } else {
                    themePictures = getString(R.string.defaultTheme);
                }
            }
        });

        aboutButton = view.findViewById(R.id.aboutButton);
        aboutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.manager.beginTransaction().replace(R.id.fragment_container,
                        new InfoFragment(), null).addToBackStack(null).commit();
            }
        });

        playButton = view.findViewById(R.id.playButton);
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // setFragment("Game", new GameFragment());
                    Fragment game = new GameFragment();
                    MainActivity.manager.beginTransaction().replace(R.id.fragment_container,game).commit();
                }
        });

        return view;
    }

    // Separate toolbar menu for each fragment
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.info_action:
                MainActivity.manager.beginTransaction().replace(R.id.fragment_container,
                        new InfoFragment(), null).addToBackStack(null).commit();
                break;
            case R.id.play_action:
                MainActivity.manager.beginTransaction().replace(R.id.fragment_container,
                        new GameFragment(), null).addToBackStack(null).commit();
                break;
            default:
                // unknown error
        }
        return super.onOptionsItemSelected(item);
    }

    public void createHomeFragmentToolBar(View v) {
    Toolbar toolbar = v.findViewById(R.id.app_bar);
    AppCompatActivity activity = (AppCompatActivity) getActivity();
    activity.setSupportActionBar(toolbar);
    activity.getSupportActionBar().setIcon(R.drawable.ic_hangman);
    setHasOptionsMenu(true);

    }
}








/*
    public void clickButton(View view) {
        if (view == view.findViewById(R.id.aboutButton)) {
            MainActivity.fragmentManager.beginTransaction().
                    replace(R.id.fragment_container, new InfoFragment(), null).commit();

            //Intent info = new Intent(this, InfoFragment.class);
            //  startActivity(info);
        } else if (view == view.findViewById(R.id.playButton)) {
            //     Intent play = new Intent(this, GameFragment.class);
            //     startActivity(play);
        }
    }
*/







