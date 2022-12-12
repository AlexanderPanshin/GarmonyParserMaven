
import org.apache.commons.net.ftp.FTPClient;


import java.io.File;
import java.io.FileInputStream;

import java.io.IOException;


public class FTPLoader {
    private String hostAddress;
    private String log;
    private String password;
    private ParserText pt;

    public String getFtpPath() {
        return ftpPath;
    }

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
        /*Переписал транслятор убрал библиотеку com.ibm.icu.text.Transliterator
        она странно траслировала символы в какие то сатанинские иероглифы и арабскую вязь
        а это ломало урлы. Я не хотел писать большой свитч ниже, но мне пришлось ...
         */
        public static String translitor(String st){
            st = st.toLowerCase();
            char [] charMass = st.toCharArray();
            for(int i = 0; i < charMass.length; i++){
                switch (charMass[i]){
                    case 'а':
                        charMass[i]='a';
                        break;
                    case 'б':
                        charMass[i]='b';
                        break;
                    case 'в':
                        charMass[i]='v';
                        break;
                    case 'г':
                        charMass[i]='g';
                        break;
                    case 'д':
                        charMass[i]='d';
                        break;
                    case 'е':
                    case 'э':
                        charMass[i]='e';
                        break;
                    case 'ё':
                    case 'о':
                        charMass[i]='o';
                        break;
                    case 'ж':
                        charMass[i]='z';
                        break;
                    case 'з':
                        charMass[i]='z';
                        break;
                    case 'и':
                    case 'ы':
                        charMass[i]='i';
                        break;
                    case 'й':
                        charMass[i]='i';
                        break;
                    case 'к':
                        charMass[i]='k';
                        break;
                    case 'л':
                        charMass[i]='l';
                        break;
                    case 'м':
                        charMass[i]='m';
                        break;
                    case 'н':
                        charMass[i]='n';
                        break;
                    case 'п':
                        charMass[i]='p';
                        break;
                    case 'р':
                        charMass[i]='r';
                        break;
                    case 'с':
                        charMass[i]='c';
                        break;
                    case 'т':
                        charMass[i]='t';
                        break;
                    case 'у':
                        charMass[i]='y';
                        break;
                    case 'ф':
                        charMass[i]='f';
                        break;
                    case 'х':
                        charMass[i]='h';
                        break;
                    case 'ц':
                    case 'ч':
                        charMass[i]='c';
                        break;
                    case 'ш':
                    case 'щ':
                        charMass[i]='s';
                        break;
                    case 'ъ':
                    case 'ь':
                        charMass[i]='_';
                        break;
                    case 'ю':
                        charMass[i]='u';
                        break;
                    case 'я':
                        charMass[i]='y';
                        break;
                }
            }
            return String.valueOf(charMass);
         }
    }
}
