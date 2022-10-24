import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.util.ArrayList;

public class FileManager {
    ParserText pt;
    File fpath;
    ParserImage pi;
    FTPLoader ftp;
    boolean ftpon = false;

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
        File newDirectory = new File(file.getAbsolutePath()+"\\"+pt.fileName);
        System.out.println(newDirectory);
        System.out.println("попытка создать директорию фото = "+newDirectory.mkdirs());
        recText(new File(newDirectory.getAbsolutePath()+"\\"+pt.fileName+".txt"));
        for(String s : pi.getUrlImage()){
            recImage(newDirectory.getAbsoluteFile(),s);
        }
    }
    public void recText(File file){
        try {
            FileWriter fw = new FileWriter(file);
            fw.write(pt.getTextContent());
            fw.write(System.lineSeparator());
            fw.write(System.lineSeparator());
            fw.write(pt.namePost);
            fw.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
    public void recImage(File file,String url){
        try{
            String fileName = file.getAbsolutePath()+"\\"+pt.getFileName()+"img"+conterImg+".jpg";
            conterImg++;
            String website = url;
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
                ftp.ftpConn(new File(fileName));
            }

            inputStream.close();
            outputStream.close();

        } catch(Exception e) {
            System.out.println("Exception: " + e.getMessage());
        }
    }
}
