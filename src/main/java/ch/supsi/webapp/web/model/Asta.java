package ch.supsi.webapp.web.model;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Asta {

    @Id
    @GeneratedValue
    private int id;

    private Date date;

    private int prezzoBase;

    private int rilancio;

    private int prezzoVendita;

    @ManyToOne
    private Author author;

    @OneToOne
    private Item item;

    private boolean closed = false;


    public Asta(Date date, int prezzoBase, int rilancio, Author author) {
        this.date = date;
        this.prezzoBase = prezzoBase;
        this.rilancio = rilancio;
        this.author = author;
    }

    public Asta() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getPrezzoBase() {
        return prezzoBase;
    }

    public void setPrezzoBase(int prezzoBase) {
        this.prezzoBase = prezzoBase;
    }

    public int getRilancio() {
        return rilancio;
    }

    public void setRilancio(int rilancio) {
        this.rilancio = rilancio;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public boolean isClosed() {
        return closed;
    }

    public void setClosed(boolean closed) {
        this.closed = closed;
    }



    public int getPrezzoVendita() {
        return prezzoVendita;
    }

    public void setPrezzoVendita(int prezzoVendita) {
        this.prezzoVendita = prezzoVendita;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }
}
