package appdevelopement.max.hangman;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    static FragmentManager manager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        manager = getSupportFragmentManager();

        if (findViewById(R.id.fragment_container) != null) {
            if (savedInstanceState == null) {
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.add(R.id.fragment_container, new HomeFragment());
                transaction.commit();

            }

            }
        }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //Save the fragment's instance


    }
}




/*
            if (savedInstanceState != null) {
                homeFragment = getSupportFragmentManager().getFragment(savedInstanceState,"HomeFragment");
                gameFragment = getSupportFragmentManager().getFragment(savedInstanceState,"GameFragment");
                return;
            }
/*
            Fragment homeActivity = new HomeFragment();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.add(R.id.fragment_container, homeActivity, "HomeFragment");
            transaction.commit();
        }
        */

