package eular.net.eular;

/**
 * Created by bharat on 4/3/18.
 * Work out the first ten digits of the sum of the following one-hundred 50-digit numbers.
 */

        import java.io.BufferedReader;
        import java.io.File;
        import java.io.FileNotFoundException;
        import java.io.FileReader;
        import java.io.IOException;

public class Problem13 {
    private static int[][] data =  new int[100][50];

    private static FileReader reader;
    private static BufferedReader bfReader;
    public static void main(String[] args){
        populateData();
        int[] result = new int[10];
        int i=49;
        int carry =0;
        while(i>9){
            carry = findSum(i,carry)/10;
            i--;
        }
        int sum=0;
        while(i>=0){
            sum = findSum(i,carry);
            if(i==0){
                result[9-i]= sum;
            }else{
                result[9-i] =  sum%10;
                carry = sum/10;
            }
            i--;
        }
        for(i=9;i>=0;i--){
            System.out.print(result[i]);
        }
    }
    private static int findSum(int col, int previousCarry) {
        int sum =previousCarry;
        for(int i=0; i<100;i++){
            sum += data[i][col];
        }
        return sum;
    }
    public static void populateData(){
        File path = new File("eular/Problem13");
        try{
            reader = new FileReader(path);
            bfReader =  new BufferedReader(reader);

            String buffer = null;
            int j=0;
            while((buffer =bfReader.readLine())!= null&& j<101){
                int i=0;
                int [] line = new int [50];
                while(i<buffer.length()){
                    line[i] = Integer.parseInt(String.valueOf((buffer.charAt(i))));
                    i++;
                }
                storeline(line,j++);
            }
        }catch(FileNotFoundException fnf){
            fnf.printStackTrace();
        }catch(IOException ioe){
            ioe.printStackTrace();
        }
    }
    private static void storeline(int[] line, int index) {
        data[index] = line;
    }
}
