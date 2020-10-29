package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.utils.TestConstant;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import java.util.List;
import java.util.Locale;

@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class NoteMapperTests {

    private Note test ;
    private User user;
    @LocalServerPort
    private int port;

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private NoteMapper noteMapper;


    @BeforeAll
    static void beforeAll() {
        Locale.setDefault(new Locale("en","US"));

    }

    @BeforeEach
    public void beforeEach() {
        user = TestConstant.getUser();
        userMapper.insert(user);
        user = userMapper.getUser(user.getUsername());
        test = TestConstant.getNote(user.getUserId());
    }

    @AfterEach
    public void afterEach() {
        noteMapper.deleteAll();
        userMapper.deleteAll();
    }

    @Test
    public void testInsertNote(){
        int addRow = noteMapper.insert(test);
        Assertions.assertEquals(1, addRow);
    }

    @Test void testSelectNote(){
        int addRow = noteMapper.insert(test);
        //List<Note> list = noteMapper.getAllNotesByUserId(user.getUserId());
        List<Note> list = noteMapper.getAll(user.getUserId());
        int noteId = list.get(0).getNoteId();
        test.setNoteId(noteId);
        //Note result = noteMapper.getNoteByNoteId(noteId);
        Note result = noteMapper.getItemByItemId(noteId);
        Assertions.assertEquals(test.toString(), result.toString());
    }

    @Test void testUpdateNote(){
        int addRow = noteMapper.insert(test);
        List<Note> list = noteMapper.getAll(user.getUserId());
        int noteId = list.get(0).getNoteId();
        test.setNoteId(noteId);
        Note expected = noteMapper.getItemByItemId(noteId);
        expected.setNoteDescription("testing");
        int result = noteMapper.update(expected);
        Assertions.assertEquals(1, result);
        Note resultNote = noteMapper.getItemByItemId(noteId);
        Assertions.assertEquals(expected.toString(), resultNote.toString());
    }

    @Test void testDeleteNote(){
        int addRow = noteMapper.insert(test);
        List<Note> list = noteMapper.getAll(user.getUserId());
        int noteId = list.get(0).getNoteId();
        noteMapper.delete(noteId);
        List<Note> result = noteMapper.getAll(user.getUserId());
        Assertions.assertEquals(0, result.size());
    }

    @Test void testListOfNote(){
        noteMapper.deleteAll();
        int expected = 5;
        for(int i = 0; i< expected; i++) {
            test = new Note(0, "title", "description", user.getUserId());
            noteMapper.insert(test);
        }
        List<Note> result = noteMapper.getAll(user.getUserId());
        Assertions.assertEquals(expected, result.size());
    }

}
