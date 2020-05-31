package Parser.Author;

import java.util.ArrayList;

public class AlphabetsParser {
    public static ArrayList<Author> parseAlphabetsForAuthors() {
        ArrayList<Author> authors = new ArrayList<>();
        for(char alphabet = 'a'; alphabet <='z'; alphabet++ )
        {
            authors.addAll(AlphabetParser.parseAlphabet(alphabet));
        }
        return authors;
    }
}
