package com.example.yepa.service;

import com.example.yepa.pojo.PyAcgPojo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Selectable;
import us.codecraft.webmagic.utils.HttpConstant;

import java.util.*;

//https://18asmr.com/
@Service
public class PyAcgService implements PageProcessor {

//    @Autowired
//    private LoggerService loggerService;

    public static final Map<String,String> pageMap = new HashMap<>();



    public final static String startUrl = "https://www.acgpy.moe/wpx/";

    private Site site = Site.me()
            .setUserAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.110 Safari/537.36Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.110 Safari/537.36")
            .addHeader("Cookie","tt_token=eyJhbGciOiJSUzI1NiJ9.eyJpZCI6MzAsInVzZXJuYW1lIjoiMTAwNDIzNDg5MyIsImV4cCI6MTU0NTU1NDg4N30.Hcvq6cJmBBsQ5A5RgrFPYU_hMuA97j-HN-Fyh2Q6KOk9IkHyfIuGGsnOM_rByRRAOPNUV8SLkL3SAH5P9T52hYijKzS0tSaCXZOvR2KCgdck9NniVmX1ku31v2WAhQXcExNTwtHK2uoR_PlarIg6I7h8FLTwpeqcY0LWYx79Qm4; PHPSESSID=c4gi1m25tn256mcl49ak9h2444; UM_distinctid=1691ac3964c0-0214478d707f38-6313363-1fa400-1691ac3964ee; CNZZDATA1259995395=1179190372-1550927952-%7C1550927952; Hm_lvt_98de7d68e65c614258d77f9ddb7a658a=1550932713; Hm_lpvt_98de7d68e65c614258d77f9ddb7a658a=1550932927; trade=2.2")
            .setRetryTimes(30).setSleepTime(20000);
    String user = "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:65.0) Gecko/20100101 Firefox/65.0";
    String user2 = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.110 Safari/537.36Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.110 Safari/537.36";

    public boolean isFirst = false;
    @Override
    public void process(Page page) {

        init(page);

        String currentUrl = page.getUrl().toString();
        if (currentUrl.contains("page")) {
            site.setUserAgent(user);
            List<String> pagesItemLink = page.getHtml().xpath("//*[@id=\"main-content\"]/div/div/section/div/div/ul/li/article/h2/a").links().all();
            page.addTargetRequests(pagesItemLink);

            //翻页  //*[@id="main-content"]/div/div/section/div/div/div/a[6]
            List<String> all = page.getHtml().xpath("//*[@id=\"main-content\"]/div/div/section/div/div/div/a[@class=\"next\"]").links().all();
            page.addTargetRequests(all);
//            getcurrentPage(currentUrl);
            page.setSkip(true);
            System.err.println("current getPage:" + page.getUrl().toString());
        }else if(currentUrl.endsWith(".html")){
            getDownloadPageUrl(page);
            site.setUserAgent(user2);
            page.setSkip(true);
            System.err.println("current getDownloadPageUrl:" + page.getUrl().toString());
        }else if(currentUrl.contains("download")){
            site.setUserAgent(user);
            getMessage(page);
            System.err.println("current getMessage:" + page.getUrl().toString());
        }else {
            page.setSkip(true);
            System.err.println("error Page:" + page.getUrl().toString());
//            loggerService.SendErrorPage(page);
        }


    }




    @Override
    public Site getSite() {
        return site;
    }

    public void init(Page page){
        if (isFirst){
            //分类
            List<String> all = page.getHtml().xpath("//*[@id=\"main-nav\"]/ul/li/a").links().all();
            for (String mayCategory : all) {
                if (mayCategory.contains("category")) {
                    page.addTargetRequest(mayCategory+"/page/1");
                }
            }
            isFirst = false;
        }

    }

    public void getDownloadPageUrl(Page page){
        List<String> downloadPageUrl = page.getHtml().xpath("//article/div/div/a").links().all();
        Iterator<String> iterator = downloadPageUrl.iterator();
        while (iterator.hasNext()) {
            String next = iterator.next();
            if (next.contains("download")){
                page.addTargetRequest(next);
            }
        }

    }

    public void getMessage(Page page) {



//        baiduyunUrl
        List<String> all = page.getHtml().xpath("/html/body/div/a").links().all();
        Iterator<String> iterator = all.iterator();
        while (iterator.hasNext()) {
            String next = iterator.next();
            if (!next.contains("pan.baidu")){
                iterator.remove();
            }
        }
        page.putField("baiduyunUrl" ,all);

//        title
        String title = page.getHtml().xpath("/html/body/h1/a/text()").toString();
        page.putField("title" ,title);

//        baiduyunPass
        List<String> all1 = page.getHtml().xpath("/html/body/div/span/text()").all();
        page.putField("baiduyunPass",all1.toString());

        page.putField("itemUrl",page.getUrl().toString());

    }

//    public void getcurrentPage(String currentPage){
//
//
//        map.put(
//                StringUtils.substringBefore(temp,"page"),
//                StringUtils.substringAfter(temp,"page/"));
//    }



    public static void main(String[] args) {
        Spider.create(new PyAcgService()).addUrl(startUrl).thread(50).run();
    }
}
