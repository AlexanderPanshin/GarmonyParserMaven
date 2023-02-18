package gui;

import javax.swing.*;

public class DialogFTP{
  private int otvet;

    public DialogFTP() {
        this.otvet = JOptionPane. showConfirmDialog (null, "Включить модуль FTP?","Garmony Parser",1);
    }
    public String otvetReturn(){
        if(otvet == 0){
            return "да";
        }
        if (otvet==1){
            return "нет";
        }else return "выход";
    }
    public String hostFTPReturn(){
        String otvet = JOptionPane.showInputDialog(null,"Введите адрес FTP хоста","Garmony Parser",1);
        return otvet;
    }
    public String loginFTPReturn(){
        String otvet = JOptionPane.showInputDialog(null,"Введите логин FTP хоста","Garmony Parser",1);
        return otvet;
    }

    public String passFTPReturn(){
        String otvet = JOptionPane.showInputDialog(null,"Введите пароль FTP хоста","Garmony Parser",1);
        return otvet;
    }
    public String pathFTPReturn(){
        String otvet = JOptionPane.showInputDialog(null,"Введите путь FTP хоста","Garmony Parser",1);
        return otvet;
    }
}
