import com.ibm.icu.text.Transliterator;
import org.apache.commons.net.ftp.FTPClient;

import javax.imageio.IIOException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class FTPLoader {
    private String hostAddress;
    private String log;
    private String password;
    private ParserText pt;
    private String ftpPath;
    public FTPLoader(String hostAddress, String log, String password, ParserText pt,String ftpPath) {
        this.hostAddress = hostAddress;
        this.log = log;
        this.password = password;
        this.pt = pt;
        this.ftpPath = ftpPath;
    }

    public void ftpConn(File filePath) throws IOException {
        FTPClient fClient = new FTPClient();
        FileInputStream fInput = new FileInputStream(filePath);
        String fs = ftpPath+Translator.translitor(pt.getFileName());
        /*
        хм имеем проблема что на ftp сервере не создаються кирилические имена
        транслитилирвать для этого создам отдельный статичный класс со стачиным методом да бы не создавать
        экземпляры
         */
        try {
            fClient.connect(hostAddress);
            fClient.enterLocalPassiveMode();
            fClient.login(log, password);
            System.out.println("Пытаемься создать директорию по адресу = "+fs+" "+fClient.makeDirectory(fs));
            String newFs = fs+"/"+Translator.translitor(filePath.getName());
            fClient.storeFile(newFs,fInput);
            fClient.logout();
            fClient.disconnect();
        } catch (IOException ex) {
            System.err.println(ex);
        }
    }
    static class Translator{
        public static final String CYRILLIC_TO_LATIN = "Cyrillic-Latin";
        public static String translitor(String st){
            Transliterator toLatinTrans = Transliterator.getInstance(CYRILLIC_TO_LATIN);
            String result = toLatinTrans.transliterate(st);
            return result;
        }
    }
}
