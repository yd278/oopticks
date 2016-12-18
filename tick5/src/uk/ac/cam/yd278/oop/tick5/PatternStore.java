package uk.ac.cam.yd278.oop.tick5;

/**
 * Created by Anchor on 2016/11/22.
 */

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;

public class PatternStore {
    private List<Pattern> mPatterns = new LinkedList<>();
    private Map<String, List<Pattern>> mMapAuths = new HashMap<>();
    private Map<String, Pattern> mMapName = new HashMap<>();

    PatternStore(String source) throws IOException {
        if (source.startsWith("http://")) {
            loadFromURL(source);
        } else {
            loadFromDisk(source);
        }
    }

    public PatternStore(Reader source) throws IOException {
        load(source);
    }


    private void load(Reader r) throws IOException {
        BufferedReader b = new BufferedReader(r);
        String line = b.readLine();
        while (line != null) {
            try {
                Pattern thisPattern = new Pattern(line);
                mPatterns.add(thisPattern);
                mMapName.put(thisPattern.getName(), thisPattern);
                List<Pattern> patternsFromThisAuthor = mMapAuths.get(thisPattern.getAuthor());
                if (patternsFromThisAuthor != null) {
                    patternsFromThisAuthor.add(thisPattern);
                } else {
                    patternsFromThisAuthor = new LinkedList<>();
                    patternsFromThisAuthor.add(thisPattern);
                    mMapAuths.put(thisPattern.getAuthor(), patternsFromThisAuthor);
                }
            } catch (PatternFormatException e) {
                System.out.println(e.getMessage());
            } finally {
                line = b.readLine();
            }

        }
    }

    private void loadFromURL(String url) throws IOException {
        URL destination = new URL(url);
        URLConnection conn = destination.openConnection();
        Reader r = new java.io.InputStreamReader(conn.getInputStream());
        load(r);
    }

    private void loadFromDisk(String filename) throws IOException {
        Reader r = new FileReader(filename);
        load(r);
    }

    List<Pattern> getPatternsNameSorted() {
        Collections.sort(mPatterns);
        List<Pattern> copy = new LinkedList<>(mPatterns);
        return copy;
    }

    public List<Pattern> getPatternsAuthorSorted() {
        Collections.sort(mPatterns, new Comparator<Pattern>() {
            @Override
            public int compare(Pattern o1, Pattern o2) {
                int tmp =  o1.getAuthor().compareTo(o2.getAuthor());
                if(tmp == 0) return o1.getName().compareTo(o2.getName());
                else return tmp;
            }
        });
        List<Pattern> copy = new LinkedList<>(mPatterns);
        return copy;

    }

    public List<Pattern> getPatternsByAuthor(String author) throws PatternNotFound {
        List<Pattern> patternsFromThisAuther = mMapAuths.get(author);
        if(patternsFromThisAuther == null) throw new PatternNotFound("this author has no pattern");
        else{
            Collections.sort(patternsFromThisAuther);
            List<Pattern> copy = new LinkedList<>(patternsFromThisAuther);
            return copy;
        }
    }

    public Pattern getPatternByName(String name) throws PatternNotFound {
        Pattern result = mMapName.get(name);
        if(result == null) throw new PatternNotFound("No pattern with this name");
        return result;
    }

    public List<String> getPatternAuthors() {
        List<String> res = new LinkedList<>();
        for (Object o : mMapAuths.keySet()) {
            res.add((String)o);
        }
        Collections.sort(res);
        return res;
    }

    public List<String> getPatternNames() {
        List<String> res = new LinkedList<>();
        for(Pattern i : mPatterns){
            res.add(i.getName());
        }
        Collections.sort(res);
        return res;
    }
}