package appdevelopement.max.hangman;

import android.arch.lifecycle.ViewModelProviders;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Random;

import com.squareup.picasso.Picasso;

public class GameFragment extends Fragment {

    SaveStateViewModel model;
    Hangman hangman;
    Random random = new Random();
    ImageView pictureView;
    TextView hiddenWord;
    TextView triesLeft;
    TextView badLettersUsed;
    EditText userInput;

    public GameFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        model = ViewModelProviders.of(getActivity()).get(SaveStateViewModel.class);
        View view = inflater.inflate(R.layout.fragment_game, container, false);
        createGameFragmentToolbar(view);

        pictureView = view.findViewById(R.id.pictureView);
        hiddenWord = view.findViewById(R.id.displayHiddenWord);
        triesLeft = view.findViewById(R.id.numberOfTriesLeft);
        badLettersUsed = view.findViewById(R.id.badLettersUsed);
        userInput = view.findViewById(R.id.guessLetter);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getActivity().findViewById(R.id.guessButton).setOnClickListener(this::guessButton);

        if (!model.isActiveGame()) {
            String wordsArray[] = getResources().getStringArray(R.array.playWords);
            String randomWord = wordsArray[random.nextInt(wordsArray.length)];
            hangman = new Hangman(randomWord.toLowerCase());
            model.setHangman(hangman);
            model.setActiveGame(true);
        } else {
            hangman = model.getHangman();
        }

        hiddenWord.setText(model.getHangman().getHiddenWord());
        triesLeft.setText(model.getHangman().getTriesLeft());
        badLettersUsed.setText(model.getHangman().getBadLettersUsed());
        loadPicture();
    }

    public void guessButton(View view) {
        char guessLetter;
        if (multipleLetter()) {
            makeToast(R.string.oneLetter);
        } else if (isInput()) {
            guessLetter = userInput.getText().charAt(0);
            if (!Character.isAlphabetic(guessLetter)) {
                makeToast(R.string.oneLetter);
            } else {
                model.setGuessLetter(guessLetter);
                if (hasUsedLetter()) {
                    makeToast(R.string.usedLetter);
                } else {
                    hangman.setGuessLetter(guessLetter);
                    hangman.guess(guessLetter);
                    hiddenWord.setText(hangman.getHiddenWord());
                    triesLeft.setText(hangman.getTriesLeft());
                    badLettersUsed.setText(hangman.getBadLettersUsed());
                    loadPicture();
                    if (hangman.getWord().equals(hangman.getHiddenWord().replaceAll("\\s+", ""))) {
                        newResultFragment();
                    } else if (Integer.parseInt(hangman.getTriesLeft()) == 0) {
                        newResultFragment();
                    }
                }
            }
        }
    }

    // byta ut xml kod till snake_case
    //pop
    //recreate


    private boolean isInput() {
         if(userInput.getText().length() > 0) {
             return true;
         }
         return false;
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
        if (hangman.hasUsedLetter(model.getGuessLetter())) {
            return true;
        } else {
            return false;
        }
    }

    private void loadPicture() {
        Picasso.get()
                .load(HomeFragment.themePictures + model.getHangman().getTriesLeft() + ".gif")
                .resize(400, 400)
                .into(pictureView);
    }

    private void makeToast(int toastText) {
        Toast.makeText(getActivity(), getString(toastText), Toast.LENGTH_SHORT).show();
    }

    private void newResultFragment() {
        Fragment result = new ResultFragment();
        getFragmentManager().beginTransaction().replace(R.id.fragment_container, result).commit();
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
                char guessLetter = userInput.getText().charAt(0);
                if (multipleLetter() || !Character.isAlphabetic(guessLetter)) {
                    Toast.makeText(getActivity(), "Only one letter is allowed", Toast.LENGTH_SHORT).show();
                //} else if (hasUsedLetter()) {
                    Toast.makeText(getActivity(), "Letter already used", Toast.LENGTH_SHORT).show();
                } else {
                    hangman.guess(guessLetter);
                    hangman.setGuessLetter(guessLetter);

                    hiddenWord.setText(hangman.getHiddenWord());
                    triesLeft.setText(hangman.getTriesLeft());
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




