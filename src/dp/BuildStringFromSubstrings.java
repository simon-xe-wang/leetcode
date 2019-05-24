package dp;

import java.util.HashSet;
import java.util.Set;

public class BuildStringFromSubstrings {
    int min = Integer.MAX_VALUE;

    /*
        s1 = ABABZ, s2 = ABZ
        neg: s1 = ABDABZ, s2 = ABZ
     */
    public int minSteps(String s1, String s2) {
        Set<String> subStrs = new HashSet<>();
        // put all substrings of s2, into a set. n^2,  A, AB, ABZ, B, BZ, Z
        for (int i = 0; i < s2.length(); i++) {
            for (int j = i+1; j <= s2.length(); j++) {
                //System.out.println(i + ", " + j);
                subStrs.add(s2.substring(i, j));
            }
        }

        // Recursively count steps for remaining string. start from whole one.
        // till str is empty,
        // void countSteps(str, steps, set)
        countSteps(s1, 0, subStrs);

        return min == Integer.MAX_VALUE ? -1 : min;
    }

    private void countSteps(String str, int steps, Set<String> subStrs) {
        if (str.isEmpty()) {
            min = Math.min(min, steps);
            return;
        }

        for (int i = 1; i <= str.length(); i++) {
            String prefix = str.substring(0, i);
            if (subStrs.contains(prefix)) {
                countSteps(str.substring(i), steps+1, subStrs);
            }
        }
    }
}
