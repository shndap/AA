package controller;

import javafx.scene.media.Media;
import view.GameMenu.GameMenu;

import java.io.File;
import java.net.URL;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MusicMenuController {
    private static final HashMap<String, Media> songs;
    private static final HashMap<String, String> next;
    private static final HashMap<String, String> prev;

    static {
        songs = new HashMap<>();
        next = new HashMap<>();
        prev = new HashMap<>();

        String base = "src/main/resources/Music";
        File file = new File(base);

        String first = null;
        String last = null;
        for (File song : file.listFiles()) {
            if (song.isFile()) {
                URL url = GameMenu.class.getResource("/Music/" + song.getName());
                Media media = new Media(url.toExternalForm());
                songs.put(song.getName(), media);
                if (first == null) first = song.getName();
                last = song.getName();
            }
        }

        fetchPrevNext(first, last);
    }

    private static void fetchPrevNext(String first, String last) {
        String prevSong = last;
        for (String song : songs.keySet()) {
            prev.put(song, prevSong);
            next.put(prevSong, song);
            prevSong = song;
        }
        next.put(prevSong, first);
    }

    public static HashMap<String, Media> getSongs() {
        return songs;
    }

    public static String getCurrentSongName() {
        String source = GameMenuController.getMediaPlayer().getMedia().getSource();
        Pattern pattern = Pattern.compile(".*\\/(?<name>[^\\/]+)\\.mp3");
        Matcher matcher = pattern.matcher(source);
        matcher.find();
        return matcher.group("name");
    }

    public static Media getPreviousSong() {
        String previous = prev.get(getCurrentSongName() + ".mp3");
        return songs.get(previous);
    }

    public static Media getNextSong() {
        String nextSong = MusicMenuController.next.get(getCurrentSongName() + ".mp3");
        return songs.get(nextSong);
    }
}
