package com.n00ner.newsbuddy.adapters;

import android.content.ContextWrapper;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.n00ner.newsbuddy.BaseApp;
import com.n00ner.newsbuddy.R;
import com.n00ner.newsbuddy.models.SectionTheme;
import com.n00ner.newsbuddy.models.Theme;
import com.n00ner.newsbuddy.storage.database.TagsController;
import com.n00ner.newsbuddy.themes.ThemesInteractor;
import com.n00ner.newsbuddy.themes.ThemesPresenter;
import com.n00ner.newsbuddy.themes.ThemesView;

import java.lang.reflect.Method;
import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class ThemesAdapter extends RecyclerView.Adapter<ThemeHolder>  {

    private ArrayList<SectionTheme> items = new ArrayList<>();
    private ThemesView themesView;
    private ThemesPresenter presenter;

    public ThemesAdapter(ThemesView themesView, ThemesPresenter presenter) {
        this.themesView = themesView;
        this.presenter = presenter;
    }

    public void setItems(ArrayList<SectionTheme> data){
        items = data;
        notifyDataSetChanged();
        if(items.size() > 0){
            themesView.showThemesList();
        }else{
            themesView.showEmptyList();
        }
    }

    @NonNull
    @Override
    public ThemeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_theme, parent, false);
        return new ThemeHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ThemeHolder holder, int position) {
        holder.name.setText(items.get(position).getTheme().getName());
        holder.themeMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopupMenu(view, position);
            }
        });
        if(items.get(position).getTagList().size() == 0){
            holder.emptyTagList.setVisibility(View.VISIBLE);
            holder.tagList.setVisibility(View.GONE);
        }else{
            holder.emptyTagList.setVisibility(View.GONE);
            holder.tagList.setVisibility(View.VISIBLE);
            holder.tagList.setLayoutManager(new LinearLayoutManager(holder.tagList.getContext()));
            TagsAdapter adapter = new TagsAdapter(themesView);
            adapter.setCountListener(count -> {
                if(count > 0){
                    holder.emptyTagList.setVisibility(View.GONE);
                    holder.tagList.setVisibility(View.VISIBLE);
                }else {
                    holder.emptyTagList.setVisibility(View.VISIBLE);
                    holder.tagList.setVisibility(View.GONE);
                }
            });
            adapter.setItems(items.get(position).getTagList());
            holder.tagList.setAdapter(adapter);
        }
    }

    private void showPopupMenu(View view, int position){
        PopupMenu menu = new PopupMenu(new ContextThemeWrapper(BaseApp.getAppContext(), R.style.OptionMenu), view);
        try {
            Method method = menu.getMenu().getClass().getDeclaredMethod("setOptionalIconsVisible", boolean.class);
            method.setAccessible(true);
            method.invoke(menu.getMenu(), true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        MenuInflater inflater = menu.getMenuInflater();
        inflater.inflate(R.menu.theme, menu.getMenu());
        menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                switch (item.getItemId()){
                    case R.id.theme_delete:
                        if(presenter.deleteTheme(items.get(position).getTheme())){
                            removeAt(position);
                        }
                        break;
                    case R.id.theme_edit:
                        themesView.showUpdateThemeDialog(items.get(position).getTheme());
                        break;
                    case R.id.theme_add_tag:
                        themesView.showAddTagDialog(items.get(position).getTheme());
                        break;
                }
                return false;
            }
        });
        menu.show();
    }

    private void removeAt(int position){
        items.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, items.size());
        if(items.size() > 0){
            themesView.showThemesList();
        }else{
            themesView.showEmptyList();
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }


}

class ThemeHolder extends RecyclerView.ViewHolder{

    @BindView(R.id.themes_section_header_text) TextView name;
    @BindView(R.id.img_theme_more) ImageView themeMenu;
    @BindView(R.id.empty_tag_list) RelativeLayout emptyTagList;
    @BindView(R.id.tags_list) RecyclerView tagList;

    public ThemeHolder(@NonNull View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
