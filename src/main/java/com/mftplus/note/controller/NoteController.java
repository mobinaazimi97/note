package com.mftplus.note.controller;

import com.mftplus.note.model.entity.Note;
import com.mftplus.note.model.service.NoteService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/notes")
public class NoteController {
    private final NoteService noteService;

    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    @GetMapping
    public String listNotes(Model model) {
        List<Note> notes = noteService.getAllNotesForCurrentUser();
        model.addAttribute("notes", notes);
        return "notes/list";  // refers to src/main/resources/templates/notes/list.html
    }

    // صفحه نمایش جزئیات یک نوت
    @GetMapping("/{id}")
    public String noteDetails(@PathVariable Long id, Model model) {
        Note note = noteService.getNoteById(id);
        if (note == null) {
            return "redirect:/notes"; // اگر نوت پیدا نشد، به لیست برمی‌گردیم
        }
        model.addAttribute("note", note);
        return "notes/detail";  // refers to templates/notes/detail.html
    }

    // صفحه فرم ایجاد نوت جدید
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("note", new Note());
        return "notes/create";  // refers to templates/notes/create.html
    }

    // ذخیره نوت جدید
    @PostMapping
    public String createNote(@ModelAttribute Note note) {
        noteService.saveNote(note);
        return "redirect:/notes";
    }

    // صفحه فرم ویرایش نوت
    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model) {
        Note note = noteService.getNoteById(id);
        if (note == null) {
            return "redirect:/notes";
        }
        model.addAttribute("note", note);
        return "notes/edit";  // refers to templates/notes/edit.html
    }

    // ویرایش نوت
    @PostMapping("/{id}")
    public String updateNote(@PathVariable Long id, @ModelAttribute Note note) {
        noteService.updateNote(id, note);
        return "redirect:/notes/" + id;
    }

    // حذف نوت
    @PostMapping("/{id}/delete")
    public String deleteNote(@PathVariable Long id) {
        noteService.deleteNoteById(id);
        return "redirect:/notes";
    }

//    @GetMapping
//    public String listNotes(Model model) {
//        model.addAttribute("notes", noteService.getAllNotesForCurrentUser());
//        return "notes/list";
//    }
//
//    @GetMapping("/add")
//    public String showAddForm(Model model) {
//        model.addAttribute("note", new Note());
//        return "notes/add";
//    }
//
//    @PostMapping("/add")
//    public String saveNote(@ModelAttribute("note") Note note) {
//        noteService.saveNote(note);
//        return "redirect:/notes";
//    }
//
//    @GetMapping("/edit/{id}")
//    public String showEditForm(@PathVariable Long id, Model model) {
//        Note note = noteService.getNoteById(id);
//        model.addAttribute("note", note);
//        return "notes/edit";
//    }
//
//    @PostMapping("/update/{id}")
//    public String updateNote(@PathVariable Long id, @ModelAttribute("note") Note note) {
//        noteService.updateNote(id, note);
//        return "redirect:/notes";
//    }
//
//    @GetMapping("/delete/{id}")
//    public String deleteNote(@PathVariable Long id) {
//        noteService.deleteNoteById(id);
//        return "redirect:/notes";
//    }
//
//    @GetMapping("/lock/{id}")
//    public String lockNote(@PathVariable Long id) {
//        noteService.lockNote(id);
//        return "redirect:/notes";
//    }
//
//    @GetMapping("/unlock/{id}")
//    public String unlockNote(@PathVariable Long id) {
//        noteService.unlockNote(id);
//        return "redirect:/notes";
//    }
    //------------------------------------------------

    //    @GetMapping
    //    public String listNotes(@RequestParam(required = false) String category,
    //                            @RequestParam(required = false) String keyword,
    //                            Model model) {
    //        List<Note> notes;
    //        if (keyword != null && !keyword.isEmpty()) {
    //            notes = noteService.searchNotes(keyword);
    //        } else if (category != null && !category.isEmpty()) {
    //            notes = noteService.getNotesByCategory(category);
    //        } else {
    //            notes = noteService.getAllNotes();
    //        }
    //        model.addAttribute("notes", notes);
    //        model.addAttribute("selectedCategory", category == null ? "" : category);
    //        model.addAttribute("keyword", keyword == null ? "" : keyword);
    //        return "home";
    //    }
    //----------------------------------------------------------
//    @GetMapping
//    public String listNotes(Model model) {
//        model.addAttribute("notes", noteService.getAllNotesForCurrentUser());
//        model.addAttribute("newNote", new Note());
//        return "notes";
//    }
//
//    @GetMapping("/new")
//    public String createForm(Model model) {
//        model.addAttribute("note", new Note());
//        return "add-note";
//    }
//
//    //    @PostMapping
////    public String saveNote(@ModelAttribute Note note) {
////        noteService.saveOrUpdate(note);
////        return "redirect:/notes";
////    }
//    @PostMapping
//    public String saveNote(@ModelAttribute("newNote") Note note) {
//        noteService.saveNote(note);
//        return "redirect:/notes";
//    }
//
//    @GetMapping("/edit/{id}")
//    public String editForm(@PathVariable Long id, Model model) {
//        Note note = noteService.getNoteById(id);
//        if (note.isLocked()) {
//            // اگر قفل بود به صفحه لیست یادداشت‌ها برگشت بده یا پیام خطا نمایش بده
//            return "redirect:/notes?error=locked";
//        }
//        model.addAttribute("note", note);
//        return "edit-note";
//    }
//
////    @GetMapping("/delete/{id}")
////    public String deleteNote(@PathVariable Long id) {
////        Note note = noteService.getNoteById(id);
////        if (note.isLocked()) {
////            return "redirect:/notes?error=locked";
////        }
////        noteService.deleteNote(id);
////        return "redirect:/notes";
////    }
//    @DeleteMapping("/delete/{id}")
//    public String deleteNote(@PathVariable Long id) {
//        noteService.deleteNote(id);
//        return "redirect:/notes";
//    }
//
//    @PostMapping("/lock/{id}")
//    public String lockNote(@PathVariable Long id) {
//        noteService.lockNoteById(id);
//        return "redirect:/notes";
//    }
//
//    @PostMapping("/unlock/{id}")
//    public String unlockNote(@PathVariable Long id) {
//        noteService.unlockNoteById(id);
//        return "redirect:/notes";
//    }
//
//    @GetMapping("/edit/{id}")
//    public String showEditForm(@PathVariable Long id, Model model) {
//        Note note = noteService.getNoteById(id);
//        model.addAttribute("note", note);
//        return "notes/edit";  // صفحه thymeleaf برای ویرایش نوت
//    }
//
//    // پردازش فرم ویرایش نوت (ارسال اطلاعات به سرور)
//    @PostMapping("/edit/{id}")
//    public String updateNote(@PathVariable Long id, @ModelAttribute("note") Note updatedNote) {
//        noteService.updateNote(id, updatedNote);
//        return "redirect:/notes";  // پس از ویرایش، به صفحه لیست نوت‌ها برمی‌گردیم
//    }
    //----------------------------------------

//    @PostMapping("/update")
//    public String updateNote(@ModelAttribute Note note) {
//        noteService.saveOrUpdate(note);
//        return "redirect:/notes";
//    }

//    @GetMapping("/delete/{id}")
//    public String deleteNote(@PathVariable Long id) {
//        noteService.deleteNote(id);
//        return "redirect:/notes";
//    }

//    @GetMapping("/lock/{id}")
//    public String lockNote(@PathVariable Long id) {
//        noteService.lockNote(id);
//        return "redirect:/notes";
//    }
//
//    @GetMapping("/unlock/{id}")
//    public String unlockNote(@PathVariable Long id) {
//        noteService.unlockNote(id);
//        return "redirect:/notes";
//    }
}
