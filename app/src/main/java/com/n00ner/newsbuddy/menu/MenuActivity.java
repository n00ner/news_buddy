package com.n00ner.newsbuddy.menu;

import android.os.Bundle;
import androidx.annotation.NonNull;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.MenuItem;

import com.n00ner.newsbuddy.BaseActivity;
import com.n00ner.newsbuddy.R;
import com.n00ner.newsbuddy.favorite.FavoriteFragment;
import com.n00ner.newsbuddy.feed.FeedFragment;
import com.n00ner.newsbuddy.themes.ThemesFragment;

public class MenuActivity extends BaseActivity implements MenuView{

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
              case R.id.navigation_feed:
                    setFeed();
                    return true;
                case R.id.navigation_themes:
                    setThemes();
                    return true;

            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.setSelectedItemId(R.id.navigation_feed);
        setFeed();
    }

    @Override
    public void setFeed() {
        setTitle(R.string.menu_title_feed);
        setFragment(new FeedFragment(), R.id.menu_container);
    }

    @Override
    public void setThemes() {
        setTitle(R.string.menu_title_labels);
        setFragment(new ThemesFragment(), R.id.menu_container);
    }

    @Override
    public void setFavorite() {
        setTitle(R.string.menu_title_bookmark);
        setFragment(new FavoriteFragment(), R.id.menu_container);
    }


}
