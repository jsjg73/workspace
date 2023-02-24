import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class P23309 extends InputHelper{
    private static int[] pre = new int[1000001];
    private static int[] next = new int[1000001];

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(fis()));
        String[] NM = br.readLine().split(" ");
        int N = Integer.parseInt(NM[0]);
        int M = Integer.parseInt(NM[1]);

        String[] stationsInput = br.readLine().split(" ");
        int[] stations = new int[stationsInput.length];
        for(int i=0; i< N; i++) {
            stations[i] = Integer.parseInt(stationsInput[i]);
        }

        for(int i=0; i< N; i++) {
            int nextIdx = (i+1)% stations.length;

            int currentStation = stations[i];
            int nextStation = stations[nextIdx];
            next[currentStation] = nextStation;
            pre[nextStation] = currentStation;
        }

        StringBuffer sb = new StringBuffer();

        for(int i=0; i<M; i++) {
            String[] input = br.readLine().split(" ");
            String cmd = input[0];

            int curr = Integer.parseInt(input[1]);
            int newStation = -1;
            if(input.length == 3) {
                newStation = Integer.parseInt(input[2]);
            }

            if("BN".equals(cmd)) {
                sb.append(next[curr]);
                add(curr, newStation);
            }else if("BP".equals(cmd)) {
                sb.append(pre[curr]);
                add(pre[curr], newStation);
            }else if("CN".equals(cmd)) {
                sb.append(next[curr]);
                remove(next[curr]);
            }else if("CP".equals(cmd)) {
                sb.append(pre[curr]);
                remove(pre[curr]);
            }
            sb.append("\n");
        }
        System.out.println(sb.toString());
    }

    public static void remove(int id) {
        int p = pre[id];
        int n = next[id];
        pre[n] = p;
        next[p] = n;
    }

    public static void add(int before, int newStation) {
        int after = next[before];
        pre[newStation] = before;
        next[newStation] = after;

        pre[after] = newStation;
        next[before] = newStation;
    }
}
