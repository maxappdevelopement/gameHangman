package appdevelopement.max.hangman;

import android.arch.lifecycle.ViewModel;

import java.util.ArrayList;

public class SaveStateViewModel extends ViewModel {

    private Hangman hangman;
    private boolean isActiveGame = false;
    private String word;
    private char guessLetter;
    private String hiddenWord;
    private String triesLeft;
    private String badLettersUsed;
    private ArrayList<Character> guessLetters;

    public void setHangman(Hangman hangman) {
        this.hangman = hangman;
    }

    public Hangman getHangman() {
        return hangman;
    }

    public String getHiddenWord() {
        return hiddenWord;
    }

    public void setHiddenWord(String hiddenWord) {
        this.hiddenWord = hiddenWord;
    }

    public String getTriesLeft() {
        return triesLeft;
    }

    public void setTriesLeft(String triesLeft) {
        this.triesLeft = triesLeft;
    }

    public String getBadLettersUsed() {
        return badLettersUsed;
    }

    public void setBadLettersUsed(String badLettersUsed) {
        this.badLettersUsed = badLettersUsed;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
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
