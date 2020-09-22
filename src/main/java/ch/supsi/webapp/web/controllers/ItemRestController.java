package ch.supsi.webapp.web.controllers;

import ch.supsi.webapp.web.model.Item;
import ch.supsi.webapp.web.services.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class ItemRestController {


    @Autowired
    private ItemService itemService;


    @RequestMapping(value = "/items", method = RequestMethod.GET)
    public List<Item> getItems() {
        return itemService.getAllItems();
    }

    @RequestMapping(value = "/items/{id}", method = RequestMethod.GET)
    public ResponseEntity<Item> getItem(@PathVariable int id) {
        if (itemService.exists(id)) {
            Item item = itemService.getItemById(id);
            return new ResponseEntity<>(item, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @RequestMapping(value = "/items", method = RequestMethod.POST)
    public ResponseEntity<Item> post(@RequestBody Item item) {
        itemService.saveItem(item);
        return new ResponseEntity<>(item, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/items/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Item> update(@PathVariable int id, @RequestBody Item itemInRequest) {
        if (itemService.exists(id)) { // non funziona bene! non modifica ma aggiunge item con un nuovo id
            itemInRequest.setId(id);
            itemService.saveItem(itemInRequest);
            return new ResponseEntity<>(itemInRequest, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @RequestMapping(value = "/items/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Map<String, String>> delete(@PathVariable int id) {
        HashMap<String, String> jsonStatus;
        if (itemService.exists(id)) {
            itemService.deleteById(id);
            jsonStatus = new HashMap<>();
            jsonStatus.put("success", "true");
            return new ResponseEntity<>(jsonStatus, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/items/search")
    @ResponseBody
    public List<Item> search(@RequestParam("q") String q) {
        return itemService.searchItems(q);
    }

}
