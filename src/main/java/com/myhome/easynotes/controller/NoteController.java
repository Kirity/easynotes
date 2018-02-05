package com.myhome.easynotes.controller;



import java.util.Arrays;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import com.myhome.easynotes.model.Note;
import com.myhome.easynotes.model.WeatherPojo;
import com.myhome.easynotes.repository.NoteRepository;


@RestController
@RequestMapping("/api")
public class NoteController {
	
	@Autowired
    NoteRepository noteRepository;
	
	RestTemplate restTemplate = new RestTemplate();
	
	

    // Get All Notes
	@GetMapping("/notes")
	public List<Note> getAllNotes(){
		return noteRepository.findAll();
	}

    // Create a new Note
	@PostMapping("/notes")
	public Note createNote(@Valid @RequestBody Note note) {
		System.out.println(note);
		String getUrl = "http://api.openweathermap.org/data/2.5/weather?q=London,uk&appid=3cfd22ebd593aafe300579b626c16438";
		 ResponseEntity<WeatherPojo> getResponse =restTemplate.getForEntity(getUrl, WeatherPojo.class);
		 //System.out.println("Weather in london is "+getResponse.getBody().weather);
		 WeatherPojo w = getResponse.getBody();
		 System.out.println("Weather in london is "+w.getMain().getTemp());
		 return noteRepository.save(note);
	}

    // Get a Single Note
	
	@GetMapping("/notes/{id}")
	public ResponseEntity<Note> getNoteById(@PathVariable(value = "id") Long noteId) {
	    Note note = noteRepository.findOne(noteId);
	    if(note == null) {
	        return ResponseEntity.notFound().build();
	    }
	    return ResponseEntity.ok().body(note);
	}

    // Update a Note
	
	@PutMapping("/notes/{id}")
	public ResponseEntity<Note> updateNote(@PathVariable(value = "id") Long noteId, 
	                                       @Valid @RequestBody Note noteDetails) {
	    Note note = noteRepository.findOne(noteId);
	    if(note == null) {
	        return ResponseEntity.notFound().build();
	    }
	    note.setTitle(noteDetails.getTitle());
	    note.setContent(noteDetails.getContent());
	    System.out.println("which we got from ui "+noteDetails);

	    Note updatedNote = noteRepository.save(note);
	    return ResponseEntity.ok(updatedNote);
	}

    // Delete a Note
	
	@DeleteMapping("/notes/{id}")
	public ResponseEntity<Note> deleteNote(@PathVariable(value = "id") Long noteId) {
	    Note note = noteRepository.findOne(noteId);
	    if(note == null) {
	        return ResponseEntity.notFound().build();
	    }

	    noteRepository.delete(note);
	    return ResponseEntity.ok().build();
	}

}
