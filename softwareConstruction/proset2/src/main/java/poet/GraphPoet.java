/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package poet;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

import graph.Graph;

/**
 * A graph-based poetry generator.
 * 
 * <p>GraphPoet is initialized with a corpus of text, which it uses to derive a
 * word affinity graph.
 * Vertices in the graph are words. Words are defined as non-empty
 * case-insensitive strings of non-space non-newline characters. They are
 * delimited in the corpus by spaces, newlines, or the ends of the file.
 * Edges in the graph count adjacencies: the number of times "w1" is followed by
 * "w2" in the corpus is the weight of the edge from w1 to w2.
 * 
 * <p>For example, given this corpus:
 * <pre>    Hello, HELLO, hello, goodbye!    </pre>
 * <p>the graph would contain two edges:
 * <ul><li> ("hello,") -> ("hello,")   with weight 2
 *     <li> ("hello,") -> ("goodbye!") with weight 1 </ul>
 * <p>where the vertices represent case-insensitive {@code "hello,"} and
 * {@code "goodbye!"}.
 * 
 * <p>Given an input string, GraphPoet generates a poem by attempting to
 * insert a bridge word between every adjacent pair of words in the input.
 * The bridge word between input words "w1" and "w2" will be some "b" such that
 * w1 -> b -> w2 is a two-edge-long path with maximum-weight weight among all
 * the two-edge-long paths from w1 to w2 in the affinity graph.
 * If there are no such paths, no bridge word is inserted.
 * In the output poem, input words retain their original case, while bridge
 * words are lower case. The whitespace between every word in the poem is a
 * single space.
 * 
 * <p>For example, given this corpus:
 * <pre>    This is a test of the Mugar Omni Theater sound system.    </pre>
 * <p>on this input:
 * <pre>    Test the system.    </pre>
 * <p>the output poem would be:
 * <pre>    Test of the system.    </pre>
 * 
 * <p>PS2 instructions: this is a required ADT class, and you MUST NOT weaken
 * the required specifications. However, you MAY strengthen the specifications
 * and you MAY add additional methods.
 * You MUST use Graph in your rep, but otherwise the implementation of this
 * class is up to you.
 */
public class GraphPoet {
    
    private final Graph<String> graph = Graph.empty();
    
    // Abstraction function:
    //   vertices ,edges -> graph
    // Representation invariant:
    //   vertices are not null, weights in edges are non-negative
    // Safety from rep exposure:
    //   field is not exposed
    
    /**
     * Create a new poet with the graph from corpus (as described above).
     * 
     * @param corpus text file from which to derive the poet's affinity graph
     * @throws IOException if the corpus file cannot be found or read
     */
    public GraphPoet(File corpus) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(corpus));
        String line = "";
        while ((line = reader.readLine()) != null) {
            String[] words = line.toLowerCase().split(" ");
            for (int i = 0; i < words.length - 1; i++) {
                if (words[i].equals("-")) continue;
                int w = 1;
                for (int j = i + 1; j < words.length; j++) {
                    if (words[i].equals(words[j])) {
                        w++;
                        words[j] = "-";
                    }
                    else {
                        graph.add(words[i]);
                        graph.add(words[j]);
                        graph.set(words[i], words[j], w);
                        break;
                    }
                }
            }
        }
    }
    
    // checkRep
    private void checkRep() {

    }
    
    /**
     * Generate a poem.
     * 
     * @param input string from which to create the poem
     * @return poem (as described above)
     */
    public String poem(String input) {
        String[] words = input.toLowerCase().split(" ",0);
        Set<String> rep = graph.vertices();
        StringBuffer buf = new StringBuffer();
        for (int i = 0; i < words.length - 1; i++) {
            Map<String, Integer> targets = graph.targets(words[i]);
            Map<String, Integer> sources = graph.sources(words[i + 1]);
            buf.append(" ").append(words[i]);
            for (String key : targets.keySet())
            {
                if (sources.containsKey(key))
                {
                    int w1 = sources.get(key);
                    int w2 = targets.get(key);
                    for (int j = 1; j < w2; j++)
                        buf.append(" ").append(words[i]);
                    for (int j = 0; j < w1; j++)
                        buf.append(" ").append(key);
                }            
            }
        }
        buf.append(" ").append(words[words.length - 1]);
        return buf.toString();
    }

    
    
    // TODO toString()
    
}
