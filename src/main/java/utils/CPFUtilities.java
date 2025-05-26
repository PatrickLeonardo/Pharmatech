package utils;

public class CPFUtilities {

    public static void main(String[] args) {
        
        char[] a = new char[10];
        a[0] = '1';
        a[1] = '2';
        a[2] = '3';

        System.out.println(new String(a));

    }

    public static boolean validate(String CPF) {
        
        CPF = CPF.replace(".", "").replace("-", "");
        if (CPF.length() != 11) return false;

        int weight = 1;
        int result1 = 0;
        int result2 = 0;

        for(int index = 0; index < CPF.length()-1; index++) {

            int digit = Integer.parseInt(Character.toString(CPF.charAt(index)));
            
            if (!Character.toString(CPF.charAt(CPF.length()-2)).equals(String.valueOf(digit))) {
                result1 += digit * weight;
            }
             
            result2 += digit * (weight - 1);
            weight++;

        }

        int register1 = result1 % 11;
        int register2 = result2 % 11;

        if (register1 == 10) register1 = 0;
        if (register2 == 10) result2 = 0;

        if (Character.toString(CPF.charAt(CPF.length()-1)).compareTo(String.valueOf(0)) == register2 &&
            Character.toString(CPF.charAt(CPF.length()-2)).compareTo(String.valueOf(0)) == register1) {

            return true;

        } else return false;

    }

    public static String format(String CPF) {
        
        CPF = CPF.strip();
        
        if (CPF.length() < 11) {
            return "";
        }
        
        CPF = CPF.replace(".", "").replace("-", "");
        if (CPF.length() > 11) {
            return "";
        }
        
        CPF = new StringBuilder(CPF).insert(3, ".").insert(7, ".").insert(11, "-").toString();
        
        return CPF;

    }    
    
}
