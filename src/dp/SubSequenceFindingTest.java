package dp;

import org.junit.Assert;
import org.junit.Test;

public class SubSequenceFindingTest {

    @Test
    public void findSubSequence() {
        SubSequenceFinding finder = new SubSequenceFinding();
        Assert.assertEquals(3, finder.findSubSequence("123123", "13"));
    }

    @Test
    public void emptyTarget() {
        SubSequenceFinding finder = new SubSequenceFinding();
        Assert.assertEquals(1, finder.findSubSequence("123123", ""));

    }
}