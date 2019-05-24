package dp;

public class SubSequenceFinding {
    public int findSubSequence(String s, String t) {
        // cnt[i][j]
        // if s[i-1] == t[j-1], cnt[i][j] = cnt[i-1][j-1] + cnt[i-1][j]
        // if not, cnt[i][j] = cnt[i-1][j]

        if (s.length() < t.length()) return 0;

        int cnt[][] = new int[s.length()+1][t.length()+1];
        for (int i = 0; i <= s.length(); i++) {
            cnt[i][0] = 1;
        }

        for (int i = 1; i <= s.length(); i++) {
            for (int j = 1; j <= t.length(); j++) {
                if (s.charAt(i-1) == t.charAt(j-1)) {
                    cnt[i][j] = cnt[i-1][j-1] + cnt[i-1][j];
                } else {
                    cnt[i][j] = cnt[i-1][j];
                }
            }
        }
        return cnt[s.length()][t.length()];
    }
}
