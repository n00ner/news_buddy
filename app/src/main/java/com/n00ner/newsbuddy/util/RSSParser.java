package com.n00ner.newsbuddy.util;

import android.provider.DocumentsContract;
import android.util.Log;
import android.util.Xml;

import com.n00ner.newsbuddy.models.NewsItem;

import org.jsoup.Jsoup;
import org.jsoup.select.Elements;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;


import java.io.ByteArrayInputStream;
import java.io.IOException;

import java.nio.charset.StandardCharsets;

import java.util.ArrayList;


import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class RSSParser {

    public ArrayList<NewsItem> anotherParser(String input) throws ParserConfigurationException, IOException, SAXException {
        ArrayList<NewsItem> items = new ArrayList<>();
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8)));
        Element element = document.getDocumentElement();

        NodeList nodeList = element.getElementsByTagName("item");

        if (nodeList.getLength() > 0) {
            for (int i = 0; i < nodeList.getLength(); i++) {
                Element newsEntry = (Element) nodeList.item(i);
                Element title = (Element) newsEntry.getElementsByTagName("title").item(0);
                Element description = (Element) newsEntry.getElementsByTagName("description").item(0);
                Element pubDate = (Element) newsEntry
                        .getElementsByTagName("pubDate").item(0);
                Element link = (Element) newsEntry.getElementsByTagName("link").item(0);
                Element image = (Element) newsEntry.getElementsByTagName("media:content").item(0);
                Element pubSource = (Element) newsEntry.getElementsByTagName("source").item(0);
                NewsItem item = new NewsItem();
                if(title != null)
                    item.setTitle(title.getFirstChild().getNodeValue());
                if(description != null)
                    item.setDescription(description.getFirstChild().getNodeValue());
                if(pubDate != null){
                    item.setPubDate(pubDate.getFirstChild().getNodeValue());
                }
                if(link != null)
                    item.setLink(link.getFirstChild().getNodeValue());
                if(image != null){
                    item.setImageUrl(image.getAttributes().item(0).getNodeValue());
                }
                if(pubSource != null)
                    item.setPubSource(pubSource.getFirstChild().getNodeValue());
                items.add(item);
            }
        }
        return items;
    }

    public String fetchOGtag(String html) {
        org.jsoup.nodes.Document document = Jsoup.parse(html);
        Elements metaTags = document.select("meta");
        String siteImgUrl = null;
        for(org.jsoup.nodes.Element element: metaTags){
            String name = element.attr("property");
            String content = element.attr("content");
            if(name != null && content != null){
                if(name.equals("og:image")){
                    siteImgUrl = content;
                }

            }
        }
        return siteImgUrl;
    }

}
