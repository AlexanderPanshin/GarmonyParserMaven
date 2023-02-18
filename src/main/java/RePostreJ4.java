import java.io.*;
;
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
    private String siteName;



    private  String title = "";
    private String articletext= "";

    public RePostreJ4(String tokenValue, String siteName, String metadesc, String metakey,ParserText pt) {
        this.tokenValue = tokenValue;

        this.catid = catid;
        this.metadesc = metadesc;
        this.metakey = metakey;
        this.pt = pt;
        this.title = pt.namePost;
        this.articletext = pt.getTextContent();
        this.siteName =siteName;

        this.alias = FTPLoader.Translator.translitor(pt.fileName);
        selectCatId();
    }
    public void goPost(){//Это отправляет пост запрос
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
    public Integer selectCatId(){

        try {
            URL url = new URL(siteName+"/api/index.php/v1/content/categories");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty(TYPE_KEY,TYPE_VALUE);
            conn.setRequestProperty(TOKEN_KEY,tokenValue);
            int responseCode = conn.getResponseCode();
            System.out.println("GET Response Code :: " + responseCode);
            if (responseCode == HttpURLConnection.HTTP_OK) { // success
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();
                String [] arrGr = response.toString().split("\\{\"id\":");
                //String [] arrClear =
                for(String s : arrGr){
                    System.out.print("***    ");
                    System.out.println(s);
                }

                // print result
                //System.out.println(response.toString());
            } else {
                System.out.println("GET request did not work.");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return 5;

    }


}
