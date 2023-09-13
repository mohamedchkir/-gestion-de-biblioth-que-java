package models;
import java.util.List;
import models.Author;

public class Book {

    private int id;
    private String title;
    private String category;
    private String edition;
    private String isbn;
    private Author author;
    private int quantity;
    private int available;
    private int borrow;
    int lost;


    public Book(int id, String title, String category, String edition, String isbn, Author author, int quantity, int available, int borrow, int lost) {
        this.id = id;
        this.title = title;
        this.category = category;
        this.edition = edition;
        this.isbn = isbn;
        this.author = author;
        this.quantity = quantity;
        this.available = available;
        this.borrow = borrow;
        this.lost = lost;
    }

    public Book(String title, String category, String edition, int author, String isbn, int quantity) {
        this.title = title;
        this.category = category;
        this.edition = edition;
        this.isbn = isbn;
        this.author = new Author(author);
        this.quantity = quantity;
    }

    public Book(int id, String title, String category, String edition, int author, String isbn, int quantity, int available, int borrow, int lost) {
        this.id = id;
        this.title = title;
        this.category = category;
        this.edition = edition;
        this.isbn = isbn;
        this.author = new Author(author);
        this.quantity = quantity;
        this.available = available;
        this.borrow = borrow;
        this.lost = lost;
    }
    public Book(int id){
        this.id = id;
    }

    public Book(int id, String title, String category, String edition, String isbn, int authorId, String authorName, int quantity, int available, int borrow, int lost) {
        this.id = id;
        this.title = title;
        this.category = category;
        this.edition = edition;
        this.isbn = isbn;
        this.author = new Author(authorId,authorName);
        this.quantity = quantity;
        this.available = available;
        this.borrow = borrow;
        this.lost = lost;
    }

    public Book() {

    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getEdition() {
        return edition;
    }

    public void setEdition(String edition) {
        this.edition = edition;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getAvailable() {
        return available;
    }

    public void setAvailable(int available) {
        this.available = available;
    }

    public int getBorrow() {
        return borrow;
    }

    public void setBorrow(int borrow) {
        this.borrow = borrow;
    }

    public int getLost() {
        return lost;
    }

    public void setLost(int lost) {
        this.lost = lost;
    }

    public Book searchBook(){
        return null;
    }


}
