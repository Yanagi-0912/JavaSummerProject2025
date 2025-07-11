package com.example.demo.model;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class KeelungSightsCrawler {
    public KeelungSightsCrawler(){}
    public Sight[] getItems(String setZone){
        Sight[] sights;
        try{
            // 連線並取得網頁 Document 物件
            String URL = "https://www.travelking.com.tw/tourguide/taiwan/keelungcity/";
            Document doc = Jsoup.connect(URL).get();

            Element guideDiv = doc.getElementById("guide-point");
            Elements headings = guideDiv.select("h4");

            List<Sight> sightList = new ArrayList<>();
            for (Element heading : headings) {
                if(heading.text().contains(setZone)) {
                    Element nextUl = heading.nextElementSibling();
                    if (nextUl != null && nextUl.tagName().equals("ul")) {
                        for (Element li : nextUl.select("li")) {
                            String link = li.select("a").attr("href");
                            if (!link.isEmpty()) {
                                if (!link.startsWith("http")) {
                                    link = "https://www.travelking.com.tw" + link;
                                }
                                sightList.add(getItem(link, heading.text()));
                            }
                        }
                    }
                }
            }
            sights = sightList.toArray(new Sight[0]);
            return sights;
        } catch (IOException e) {
            return new Sight[0];
        }
    }

    public Sight getItem(String url, String setZone) {
        try {
            Document doc = Jsoup.connect(url).get();
            Sight sight = new Sight();

            Element nameMeta = doc.selectFirst("meta[itemprop=name]");
            sight.setSightName(nameMeta != null ? nameMeta.attr("content") : "");

            sight.setZone(setZone);

            Element categorySpan = doc.selectFirst("span[property=rdfs:label] strong");
            sight.setCategory(categorySpan != null ? categorySpan.text() : "");

            Element photoMeta = doc.selectFirst("meta[itemprop=image]");
            sight.setPhotoURL(photoMeta != null ? photoMeta.attr("content") : "");

            Element descMeta = doc.selectFirst("meta[itemprop=description]");
            sight.setDescription(descMeta != null ? descMeta.attr("content") : "");

            Element addrMeta = doc.selectFirst("meta[itemprop=address]");
            sight.setAddress(addrMeta != null ? addrMeta.attr("content") : "");
            return sight;
        } catch (IOException e) {
            return null;
        }
    }
}
