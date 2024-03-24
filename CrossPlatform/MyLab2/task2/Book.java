package task2;


import java.util.ArrayList;

public class Book {

    private ArrayList <Author> authors;
    private String title;
    private int yearOfWriting, series;

    public Book(String title, ArrayList<Author> authors, int yearOfWriting, int series){
        this.title = title;
        this.authors = authors;
        this.yearOfWriting = yearOfWriting;
        this.series = series;
    }

    public Book(String title, Author author, int yearOfWriting, int series){
        ArrayList <Author> authors = new ArrayList<>();
        authors.add(author);

        this.title = title;
        this.authors = authors;
        this.yearOfWriting = yearOfWriting;
        this.series = series;
    }

    public ArrayList<Author> getAuthors() {
        return authors;
    }


    public String getTitle() {
        return title;
    }


    public int getYearOfWriting() {
        return yearOfWriting;
    }


    public int getSeries() {
        return series;
    }

    public String toString(){
        StringBuilder bookString = new StringBuilder("\nBook name: " + title + "\n");
        bookString.append("Authors:");
        for (Author author : authors){
            bookString.append(" ").append(author.toString()).append("\n");
        }
        bookString.append("Year of writing: ").append(yearOfWriting).append("\n");
        bookString.append("Series: ").append(series).append("\n");

        return bookString.toString();
    }
}
