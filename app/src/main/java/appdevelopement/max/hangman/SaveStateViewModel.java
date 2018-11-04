package appdevelopement.max.hangman;

import android.arch.lifecycle.ViewModel;

import java.util.ArrayList;

public class SaveStateViewModel extends ViewModel {

    private Hangman hangman;
    private boolean isActiveGame = false;
    private char guessLetter;

    public void setHangman(Hangman hangman) {
        this.hangman = hangman;
    }

    public Hangman getHangman() {
        return hangman;
    }

    public char getGuessLetter() {
        return guessLetter;
    }

    public void setGuessLetter(char guessLetter) {
        this.guessLetter = guessLetter;
    }

    public boolean isActiveGame() {
        return isActiveGame;
    }

    public void setActiveGame(boolean active) {
        isActiveGame = active;
    }
}
