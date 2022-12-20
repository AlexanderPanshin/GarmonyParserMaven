import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class ParserImage {
    private String urlParsing;
    private ArrayList<String> urlImage = new ArrayList<String>();

    public ArrayList<String> getUrlImage() {
        return urlImage;
    }

    public ParserImage(String urlParsing) {
        this.urlParsing = urlParsing;
        parsingUrlImage(urlParsing);
    }
    public void parsingUrlImage(String url){
        Document document = null;
        try {
            document = Jsoup.connect(url).get();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Elements title = document.select("img.MediaGrid__imageElement");
        if(!title.isEmpty()) {
            for (Element e : title) {
                String urlParse = e.toString();
                urlParse = urlParse.split("src=\"")[1];
                urlParse = urlParse.split("\"")[0];
                urlParse = coorectUrl(urlParse);
                urlImage.add(urlParse);
            }
        }else {
            title = document.select("img.MediaGrid__imageOld");
            for (Element e : title) {
                String urlParse = e.toString();
                urlParse = urlParse.split("src=\"")[1];
                urlParse = urlParse.split("\"")[0];
                urlParse = coorectUrl(urlParse);
                urlImage.add(urlParse);
            }
        }
    }
    public String coorectUrl (String url){
        return url.replaceAll("amp;","");
    }
}
