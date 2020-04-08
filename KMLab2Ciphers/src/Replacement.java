public class Replacement {
    private String X,Y;
    private int key, leftBorder=97, numbAlph=27;
    private Character[] special={' '};
    public Replacement(int aK){
        key=aK;
    }

    public String Ex(String X){
        StringBuilder ySb=new StringBuilder();
        for(int i=0;i<X.length();i++){
            int codeASCII =(int)X.charAt(i);
            int code=(checkCode(codeASCII) ? codeASCII-leftBorder:26);
            int value=(code + key)%numbAlph;
            int newCode=(value<0 ? value+numbAlph:value ) + leftBorder;
            ySb.append(Character.toChars((newCode==123 ? ' ':newCode)));
        }
        Y=ySb.toString();
        return Y;
    }

    public String Dx(String Y){
        StringBuilder xSb=new StringBuilder();
        for(int i=0;i<Y.length();i++){
            int codeASCII =(int)Y.charAt(i);
            int code=(checkCode(codeASCII) ? codeASCII-leftBorder:26);
            int value=(code - key)%numbAlph;
            int newCode=(value<0 ? value+numbAlph:value ) + leftBorder;
            xSb.append(Character.toChars((newCode==123 ? ' ':newCode)));
        }
        return xSb.toString();
    }

    public boolean checkCode(int code){
        return (96<code&& code<123);
    }


    public static void main(String[] args){
        Replacement replacement=new Replacement(5);
        System.out.println(replacement.Ex("ab cd"));
        System.out.println(replacement.Dx("ab cd"));
    }
}
