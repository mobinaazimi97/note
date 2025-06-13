package com.mftplus.note.model.repo;

import com.mftplus.note.model.entity.Note;
import com.mftplus.note.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoteRepo extends JpaRepository<Note, Long> {

    List<Note> findByCategory(String category);

    List<Note> findAllByUser(User owner);

//    @Query("SELECT n FROM noteEntity n WHERE n.user = :user")
//    List<Note> findByUser(@Param("user") User user);

    @Query("SELECT n FROM noteEntity n WHERE n.user.id = :id")
    List<Note> findByUserId(@Param("id") Long id);

    @Query("SELECT n FROM noteEntity n WHERE n.user.username = :username")
    List<Note> findByUsername(@Param("username") String username);
    // گرفتن یادداشت‌ها بر اساس دسته‌بندی و کاربر
    @Query("SELECT n FROM noteEntity n WHERE n.user.username = :username AND n.category = :category")
    List<Note> findByUserAndCategory(@Param("username") String username, @Param("category") String category);

    List<Note> findByTitle(String title);
}
