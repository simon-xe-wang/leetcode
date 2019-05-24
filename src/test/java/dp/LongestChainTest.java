package dp;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

public class LongestChainTest {
    LongestChain solution;

    @Before
    public void setup() {
        solution = new LongestChain();
    }

    @Test
    public void normal1() {
        String[] strs = new String[] {"ab", "a", "abc", "b", "bd", "abd", "abcd", "abef"};
        assertEquals(4, solution.findLongestChain(Arrays.asList(strs)) );
    }

    @Test
    public void normal2() {
        String[] strs = new String[] {"ab", "cde", "cdae", "de"};
        assertEquals(3, solution.findLongestChain(Arrays.asList(strs)) );
    }
}