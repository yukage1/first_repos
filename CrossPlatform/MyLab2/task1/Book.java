package task1;

import java.io.Serializable;
import java.util.ArrayList;

public class Book implements Serializable{
    private static final long serialVersionUID = 1L;

    private ArrayList <Author> authors;
    private String title;
    private int yearOfWriting, series;

    public Book(String title, Author author, int yearOfWriting, int series){
        ArrayList <Author> authors = new ArrayList();
        authors.add(author);

        this.title = title;
        this.authors = authors;
        this.yearOfWriting = yearOfWriting;
        this.series = series;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public String toString(){
        StringBuilder bookString = new StringBuilder("\033[36m" + "Book name: " + title + "\n");
        bookString.append("Authors:");
        for (Author author : authors){
            bookString.append(" ").append(author.toString()).append("\n");
        }
        bookString.append("Year of writing: ").append(yearOfWriting).append("\n");
        bookString.append("Series: ").append(series).append("\033[35m").append("\n");

        return bookString.toString();
    }
}
