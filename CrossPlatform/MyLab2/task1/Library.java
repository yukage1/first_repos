package task1;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

public class Library implements Serializable{
    private static final long serialVersionUID = 1L;

    private final ArrayList <BookStore> bookStores;
    private final ArrayList <BookReader> registeredReaders;
    private final String name;

    public Library(String name){
        this.name = name;
        this.bookStores = new ArrayList<>();
        this.registeredReaders = new ArrayList<>();
    }

    public void addBookStore(BookStore bookStore){
        bookStores.add(bookStore);
    }



    public Book giveBook(Book book){
        Book neededBook;


        for (int i = 0; i < bookStores.size(); i++) {
            if((neededBook = bookStores.get(i).getBook(book)) != null){
                return neededBook;
            }
        }

        return null;

    }


    public void registerReader(BookReader bookReader){
        registeredReaders.add(bookReader);
    }

    public String getName(){
        return name;
    }

    @Override
    public String toString(){
        StringBuilder libraryString = new StringBuilder();
        libraryString.append("Library name: ").append("\033[36m").append(name).append("\n\n" + "\033[35m");

        libraryString.append("Book Stores: ");
        for (BookStore bookStore: bookStores){
            libraryString.append(bookStore.toString()).append("\033[35m");
        }

        libraryString.append("Readers:");
        for (BookReader bookReader : registeredReaders){
            libraryString.append(bookReader.toString()).append("\n\n");
        }

        return libraryString.toString();
    }
}
