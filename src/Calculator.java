import java.util.Scanner;
public class Calculator {
    public static void main(String[] args) {
        Scanner expression = new Scanner(System.in);
        System.out.println("Введите выражение.");
        String expressionStr = expression.nextLine();
        String[] subStrExp = new String[3];
        try{
            subStrExp = expressionStr.split(" ");
            if (subStrExp.length > 3){
                throw new Exception("Формат математической операции не удовлетворяет заданию");
            }
            else if(subStrExp.length == 1){
                throw new Exception("Cтрока не является математической операцией");
            }
        }
        catch (Exception ex){
            ex.printStackTrace();
            System.exit(0);
        }
        Transformation transXY = new Transformation();
        transXY.transformation(subStrExp[0], subStrExp[2]);
        transXY.setOperation(subStrExp[1]);
        try {
            if (transXY.xIsRoman == transXY.yIsRoman) {
                if(!transXY.xIsRoman) transXY.transInt();
            }
            else {
                throw new Exception("используются одновременно разные системы счисления");
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }
        Check checkOfValues = new Check();
        checkOfValues.setOp(transXY.op);
        checkOfValues.setX(transXY.x);
        checkOfValues.setY(transXY.y);
        try {
            checkOfValues.check();
        }
        catch (Exception ex){
            ex.printStackTrace();
            System.exit(0);
        }
        //getSolution
        switch (transXY.op) {
            case "+" -> {
                Operation sum = Operation.SUM;
                sum.action(transXY.x, transXY.y);
            }
            case "-" -> {
                Operation subt = Operation.SUBTRACTION;
                subt.action(transXY.x, transXY.y);
            }
            case "*" -> {
                Operation mult = Operation.MULTIPLY;
                mult.action(transXY.x, transXY.y);
            }
            case "/" -> {
                Operation div = Operation.DIVISION;
                div.action(transXY.x, transXY.y);
            }
        }


    }
}
class Check{
    private int x;
    private int y;
    private String op;
    public void setX(int x){
        this.x = x;
    }
    public void setY(int y){
        this.y = y;
    }
    public void setOp(String Op){
        this.op = Op;
    }
    public void check() throws Exception{
        if(x<1 || x>10 || y<1 || y>10) throw new Exception("x или y находятся вне интервала [1;10]");
        else if(!(op.equals("+") || op.equals("-") || op.equals("*") || op.equals("/"))) throw new Exception("Неверный операнд");
    }
}
class Transformation{
    private String xStr;
    private String yStr;
    String op;
    int x;
    int y;
    boolean xIsRoman;
    boolean yIsRoman;
    static boolean numbersAreRoman;
    public void transformation(String xStr, String yStr){
        xIsRoman = true;
        yIsRoman = true;
        switch (xStr){
            case "I" -> x = 1;
            case "II" -> x = 2;
            case "III" -> x = 3;
            case "IV" -> x = 4;
            case "V" -> x = 5;
            case "VI" -> x = 6;
            case  "VII" -> x = 7;
            case  "VIII" -> x = 8;
            case "IX" -> x = 9;
            case "X" -> x = 10;
            default -> xIsRoman = false;
        }
        this.xStr = xStr;
        switch (yStr){
            case "I" -> y = 1;
            case "II" -> y = 2;
            case "III" -> y = 3;
            case "IV" -> y = 4;
            case "V" -> y = 5;
            case "VI" -> y = 6;
            case  "VII" -> y = 7;
            case  "VIII" -> y = 8;
            case "IX" -> y = 9;
            case "X" -> y = 10;
            default -> yIsRoman = false;
        }
        this.yStr = yStr;
        numbersAreRoman = xIsRoman && yIsRoman;
    }
    public void setOperation(String op){
        this.op = op;
    }
    public void transInt(){
        try {
            x = Integer.parseInt(xStr.trim());
            y = Integer.parseInt(yStr.trim());
        }
        catch (Exception e) {
            e.printStackTrace();
            System.out.println("x и y должны быть целыми чилсами");
            System.exit(0);
        }
    }
}
class RomanNumbers{
    public static String romanNumbers(int number, String firstN, String secondN, String thirdN){
        switch (number){
            case 1 ->{
                return firstN;
            }
            case 2 ->{
                return firstN + firstN;
            }
            case 3 ->{
                return firstN + firstN + firstN;
            }
            case 4 ->{
                return firstN + secondN;
            }
            case 5 ->{
                return secondN;
            }
            case 6 ->{
                return secondN + firstN;
            }
            case 7 ->{
                return secondN + firstN + firstN;
            }
            case 8 ->{
                return secondN + firstN + firstN + firstN;
            }
            case 9 ->{
                return firstN + thirdN;
            }
            case 10 ->{
                return thirdN;
            }
        }
        return "";
    }
    public static String convertToRoman(int number){
        String romanOnes = romanNumbers(number%10,"I", "V", "X");
        number/=10;
        String romanTens = romanNumbers(number%10, "X", "L", "C");
        number/=10;
        String romanHundreds = romanNumbers(number%10, "C", "D", "M");
        return romanHundreds + romanTens + romanOnes;
    }
}
enum Operation{
    SUM{
        public void action(int x, int y){
            if(Transformation.numbersAreRoman){
                System.out.println(RomanNumbers.convertToRoman(x+y));
            }
            else {
                System.out.println(x + y);
            }
        }
    },
    SUBTRACTION{
        public void action(int x, int y) {
            try {
                if (Transformation.numbersAreRoman && (x - y) > 0) {
                    System.out.println(RomanNumbers.convertToRoman(x - y));
                } else if (Transformation.numbersAreRoman && (x - y) < 0) {
                    throw new Exception("в римской системе нет отрицательных чисел");
                } else {
                    System.out.println(x - y);
                }
            }
            catch (Exception e) {
                e.printStackTrace();
                System.exit(0);
            }
        }
    },
    MULTIPLY{
        public void action(int x, int y){
            if (Transformation.numbersAreRoman) {
                System.out.println(RomanNumbers.convertToRoman(x * y));
            } else {
                System.out.println(x * y);
            }
        }
    },
    DIVISION{
        public void action(int x, int y){
            if (Transformation.numbersAreRoman) {
                System.out.println(RomanNumbers.convertToRoman(x / y));
            } else {
                System.out.println(x / y);
            }
        }
    };
    public abstract void action(int x, int y);
}