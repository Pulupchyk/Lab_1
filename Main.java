package org.kpi.fpm.lab_1;

import java.io.*;

public class Main
{
    public static void main(String[] args)
    {
        Program.work("note.txt", "result.txt", ',', '+');
    }
}

class InputFile
{
    static String in_file_name;
    static String[] lines;
    static int length = 0;
    static char delimiter;
    static char unit;
    public static void read_file() {
        int br = 0;
        int check = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(in_file_name))) {
            String s;
            while ((s = reader.readLine()) != null) {
                for (int i = 0; i < s.length(); i++) {
                    char c = s.charAt(i);
                    if (c == '"') { br++; }
                }
                length++;
                if (br % 2 != 0) { throw new Exception("Не парна кількість лапок"); }
            }
        } catch (Exception ex) {
            check++;
            System.out.println(ex.getMessage());
        }
        lines = new String[length];
        if(check == 0) {
            try (BufferedReader reader = new BufferedReader(new FileReader(in_file_name))) {
                String str,line = "";
                String new_str = "";
                int index = 0;
                while ((str = reader.readLine()) != null) {
                    new_str = str.replace('"', '*');
                    new_str = new_str.replace("**", "0");
                    int quotes = 0;
                    char[] c = new_str.toCharArray();
                    if(c[0] == delimiter){
                        c[0] = '@';
                    }
                    if(c[new_str.length()-1] == delimiter) {
                        c[new_str.length()-1] = '@';
                    }
                    for (int i = 0; i < c.length; i++) {
                        if(c[i] == '*'){
                            line += "1";
                            quotes++;
                        }
                        else if(c[i] == '@'){
                            line += " ";
                        }
                        else if(c[i] == delimiter && quotes%2 ==0){
                            line += "-";
                        }
                        else{
                            line += "0";
                        }
                    }
                    line = line.trim();
                    String words[] = line.split("-");
                    String new_line = "";
                    char[] ch;
                    for(int k = 0; k < words.length; k++){
                        ch = words[k].toCharArray();
                        int new_quotes = 0;
                        int res = 0;
                        for (int j = 0; j < ch.length; j++) {
                            if (ch[j] == '1') {
                                new_quotes++;
                            }
                        }
                        if (ch[0] == '1' && new_quotes == 2) {
                            res = ch.length - 2;
                        } else if (ch[0] != '1' && new_quotes == 2) {
                            res = ch.length;
                        }
                        else if (new_quotes == 0) {
                            res = ch.length;
                        }
                        else{
                            res = ch.length - 2;
                        }
                        new_line += res + " ";
                    }
                    lines[index] = new_line.trim().replace(' ', unit);
                    line = "";
                    index++;
                }
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }
    public static void get_array(String[] array){
        for(int i = 0; i < lines.length; i++){
            array[i] = lines[i];
        }
    }
}
class OutputFile
{
    static String out_file_name;
    static String[] array;
    public static void write_file(){
        array = new String[InputFile.length];
        InputFile.get_array(array);
        try(FileWriter writer = new FileWriter(out_file_name, false))
        {
            for(int i = 0; i < array.length; i++){
                writer.write(array[i]);
                writer.append('\n');
                writer.flush();
            }
        }
        catch(IOException ex){
            System.out.println(ex.getMessage());
        }
    }
}
class Program
{
    public static void work(String in_file_name,String out_file_name, char delimiter, char unit){
        InputFile.in_file_name = in_file_name;
        OutputFile.out_file_name = out_file_name;
        InputFile.delimiter = delimiter;
        InputFile.unit = unit;
        InputFile.read_file();
        OutputFile.write_file();
    }
}