package appdevelopement.max.hangman;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
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

public class GameActivity extends Fragment {

    Random random = new Random();
    ImageView pictureView;
    private Button guess;
    TextView displayBar;
    TextView textTriesLeft;
    TextView numberOfTriesLeft;
    TextView failedLetters;
    static String themePictures;
    Hangman hangman;

    public GameActivity() {}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_game, container, false);

        Toolbar toolbar = view.findViewById(R.id.app_bar);
        final AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setIcon(R.drawable.ic_hangman);
        activity.getSupportActionBar().setTitle("Hangman");
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setHasOptionsMenu(true);

        pictureView = view.findViewById(R.id.pictureView);
        guess = view.findViewById(R.id.guessButton);
        displayBar = view.findViewById(R.id.answerSequence);
        textTriesLeft = view.findViewById(R.id.textTriesLeft);
        numberOfTriesLeft = view.findViewById(R.id.numberOfTriesLeft);
        failedLetters = view.findViewById(R.id.failedLetters);

        String wordsArray[] = getResources().getStringArray(R.array.playWords);
        String randomWord = wordsArray[random.nextInt(wordsArray.length)];
        hangman = new Hangman(randomWord);
        displayBar.setText(hangman.getHiddenWord());
        textTriesLeft.setText(textTriesLeft.getText());
        numberOfTriesLeft.setText("" + hangman.getTriesLeft());
        failedLetters.setText(hangman.getBadLettersUsed());
        Picasso.get()
                .load(themePictures + hangman.getTriesLeft() + ".gif")
                .resize(400, 400)
                .into(pictureView);

        guess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editText = getView().findViewById(R.id.guessLetter);
                char guessLetter = editText.getText().charAt(0);
                if (multipleLetter() || !Character.isAlphabetic(guessLetter)) {
                    Toast.makeText(getActivity(), "Only one letter is allowed", Toast.LENGTH_SHORT).show();
                } else if (hasUsedLetter()) {
                    Toast.makeText(getActivity(), "Letter already used", Toast.LENGTH_SHORT).show();
                } else {
                    hangman.guess(guessLetter);
                    hangman.setGuessLetter(guessLetter);
                    displayBar.setText(hangman.getHiddenWord());
                    numberOfTriesLeft.setText("" + hangman.getTriesLeft());
                    failedLetters.setText(hangman.getBadLettersUsed());
                    Picasso.get()
                            .load(themePictures + hangman.getTriesLeft() + ".gif")
                            .resize(400, 400)
                            .into(pictureView);
                }
            }
        });
        return view;
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
        if (hangman.hasUsedLetter(guessLetter)) {
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
        MenuItem item = menu.findItem(R.id.play_action);
        item.setVisible(false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(false);
    }
}
        /*
      public void guess(View view) {
          EditText editText = view.findViewById(R.id.guessLetter);
          char guessLetter = editText.getText().charAt(0);
          if (multipleLetter() || !Character.isAlphabetic(guessLetter)) {
              Toast.makeText(getApplicationContext(), "Only one letter is allowed", Toast.LENGTH_SHORT).show();
          } else if (hasUsedLetter()) {
              Toast.makeText(getApplicationContext(), "Letter already used", Toast.LENGTH_SHORT).show();
          } else {
              hangman.guess(guessLetter);
              hangman.setGuessLetter(guessLetter);
              displayBar.setText(hangman.getHiddenWord());
              numberOfTriesLeft.setText("" + hangman.getTriesLeft());
              failedLetters.setText(hangman.getBadLettersUsed());
              Picasso.get()
                      .load(themePictures + hangman.getTriesLeft() + ".gif")
                      .resize(400, 400)
                      .into(imageView);
          }
    }
    */








