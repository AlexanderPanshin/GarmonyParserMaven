import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class CuterImg {
    FileManager fm;

    public CuterImg(FileManager fm) {
        this.fm = fm;
        cutImage();
    }
    public void cutImage(){
        try {
            if(!fm.getFileImg().isEmpty()&&!fm.isNoImage()) {
                BufferedImage image = ImageIO.read(new File(fm.getFileImg().get(0)));
                int x = image.getWidth() / 2 - 100;
                int y = image.getHeight() / 2 - 100;
                if (image.getHeight() > 400) {
                    System.out.println("Start big cuter");
                    image = image.getSubimage(x, y, 300, 300);
                    ImageIO.write(image, "JPG", new File(fm.getFileImg().get(0).replace("img", "cuter")));
                    if(fm.isFtpon()){
                        fm.getFtp().ftpConn(new File(fm.getFileImg().get(0).replace("img", "cuter")));
                    }
                } else {
                    System.out.println("Start small cuter");
                    image = image.getSubimage(x, y, 200, 200);
                    ImageIO.write(image, "JPG", new File(fm.getFileImg().get(0).replace("img", "cuter")));
                    if(fm.isFtpon()){
                        fm.getFtp().ftpConn(new File(fm.getFileImg().get(0).replace("img", "cuter")));
                    }
                }
            }else System.out.println("Нет фотокарточек кутер не нужен");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
