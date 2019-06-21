package com.example.yepa.service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

import java.util.*;

//https://idanmu.at/category/v15/
//TODO
@Service
public class IdanmuService implements PageProcessor {

//    @Autowired
//    private LoggerService loggerService;

    public static final Map<String, String> pageMap = new HashMap<>();


    public final static String startUrl = "http://www.java1234.com/";

    private Site site = Site.me().setUserAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.110 Safari/537.36Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.110 Safari/537.36")
//            .addHeader("Cookie","tt_token=eyJhbGciOiJSUzI1NiJ9.eyJpZCI6MzAsInVzZXJuYW1lIjoiMTAwNDIzNDg5MyIsImV4cCI6MTU0NTU1NDg4N30.Hcvq6cJmBBsQ5A5RgrFPYU_hMuA97j-HN-Fyh2Q6KOk9IkHyfIuGGsnOM_rByRRAOPNUV8SLkL3SAH5P9T52hYijKzS0tSaCXZOvR2KCgdck9NniVmX1ku31v2WAhQXcExNTwtHK2uoR_PlarIg6I7h8FLTwpeqcY0LWYx79Qm4; PHPSESSID=c4gi1m25tn256mcl49ak9h2444; UM_distinctid=1691ac3964c0-0214478d707f38-6313363-1fa400-1691ac3964ee; CNZZDATA1259995395=1179190372-1550927952-%7C1550927952; Hm_lvt_98de7d68e65c614258d77f9ddb7a658a=1550932713; Hm_lpvt_98de7d68e65c614258d77f9ddb7a658a=1550932927; trade=2.2")
            .setRetryTimes(30).setSleepTime(20000);
    String user = "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:65.0) Gecko/20100101 Firefox/65.0";
    String user2 = "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/69.0.3497.100 Safari/537.36";

    public boolean isFirst = true;

    @Override
    public void process(Page page) {

        init(page);
        String currentUrl = page.getUrl().toString();
        if (currentUrl.contains("category") && currentUrl.contains("page")) {
            getPageItemAndChangePage(page);
            System.err.println("PageItemAndChangePage:" + currentUrl);
        } else if (!currentUrl.contains("category") && currentUrl.contains("v15")) {
            getItemDownloadPage(page, currentUrl);
            System.err.println("           getItemDownloadPage:" + currentUrl);
        } else if (!currentUrl.contains("category") && currentUrl.contains("v15")) {
            getMessage(page);
            System.err.println("okok     ok     !!!! getMessage" + currentUrl);
        } else {
            System.err.println("  !OtherUrl:" + currentUrl);
        }
    }


    @Override
    public Site getSite() {
        return site;
    }

    public void init(Page page) {

        if (isFirst) {
            //分类
            List<String> all = page.getHtml().xpath("//*[@id=\"menu-mobile\"]/li[@class=\"item has-sub\"]").links().all();

            for (String mayCategory : all) {
                if (mayCategory.contains("category") && mayCategory.contains("v15")) {
                    Request request = new Request();
                    request.setUrl(mayCategory + "page/1");
//                    Map<String,Object> extrasMap = new HashMap<>();
//                    request.setExtras(extrasMap);
                    page.addTargetRequest(request);
                }
            }
            isFirst = false;
        }

    }

    public void getPageItemAndChangePage(Page page) {
        //item
        List<String> itemLinks = page.getHtml().xpath("/html/body/div/div/section/div/a[@class=\"thumbnail-container general\"]").links().all();
        itemLinks.forEach(link -> {
            Request request = new Request();
            request.setUrl(link);
            page.addTargetRequest(request);
        });

        //changePage
        List<String> changePageLinks = page.getHtml().xpath("/html/body/div/div/div/a[@class=\"next\"]").links().all();
        page.addTargetRequests(changePageLinks);
    }

    public void getItemDownloadPage(Page page, String currentPage) {
        String baseUrl = "https://idanmu.at/download/?id=";
        String id = getId(baseUrl);
        Request request = new Request();
        request.setUrl(baseUrl + id);
        page.addTargetRequest(request);
    }

    //    getItemDownloadPage需要
    public static String getId(String url) {
        String s = StringUtils.substringAfter(url, "v15/");
        return s.substring(0, s.length() - 1);
    }

    public void getMessage(Page page) {


//        baiduyunUrl
        List<String> all = page.getHtml().xpath("/html/body/div/a").links().all();
        Iterator<String> iterator = all.iterator();
        while (iterator.hasNext()) {
            String next = iterator.next();
            if (!next.contains("pan.baidu")) {
                iterator.remove();
            }
        }
        page.putField("baiduyunUrl", all);

//        title
        String title = page.getHtml().xpath("/html/body/h1/a/text()").toString();
        page.putField("title", title);

//        baiduyunPass
        List<String> all1 = page.getHtml().xpath("/html/body/div/span/text()").all();
        page.putField("baiduyunPass", all1.toString());

        page.putField("itemUrl", page.getUrl().toString());

    }


    public static void main(String[] args) {
        Spider.create(new IdanmuService()).addUrl(startUrl).thread(10).run();
    }
}
