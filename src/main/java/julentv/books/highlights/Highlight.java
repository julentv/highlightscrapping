package julentv.books.highlights;

import java.util.List;

public class Highlight {
    private final List<String> lines;

    public Highlight(List<String> lines) {
        this.lines = lines;
    }

    public List<String> getLines() {
        return lines;
    }

    @Override
    public String toString() {
        return lines.stream().reduce((s1, s2) -> s1 + "\n" + s2).orElse("");
    }
}
