package eular.net.eular;

/**
 * Created by bharat on 4/3/18.
 */
public class Problem17 {

    private static final int tillNo = 1000;
    public static final int hundred_And = 10;
    public static final int hundred = 7;
    public static final int thousand = 8;

    public static void main(String[] args) {
        int sum = 0;
        int temp =0,i=0;
        int firstDigit = 0;
        for ( temp = 1; temp <= tillNo; temp++) {
            i = temp;
            if (i < 10) {
                sum += oneDigitWord(i);
            } else if (i > 9 && i < 100) {
                sum += twoDigitword(i);
            } else if (i > 99 && i < 1000) {
                if (i % 100 != 0) {
                    firstDigit = i / 100;
                    i = i % 100;
                    sum += oneDigitWord(firstDigit);
                    sum += hundred_And;
                    sum += twoDigitword(i);
                } else {
                    firstDigit = i / 100;
                    sum += oneDigitWord(firstDigit);
                    sum += hundred;
                }
            } else{
                sum +=3;//one
                sum += thousand;//
            }
        }
        System.out.println(sum);

    }

    private static int twoDigitword(int i) {
        int finalValue = 0;
        if (i / 10 == 1) {
            switch (i) {
                case 10:
                    finalValue = 3;
                    break;
                case 11:
                case 12:
                    finalValue = 6;
                    break;

                case 13:
                case 14:
                case 19:
                case 18:
                    finalValue = 8;
                    break;
                case 15:
                case 16:
                    finalValue = 7;
                    break;
                case 17:
                    finalValue = 9;

            }
        } else {
            switch (i / 10) {
                case 2:
                case 3:
                case 8:
                case 9:
                    finalValue = 6;
                    break;
                case 5:
                case 6:
                case 4:
                    finalValue = 5;
                    break;
                case 7 :
                    finalValue =7;
            }
            finalValue += oneDigitWord(i % 10);
        }
        return finalValue;
    }

    private static int oneDigitWord(int i) {
        switch (i) {
            case 1:
            case 2:
            case 6:
                return 3;
            case 4:
            case 5:
            case 9:
                return 4;
            case 7:
            case 8:
            case 3:
                return 5;
            default:
                return 0;
        }
    }
}
