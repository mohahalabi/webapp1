package ch.supsi.webapp.web.services;

import ch.supsi.webapp.web.model.*;
import ch.supsi.webapp.web.repository.*;
import org.aspectj.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ItemService {

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private AstaRepository astaRepository;

    @PostConstruct
    public void init() throws IOException {
        if (categoryRepository.findAll().size() == 0) {
            categoryRepository.save(new Category("Veicoli"));
            categoryRepository.save(new Category("Immobili"));
            categoryRepository.save(new Category("Lavoro"));
        }

        if (userRepository.findAll().size() == 0) {
            Author author = new Author("admin", new Role("ROLE_ADMIN"));
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            author.setPassword(encoder.encode("admin"));
            author.setName("Admin");
            author.setLastName("Admin");
            userRepository.save(author);
        }

        if (itemRepository.findAll().size() == 0) {
            Item item = new Item();
            Author author = userRepository.findById("admin").get();
            item.setAuthor(author);
            item.setCategory(categoryRepository.findById("Veicoli").get());
            item.setType(ItemType.Offerta);
            item.setTitle("Audi A3");
            item.setDescription("Bellissima auto, divertente nel misto. Perfette condizioni. Airscarf,Parktronic,pelle...");
            item.setDate(new Date());
            item.setImage(FileUtil.readAsByteArray(this.getClass().getResourceAsStream("/static/images/audi.jpg")));
            System.out.println(item);
            saveItem(item);

            item = new Item();
            item.setAuthor(author);
            item.setCategory(categoryRepository.findById("Veicoli").get());
            item.setType(ItemType.Offerta);
            item.setTitle("Affare Vendo macchina VW Polo");
            item.setDescription("ciao scherzavo non vendo nessuna macchina :)");
            item.setDate(new Date());
            item.setImage(getEmptyImageTemplate());
            saveItem(item);

            item = new Item();
            item.setAuthor(author);
            item.setCategory(categoryRepository.findById("Veicoli").get());
            item.setType(ItemType.Richiesta);
            item.setTitle("Compro auto usate");
            item.setDescription("Compro auto usate di tutte le marche. In tutti gli stati possibili guaste o accidentate tanti chilometri ecc");
            item.setDate(new Date());
            item.setImage(FileUtil.readAsByteArray(this.getClass().getResourceAsStream("/static/images/used.jpg")));
            saveItem(item);

            item = new Item();
            item.setAuthor(author);
            item.setCategory(categoryRepository.findById("Immobili").get());
            item.setType(ItemType.Offerta);
            item.setTitle("Affare casa con giardino");
            item.setDescription("La struttura della casa è stata realizzata con ottimi materiali isolanti, il tetto è ventilato il tutto garantisce in inverno la tenuta del caldo all'interno ed in estate l' isolazione dal caldo.");
            item.setDate(new Date());
            item.setImage(FileUtil.readAsByteArray(this.getClass().getResourceAsStream("/static/images/house.jpg")));
            saveItem(item);

        }

    }

    public byte[] getEmptyImageTemplate() throws IOException {
        return FileUtil.readAsByteArray(this.getClass().getResourceAsStream("/static/images/empty-image.jpg"));
    }

    public boolean exists(int id) {
        return itemRepository.existsById(id);
    }

    public Item getItemById(int id) {
        Optional<Item> itemOptional = itemRepository.findById(id);
        return itemOptional.orElse(null);
    }

    public List<Item> getAllItems() {
        return itemRepository.findAll();
    }

    public void saveItem(Item item) {
        itemRepository.save(item);
    }

    public void saveItems(List<Item> items) {
        itemRepository.saveAll(items);
    }

    public void deleteItem(Item item) {
        itemRepository.delete(item);
    }

    public void deleteById(int id) {
        itemRepository.deleteById(id);
    }

    public List<Category> getCategories() {
        return categoryRepository.findAll();
    }

    public List<Author> getAuthors() {
        return userRepository.findAll();
    }

    public List<Item> searchItems(String query) {
        String loweredCase = query.toLowerCase();
        return getAllItems()
                .stream()
                .filter(item -> item.getTitle().toLowerCase().contains(loweredCase)
                        || item.getCategory().getName().toLowerCase().contains(loweredCase)
                        || item.getDescription().toLowerCase().contains(loweredCase)).collect(Collectors.toList());
    }

    public Asta getAsta(int id) {
        return astaRepository.findById(id).orElse(null);
    }

    public void saveAsta(Asta asta) {
        astaRepository.save(asta);
    }

/*    public Asta getAstaNotClosed(int id) {
        return astaRepository.findByIdClosedFalse(id).orElse(null);
    }

    public Asta getAstaClosed(int id) {
        return astaRepository.findByIdClosedTrue(id).orElse(null);
    }*/

}
