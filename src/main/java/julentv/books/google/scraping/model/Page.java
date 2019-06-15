package julentv.books.google.scraping.model;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class Page {
    private final List<Word> words;
    private final String pageText;

    public Page(List<Word> allWords) {
        this.words = new ArrayList<>();
        StringBuilder pageTextBuilder = new StringBuilder();
        for (Word word : allWords) {
            String wordText = word.getText();
            if (StringUtils.isNotEmpty(wordText)) {
                this.words.add(word);
                pageTextBuilder.append(wordText).append(" ");
            }
        }
        if (pageTextBuilder.length() > 0) {
            pageTextBuilder.deleteCharAt(pageTextBuilder.length() - 1);
        }
        pageText = pageTextBuilder.toString();
    }

    public List<Word> getWords() {
        return words;
    }

    public String getPageText() {
        return pageText;
    }

    public boolean contains(String text) {
        return this.pageText.contains(text);
    }

    public int getBeginningMatchingWords(String[] words) {
        int numberOfWords = 0;

        for (int i = 0; i < words.length && i < this.words.size(); i++) {
            boolean matches = true;
            for (int j = i; j >= 0; j--) {
                if (!words[words.length - 1 - (i - j)].equals(this.words.get(j).getText())) {
                    matches = false;
                    break;
                }
            }
            if (matches) {
                numberOfWords = i + 1;
            }
        }
        return numberOfWords;
    }

    public HighlightElements getHighlightElements(String textToHighlight) {
        String[] wordsToHighlight = textToHighlight.split(" ");
        int firstElement = 0;
        for (int i = 0; i < this.getWords().size(); i++) {
            if (wordsToHighlight[0].equals(this.getWords().get(i).getText())) {
                firstElement = i;
                if (matchesFrom(wordsToHighlight, i)) {
                    break;
                }
            }
        }
        return new HighlightElements(this.getWords().get(firstElement), this.getWords().get(firstElement + wordsToHighlight.length - 1));
    }

    private boolean matchesFrom(String[] wordsToHighlight, int startPossition) {
        for (int j = 1; j < wordsToHighlight.length; j++) {
            if (!wordsToHighlight[j].contains(this.getWords().get(startPossition + j).getText())) {
                return false;
            }
        }
        return true;
    }
}
