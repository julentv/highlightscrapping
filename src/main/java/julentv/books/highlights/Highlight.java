package julentv.books.highlights;

import java.util.List;

public class Highlight {
    private final List<String> lines;
    private final String chapter;

    public Highlight(List<String> lines, String chapter) {
        this.lines = lines;
        this.chapter = chapter;
    }

    public List<String> getLines() {
        return lines;
    }

    public String joinedLines() {
        return lines.stream().reduce((s1, s2) -> s1 + " " + s2).orElse("");
    }

    public String getChapter() {
        return chapter;
    }

    @Override
    public String toString() {
        return lines.stream().reduce((s1, s2) -> s1 + "\n" + s2).orElse("");
    }
}
