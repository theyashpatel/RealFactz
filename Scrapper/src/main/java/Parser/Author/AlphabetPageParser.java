package Parser.Author;

import com.gargoylesoftware.htmlunit.html.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AlphabetPageParser {

    public static ArrayList<Author> parseAlphabetPage(HtmlPage htmlPage, String xPath) {
        ArrayList<Author> authors = new ArrayList<>();
        try {
            List<Object> nodes = htmlPage.getByXPath(xPath);
            HtmlTable table = (HtmlTable) nodes.get(0);
            int tableRowCount = table.getRowCount();
            for (int row = 1; row < tableRowCount; row++ ) {
                // new author
                Author author = new Author();
                for (int col = 0; col < 2; col++) {
                    HtmlTableCell cell = table.getCellAt(row,col);
                    String cellText = cell.getTextContent().strip();
                    if (col == 0) {
                        HtmlAnchor anchor = (HtmlAnchor) cell.getFirstElementChild();
                        String link = anchor.getHrefAttribute();
                        author.setAuthor(cellText);
                        author.setLink(link);
                    } else {
                        author.setProfession(cellText);
                    }
                }
                authors.add(author);
            }
            return authors;
        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
            return authors;
        }
    }
}
