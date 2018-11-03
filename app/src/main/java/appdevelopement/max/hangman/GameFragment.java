package appdevelopement.max.hangman;

import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Random;

import com.squareup.picasso.Picasso;

public class GameFragment extends Fragment {

    SaveStateViewModel model;

    Random random = new Random();
    ImageView pictureView;
    private Button guess;
    TextView displayHiddenWord;
    TextView numberOfTriesLeft;
    TextView badLettersUsed;
    EditText letterGuess;
    Hangman hangman;
    String randomWord;
    char guessLetter;

    public GameFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        model = ViewModelProviders.of(getActivity()).get(SaveStateViewModel.class);
        View view = inflater.inflate(R.layout.fragment_game, container, false);
        createGameFragmentToolbar(view);

        pictureView = view.findViewById(R.id.pictureView);
        guess = view.findViewById(R.id.guessButton);
        displayHiddenWord = view.findViewById(R.id.displayHiddenWord);
        numberOfTriesLeft = view.findViewById(R.id.numberOfTriesLeft);
        badLettersUsed = view.findViewById(R.id.badLettersUsed);
        letterGuess = view.findViewById(R.id.guessLetter);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getActivity().findViewById(R.id.guessButton).setOnClickListener(this::guessButton);

        if (!model.isActiveGame()) {
            String wordsArray[] = getResources().getStringArray(R.array.playWords);
            randomWord = wordsArray[random.nextInt(wordsArray.length)];
            hangman = new Hangman(randomWord.toLowerCase());
            model.setHangman(hangman);
            model.setWord(hangman.getWord());


            // byta ut xml kod till snake_case
            //metod för toast
            //pop
            //recreate
            //första gången spelet skapas, sätt värdena från hangman i model
            model.setHiddenWord(hangman.getHiddenWord());
            model.setTriesLeft(hangman.getTriesLeft());
            model.setBadLettersUsed(hangman.getBadLettersUsed());
            model.setActiveGame(true);
        } else {
            hangman = model.getHangman();
        }



        //displaya värden som ligger i model direkt om aktiv
        displayHiddenWord.setText(model.getHiddenWord());
        numberOfTriesLeft.setText(model.getTriesLeft());
        badLettersUsed.setText(model.getBadLettersUsed());

        Picasso.get()
                .load(HomeFragment.themePictures + model.getTriesLeft() + ".gif")
                .resize(400, 400)
                .into(pictureView);
    }

    public void guessButton(View view) {

       if (letterGuess.getText().length() > 0) {
            guessLetter = letterGuess.getText().charAt(0);
            model.setGuessLetter(guessLetter);
       }

        if (multipleLetter() || !Character.isAlphabetic(guessLetter)) {
            Toast toast = Toast.makeText(getActivity(), getString(R.string.oneLetter), Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.TOP, 0, 200);
       } else if (hasUsedLetter()) {
           Toast.makeText(getActivity(), getString(R.string.usedLetter), Toast.LENGTH_SHORT).show();
        } else {

            hangman.guess(model.getGuessLetter());
            hangman.setGuessLetter(model.getGuessLetter());

            displayHiddenWord.setText(hangman.getHiddenWord());
            numberOfTriesLeft.setText(hangman.getTriesLeft());
            badLettersUsed.setText(hangman.getBadLettersUsed());
            Picasso.get()
                    .load(HomeFragment.themePictures + hangman.getTriesLeft() + ".gif")
                    .resize(400, 400)
                    .into(pictureView);

            //sätt alla värden model så att de visas när det startas om
            model.setHiddenWord(hangman.getHiddenWord());
            model.setTriesLeft(hangman.getTriesLeft());
            model.setBadLettersUsed(hangman.getBadLettersUsed());


            if (model.getWord().equals(hangman.getHiddenWord().replaceAll("\\s+", ""))) {
                Fragment result = new ResultFragment();
                getFragmentManager().beginTransaction().replace(R.id.fragment_container, result).addToBackStack(null).commit();
            } else if (Integer.parseInt(model.getTriesLeft()) == 0) {
                Fragment result = new ResultFragment();
                getFragmentManager().beginTransaction().replace(R.id.fragment_container, result).addToBackStack(null).commit();
            }
        }
    }

    private boolean multipleLetter() {
        EditText guessLetter = getView().findViewById(R.id.guessLetter);
        if (guessLetter.length() > 1) {
            return true;
        } else {
            return false;
        }
    }


    private boolean hasUsedLetter() {
        EditText editText = getView().findViewById(R.id.guessLetter);
        char guessLetter = editText.getText().charAt(0);
        if (hangman.hasUsedLetter(model.getGuessLetter())) {
            return true;
        } else {
            return false;
        }
    }



    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu, menu);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        MenuItem playAction = menu.findItem(R.id.play_action);
        MenuItem backAction = menu.findItem(R.id.back_action);
        playAction.setVisible(false);
        backAction.setVisible(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.info_action:
                getFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new InfoFragment()).addToBackStack(null).commit();
                break;
            case R.id.back_action:
                getFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new HomeFragment()).addToBackStack(null).commit();
                break;
            default:
        }
        return super.onOptionsItemSelected(item);
    }

    private void createGameFragmentToolbar(View view) {
        Toolbar toolbar = view.findViewById(R.id.app_bar);
        final AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setIcon(R.drawable.ic_hangman);
        activity.getSupportActionBar().setTitle(R.string.titleHangman);
        setHasOptionsMenu(true);
    }
}

/*
        guess.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //EditText editText = getView().findViewById(R.id.guessLetter);
                char guessLetter = letterGuess.getText().charAt(0);
                if (multipleLetter() || !Character.isAlphabetic(guessLetter)) {
                    Toast.makeText(getActivity(), "Only one letter is allowed", Toast.LENGTH_SHORT).show();
                //} else if (hasUsedLetter()) {
                    Toast.makeText(getActivity(), "Letter already used", Toast.LENGTH_SHORT).show();
                } else {
                    hangman.guess(guessLetter);
                    hangman.setGuessLetter(guessLetter);

                    displayHiddenWord.setText(hangman.getHiddenWord());
                    numberOfTriesLeft.setText(hangman.getTriesLeft());
                    badLettersUsed.setText(hangman.getBadLettersUsed());
                    Picasso.get()
                            .load(HomeFragment.themePictures + hangman.getTriesLeft() + ".gif")
                            .resize(400, 400)
                            .into(pictureView);

                    //sätt alla värden model så att de visas när det startas om
                    model.setHiddenWord(hangman.getHiddenWord());
                    model.setTriesLeft(hangman.getTriesLeft());
                    model.setBadLettersUsed(hangman.getBadLettersUsed());
                   // model.setGuessLetter(hangman.getGuessLetter());
                }
            }
        });
        */




