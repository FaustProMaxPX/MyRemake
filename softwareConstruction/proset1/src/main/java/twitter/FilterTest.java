package twitter;

import org.junit.Assert;
import org.junit.Test;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class FilterTest {

    /**
     * partition:
     * username not valid:
     * - empty
     * - invalid character
     * author in uppercase
     * */
    @Test
    public void filterAuthorTest()
    {
        final String name1 = "";
        final String name2 = "a!b";
        final String name3 = "ab";
        final List<Tweet> tweets = new ArrayList<>();
        final List<Tweet> expected = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            if (i % 3 == 0) {
                Tweet tweet = new Tweet(i, "ab", "", Instant.now());
                tweets.add(tweet);
                expected.add(tweet);
            }
            else if (i % 4 == 0) {
                Tweet tweet = new Tweet(i, "Ab", "", Instant.now());
                tweets.add(tweet);
                expected.add(tweet);
            }
            else {
                tweets.add(new Tweet(i, "s", "", Instant.now()));
            }
        }
        List<Tweet> tweets1 = Filter.writtenBy(tweets, name1);
        Assert.assertTrue("The program didn't find the username is invalid", tweets1 == null);
        List<Tweet> tweets2 = Filter.writtenBy(tweets, name2);
        Assert.assertTrue("The program didn't find the username is invalid", tweets2 == null);
        List<Tweet> tweets3 = Filter.writtenBy(tweets, name3);
        Assert.assertArrayEquals("The array is not right", expected.toArray(), tweets3.toArray());
    }

    /**
     * find tweet publish in given timespan
     * partition:
     * < start
     * > end
     * = start
     * = end
     * > start and < end
     * */
    @Test
    public void timespanTest()
    {
    }

    /**
     * find word
     * partition:
     * no particular word
     * ? have word containing word in the words eg:adjusting and adjust (is needed?)
     * uppercase and lowercase
     * */
    @Test
    public void wordTest()
    {}
}
