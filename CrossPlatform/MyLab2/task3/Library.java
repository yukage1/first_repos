package task3;

import java.io.*;
import java.util.ArrayList;

public class Library implements Externalizable {


    private  ArrayList <BookStore> bookStores;
    private  ArrayList <BookReader> registeredReaders;
    private  String name;

    public Library(String name){
        this.name = name;
        this.bookStores = new ArrayList<>();
        this.registeredReaders = new ArrayList<>();
    }

    public Library(){

    }

    public void addBookStore(BookStore bookStore){
        bookStores.add(bookStore);
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeObject(name);

        out.writeInt(bookStores.size());
        for(BookStore bookStore : bookStores)
            bookStore.writeExternal(out);

        out.writeInt(registeredReaders.size());
        for (BookReader bookReader : registeredReaders)
            bookReader.writeExternal(out);
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        name = (String) in.readObject();

        int bookStoreCount = in.readInt();
        bookStores = new ArrayList<>();
        for (int i = 0; i < bookStoreCount; i++) {
            BookStore bookStore = new BookStore();
            bookStore.readExternal(in);

            bookStores.add(bookStore);
        }

        int registeredReadersCount = in.readInt();
        registeredReaders = new ArrayList<>();
        for (int i = 0; i < registeredReadersCount; i++) {
            BookReader bookReader = new BookReader();
            bookReader.readExternal(in);

            registeredReaders.add(bookReader);
        }
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

        libraryString.append("Book Stores: \n");

        for (BookStore bookStore: bookStores){
            libraryString.append(bookStore.toString()).append("\n" + "\033[35m");
        }

        libraryString.append("\nReaders:\n");

        for (BookReader bookReader : registeredReaders){
            libraryString.append(bookReader.toString());
        }


        return libraryString.toString();
    }
}
