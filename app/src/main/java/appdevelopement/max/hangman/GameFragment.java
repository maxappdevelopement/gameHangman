package appdevelopement.max.hangman;

import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
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

        pictureView = view.findViewById(R.id.picture_view);
        hiddenWord = view.findViewById(R.id.hidden_word);
        triesLeft = view.findViewById(R.id.number_of_tries_left);
        badLettersUsed = view.findViewById(R.id.bad_letters_used);
        userInput = view.findViewById(R.id.guess_letter);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getActivity().findViewById(R.id.guess_button).setOnClickListener(this::guessButton);

        if (!model.isActiveGame()) {
            String wordsArray[] = getResources().getStringArray(R.array.play_words);
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
            makeToast(R.string.one_letter);
        } else if (isInput()) {
            guessLetter = userInput.getText().charAt(0);
            if (!Character.isAlphabetic(guessLetter)) {
                makeToast(R.string.one_letter);
            } else {
                model.setGuessLetter(guessLetter);
                if (hasUsedLetter()) {
                    makeToast(R.string.used_letter);
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

    // recreate för att tema ska ändra sig direkt till orange
    // layouta när du vänder på skiten?
    // stack på ngt?

    private boolean isInput() {
         return (userInput.getText().length() > 0);
     }

    private boolean multipleLetter() {
        EditText guessLetter = getView().findViewById(R.id.guess_letter);
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
        Toast toast = Toast.makeText(getActivity(), getString(toastText), Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.TOP, 0, 565);
        toast.show();
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
        activity.getSupportActionBar().setTitle(R.string.title_hangman);
        setHasOptionsMenu(true);
    }
}




