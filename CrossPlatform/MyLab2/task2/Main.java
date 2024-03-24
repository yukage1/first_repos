package task2;


public class Main {

    public static void main(String[] args) {
        Library library = new Library("Central  library of KhNU");

        BookStore bookStore1 = new BookStore("Science");
        BookStore bookStore2 = new BookStore("History");
        BookStore bookStore3 = new BookStore("Chemistry");

        Book b1 = new Book("T1", new Author("Name1", "Surname1"), 1996, 11);
        Book b2 = new Book("T2", new Author("Name2", "Surname2"), 2011, 1);
        Book b3 = new Book("T3", new Author("Name3", "Surname3"), 2014, 2);
        Book b4 = new Book("T4", new Author("Name4", "Surname4"), 1995, 3);
        Book b5 = new Book("T5", new Author("Name5", "Surname5"), 1965, 4);
        Book b6 = new Book("T6", new Author("Name6", "Surname6"), 2007, 5);
        Book b7 = new Book("T7", new Author("Name7", "Surname7"), 1969, 15);
        Book b8 = new Book("T8", new Author("Name8", "Surname8"), 2018, 3);
        Book b9 = new Book("T10", new Author("Name9", "Surname9"), 1999, 10);

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

        BookReader bookReader1 = new BookReader("NameBR1", "SurnameBR1", 33);
        BookReader bookReader2 = new BookReader("NameBR2", "SurnameBR2", 99);


        library.registerReader(bookReader1);
        library.registerReader(bookReader2);


        bookReader2.takeBook(library.giveBook(b1));
        bookReader2.takeBook(library.giveBook(b2));
        bookReader2.takeBook(library.giveBook(b3));
        bookReader2.takeBook(library.giveBook(b4));

        bookReader1.takeBook(library.giveBook(b5));
        bookReader1.takeBook(library.giveBook(b6));

        System.out.println((char)27 + "[35m" + "Before serialization");
        System.out.println(library);

        System.out.println("After deserialization");
        LibraryDriver.serializeObject(library);
        System.out.println(LibraryDriver.deserializeObject());


    }
}
