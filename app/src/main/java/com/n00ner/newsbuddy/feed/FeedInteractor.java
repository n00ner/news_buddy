package com.n00ner.newsbuddy.feed;

import com.n00ner.newsbuddy.api.NewsApi;
import com.n00ner.newsbuddy.models.NewsItem;
import com.n00ner.newsbuddy.util.RSSParser;

import org.xml.sax.SAXException;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class FeedInteractor {

    public interface OnFeedListener{
        void onSuccessFeed(ArrayList<NewsItem> data, boolean isLast);
        void onError(String error);
    }

    public void requestNewsByTheme(String themeName,OnFeedListener listener, boolean isLast ){
        NewsApi.create().getNews(themeName, "ru&gl=RU&ceid=RU:ru")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(String s) {
                        try {
                            listener.onSuccessFeed(new RSSParser().anotherParser(s), isLast);
                        } catch (ParserConfigurationException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (SAXException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                        listener.onError(e.getLocalizedMessage());
                    }
                });
    }
}
