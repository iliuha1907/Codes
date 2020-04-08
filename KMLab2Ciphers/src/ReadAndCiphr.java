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
    private List<String> data=new ArrayList<>();

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
                    data.add(buf);
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
                for(String s:data){
                    bw.write(s);
                }

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
                for(String s:data){
                    bw.write(s);
                }
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
      for(int i=0;i<data.size();i++){
          data.set(i,replacement.Ex(data.get(i)));
      }
      writeEncr();
   }

    public void proceedDecrRepl(){
        for(int i=0;i<data.size();i++){
            data.set(i,replacement.Dx(data.get(i)));
        }
      writeDecr();
    }

    public void proceedEncrAff(){
        read();
        for(int i=0;i<data.size();i++){
            data.set(i,affine.Ex(data.get(i)));
        }
        writeEncr();
    }

    public void proceedDecrAff(){
        for(int i=0;i<data.size();i++){
                data.set(i,affine.Dx(data.get(i)));
            }
        writeDecr();
    }

    public void proceedEncrVig(){
        read();
        for(int i=0;i<data.size();i++){
            data.set(i,vigener.Ex(data.get(i)));
        }
        writeEncr();
    }

    public void proceedDecrVig(){
        for(int i=0;i<data.size();i++){
                data.set(i,vigener.Dx(data.get(i)));
            }
        writeDecr();
    }
}
