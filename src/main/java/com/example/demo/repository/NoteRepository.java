package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Note;

public interface NoteRepository extends JpaRepository<Note, Long> {

    List<Note> findByTitleContaining(String keyword);

    List<Note> findByTagsContaining(String tag);
}