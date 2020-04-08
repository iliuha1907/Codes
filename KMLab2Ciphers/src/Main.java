import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner in=new Scanner(System.in);
        ReadAndCiphr ra=new ReadAndCiphr("Text","Encrypted.txt","Key.txt");
        System.out.println("Enter 1 for Replacement, 2 for Affine, 3 for Vigener");
        int flag=in.nextInt();
        switch (flag){
            case 1:
                ra.proceedEncrRepl();
                ra.proceedDecrRepl();
                break;
            case 2:
                ra.proceedEncrAff();
                ra.proceedDecrAff();
                break;
            case 3:
                ra.proceedEncrVig();
                ra.proceedDecrVig();
                break;
                default:
                    System.out.println("Error");
                    break;
        }
        in.close();
    }
}
