/*
* 직접 푼 문제 점수 산정 방식 = 점수 - 대회 시작 후 지난 시간
* 가산점 - 가장 빨리 푼 사람에게만 점수 부여.단, 프리즈 이전에 풀어야함. (두 명 이상일 때는? 입력x)
* 보정 점수 :
*   남은 시간 = Min(프리즈 후 남은 시간, 직접 푼 마지막 문제 이후 남은 시간)
*   남은 시간 동안 획득 할 수 있는 최대 점수 :
*       필요 정보 : 못푼 문제 && 문제별 예측 시간 && 문제별 점수(문제 점수 -마지막 문제 푼 시간 - 예측 시간)
* */

/*
입출력

N : 문제 수  1000
M : 참가자 수  100,000
N*M : <= 1,000,000
A : 문제당 배점 (100 ~ 1000점)
V : [누가, 문제를, 언제]
B : 추가 점수
T : 프리즈 시간
E : 예측 시간
*/

import java.util.*;
import java.util.stream.Collectors;

public class PHS재첨프로그램 {
    public static void main(String[] args) {
        PHS재첨프로그램 program = new PHS재첨프로그램();
        int[] solution = program.solution(3, 5,
                new int[]{100, 200, 300},
                new int[][]{{1, 2, 10}, {2, 3, 20}, {5, 1, 5}, {4, 3, 10}, {3, 1, 15}, {5, 3, 35}, {4, 1, 30}, {2, 1, 50}},
                new int[]{10, 20, 30},
                40,
                new int[]{10, 15, 25});

        System.out.println(Arrays.toString(solution));
    }
    int[] 마지막문제푼시간 ;

    // 각 참가자가 직접 푼 점수 per 참가자
    int[] 누적점수;
    int[] 스코어보드점수;

    // 각 문제별 가장 빨리 푼 사람 번호 그리고 시간 저장 per 문제 수
    int[] k문제빨리푼사람 ;
    int[] k문제가풀린시간 ;

    // 직접 해결만 문제 목록 관리 per 참가자
    Set<Integer>[] 직접해결문제목록;

    class Expected implements Comparable<Expected>{
        int time;
        int number;

        @Override
        public int compareTo(Expected o) {
            return this.time - o.time;
        }
    }

    public int[] solution(int N, int M, int[] A, int[][] V, int[] B, int T, int[] E) {
        init(N, M);

        for(int[] info : V) {
            int member = info[0];
            int problem = info[1];
            int solvedTime = info[2];

            직접해결문제목록[member].add(problem);

            if(k문제가풀린시간[problem]>solvedTime){
                k문제가풀린시간[problem] = solvedTime;
                k문제빨리푼사람[problem] = member;
            }

            int score = calculateScore(solvedTime, A[problem-1]);
            누적점수[member] += score;
            if(solvedTime <= T) {
                스코어보드점수[member] += score;
            }

            updateEndTime(member, solvedTime);
        }

        // 가산점 적용, 단 프리즈 이전
        for (int problemNumber = 0; problemNumber < k문제가풀린시간.length; problemNumber++) {
            int solvedTime = k문제가풀린시간[problemNumber];

            if(solvedTime == 0 || solvedTime > T)continue; //푼사람이 없는 문제 혹은 프리즈 이후에 풀린 문제는 가산점 없음

            int member = k문제빨리푼사람[problemNumber];
            누적점수[member] += B[problemNumber-1];
            스코어보드점수[member] += B[problemNumber-1];
        }

        List<Expected> 예측시간 = new ArrayList<>();
        for (int i = 0; i < E.length; i++) {
            Expected expected = new Expected();
            expected.time = E[i];
            expected.number = i+1;
            예측시간.add(expected);
        }

        Collections.sort(예측시간);

        //예측 점수를 계산하기 위한 구현 시작
        for (int i = 1; i < M+1; i++) {
            추가시간보정점수계산(A, T, 예측시간, i);
        }

        // 해결못한 문제 목록 추리기 -> Big-O(M) 단, 문제 푸는데 걸리는 시간이 작은 순으로 정렬해야함. 이는 B를 활용해서 미리 만들어 둘 수 있음.
            // 60분짜리 배열 - k 남은 시간동안 얻을 수 있는 최대 점수
            // 예측 시간이 짧은 문제부터 하나씩 순회하면서
            // k 남은 시간동안 얻을 수 있는 최대 점수 배열을 갱신해간다.
            // 즉, 고려 대상이 한개, 두개, 세개로 늘어가며 배열이 갱신됨.
            // 4개째 예측 시간 정보를 고려할 때 앞에서 계산한 세개의 정보를 그대로 사용할 수 있다. 그렇기 때문에 이런 로직을 선택할 수 있는 것.

        List<Integer> collect = Arrays.stream(스코어보드점수).boxed().sorted(Comparator.reverseOrder()).collect(Collectors.toList());

        int[] answer = new int[M];
        for (int i = 1; i < 누적점수.length; i++) {
            int index = Collections.binarySearch(collect, 누적점수[i], Collections.reverseOrder());
            if( index < 0 ) {
                index *=-1;
            }
            answer[i-1] = index;
        }

        //스코어보드 기준으로 예상 등수 출력해야하는데.... 넘 귀찮다..
        return answer;
    }

    private int[] rating(List<Rating> memberScores) {
        int[] answer = new int[memberScores.size()];
        Collections.sort(memberScores);

        int rank = 0;
        int score = 0;
        for (int i = 0; i < memberScores.size(); i++) {
            Rating rating = memberScores.get(i);
            if(score == rating.score) {
                answer[rating.number-1] = rank;
            }else {
                rank = i+1;
                answer[rating.number -1] = rank;
                score = rating.score;
            }
        }

        return answer;
    }

    class Rating implements Comparable<Rating>{
        int score;
        int number;

        @Override
        public int compareTo(Rating o) {
            return o.score - score;
        }
    }

    private void 추가시간보정점수계산(int[] A, int T, List<Expected> 예측시간, int member) {
        // 각 참가자 남은 시간.
        int endTime = Math.max(T, 마지막문제푼시간[member]);
        int restTime = 60 - endTime;
        int[] 추가보정점수 = new int[restTime+1];

        for(Expected expected : 예측시간) {
            int time = expected.time;
            int problemNumber = expected.number;
            int score = A[problemNumber-1];
            if(직접해결문제목록[member].contains(problemNumber))continue; // 이미 해결한 문제일 경우는 넘어가기.
            if(restTime < time)continue;

            for (int i = 추가보정점수.length-1; i > 0; i--) {
                if(i < time)continue;
                // 새 문제(problemNumber)를 고려하므로써
                int expectedScore = score - (endTime + i);
                추가보정점수[i] = Math.max(추가보정점수[i], 추가보정점수[i - time] + expectedScore);
            }
        }

        int max = 0;
        for(int score : 추가보정점수) {
            max = Math.max(max, score);
        }

        누적점수[member] += max;
    }

    private void updateEndTime(int member, int solvedTime) {
        if(마지막문제푼시간[member] < solvedTime) {
            마지막문제푼시간[member] = solvedTime;
        }
    }

    private int calculateScore(int solvedTime, int A) {
        return A - solvedTime;
    }

    private void init(int N, int M) {
        // 마지막 문제 푼 시간 기록 per 참가자
       마지막문제푼시간 = new int[M +1];

        // 각 참가자가 직접 푼 점수 per 참가자
        누적점수 = new int[M +1];
        스코어보드점수 = new int[M +1];

        // 각 문제별 가장 빨리 푼 사람 번호 그리고 시간 저장 per 문제 수
        k문제빨리푼사람 = new int[N +1];
        k문제가풀린시간 = new int[N +1];
        Arrays.fill(k문제가풀린시간, 99999);

        // 직접 해결만 문제 목록 관리 per 참가자
        직접해결문제목록 = new HashSet[M +1];
        for (int i = 0; i < 직접해결문제목록.length; i++) {
            직접해결문제목록[i] = new HashSet<>();
        }
    }
}
