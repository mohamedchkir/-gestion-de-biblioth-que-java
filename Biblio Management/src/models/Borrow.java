package models;

import java.util.Date;

public class Borrow {
    private int id;
    private Client client_id;
    private Book isbn;
    private Date start_date;
    private Date end_date;

    public Borrow(int id, Client client_id, Book isbn, Date start_date, Date end_date) {
        this.id = id;
        this.client_id = client_id;
        this.isbn = isbn;
        this.start_date = start_date;
        this.end_date = end_date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Client getClient_id() {
        return client_id;
    }

    public void setClient_id(Client client_id) {
        this.client_id = client_id;
    }

    public Book getIsbn() {
        return isbn;
    }

    public void setIsbn(Book isbn) {
        this.isbn = isbn;
    }

    public Date getStart_date() {
        return start_date;
    }

    public void setStart_date(Date start_date) {
        this.start_date = start_date;
    }

    public Date getEnd_date() {
        return end_date;
    }

    public void setEnd_date(Date end_date) {
        this.end_date = end_date;
    }

    public void borrowBook(Client client_id, Book isbn){

    }

    public void returnBook(){

    }
}
