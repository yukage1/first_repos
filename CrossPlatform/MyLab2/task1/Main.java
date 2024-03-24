package task1;

public class Main {

    public static void main(String[] args) {
        System.out.println("Hladkoskok Maksym CS24");
        Library library = new Library("Main library");

        BookStore bookStore1 = new BookStore("Math");
        BookStore bookStore2 = new BookStore("History");
        BookStore bookStore3 = new BookStore("Chemistry");

        Book b1 = new Book("t1", new Author("name1", "surname1"), 1994, 11);
        Book b2 = new Book("t2", new Author("name2", "surname2"), 2010, 1);
        Book b3 = new Book("t3", new Author("name3", "surname3"), 2014, 2);
        Book b4 = new Book("t4", new Author("name4", "surname4"), 1996, 3);
        Book b5 = new Book("t5", new Author("name5", "surname5"), 1966, 4);
        Book b6 = new Book("t6", new Author("name6", "surname6"), 2007, 5);
        Book b7 = new Book("t7", new Author("name7", "surname7"), 1961, 15);
        Book b8 = new Book("t8", new Author("name8", "surname8"), 2017, 3);
        Book b9 = new Book("t9", new Author("name9", "surname9"), 1999, 10);

        bookStore1.addBook(b1);
        bookStore1.addBook(b2);

        bookStore2.addBook(b3);
        bookStore2.addBook(b4);
        bookStore2.addBook(b5);

        bookStore3.addBook(b6);

        bookStore1.addBook(b7);
        bookStore2.addBook(b8);
        bookStore3.addBook(b9);


        library.addBookStore(bookStore1);
        library.addBookStore(bookStore2);
        library.addBookStore(bookStore3);

        BookReader bookReader1 = new BookReader("nameBR1", "surnameBR1", 33);
        BookReader bookReader2 = new BookReader("nameBR2", "surnameBR2", 99);


        library.registerReader(bookReader1);
        library.registerReader(bookReader2);


        bookReader2.takeBook(library.giveBook(b1));
        bookReader2.takeBook(library.giveBook(b2));
        bookReader2.takeBook(library.giveBook(b3));
        bookReader2.takeBook(library.giveBook(b4));

        bookReader1.takeBook(library.giveBook(b5));
        bookReader1.takeBook(library.giveBook(b6));

        System.out.println("Before serialization");
        System.out.println(library);

        System.out.println("After deserialization");
        LibraryDriver.serializeObject(library);
        System.out.println(LibraryDriver.deserializeObject());

    }
}
