package Database.AuthorDB;

import Database.DatabaseUtils;
import Parser.Author.AlphabetsParser;
import Parser.Author.Author;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

public class AuthorDBUtils {
    private static final String AUTHOR_INSERT = "INSERT INTO authors (name, profession, link) values(?, ?, ?)";
    private static final int BATCH_SIZE = 1000;
    private  AuthorDBUtils() { }

    // this method will fetch authors from the website and insert
    // into database and return number of authors inserted
    public static Integer fetchAndInsert() {
        ArrayList<Author> authors = AlphabetsParser.parse();
        System.out.println("Total Authors: " + authors.size());
        System.out.println("Initiating insert into the database:");
        try {
            AuthorDBUtils.insertAuthorsInDB(authors);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return authors.size();
    }

    private static void insertAuthorsInDB(ArrayList<Author> authors) throws SQLException {
        DatabaseUtils dbUtil = new DatabaseUtils();
        Connection connection = dbUtil.connect();
        PreparedStatement ps = connection.prepareStatement(AUTHOR_INSERT);

        int count = 0;
        for (Author author: authors) {
            ps.setString(1, author.getName());
            ps.setString(2, author.getProfession());
            ps.setString(3, author.getLink());
            ps.addBatch();
            if (++count % BATCH_SIZE == 0) {
                ps.executeBatch();
                System.out.println("Inserted Records by far: " + count);
            }
        }
        ps.executeBatch();
        ps.close();
        dbUtil.disconnect();
    }
}
