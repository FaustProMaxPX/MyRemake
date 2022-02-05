/* Copyright (c) 2007-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package twitter;

import java.time.Instant;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

/**
 * Extract consists of methods that extract information from a list of tweets.
 * 
 * DO NOT change the method signatures and specifications of these methods, but
 * you should implement their method bodies, and you may add new public or
 * private methods or classes if you like.
 */
public class Extract {

    /**
     * Get the time period spanned by tweets.
     * 
     * @param tweets
     *            list of tweets with distinct ids, not modified by this method.
     * @return a minimum-length time interval that contains the timestamp of
     *         every tweet in the list.
     */
    public static Timespan getTimespan(List<Tweet> tweets) {
        Instant start = Instant.MAX;
        Instant end = Instant.MIN;
        for (Tweet tweet : tweets) {
            Instant timestamp = tweet.getTimestamp();
            if (timestamp.isBefore(start)) {
                start = timestamp;
            }
            if (timestamp.isAfter(end)) {
                end = timestamp;
            }
        }
        return new Timespan(start, end);
    }


    /**
     * Get usernames mentioned in a list of tweets.
     * 
     * @param tweets
     *            list of tweets with distinct ids, not modified by this method.
     * @return the set of usernames who are mentioned in the text of the tweets.
     *         A username-mention is "@" followed by a Twitter username (as
     *         defined by Tweet.getAuthor()'s spec).
     *         The username-mention cannot be immediately preceded or followed by any
     *         character valid in a Twitter username.
     *         For this reason, an email address like bitdiddle@mit.edu does NOT 
     *         contain a mention of the username mit.
     *         Twitter usernames are case-insensitive, and the returned set may
     *         include a username at most once.
     */
    private static final String validUserNameChar =
            "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789_-";
    public static Set<String> getMentionedUsers(List<Tweet> tweets) {
        if (tweets == null) {
            throw new RuntimeException("list of tweets can not be null");
        }
        Set<String> mentionUser = new HashSet<>();
        for (Tweet tweet : tweets) {
            String text = tweet.getText().toLowerCase(Locale.ROOT);
            int startIndex = 0;
            while (startIndex < text.length() &&
                    (startIndex = text.indexOf('@', startIndex)) != -1) {
                // if precede by a valid character, continue
                if (startIndex != 0 && validUserNameChar.indexOf(text.charAt(startIndex - 1)) != -1) {
                    startIndex++;
                    continue;
                }
                int nextIndex = startIndex + 1;
                while (nextIndex < text.length() && (validUserNameChar.indexOf(text.charAt(nextIndex))) != -1) {
                    nextIndex++;

                }
                String username = text.substring(startIndex + 1, nextIndex);
                if (username != null && username.length() > 0) {
                    mentionUser.add(username);
                }
                startIndex = nextIndex;
            }
        }
        return mentionUser;
    }
}
