package dp;

import org.junit.Assert;
import org.junit.Test;

public class BuildStringFromSubstringsTest {

    @Test
    public void normal() {
        Assert.assertEquals(2, new BuildStringFromSubstrings().minSteps("ABABZ", "ABZ"));
    }

    @Test
    public void notFound() {
        Assert.assertEquals(-1, new BuildStringFromSubstrings().minSteps("ABDABZ", "ABZ"));
    }
}