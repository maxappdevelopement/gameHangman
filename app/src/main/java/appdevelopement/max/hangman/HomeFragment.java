package appdevelopement.max.hangman;

import android.arch.lifecycle.ViewModelProviders;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
import android.widget.Switch;

public class HomeFragment extends Fragment {

    SaveStateViewModel model;
    private Switch switchButton;
    private Button aboutButton;
    private Button playButton;
    static String themePictures;
    static int themeColor;

    public HomeFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, final Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        createHomeFragmentToolBar(view);

        switchButton = view.findViewById(R.id.switch_button);
        aboutButton = view.findViewById(R.id.about_button);
        playButton = view.findViewById(R.id.play_button);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        model = ViewModelProviders.of(getActivity()).get(SaveStateViewModel.class);

        if (model.isActiveGame()) {
            playButton.setText(getString(R.string.game_continue));
        }

        if (model.isActiveTheme()) {
            switchButton.setChecked(true);
        } else {
            switchButton.setChecked(false);
            themePictures = getString(R.string.default_theme);
        }

        switchButton.setOnCheckedChangeListener((compoundButton, bChecked) -> {
            if (switchButton.isChecked()) {
                model.setActiveTheme(true);
                themePictures = getString(R.string.halloween_theme);
                themeColor = getActivity().getColor(R.color.halloweenColor);
                getActivity().recreate();

            } else {
                model.setActiveTheme(false);
                themePictures = getString(R.string.default_theme);
                themeColor = getActivity().getColor(R.color.defaultColor);
                getActivity().recreate();
            }
        });

        aboutButton.setOnClickListener(v -> getFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, new InfoFragment(), null).addToBackStack(null).commit());

        playButton.setOnClickListener(v ->
                getFragmentManager().beginTransaction().replace(R.id.fragment_container,
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
                model.setActiveGame(false);
                getFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new GameFragment()).addToBackStack(null).commit();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void createHomeFragmentToolBar(View view) {
    Toolbar toolbar = view.findViewById(R.id.custom_toolbar);
    if (themeColor==0) {
        themeColor = getActivity().getColor(R.color.defaultColor);
        toolbar.setBackground(new ColorDrawable(getActivity().getColor(R.color.defaultColor)));
    } else {
        toolbar.setBackground(new ColorDrawable(themeColor));
    }
    AppCompatActivity activity = (AppCompatActivity) getActivity();
    activity.setSupportActionBar(toolbar);
    activity.getSupportActionBar().setIcon(R.drawable.ic_hangman);
    setHasOptionsMenu(true);
    }

}

