package appdevelopement.max.hangman;


import java.util.ArrayList;

public class Hangman {

    int numberOfGuesses = 10;
    private String word;
    private boolean[] visible;
    private ArrayList<Character> guessLetters = new ArrayList<>();
    char guessLetter;

    public Hangman(String word) {
        this.word = word;
        visible = new boolean[word.length()];
    }

    public void setGuessLetter(char guessLetter) {
        this.guessLetter = guessLetter;
    }

    public void guess(char guess) {
        guessLetters.add(guess);
    }

    public String getWord() {
        return word;
    }

    public String getHiddenWord() {
        String hiddenWord = "";
        for (int i = 0; i < word.length(); i++) {
            if (guessLetter == word.charAt(i)) {
                visible[i] = true;
            }
        }

        for (int i = 0; i < word.length(); i++) {
            if (visible[i] == true) {
                hiddenWord += word.charAt(i) + " ";
            } else {
                hiddenWord += "_ ";
            }
        }
        return hiddenWord;
    }

     public String getBadLettersUsed() {
        String badLettersUsed = "";
        for (int i = 0; i < guessLetters.size(); i++) {
            if((word.indexOf(guessLetters.get(i))== -1) && badLettersUsed.length() > -1 && badLettersUsed.length() < 1) {
                badLettersUsed += "" + guessLetters.get(i);
            } else if ((word.indexOf(guessLetters.get(i))== -1) && badLettersUsed.length() > 0) {
                badLettersUsed += ", " + guessLetters.get(i);
            }
        }
        return badLettersUsed.toUpperCase();
    }

    public String getTriesLeft() {
        int triesLeft = numberOfGuesses;
        for (int i = 0; i < guessLetters.size(); i++) {
            if(word.indexOf(guessLetters.get(i))== -1) {
                triesLeft--;
            }
        }
        return triesLeft + "";
    }

    public boolean hasUsedLetter(char guessLetter) {
        for (int i = 0; i < guessLetters.size(); i++) {
            if(guessLetters.get(i) == guessLetter) {
                return true;
            }
        }
        return false;
    }
}
