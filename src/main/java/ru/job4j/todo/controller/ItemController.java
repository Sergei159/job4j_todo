package ru.job4j.todo.controller;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.job4j.todo.model.Item;
import ru.job4j.todo.service.ItemService;

import java.util.Date;

@ThreadSafe
@Controller
public class ItemController {

    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping("/items")
    public String items(Model model) {
        model.addAttribute("items", itemService.findAll());
        return "items";
    }

    @GetMapping("/completed")
    public String completed(Model model) {
        model.addAttribute("items", itemService.findCompleted());
        return "items";
    }

    @GetMapping("/itemsToDo")
    public String itemsToDo(Model model) {
        model.addAttribute("items", itemService.findTodo());
        return "items";
    }

    @GetMapping("/addItem")
    public String addItem(Model model) {
        model.addAttribute("items", itemService.findAll());
        return "addItem";
    }

    @GetMapping("/itemInfo/{itemId}")
    public String itemInfo(Model model, @PathVariable("itemId") int id) {
        model.addAttribute("item", itemService.findById(id));
        return "itemInfo";
    }

    @GetMapping("/updateItem/{itemId}")
    public String updateItem(Model model, @PathVariable("itemId") int id) {
        model.addAttribute("item", itemService.findById(id));
        return "updateItem";
    }

    @GetMapping("/completeItem/{itemId}")
    public String completeItem(@PathVariable("itemId") int id) {
        itemService.completeById(id);
        return "redirect:/items";
    }

    @GetMapping("/resumeItem/{itemId}")
    public String resumeItem(@PathVariable("itemId") int id) {
        itemService.resumeById(id);
        return "redirect:/items";
    }

    @GetMapping("/deleteItem/{itemId}")
    public String deleteItem(@PathVariable("itemId") int id) {
        itemService.delete(id);
        return "redirect:/items";
    }

    @PostMapping("/createItem")
    public String createItem(@ModelAttribute Item item) {
        item.setCreated(new Date(System.currentTimeMillis()));
        itemService.create(item);
        return "redirect:/items";
    }

    @PostMapping("/updateItem")
    public String updateItem(@ModelAttribute Item item) {
        item.setCreated(itemService.findById(item.getId()).getCreated());
        itemService.update(item);
        return "redirect:/items";
    }
}


