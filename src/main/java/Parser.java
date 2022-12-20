import gui.DialogFTP;
import gui.DialogJoom;
import gui.DialogVk;

import java.io.File;
import java.util.Scanner;

public class Parser {

    private static String siteJoomla;
    private static String keyJoomla;
    private static String hostAddress;
    private static String log;
    private static String password;
    private static FTPLoader ftp = null;
    private  static String ftpPath;
    private static boolean ftpOn = false;

    private static ParserText pt1 = null;

    private static boolean joomlaOn = false;
    public static void main(String[] args) {
        DialogFTP d = new DialogFTP();
        if(d.otvetReturn().equals("да")){
            hostAddress = d.hostFTPReturn();
            log = d.loginFTPReturn();
            password = d.passFTPReturn();
            ftpPath = d.pathFTPReturn();
            ftpOn = true;
        }
        DialogJoom j = new DialogJoom();
        if(j.otvetJ4().equals("да")){
            joomlaOn = true;
            keyJoomla = j.tokenJoomla();
            siteJoomla = j.urlJoomla();

        }
        DialogVk v = new DialogVk();
        String newUrl;
        while ((newUrl = v.urlVk())!=null) {
            newUrl = urlCorector(newUrl);
            pt1 = new ParserText(newUrl);

            ParserImage pi = new ParserImage(pt1.getUrlParsing());
            System.out.println("Имя записи :" + pt1.getNamePost());
            System.out.println("Имя файла :" + pt1.getFileName());
            System.out.println("Текск записи :" + pt1.getTextContent());
            if(ftpOn){
                ftp = new FTPLoader(hostAddress,log,password,pt1,ftpPath);
            }
            FileManager fl = new FileManager(pt1, v.chooserVK(), pi,ftp,ftpOn);

            CuterImg cuterImg = new CuterImg(fl);

            if (joomlaOn){
                RePostreJ4 postreJ4 = new RePostreJ4(keyJoomla,2,"Parser meta discription","Raz,Dva",pt1);
                postreJ4.goPost(siteJoomla);
            }

        }


    }
    public static String urlCorector(String url){
        if(url.contains("w=")) {
            url = "https://vk.com/" + url.split("w=")[1];
        }
        if(url.contains("/all")) {
            return url.split("/a")[0];
        }else {
            return url;
        }
    }
}
