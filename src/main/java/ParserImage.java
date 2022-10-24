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
        Elements title = document.select("[aria-label=\"фотография\"]");
        /*
        Тут я получаю строки странного вида
        <a aria-label="фотография" onclick="return showPhoto('-162373617_457239964', 'wall-162373617_1035', {&quot;temp&quot;:{&quot;x&quot;:&quot;&quot;,&quot;y&quot;:&quot;&quot;,&quot;z&quot;:&quot;&quot;,&quot;w&quot;:&quot;&quot;,&quot;x_&quot;:[&quot;&quot;,604,453],&quot;y_&quot;:[&quot;&quot;,807,605],&quot;z_&quot;:[&quot;&quot;,1280,960],&quot;w_&quot;:[&quot;&quot;,2560,1920],&quot;base&quot;:&quot;&quot;},&quot;queue&quot;:1}, event)" style="width: 252px; height: 189px;background-image: url(https://sun9-38.userapi.com/impg/o3JQZB6buGmfXddTVT9YRyoJjYUxXuW128lA7w/gw7cRLiyYSw.jpg?size=604x453&amp;quality=95&amp;sign=dc7fb39fa0e59d954a9a4dfbb93c91d5&amp;type=album);" class="page_post_thumb_wrap image_cover  page_post_thumb_last_row fl_l page_post_thumb_not_single"></a>
        по атрибуту ариа
        потом пытаюсь вытащить из него url
        и добавить в список
        */
        for(Element e:title){
            String urlParse = e.toString();
            urlParse = urlParse.split("\\(")[2];
            urlParse = urlParse.split("\\)")[0];
            urlParse = coorectUrl(urlParse);
            urlImage.add(urlParse);
        }
    }
    public String coorectUrl (String url){
        return url.replaceAll("amp;","");
    }
}
