package gui;

import javax.swing.*;
import java.io.File;

public class DialogVk {
    public String urlVk(){
        String otvet = JOptionPane.showInputDialog(null,"Введите адрес записи в вк","Garmony Parser",1);
        return otvet;
    }
    public File chooserVK(){
        JFileChooser file = new JFileChooser();
        file.setDialogTitle("Сохранить в ...");
        file.setMultiSelectionEnabled(false);
        file.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        file.setFileHidingEnabled(false);
        if(file.showOpenDialog(null)==JFileChooser.APPROVE_OPTION){
            File f = file.getSelectedFile();
            return f;
        }
        else return null;
    }
    public void finish(String stroka){
        JOptionPane.showMessageDialog(null,"Ваши данные сохранены тут  :"+System.lineSeparator() + stroka);
    }
}
