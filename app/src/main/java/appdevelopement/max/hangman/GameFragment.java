package appdevelopement.max.hangman;

import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
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

    int hasGuess = 0;
    Random random = new Random();
    ImageView pictureView;
    private Button guess;
    private Button back;
    TextView displayHiddenWord;
    TextView textTriesLeft;
    TextView numberOfTriesLeft;
    TextView badLettersUsed;
    EditText letterGuess;


    Hangman hangman;
    String randomWord;

    public GameFragment() {}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        model = ViewModelProviders.of(getActivity()).get(SaveStateViewModel.class);
        View view = inflater.inflate(R.layout.fragment_game, container, false);
        createGameFragmentToolbar(view);

            pictureView = view.findViewById(R.id.pictureView);
            guess = view.findViewById(R.id.guessButton);
            displayHiddenWord = view.findViewById(R.id.answerSequence);
            //textTriesLeft = view.findViewById(R.id.textTriesLeft);
            numberOfTriesLeft = view.findViewById(R.id.numberOfTriesLeft);
            badLettersUsed = view.findViewById(R.id.failedLetters);
            letterGuess = view.findViewById(R.id.guessLetter);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getActivity().findViewById(R.id.guessButton).setOnClickListener(this::guessButton);
        Log.d("active", "b" + model.getHiddenWord());

        if (model.getWord().equals("")) {
            String wordsArray[] = getResources().getStringArray(R.array.playWords);
            randomWord = wordsArray[random.nextInt(wordsArray.length)];
            hangman = new Hangman(randomWord);
            model.setWord(hangman.getWord());

            // första gången det skapas, sätt värdena från hangman i model
            model.setHiddenWord(hangman.getHiddenWord());
            model.setTriesLeft(hangman.getTriesLeft());
            model.setBadLettersUsed(hangman.getBadLettersUsed());
        }

            //displaya värden som ligger i model direkt om aktiv
            displayHiddenWord.setText(model.getHiddenWord());
            numberOfTriesLeft.setText(model.getTriesLeft());
            badLettersUsed.setText(model.getBadLettersUsed());



        //textTriesLeft.setText(textTriesLeft.getText());
            Picasso.get()
                    .load(HomeFragment.themePictures + model.getTriesLeft() + ".gif")
                    .resize(400, 400)
                    .into(pictureView);
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
    }

        public void guessButton (View v) {
            Log.d("theguessletter", "" + letterGuess.getText().charAt(0));

            char guessLetter = letterGuess.getText().charAt(0);
          /*
            if (multipleLetter() || !Character.isAlphabetic(guessLetter)) {
                Toast.makeText(getActivity(), "Only one letter is allowed", Toast.LENGTH_SHORT).show();
                } else if (hasUsedLetter()) {
                Toast.makeText(getActivity(), "Letter already used", Toast.LENGTH_SHORT).show();
            } else {
            */

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
                model.setGuessLetter(guessLetter);


            }

/*
        private boolean multipleLetter() {
        EditText guessLetter = getView().findViewById(R.id.guessLetter);
        if (guessLetter.length() > 1) {
            return true;
        } else {
            return false;
        }
    }
*/


/*
    private boolean hasUsedLetter() {
        EditText editText = getView().findViewById(R.id.guessLetter);
        char guessLetter = editText.getText().charAt(0);
        if (hangman.hasUsedLetter(guessLetter)) {
            return true;
        } else {
            return false;
        }
    }
*/


    // Separate toolbar menu for each fragment
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu, menu);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        MenuItem playaction = menu.findItem(R.id.play_action);
        MenuItem backaction = menu.findItem(R.id.back_action);
        playaction.setVisible(false);
        backaction.setVisible(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        FragmentTransaction transaction = MainActivity.manager.beginTransaction();
        switch (item.getItemId()) {
            case R.id.info_action:
                transaction.replace(R.id.fragment_container,
                        new InfoFragment(), null).addToBackStack(null).commit();
                break;
            case R.id.back_action:
                transaction.replace(R.id.fragment_container,
                        new HomeFragment(), null).addToBackStack(null).commit();
                break;
            default:
        }
        return super.onOptionsItemSelected(item);
    }

    private void createGameFragmentToolbar(View v) {
        Toolbar toolbar = v.findViewById(R.id.app_bar);
        final AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setIcon(R.drawable.ic_hangman);
        activity.getSupportActionBar().setTitle("Hangman");
        setHasOptionsMenu(true);
    }

    // SavedInstanceState för var fragment

    @Override
    public void onSaveInstanceState(Bundle outState) {
     /*
        super.onSaveInstanceState(outState);
        outState.putString("randomWord", randomWord);
        outState.putCharSequence("displayHiddenWord", displayHiddenWord.getText());
        outState.putCharSequence("numberOfTriesLeft", numberOfTriesLeft.getText());
        outState.putCharSequence("badLettersUsed", badLettersUsed.getText());
        Log.d("outstateGame","Ze savedInstance Game has been ran");
        */
    }


        /*
        Log.d("OnActivityCreatedGame","Ze Activity Null has been created");

            Log.d("OnActivityCreatedGame","Ze Activity notNull saved has been created");

            CharSequence hiddenWord = savedInstanceState.getCharSequence("displayHiddenWord");
            displayHiddenWord.setText(hiddenWord);

            CharSequence triesLeft = savedInstanceState.getCharSequence("numberOfTriesLeft");
            numberOfTriesLeft.setText(triesLeft);

            CharSequence failLetters = savedInstanceState.getCharSequence("badLettersUsed");
            badLettersUsed.setText(failLetters);

            Picasso.get()
                    .load(HomeFragment.themePictures + triesLeft + ".gif")
                    .resize(400, 400)
                    .into(pictureView);
        */
    }













