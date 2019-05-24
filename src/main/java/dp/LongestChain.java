package dp;

import java.util.*;

public class LongestChain {

    /*
    abcd, abd, abc, bc, ac, ad, c
    abcde, abcd, bce, bc, ac, ad, c
     */
    public int findLongestChain(List<String> strs) {
        Set<String> strSet = new HashSet<>();
        Map<String, Integer> strLenMap = new HashMap<>();
        int maxLen = 0;

        for (String str: strs) {
            strSet.add(str);
        }

        Collections.sort(strs, (s1, s2) -> Integer.compare(s1.length(), s2.length()) );

        for (String str: strs) {
            int len = chainLen(str, strSet, strLenMap);
            maxLen = Math.max(maxLen, len);
        }

        return maxLen;
    }

    private int chainLen(String str, Set<String> strSet, Map<String, Integer> strLenMap) {
        if (str.length() == 0 || !strSet.contains(str)) return 0;
        if (strLenMap.containsKey(str)) {
            return strLenMap.get(str);
        }

        int maxNextLen = 0;
        for (int i = 0; i < str.length(); i++) {
            StringBuilder strBuf = new StringBuilder(str);
            maxNextLen = Math.max(maxNextLen, chainLen(strBuf.deleteCharAt(i).toString(), strSet, strLenMap));
        }
        strLenMap.put(str, maxNextLen+1);
        return maxNextLen+1;
    }
}
