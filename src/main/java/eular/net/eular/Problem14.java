package eular.net.eular;

/**
 * Created by bharat on 4/3/18.
 */
public class Problem14 {

    private static final int GRIDSIZE = 20;

    // private static int [] path = new int[GRIDSIZE+1];
    private static long [][] pathCount = new long[GRIDSIZE+1][GRIDSIZE+1];
    public static void main(String[] args) {
        System.out.println(findTotalPath(0, 0));
    }

    private static long findTotalPath(int row, int column) {
        for(int i = GRIDSIZE;i>=0;i-- ){
            for(int j = GRIDSIZE;j>=0;j--){
                if(i==GRIDSIZE&&j==GRIDSIZE){
                    pathCount[i][j]=0;
                }else if((i==GRIDSIZE&&j==GRIDSIZE-1)||(i==GRIDSIZE-1&&j==GRIDSIZE)){
                    pathCount[i][j]=1;
                }else if(i==GRIDSIZE){
                    pathCount[i][j] = pathCount[i][j+1];
                }else if(j==GRIDSIZE){
                    pathCount[i][j] = pathCount[i+1][j];
                }else{
                    pathCount[i][j] = pathCount[i][j+1] + pathCount[i+1][j];
                }
            }
        }

        return pathCount[0][0];
    }
}
