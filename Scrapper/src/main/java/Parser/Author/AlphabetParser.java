package Parser.Author;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;

public class AlphabetParser {

    private static final String COMMON_URL = "https://www.brainyquote.com/authors";
    private static final WebClient WEB_CLIENT = new WebClient(BrowserVersion.CHROME);
    private static HtmlPage htmlPage = null;

    // handle two different xpath
    // one is for the first page of the author for each alphabet letter
    // another one is for rest of the pages
    private static final String FIRST_PAGE_PATH = "/html/body/div[5]/div/div[1]/div[3]/table";
    private static final String PAGE_PATH = "/html/body/div[5]/div/div[1]/div[2]/table";

    public static ArrayList<Author> parseAlphabet(String alphabetToParse) {
        prePageFetch();
        ArrayList<Author> authors = new ArrayList<>();
        String siteURL = COMMON_URL + "/" + alphabetToParse;

        // get first page
        getPage(siteURL);
        if (!pageExists()) {
            System.out.println("Page does not exists: " + siteURL);
            return authors;
        }
        authors.addAll(AlphabetPageParser.parseAlphabetPage(htmlPage, FIRST_PAGE_PATH));

        Integer loopCount = 2;
        // loop through each number until urls don't match
        while (true) {
            String newURL = siteURL + loopCount;
            getPage(newURL);
            if (!pageExists()) {
                System.out.println("Page does not exists: " + newURL);
                return authors;
            }
            if (!isURLMatch(newURL, htmlPage.getBaseURL().toString())) {
                System.out.println("Done with Parsing :" + newURL + " (last page -1)");
                break;
            }
            authors.addAll(AlphabetPageParser.parseAlphabetPage(htmlPage, PAGE_PATH));
            loopCount++;
        }
        return authors;
    }

    private static void prePageFetch() {
        // some setup which needs to be done
        WEB_CLIENT.getOptions().setJavaScriptEnabled(false);
        WEB_CLIENT.getOptions().setThrowExceptionOnFailingStatusCode(false);

        // just turns off all the red stuff from the console
        java.util.logging.Logger.getLogger("com.gargoylesoftware.htmlunit").setLevel(Level.OFF);
    }

    private static Boolean isURLMatch(String myURL, String baseURL) {
        return myURL.equals(baseURL);
    }

    private static Boolean pageExists() {
        Integer code = htmlPage.getWebResponse().getStatusCode();
        if (code == 200) {
            return true;
        }
        return false;
    }

    private static void getPage(String siteURL) {
        try {
            htmlPage = WEB_CLIENT.getPage(siteURL);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
