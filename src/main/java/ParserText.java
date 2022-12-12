import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class ParserText {
    String urlParsing;
    String textContent;
    String namePost;
    String fileName;

    private StringBuilder HtmlImg = new StringBuilder();

    public StringBuilder getHtmlImg() {
        return HtmlImg;
    }

    public void setHtmlImg(StringBuilder htmlImg) {
        HtmlImg = htmlImg;
    }
    /*private ArrayList<String> ftpPathUrl;*/

    public ParserText(String urlParsing) {
        this.urlParsing = urlParsing;
        parseTexandName(urlParsing);
    }

    public String getUrlParsing() {
        return urlParsing;
    }

    public String getTextContent() {
        return textContent;
    }

    public String getNamePost() {
        return namePost;
    }

    public String getFileName() {
        return fileName;
    }

    public void  parseTexandName(String url){
        try {
            Document document = Jsoup.connect(url).get();
            Elements title = document.select("div.wall_post_text");
            textContent = title.text();
            generateName(textContent);
            generateFileName(textContent);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void generateName(String text){
        String preName = text.split("\\.")[0];
        if(preName.length()<155){
            StringBuilder sb = new StringBuilder(preName);
            sb.append("...");
            namePost=sb.toString();
        }else {
            StringBuilder sb = new StringBuilder(preName.substring(0,155));
            sb.append("...");
            namePost = sb.toString();
        }
    }
    public void generateFileName (String text){
        String [] mass = text.split(" ");
        StringBuilder sb = new StringBuilder();
        DateFormat dateFormat = new SimpleDateFormat("dMMyyyy");
        String date = dateFormat.format(Calendar.getInstance().getTime());
        if (mass.length>1){
            for(int i = 0; i < 1; i++){

                    sb.append(mass[i]);
                    sb.append(date);

            }
            fileName = sb.toString();
        }else {
            for(String s:mass){
                sb.append(s);
                sb.append(" ");
            }
            fileName = sb.toString().trim()+date;
        }
    }

}
