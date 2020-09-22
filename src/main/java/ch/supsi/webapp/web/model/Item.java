package ch.supsi.webapp.web.model;


import javax.persistence.*;
import java.util.Date;


@Entity
public class Item {

    @Id
    @GeneratedValue
    private int id;

    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    private Date date;

    @Enumerated(EnumType.STRING)
    private ItemType type;

    @Lob
    private byte[] image;

    @ManyToOne
    private Author author;

    @ManyToOne
    private Category category;

    @OneToOne(cascade = CascadeType.ALL)
    private Asta asta;

    public Item(int id, String title, String description, Date date, ItemType type, byte[] image, Author author, Category category) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.date = date;
        this.type = type;
        this.image = image;
        this.author = author;
        this.category = category;
    }

    public Item() {

    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public ItemType getType() {
        return type;
    }

    public void setType(ItemType type) {
        this.type = type;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Asta getAsta() {
        return asta;
    }

    public void setAsta(Asta asta) {
        this.asta = asta;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return id == item.id;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (author != null ? author.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", author=" + author +
                ", category=" + category +
                '}';
    }
}
