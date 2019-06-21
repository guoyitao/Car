package com.example.yepa.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Selectable;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
@Slf4j
public class ZhainansService implements PageProcessor {
    public final static String startUrl = "https://www.zhainans.com/page/1/";
    private Site site = Site.me().
            setRetryTimes(100).setSleepTime(10000);
    @Override
    public void process(Page page) {



        List<String> urls = new ArrayList<>();

        if (page.getUrl().toString().contains("page")){

            Selectable selectTitle = page.getHtml().xpath("//article/div[@class=\"picture-box\"]/h2[@class=\"grid-title\"]/a");
            urls.addAll(selectTitle.links().all());
            //翻页
            Selectable selectPage = page.getHtml().xpath("//*[@id=\"picture\"]/nav[@class=\"navigation pagination\"]/div[@class=\"nav-links\"]/a[@class=\"next page-numbers\"]");
            urls.addAll(selectPage.links().all());
            System.out.println(selectPage.toString());

            log.warn("page change"+selectPage.links().all().toString());
            page.addTargetRequests(urls);

            //https://yard.emm.moe/ssr/emmmoe_181211a/
        }else if(page.getUrl().toString().contains("meizitu") || page.getUrl().toString().contains("vip") || page.getUrl().toString().contains("tag") || page.getUrl().toString().contains("zhainanfuli")){
            getItem(page);
        }else{
            page.setSkip(true);
        }
    }

    @Override
    public Site getSite() {
        return site;
    }

    public void getItem(Page page){


        List<String> alllinks = page.getHtml().xpath("//article/div/div[@class=\"single-content\"]/div[@class=\"down down-link\"]/a").links().all();
        alllinks.addAll(page.getHtml().xpath("//article/div/div[@class=\"single-content\"]/p/span").links().all());
        Iterator<String> allbaidyunit = alllinks.iterator();
        while (allbaidyunit.hasNext()) {
            String next = allbaidyunit.next();
            if (!next.contains("begin/to.php?url") && !next.contains("pan") && !next.contains("magnet") ){
                allbaidyunit.remove();
            }
        }
        page.putField("baiduyunUrl",alllinks.toString());

        //itemUrlgetHtml().toString()
        page.putField("itemUrl",page.getUrl().toString());

//        title
        page.putField("title",page.getHtml().xpath("//article/header/h1/text()"));

//        img
        List<String> allimgs = page.getHtml().xpath("//article/div[@class=\"entry-content\"]/div[@class=\"single-content\"]/p/img/@data-original").all();
        allimgs.addAll(page.getHtml().xpath("//article/div[@class=\"entry-content\"]/div[@class=\"single-content\"]/p/img/@src").all());
        Iterator<String> iterator = allimgs.iterator();
        while (iterator.hasNext()){
            String next = iterator.next();
            if (next.endsWith(".gif")){
                iterator.remove();
            }
        }
        page.putField("imgs",allimgs.toString());

//        baiduyunpass
        List<String> baiduyunpass = page.getHtml().xpath("//article/div[@class=\"entry-content\"]/div[@class=\"single-content\"]/p/text()").all();
        Iterator<String> iterator1 = baiduyunpass.iterator();
        while (iterator1.hasNext()) {
            String next = iterator1.next();
            if (!next.contains("百度") && !next.contains("提取码")){
                iterator1.remove();
            }
        }
        page.putField("baiduyunPass",baiduyunpass.toString());


    }






    public static void main(String[] args) {
        Spider.create(new ZhainansService()).addUrl(ZhainansService.startUrl).thread(20).run();

    }

}
