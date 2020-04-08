public class Affine {
    private String X,Y;
    private int a,b,aObr, leftBorder=97, numbAlph=27;
    private Character[] special={' '};
    public Affine(int aA,int aB){
        a=aA;
        b=aB;
    }

    public String Ex(String X){
        StringBuilder ySb=new StringBuilder();
        for(int i=0;i<X.length();i++){
            int codeASCII =(int)X.charAt(i);
            int code=(checkCode(codeASCII) ? codeASCII-leftBorder:26);
            int value=(a*code + b)%numbAlph;
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
            ext_gcd(a,numbAlph);
            int value=(((code-b))*aObr)%numbAlph;
            int newCode=(value<0 ? value+numbAlph:value ) + leftBorder;
            xSb.append(Character.toChars((newCode==123 ? ' ':newCode)));
        }
        return xSb.toString();
    }

    public int ext_gcd(int a, int b){
        int q, r, x1, x2,d,x;
        x2 = 1;
        x1 = 0;
        while (b > 0) {
            q = a / b;
            r = a - q * b;
            x = x2 - q * x1;
            a = b;
            b = r;
            x2 = x1;
            x1 = x;
        }
        d = a;
        x = x2;
        aObr=x;
        return d;
    }


    public boolean checkCode(int code){
        return (96<code&& code<123);
    }

    public static void main(String[] args){
       Affine affe=new Affine(4,5);
        String ciphr=affe.Ex("sapienti sat");
        System.out.println(ciphr);
        System.out.println(affe.Dx(ciphr));
    }

}
