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
        Scanner sc = new Scanner(System.in);
        System.out.println("Включить модуль FTP? да/нет :");
        if(sc.nextLine().equals("да")){
            System.out.println("Модуль Ftp включен введите адрес хоста : ");
            hostAddress = sc.nextLine();
            System.out.println("Введите логин : ");
            log = sc.nextLine();
            System.out.println("Введите пароль : ");
            password = sc.nextLine();
            System.out.println("Путь до папки на ftp сервере : ");
            ftpPath = sc.nextLine();
            ftpOn = true;
        }
        System.out.println("Включить модуль дабавление новости Joomla? да/нет (Joomla V4)");
        if(sc.nextLine().equals("да")){
            joomlaOn = true;
            System.out.println("ВВедите Токен API :");
            keyJoomla = sc.nextLine();
            System.out.println("Введите адрес сайта :");
            siteJoomla = sc.nextLine();

        }

        System.out.println("Введите адрес записи в вк :");
        // тут он ждет урл формата https://vk.com/test?w=wall-11111111_1111/all
        String newUrl = urlCorector(sc.nextLine());
        while (newUrl!="exit") {
            pt1 = new ParserText(newUrl);
            ParserImage pi = new ParserImage(pt1.getUrlParsing());
            System.out.println("Имя записи :" + pt1.getNamePost());
            System.out.println("Имя файла :" + pt1.getFileName());
            System.out.println("Текск записи :" + pt1.getTextContent());
            if(ftpOn){
                ftp = new FTPLoader(hostAddress,log,password,pt1,ftpPath);
            }
            FileManager fl = new FileManager(pt1, new File("D:\\test1\\"), pi,ftp,ftpOn);

            CuterImg cuterImg = new CuterImg(fl);

            if (joomlaOn){
                RePostreJ4 postreJ4 = new RePostreJ4(keyJoomla,2,"Parser meta discription","Raz,Dva",pt1);
                postreJ4.goPost(siteJoomla);
            }

            newUrl = urlCorector(sc.nextLine());


        }


    }
    public static String urlCorector(String url){
        url = "https://vk.com/"+url.split("w=")[1];

        return url.split("/a")[0];
    }
}
