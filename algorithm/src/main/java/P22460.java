import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class P22460 extends InputHelper{
    static int[][] chairMap;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(fis()));
        Integer n = parseInt(br.readLine());
        chairMap = new int[n+2][n+2];
        init(br, n);

        int chairNumber = pickWinnerChair(1,1,n);
        System.out.println(chairNumber);
    }

    private static int pickWinnerChair(int rowOffset, int colOffset, Integer leng) {
        if(leng == 1) {
            return chairMap[rowOffset][colOffset];
        }
        int[] chairs = {0,0,0,0};

        int leftTop = pickWinnerChair(rowOffset, colOffset, leng/2);
        sort(chairs, leftTop);

        int rightTop = pickWinnerChair( rowOffset  , colOffset + leng/2, leng/2);
        sort(chairs, rightTop);

        int leftBottom = pickWinnerChair(rowOffset + leng/2, colOffset, leng/2);
        sort(chairs, leftBottom);

        int rightBottom = pickWinnerChair(rowOffset + leng/2, colOffset + leng/2, leng/2);
        sort(chairs, rightBottom);

        return chairs[1];
    }

    private static void sort(int[] chairs, int newChair) {
        chairs[0] = newChair;
        for(int i = 0; i<chairs.length -1; i ++) {
            if(chairs[i] > chairs[i+1]){
                swap(chairs, i, i+1);
            }else{
                break;
            }
        }
    }

    private static void swap(int[] chairs, int from, int to) {
        int tmp = chairs[from];
        chairs[from] = chairs[to];
        chairs[to] = tmp;
    }

    private static void init(BufferedReader br, Integer n) throws IOException {
        for (int i = 1; i <= n; i++) {
            String line = br.readLine();
            String[] chairNums = line.split(" ");
            for (int j = 1; j <= n; j++) {
                chairMap[i][j] = parseInt(chairNums[j-1]);
            }
        }
    }

    private static Integer parseInt(String str) {
        return Integer.parseInt(str);
    }
}
