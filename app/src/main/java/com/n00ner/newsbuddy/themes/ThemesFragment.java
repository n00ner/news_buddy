package com.n00ner.newsbuddy.themes;

import android.app.AlertDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.n00ner.newsbuddy.R;
import com.n00ner.newsbuddy.adapters.ThemesAdapter;
import com.n00ner.newsbuddy.models.SectionTheme;
import com.n00ner.newsbuddy.models.Tag;
import com.n00ner.newsbuddy.models.Theme;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class ThemesFragment extends Fragment implements ThemesView {

    @BindView(R.id.empty_list_wrapper) RelativeLayout emptyPlaceholder;
    @BindView(R.id.empty_list_title) TextView emptyTitle;
    @BindView(R.id.empty_list_subtitle) TextView emptySubtitle;
    @BindView(R.id.img_empty_list_action) ImageView emptyIcon;
    @BindView(R.id.themes_list) RecyclerView themesList;
    @BindView(R.id.fab_add_theme) FloatingActionButton fabAddTheme;

    private ThemesAdapter adapter;
    private ThemesPresenter presenter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_themes, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view,  Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter = new ThemesPresenter(this,new ThemesInteractor());
        initList();
        presenter.requestThemes();
        fabAddTheme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onAddThemeClicked();
            }
        });
        themesList.addOnScrollListener(new RecyclerView.OnScrollListener(){
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy){
                if (dy<0 && !fabAddTheme.isShown())
                    fabAddTheme.show();
                else if(dy>0 && fabAddTheme.isShown())
                    fabAddTheme.hide();
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }
        });
    }

    @Override
    public void showThemesList() {
        emptyPlaceholder.setVisibility(View.GONE);
        themesList.setVisibility(View.VISIBLE);
    }

    @Override
    public void showEmptyList() {
        emptyPlaceholder.setVisibility(View.VISIBLE);
        themesList.setVisibility(View.GONE);
        emptyTitle.setText(R.string.text_themes_empty_title);
        emptySubtitle.setText(R.string.text_themes_empty_subtitle);
        emptyIcon.setImageResource(R.drawable.ic_label);
    }

    @Override
    public void updateThemesList(ArrayList<SectionTheme> data) {

        adapter.setItems(data);
    }

    @Override
    public void showToast(int resId) {
        Toast.makeText(getContext(), getString(resId), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showAddThemeDialog() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getContext());
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_theme_add, null);
        AlertDialog dialog = dialogBuilder.create();
        dialog.setView(dialogView, 40, 0, 40, 0);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        TextView decline = (TextView)dialogView.findViewById(R.id.btn_decline_add_theme);
        TextInputEditText themeEdit = (TextInputEditText)dialogView.findViewById(R.id.theme_name_edit);
        TextView saveTheme = (TextView)dialogView.findViewById(R.id.btn_submit_add_theme);
        decline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        saveTheme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if( presenter.addTheme(themeEdit.getText().toString())){
                   dialog.dismiss();
               }
            }
        });

        dialog.show();
    }

    @Override
    public void showUpdateThemeDialog(Theme theme) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getContext());
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_theme_edit, null);
        AlertDialog dialog = dialogBuilder.create();
        dialog.setView(dialogView, 40, 0, 40, 0);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        TextView decline = (TextView)dialogView.findViewById(R.id.btn_decline_edit_theme);
        TextInputEditText themeEdit = (TextInputEditText)dialogView.findViewById(R.id.theme_name_change_edit);
        TextView saveTheme = (TextView)dialogView.findViewById(R.id.btn_submit_edit_theme);
        themeEdit.setText(theme.getName());
        decline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        saveTheme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(presenter.updateTheme(theme, themeEdit.getText().toString())){
                    dialog.dismiss();
                }
            }
        });

        dialog.show();
    }

    @Override
    public void showAddTagDialog(Theme theme) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getContext());
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_tag_add, null);
        AlertDialog dialog = dialogBuilder.create();
        dialog.setView(dialogView, 40, 0, 40, 0);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        TextView decline = (TextView)dialogView.findViewById(R.id.btn_decline_add_tag);
        TextInputEditText tagEdit = (TextInputEditText)dialogView.findViewById(R.id.tag_name_edit);
        TextView saveTheme = (TextView)dialogView.findViewById(R.id.btn_submit_add_tag);
        decline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        saveTheme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(presenter.addTag(tagEdit.getText().toString(), theme)){
                    dialog.dismiss();
                }
            }
        });

        dialog.show();
    }

    @Override
    public void showUpdateTagDialog(Tag tag) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getContext());
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_tag_edit, null);
        AlertDialog dialog = dialogBuilder.create();
        dialog.setView(dialogView, 40, 0, 40, 0);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        TextView decline = (TextView)dialogView.findViewById(R.id.btn_decline_edit_tag);
        TextInputEditText themeEdit = (TextInputEditText)dialogView.findViewById(R.id.tag_name_change_edit);
        TextView saveTheme = (TextView)dialogView.findViewById(R.id.btn_submit_edit_tag);
        themeEdit.setText(tag.getName());
        decline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        saveTheme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(presenter.updateTag(tag, themeEdit.getText().toString())){
                    dialog.dismiss();
                }
            }
        });

        dialog.show();
    }

    @Override
    public void initList() {
        adapter = new ThemesAdapter(this, presenter);
        themesList.setLayoutManager(new LinearLayoutManager(getContext()));
        themesList.setAdapter(adapter);
    }


}
