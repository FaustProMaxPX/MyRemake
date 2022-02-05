package twitter;

import org.junit.Assert;
import org.junit.Test;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ExtractTest {

   /**
    * Testing strategy
    *
    * Timespan partition:
    * array size == 1
    * start == end
    * start < end
    * */
   @Test
   public void timespanTest() throws InterruptedException {
      List<Tweet> tweets = new ArrayList<>();
      for(int i = 0; i < 10; i++) {
         tweets.add(new Tweet(i, "author"+i, "text"+i, Instant.now()));
         Thread.sleep(1000);
      }
      Timespan timespan = Extract.getTimespan(tweets);
      System.out.println(timespan);
   }

   /**
    * Testing strategy:
    * partition:
    * no @
    * repeat @ but uppercase
    * more than one @
    * @ empty
    * @ at first
    * preceded with a valid character
    * followed by a valid character
    * */
   @Test
   public void mentionUserTest()
   {
      Tweet t1 = new Tweet(1, "a", "@", Instant.now());
      Tweet t2 = new Tweet(2, "b", "@f @n g@v, @faust, @FAust", Instant.now());
      List<Tweet> t11 = List.of(t1, t2);
      Set<String> mentionedUsers = Extract.getMentionedUsers(t11);


   }
}
