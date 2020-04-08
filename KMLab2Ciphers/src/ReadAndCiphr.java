import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ReadAndCiphr {
    private String inputFile,outputFile, keyFile;
    private Replacement replacement;
    private Affine affine;
    private Vigener vigener;
    private String data="";

    public ReadAndCiphr(String aInputFile, String aOutputFile, String aKeyFile){
        inputFile=aInputFile;
        outputFile=aOutputFile;
        keyFile=aKeyFile;
        initKeys();
    }

    public void initKeys(){
        List<String> keys=new ArrayList<>();
        try {
            Scanner input = new Scanner(new File(keyFile));
            try {
                for(int i=0;i<3;i++){
                    String buf = input.nextLine();
                    keys.add(buf);
                }
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            } finally {
                input.close();
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        replacement=new Replacement(Integer.parseInt(keys.get(0)));
        String[] aB=keys.get(1).split(" ");
        affine=new Affine(Integer.parseInt(aB[0]),Integer.parseInt(aB[1]));
        vigener=new Vigener(keys.get(2));
    }

    public void read(){
        try {
            Scanner input = new Scanner(new File(inputFile));
            try {
                while (input.hasNextLine()) {
                    String buf = input.nextLine();
                    data+=(buf.toLowerCase());
                }
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            } finally {
                input.close();
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public void writeEncr(){
        try {
            BufferedWriter bw=new BufferedWriter(new FileWriter(new File(outputFile)));
            try {
                bw.write(data);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            } finally {
                bw.close();
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public void writeDecr(){
        try {
            Scanner input = new Scanner(new File(outputFile));
            BufferedWriter bw=new BufferedWriter(new FileWriter(new File("decrypt.txt")));
            try {
                bw.write(data);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            } finally {
                input.close();
                bw.close();
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public void proceedEncrRepl(){
        read();
        data=replacement.Ex(data);
        writeEncr();
    }

    public void proceedDecrRepl(){
        data=replacement.Dx(data);
        writeDecr();
    }

    public void proceedEncrAff(){
        read();
        data=affine.Ex(data);
        writeEncr();
    }

    public void proceedDecrAff(){
        data=affine.Dx(data);
        writeDecr();
    }

    public void proceedEncrVig(){
        read();
        data=vigener.Ex(data);
        writeEncr();
    }

    public void proceedDecrVig(){
        data=vigener.Dx(data);
        writeDecr();
    }
}

