package ch.supsi.webapp.web.controllers;

import ch.supsi.webapp.web.model.Asta;
import ch.supsi.webapp.web.model.Author;
import ch.supsi.webapp.web.model.Item;
import ch.supsi.webapp.web.model.Role;
import ch.supsi.webapp.web.services.ItemService;
import ch.supsi.webapp.web.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.List;

@Controller
public class MainController {

    @Autowired
    private ItemService itemService;

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("items", itemService.getAllItems());
        return "home";
    }

    @GetMapping("/item/{id}")
    public String detail(@PathVariable int id, Model model) {
        Item item = itemService.getItemById(id);
        model.addAttribute("item", item);
        return "itemDetails";
    }

    @GetMapping("/item/new")
    public String newPost(Model model) {
        model.addAttribute("item", new Item());
        model.addAttribute("categories", itemService.getCategories());
        return "createItemForm";
    }

    @PostMapping("/item/new")
    public String post(Item item, @RequestParam("imageFile") MultipartFile imageFile, @AuthenticationPrincipal UserDetails currentUser) throws IOException {
        Author currentAuthor = userService.findUserByUsername(currentUser.getUsername());
        item.setDate(new Date());
        item.setAuthor(currentAuthor);
        if (!imageFile.isEmpty()) {
            item.setImage(imageFile.getBytes());
        } else {
            // if the user didn't insert an image, insert an empty one
            item.setImage(itemService.getEmptyImageTemplate());
        }
        itemService.saveItem(item);
        return "redirect:/";
    }

    @GetMapping(value = "/item/{id}/image", produces = MediaType.IMAGE_JPEG_VALUE)
    @ResponseBody
    public byte[] image(@PathVariable int id) {
        Item item = itemService.getItemById(id);
        return item.getImage();
    }


    @GetMapping("/item/{id}/edit")
    public String edit(@PathVariable int id, Model model) {
        Item item = itemService.getItemById(id);
        model.addAttribute("item", item);
        model.addAttribute("categories", itemService.getCategories());
        model.addAttribute("authors", itemService.getAuthors());
        return "createItemForm";
    }

    @PostMapping("/item/{id}/edit")
    public String put(@PathVariable int id, Item newItem, @RequestParam("imageFile") MultipartFile imageFile) throws IOException {
        Item item = itemService.getItemById(id);
        newItem.setId(id);
        newItem.setDate(item.getDate());
        newItem.setAuthor(item.getAuthor());
        if (!imageFile.isEmpty()) {
            newItem.setImage(imageFile.getBytes());
        } else
            newItem.setImage(item.getImage());
        itemService.saveItem(newItem);
        return "redirect:/item/{id}";
    }

    @GetMapping(value = "/item/{id}/delete")
    public String detail(@PathVariable int id) {
        itemService.deleteById(id);
        return "redirect:/";
    }


    @GetMapping(value = "/login")
    public String login() {
        return "login";
    }

    @GetMapping(value = "/register")
    public String register(Model model) {
        model.addAttribute("author", new Author());
        return "register";
    }

    @PostMapping(value = "/register")
    public String registerUser(Author author) {
        String password = new BCryptPasswordEncoder().encode(author.getPassword());
        Role role = new Role("ROLE_USER");
        Author newAuthor = new Author(author.getUsername(), password, author.getName(), author.getLastName(), role);
        userService.saveUser(newAuthor);
        return "redirect:/login";
    }

    @GetMapping(value = "/loginError")
    public String loginError() {
        return "wrongCredentials";
    }

    @GetMapping(value = "/item/search")
    @ResponseBody
    public List<Item> search(@RequestParam("q") String q) {
        return itemService.searchItems(q);
    }


    @GetMapping(value = "/author/favoriti")
    public String getFavoriti(@AuthenticationPrincipal UserDetails currentUser, Model model) {
        Author currentAuthor = userService.findUserByUsername(currentUser.getUsername());
        model.addAttribute("items", currentAuthor.getFavoriti());
        return "favoriti";
    }

    @GetMapping(value = "/item/{id}/aggiungiFavorito")
    public String postFavoriti(@AuthenticationPrincipal UserDetails currentUser, @PathVariable int id) {
        Author currentAuthor = userService.findUserByUsername(currentUser.getUsername());
        Item item = itemService.getItemById(id);
        if (currentAuthor.containsFavorito(item))
            currentAuthor.removeFavorito(item);
        else
            currentAuthor.addFavorito(item);
        userService.saveUser(currentAuthor);
        return "redirect:/author/favoriti";
    }

    @GetMapping(value = "/item/{id}/creaAsta")
    public String creaAsta(@PathVariable int id, Model model) {

        Asta asta = new Asta();
        model.addAttribute("asta", asta);
        return "creaAsta";
    }

    @PostMapping(value = "/item/{id}/creaAsta")
    public String creaAsta(@PathVariable int id, Asta asta, @AuthenticationPrincipal UserDetails currentUser) {
        Author currentAuthor = userService.findUserByUsername(currentUser.getUsername());
        Item item = itemService.getItemById(id);
        asta.setDate(new Date());
        asta.setAuthor(currentAuthor);
        item.setAsta(asta);
        itemService.saveItem(item);
        return "redirect:/item/{id}";
    }

}


