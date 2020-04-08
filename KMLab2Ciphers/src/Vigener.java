public class Vigener {
    private int numbAlph=27,leftBorder=97;
    private String key;

    public Vigener(String aKey){
        key=aKey;
    }

    public String Ex(String X){
       adjustKey(X);
       StringBuilder res=new StringBuilder();
       for(int i=0;i<X.length();i++){
           int posCurr=(checkCode(X.charAt(i)) ? (int)X.charAt(i)-leftBorder : 26);
           int startPos=(checkCode(key.charAt(i)) ? (int) key.charAt(i) - leftBorder:26);
           int newCode=(posCurr+startPos)%numbAlph+leftBorder;
           res.append(Character.toChars(newCode==123 ? ' ':newCode));
       }
       return res.toString();
    }

    public String Dx(String Y){
        adjustKey(Y);
        StringBuilder res=new StringBuilder();
        for(int i=0;i<Y.length();i++){
            int posCurr=(checkCode(Y.charAt(i)) ? (int)Y.charAt(i)-leftBorder : 26);
            int startPos=(checkCode(key.charAt(i)) ? (int) key.charAt(i) - leftBorder:26);
            int oldPos=(posCurr-startPos)%numbAlph;
            int newCode=(oldPos<0 ? oldPos+numbAlph:oldPos)+leftBorder;
            res.append(Character.toChars(newCode==123 ? ' ':newCode));
        }
        return res.toString();
    }

    public boolean checkCode(int code){
        return (96<code&& code<123);
    }


    public void adjustKey(String X){
        StringBuilder resKey=new StringBuilder();
        for(int i=0;i<X.length();i++){
            resKey.append(key.charAt(i%key.length()));
        }
        key=resKey.toString();
    }



    public static void main(String[] args){
       Vigener vigener=new Vigener("le mon");
       String ciphr=vigener.Ex("sapienti sat");
       System.out.println(ciphr);
       System.out.println(vigener.Dx(ciphr));
    }
}
