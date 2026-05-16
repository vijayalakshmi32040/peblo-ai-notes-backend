package com.example.demo.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.entity.Note;
import com.example.demo.repository.NoteRepository;
import com.example.demo.service.AIService;

@RestController
@RequestMapping("/notes")

public class NoteController {

    @Autowired
    private NoteRepository noteRepository;
    
    @Autowired
    private AIService aiService;

    // Create Note
    @PostMapping
    public ResponseEntity<?> createNote(@RequestBody Note note) {

        note.setUpdatedAt(LocalDateTime.now());

        noteRepository.save(note);

        return ResponseEntity.ok("Note Created Successfully");
    }

    // Get All Notes
    @GetMapping
    public List<Note> getAllNotes() {

        return noteRepository.findAll();
    }

    // Update Note
    @PutMapping("/{id}")
    public ResponseEntity<?> updateNote(
            @PathVariable Long id,
            @RequestBody Note updatedNote) {

        Optional<Note> optionalNote =
                noteRepository.findById(id);

        if(optionalNote.isEmpty()) {
            return ResponseEntity.badRequest()
                    .body("Note Not Found");
        }

        Note note = optionalNote.get();

        note.setTitle(updatedNote.getTitle());
        note.setContent(updatedNote.getContent());
        note.setTags(updatedNote.getTags());
        note.setCategory(updatedNote.getCategory());
        note.setUpdatedAt(LocalDateTime.now());

        noteRepository.save(note);

        return ResponseEntity.ok("Note Updated Successfully");
    }

    // Delete Note
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteNote(
            @PathVariable Long id) {

        noteRepository.deleteById(id);

        return ResponseEntity.ok("Note Deleted Successfully");
    }

    // Search Notes
    @GetMapping("/search")
    public List<Note> searchNotes(
            @RequestParam String keyword) {

        return noteRepository
                .findByTitleContaining(keyword);
    }

    // Filter By Tag
    @GetMapping("/filter")
    public List<Note> filterByTag(
            @RequestParam String tag) {

        return noteRepository
                .findByTagsContaining(tag);
    }

    // Share Note
    @PostMapping("/{id}/share")
    public ResponseEntity<?> shareNote(
            @PathVariable Long id) {

        Optional<Note> optionalNote =
                noteRepository.findById(id);

        if(optionalNote.isEmpty()) {
            return ResponseEntity.badRequest()
                    .body("Note Not Found");
        }

        Note note = optionalNote.get();

        note.setPublic(true);
        note.setShareId(UUID.randomUUID().toString());

        noteRepository.save(note);

        return ResponseEntity.ok(
                "Share Link: /shared/"
                + note.getShareId());
    }
    
    @PostMapping("/{id}/generate-summary")
    public ResponseEntity<?> generateSummary(
            @PathVariable Long id) {

        Optional<Note> optionalNote =
                noteRepository.findById(id);

        if(optionalNote.isEmpty()) {

            return ResponseEntity.badRequest()
                    .body("Note Not Found");
        }

        Note note = optionalNote.get();

        String summary =
                aiService.generateSummary(
                        note.getContent());

        return ResponseEntity.ok(summary);
    }
}