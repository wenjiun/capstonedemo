package my.edu.mmu.foe.videoplayerdemo;

import android.app.FragmentManager;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // if the MainActivity is launched for the first time, a MainFragment will be added into the MainActivity root view
        if(savedInstanceState == null) {
            getFragmentManager().beginTransaction().add(android.R.id.content, new MainFragment()).commit();
        }
    }

    @Override
    public void onBackPressed() {
        // handle the back button directly to avoid pressing back button to close the entire app
        FragmentManager fragmentManager = getFragmentManager();
        if(fragmentManager.getBackStackEntryCount() > 0) {
            fragmentManager.popBackStack();
        } else {
            super.onBackPressed();
        }
    }
}
