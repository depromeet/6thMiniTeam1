package com.conveniencestore.control;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.conveniencestore.dao.ProductDao;
import com.conveniencestore.model.Product;

@RestController
public class ProductController {

    @Autowired
    private ProductDao productDao;

    @GetMapping("/products")
    public List<Product> getProducts(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "count", required = false) Integer count) {
        if (page == null) page = 1;
        if (count == null) count = 30;

        List<Product> products = productDao.findAll();

        int baseIndex = (page - 1) * count;
        if (baseIndex >= products.size()) return null;
        int lastIndex = Math.min(baseIndex + count - 1, products.size() - 1);
        return products.subList(baseIndex, lastIndex);
    }

    private int[] getPageSize() {
        String url = "https://pyony.com/search/";
        Document doc = getDocument(url);

        // page 수 넘어가서 조회되는 데이터가 없는 경우
        // Todo : 예외처리
        if (doc == null) {
            System.out.println("페이지 수 정보 얻어오기 실패");
            return new int[]{1, 1};
        }

        Elements elements = doc.select(".current");
        String[] page_strs = elements.get(0).text().split(" ");

        return new int[]{Integer.parseInt(page_strs[0]), Integer.parseInt(page_strs[2])};
    }

    private Document getDocument(String url) {
        Document doc = null;
        try {
            doc = Jsoup.connect(url).get();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return doc;
    }

    // 성공하면 success, 실패하면 fail 반환
    // Todo : 가끔 read time error 발생하는 경우 있는데 예외처리 필요
    @GetMapping("/crawl")
    public String crawl() {
        int[] pageSize = getPageSize();
        for (int page = pageSize[0]; page <= pageSize[1]; page++) {
            String url = "https://pyony.com/search/?page=" + page;
            Document doc = getDocument(url);

            // page 수 넘어가서 조회되는 데이터가 없는 경우
            if (doc == null) {
                System.out.println("doc null");
                return "fail";
            }

            Elements elements = doc.select("body .container .row div .row .col-md-6");

            for (Element e : elements) {
                Element divE = e.select("a div").get(0);
                Elements header = divE.select(".card-header small");
                String store = header.get(0).text();
                String category = header.get(1).text();

                Elements body = divE.select(".card-body");
                String image_url = body.select(".prod_img_div img").attr("src");
                String name = body.select("div strong").text();
                String event = body.select("div span").get(1).text();
                if (!event.equals("1+1")) event = "2+1";

                String divText = body.select("div").text();
                String[] texts = divText.split(" ");
                int price = 0;

                for (int i = 1; i < texts.length; i++) {
                    if (texts[i].contains(",")) {
                        String[] price_strs = texts[i].split(",");
                        price = Integer.parseInt(price_strs[0]) * 1000 + Integer.parseInt(price_strs[1]);
                        break;
                    } else {
                        try {
                            price = Integer.parseInt(texts[i]);
                            break;
                        } catch (Exception exception) {
                        }
                    }
                }

                Product product = Product.builder()
                        .name(name)
                        .imageUrl(image_url)
                        .category(category)
                        .price(price)
                        .event(event)
                        .storeName(store)
                        .createTime(LocalDate.now())
                        .build();

                productDao.save(product);
            }
        }

        return "success";
    }
}
