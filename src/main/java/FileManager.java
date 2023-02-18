import java.io.*;
import java.net.URL;
import java.util.ArrayList;

public class FileManager {
    ParserText pt;
    File fpath;
    ParserImage pi;
    FTPLoader ftp;
    boolean ftpon = false;

    private ArrayList<String> pathFTP = new ArrayList<>();

    public boolean isNoImage() {
        return noImage;
    }

    boolean noImage = false;

    public FTPLoader getFtp() {
        return ftp;
    }

    public boolean isFtpon() {
        return ftpon;
    }

    public void setFtpon(boolean ftpon) {
        this.ftpon = ftpon;
    }

    private static  int conterImg = 1;
    private ArrayList<String> fileImg = new ArrayList<String>();

    public ArrayList<String> getFileImg() {
        return fileImg;
    }

    public FileManager(ParserText pt, File fpath, ParserImage pi,FTPLoader ftp,boolean ftpon) {
        this.pt = pt;
        this.fpath = fpath;
        this.pi = pi;
        this.ftp = ftp;
        this.ftpon = ftpon;
        crateDirectory(fpath);
    }
    public void crateDirectory(File file){
        if(!file.exists()){
            System.out.println("Каталога нет, пытаемься его создать :");
            System.out.println(file.mkdir());
        }else {
            System.out.println("Каталог существуе готов для записи. ");
        }
        File newDirectory = new File(file.getAbsolutePath()+File.separator+pt.fileName+ftp.getRand());//tyt
        System.out.println(newDirectory);
        System.out.println("попытка создать директорию фото = "+newDirectory.mkdirs());

        for(String s : pi.getUrlImage()){
            recImage(newDirectory.getAbsoluteFile(),s);
        }
        if(pi.getUrlImage().isEmpty()){
            recImage(newDirectory.getAbsoluteFile(),"https://mygemorr.ru/noImage200.png");
            noImage = true;
        }

        recText(new File(newDirectory.getAbsolutePath()+File.separator+pt.fileName+".txt"));

    }
    public void recText(File file){
        try {
            FileWriter fw = new FileWriter(file);
            fw.write(pt.getTextContent());
            fw.write(System.lineSeparator());
            fw.write(System.lineSeparator());
            if (!isFtpon()) {
                for (String imageUrl:pi.getUrlImage()) {
                    fw.write("<p><img src=\""+imageUrl+"\" /></p>");
                    fw.write(System.lineSeparator());
                    pt.getHtmlImg().append("<p><img src=\\\"").append(imageUrl).append("\\\" /></p>").append("\\n");
                }
            }else {
                for (String ftpUrl:pathFTP){
                    fw.write("<p><img src=\""+ftpUrl+"\" /></p>");
                    fw.write(System.lineSeparator());
                    pt.getHtmlImg().append("<p><img src=\\\"").append(ftpUrl).append("\\\" /></p>").append("\\n");
                }
            }
            fw.write(pt.namePost);
            fw.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
    public void recImage(File file,String url){
        try{
            String fileName = file.getAbsolutePath()+File.separator+pt.getFileName()+"img"+conterImg+".jpg";
            conterImg++;
            String website;
            if(!url.contains("https")) {
                StringBuilder sb = new StringBuilder(url);
                sb.insert(4, "s");
                website = sb.toString();
            }else website = url;

            fileImg.add(fileName);

            System.out.println("Downloading File From: " + website);

            URL urll = new URL(website);
            InputStream inputStream = urll.openStream();
            OutputStream outputStream = new FileOutputStream(fileName);
            byte[] buffer = new byte[2048];

            int length = 0;
            System.out.println("Запись  " + fileName);
            while ((length = inputStream.read(buffer)) != -1) {
                //System.out.println("Buffer Read of length: " + length);
                outputStream.write(buffer, 0, length);
            }
            if(ftpon){
                ftpUrlGenerator(new File(fileName));
                ftp.ftpConn(new File(fileName));
            }

            inputStream.close();
            outputStream.close();

        } catch(Exception e) {
            System.out.println("Exception: " + e.getMessage());
        }
    }
    private void ftpUrlGenerator(File ftpPath){
        String fs = ftp.getFtpPath()+ FTPLoader.Translator.translitor(pt.getFileName())+ftp.getRand();
        String newFs = fs +"/"+ FTPLoader.Translator.translitor(ftpPath.getName());
        pathFTP.add(newFs.replace("/www/","http://"));
    }

}
