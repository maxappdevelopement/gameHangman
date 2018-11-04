package appdevelopement.max.hangman;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class ResultFragment extends Fragment {

    SaveStateViewModel model;
    TextView wonOrLost;
    TextView wordWas;
    TextView scoreTriesLeft;
    Button backMenu;

    public ResultFragment() {}

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        model = ViewModelProviders.of(getActivity()).get(SaveStateViewModel.class);
        View view = inflater.inflate(R.layout.fragment_result, container, false);
        createResultFragmentToolBar(view);

        backMenu = view.findViewById(R.id.backMenu);
        wonOrLost = view.findViewById(R.id.wonOrLost);
        wordWas = view.findViewById(R.id.containsWordWas);
        scoreTriesLeft = view.findViewById(R.id.containsScoreTriesLeft);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (Integer.parseInt(model.getTriesLeft())>0) {
            wonOrLost.setText(getResources().getString(R.string.youWon));
            model.setActiveGame(false);
        } else {
            wonOrLost.setText(getResources().getString(R.string.youLost));
            model.setActiveGame(false);
        }

        wordWas.setText(model.getWord().replaceAll("\\s+","").toUpperCase());
        scoreTriesLeft.setText(model.getTriesLeft());

        backMenu.setOnClickListener(v -> getFragmentManager().beginTransaction().replace(R.id.fragment_container,
                new HomeFragment(), null).addToBackStack(null).commit());
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
            default:
                // unknown error
        }
        return super.onOptionsItemSelected(item);
    }

    public void createResultFragmentToolBar(View view) {
        Toolbar toolbar = view.findViewById(R.id.app_bar);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setIcon(R.drawable.ic_hangman);
        activity.getSupportActionBar().setTitle(R.string.titleResult);
        setHasOptionsMenu(true);

    }
}

