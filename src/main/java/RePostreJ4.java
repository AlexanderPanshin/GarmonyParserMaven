import java.io.BufferedWriter;;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;


public class RePostreJ4 {
    private static String TOKEN_KEY = "X-Joomla-Token";
    private static String TYPE_KEY = "Content-Type";
    private static String TYPE_VALUE = "application/json;charset=UTF-8";
    private static String LANGUAGE ="\"language\":\"*\"";
    private String tokenValue;
    private String alias;
    private Integer catid;
    private  String metadesc;
    private String metakey;
    private ParserText pt;



    private  String title = "";
    private String articletext= "";

    public RePostreJ4(String tokenValue, Integer catid, String metadesc, String metakey,ParserText pt) {
        this.tokenValue = tokenValue;
        this.catid = catid;
        this.metadesc = metadesc;
        this.metakey = metakey;
        this.pt = pt;
        this.title = pt.namePost;
        this.articletext = pt.getTextContent();

        this.alias = FTPLoader.Translator.translitor(pt.fileName);

    }
    public void goPost(String siteName){//Это отправляет пост запрос
        try {
            URL url = new URL(siteName+"/api/index.php/v1/content/articles");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");

            conn.setRequestProperty("Accept-Charset", "UTF-8");
            conn.setRequestProperty("Content-Language", "ru-RU");

            conn.setRequestProperty(TYPE_KEY,TYPE_VALUE);
            conn.setRequestProperty(TOKEN_KEY,tokenValue);
            conn.setDoOutput(true);
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream(), "UTF-8"));
            out.write(jsonCreater());
            out.flush();
            out.close();
            System.out.println(conn.getResponseMessage());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private String jsonCreater(){ //Этот метод создает строку JSON
        StringBuilder sb = new StringBuilder("{\"alias\":\"");
        sb.append(alias+"\",\"articletext\":\""+clear(articletext)+pt.getHtmlImg().toString()+"\",\"catid\":"+catid+","+LANGUAGE+",\"metadesc\":\""+metadesc
                +"\",\"metakey\":\""+metakey+"\",\"title\": \""+title+"\"}");
        System.out.println(sb);
        return sb.toString();
    }
    private String clear(String stroka){//Это нужен так как если в строке будет " JSON сломаеться
        return stroka.replace("\"","'");
    }


}
