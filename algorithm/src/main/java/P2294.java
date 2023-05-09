import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class P2294 extends InputHelper{
    static int sx,sy,ex,ey = -1;
    static char[][] map;
    static int[][] dist;
    static List<Node> nodes = new ArrayList<>();
    static int[][] dxy = {{1,0}, {-1,0}, {0,1}, {0,-1}};

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(fis()));
        String s = br.readLine();
        StringTokenizer stringTokenizer = new StringTokenizer(s, " ");
        int n = Integer.parseInt(stringTokenizer.nextToken());
        int h = Integer.parseInt(stringTokenizer.nextToken());
        int d = Integer.parseInt(stringTokenizer.nextToken());
        int k = 0;

        //map 초기화
        map = new char[n+2][n+2];
        for (char[] arr : map ) {
            Arrays.fill(arr, '.');
        }

        for(int i = 1; i<= n ; i++) {
            String line = br.readLine();
            for (int j = 1; j <= n; j++) {
                char c = line.charAt(j - 1);
                if(c == 'S') {
                    sx = i;
                    sy = j;
                    map[i][j] = c;
                }else if( c == 'E') {
                    ex = i;
                    ey = j;
                    map[i][j] = c;
                }else if(c == 'U') {
                    k++;
                    nodes.add(new Node(k, i, j));
                    map[i][j] = c;
                }
            }
        }
        nodes.add(0, new Node(0, sx, sy));
        nodes.add(new Node(k+1, ex, ey));


        dist = new int[k+2][k+2];

        for (int i = 0; i <= k; i++) {
            Node node = nodes.get(i);
            int x = node.x;
            int y = node.y;

            LinkedList<Item> que = new LinkedList<>();
            que.add(new Item(x, y, 0));
            boolean[][] vi = new boolean[n + 2][n + 2];

            while(!que.isEmpty()) {
                Item now = que.poll();
                int nx = now.x;
                int ny = now.y;
                int nc = now.cost;
                if(nx == 0 || nx == n +  1 || ny == 0 || ny == n + 1) {
                    continue;
                }
                if(vi[nx][ny]) {
                    continue;
                }

                vi[nx][ny] = true;
                if(isNotEmptyPosition(nx, ny)) {
                    connect(x, y, nx, ny, nc);
                }

                for (int j = 0; j < 4; j++) {
                    que.add(new Item(nx + dxy[j][0], ny + dxy[j][1], nc + 1));
                }
            }
        }

        //

    }

    private static void connect(int x, int y, int nx, int ny, int cost) {
        Node from = null;
        Node to = null;
        for(Node node : nodes) {
            if(node.x == x && node.y == y) {
                from = node;
            }
            if(node.x == nx && node.y == ny) {
                to = node;
            }
        }

        dist[from.idx][to.idx] = dist[to.idx][from.idx] = cost;
    }

    private static boolean isNotEmptyPosition(int nx, int ny) {
        return map[nx][ny] != '.';
    }

    static class Node {
        int idx,x,y;
        public Node (int idx, int x, int y) {
            this.idx =idx;
            this.x = x;
            this.y = y;
        }
    }

    static class Item {
        int x,y,cost;

        public Item (int x, int y, int cost) {
            this.x = x;
            this.y = y;
            this.cost = cost;
        }
    }
}
