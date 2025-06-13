package com.mftplus.note.model.service;

import com.mftplus.note.model.entity.Note;
import com.mftplus.note.model.entity.User;
import com.mftplus.note.model.repo.NoteRepo;
import com.mftplus.note.model.repo.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class NoteService {
    private final NoteRepo noteRepo;
    private final UserRepository userRepository;

    public NoteService(NoteRepo noteRepo, UserRepository userRepository) {
        this.noteRepo = noteRepo;
        this.userRepository = userRepository;
    }

    public List<Note> getAllNotesForCurrentUser() {
        User currentUser = getCurrentUser();
        return noteRepo.findAllByUser(currentUser);
    }

    public Note saveNote(Note note) {
        note.setUser(getCurrentUser());
        return noteRepo.save(note);
    }

    public Note getNoteById(Long id) {
        return noteRepo.findById(id).orElse(null);
    }

    public void deleteNoteById(Long id) {
        noteRepo.deleteById(id);
    }

    // متد به روز شده برای قفل کردن نوت
    public Note lockNoteById(Long id) {
        Note note = getNoteById(id);
        if (note != null) {
            note.setLocked(true);
            return noteRepo.save(note);
        }
        return null;
    }

    // متد به روز شده برای باز کردن قفل نوت
    public Note unlockNoteById(Long id) {
        Note note = getNoteById(id);
        if (note != null) {
            note.setLocked(false);
            return noteRepo.save(note);
        }
        return null;
    }

    private User getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("کاربر یافت نشد"));
    }

    public List<Note> findByTitle(String title) {
        return noteRepo.findByTitle(title);
    }

//    public Note saveNote(Note note) {
//        note.setUser(getCurrentUser());
//        return noteRepo.save(note);
//    }
//
    public Note updateNote(Long noteId, Note updatedNote) {
        // ابتدا نوت موجود را از دیتابیس می‌گیریم
        Note existingNote = noteRepo.findById(noteId)
                .orElseThrow(() -> new RuntimeException("Note not found with id: " + noteId));

        // فیلدهای مورد نظر را آپدیت می‌کنیم
        existingNote.setTitle(updatedNote.getTitle());
        existingNote.setContent(updatedNote.getContent());
        existingNote.setCategory(updatedNote.getCategory());
        existingNote.setLocked(updatedNote.isLocked());

        // اگر نیاز هست، می‌توان رابطه کاربر را هم آپدیت کرد:
        // existingNote.setUser(updatedNote.getUser());

        // نوت آپدیت شده را ذخیره می‌کنیم
        return noteRepo.save(existingNote);
    }
//
//
//    public List<Note> getAllNotes() {
//        return noteRepo.findAll();
//    }
//
//    public List<Note> getNotesByCategory(String category) {
//        return noteRepo.findByCategory(category);
//    }
//
//    public Note getNoteById(Long id) {
//        return noteRepo.findById(id)
//                .orElseThrow(() -> new RuntimeException("یادداشت پیدا نشد با id: " + id));
//    }
//
////    public Note saveOrUpdate(Note note) {
////        if (note.getCreateAt() == null) {
////            note.setCreateAt(LocalDateTime.now());
////        }
////        return noteRepo.save(note);
////    }
//
//    public List<Note> searchNotes(String keyword) {
//        String lowerKeyword = keyword.toLowerCase();
//        return noteRepo.findAll().stream()
//                .filter(note -> note.getTitle().toLowerCase().contains(lowerKeyword) ||
//                        note.getContent().toLowerCase().contains(lowerKeyword))
//                .collect(Collectors.toList());
//    }
//
//    public List<Note> getNotesByUserId(Long id) {
//        User user = userRepository.findById(id)
//                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
//        return noteRepo.findByUserId(user.getId());
//    }
//
//    public List<Note> getNotesByUsername(String username) {
//        User user = userRepository.findByUsername(username)
//                .orElseThrow(() -> new RuntimeException("User not found with username: " + username));
//        return noteRepo.findByUsername(user.getUsername());
//    }
//
//    public Note lockNote(Long id) {
//        Note note = getNoteById(id);
//        note.setLocked(true);
//        return noteRepo.save(note);
//    }

////    // باز کردن قفل یادداشت
//    public Note unlockNote(Long id) {
//        Note note = getNoteById(id);
//        note.setLocked(false);
//        return noteRepo.save(note);
//    }
//
//    public void deleteNote(Long id) {
//        noteRepo.deleteById(id);
//    }
//
//    public void lockNoteById(Long noteId) {
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        if (auth == null || !auth.isAuthenticated()) {
//            throw new RuntimeException("کاربر لاگین نکرده است");
//        }
//
//        Note note = getNoteById(noteId);
//        note.setLocked(true);
//        noteRepo.save(note);
//    }
//
//    public void unlockNoteById(Long noteId) {
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        if (auth == null || !auth.isAuthenticated()) {
//            throw new RuntimeException("کاربر لاگین نکرده است");
//        }
//
//        Note note = getNoteById(noteId);
//        note.setLocked(false);
//        noteRepo.save(note);
//    }
}
