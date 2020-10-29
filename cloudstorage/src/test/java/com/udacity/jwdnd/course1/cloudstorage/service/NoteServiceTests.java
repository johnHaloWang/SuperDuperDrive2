package com.udacity.jwdnd.course1.cloudstorage.service;

import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import com.udacity.jwdnd.course1.cloudstorage.utils.TestConstant;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import java.util.List;
import java.util.Locale;

@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class NoteServiceTests {
    public final static String TAG_ = "NoteServiceTest";
    private User user;
    private Note note;

    @LocalServerPort
    private int port;

    @Autowired
    private UserService userService;

    @Autowired
    private NoteService noteService;

    @Autowired
    private NoteMapper noteMapper;

    @BeforeAll
    static void beforeAll() {
        Locale.setDefault(new Locale("en","US"));

    }

    @BeforeEach
    public void beforeEach() {
        user = TestConstant.getUser();
        userService.createUser(user);
        user = userService.getUser(user.getUsername());
        note = TestConstant.getNote(user.getUserId());
    }

    @AfterEach
    public void afterEach() {
        noteMapper.deleteAll();
        userService.deleteAll();
    }

    @Test
    public void testAddNote(){
        int addRow = noteService.addNote(note);
        Assertions.assertEquals(1, addRow);
    }

    @Test
    public void testEditNote(){
        int addRow = noteService.addNote(note);
        List<Note> list = noteService.getAllUserNotes(user.getUserId());
        Note update = list.get(0);
        update.setNoteDescription("edited");
        int editRow = noteService.editNoteByNoteObject(update);
        Assertions.assertEquals(1, editRow);
        list = noteService.getAllUserNotes(user.getUserId());
        Assertions.assertEquals(list.get(0).toString(), update.toString());

    }

    @Test
    public void testGetSingleNoteById(){
        int addRow = noteService.addNote(note);
        List<Note> list = noteService.getAllUserNotes(user.getUserId());
        Note expected = list.get(0);
        Note actual = noteService.getSingleNoteByNoteId(expected.getNoteId());
        Assertions.assertEquals(expected.toString(), actual.toString());
    }

    @Test
    public void testGetAllNoteById(){
        int expected = 3;
        for(int i = 0; i<expected; i++){
            noteService.addNote(note);
        }
        int actual = noteService.getAllUserNotes(user.getUserId()).size();
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void testDeleteNoteById(){
        noteService.addNote(note);
        int noteId = noteService.getAllUserNotes(user.getUserId()).get(0).getNoteId();
        int deleteRow = noteService.deleteNote(noteId);
        Assertions.assertEquals(1, deleteRow);
        Assertions.assertEquals(0, noteService.getAllUserNotes(user.getUserId()).size());
    }

    @Test
    public void testDeleteAll(){
        int expected = 3;
        for(int i = 0; i<expected; i++)
            noteService.addNote(note);
        Assertions.assertEquals(expected, noteService.deleteAll());
    }
}
