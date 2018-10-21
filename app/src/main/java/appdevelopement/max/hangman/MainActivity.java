package appdevelopement.max.hangman;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setIcon(getDrawable(R.drawable.ic_hangman));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.info_action:
                Intent info = new Intent(this, InfoActivity.class);
                startActivity(info);
                break;
            case R.id.play_action:
                Intent play = new Intent(this, GameAcitivity.class);
                startActivity(play);
                break;
            default:
                // unknown error
        }
        return super.onOptionsItemSelected(item);
    }

    public void clickButton(View view) {
        if (view == view.findViewById(R.id.aboutButton)) {
            Intent info = new Intent(this, InfoActivity.class);
            startActivity(info);
        } else if (view == view.findViewById(R.id.playButton)) {
            Intent play = new Intent(this, GameAcitivity.class);
            startActivity(play);
        }
    }
}
