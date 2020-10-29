package com.udacity.jwdnd.course1.cloudstorage.contoller;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.NoteForm;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Slf4j
@Controller
@RequestMapping("/note")
public class NoteController {

    public final static String TAG_ = "NoteController";
    private final NoteService noteService;

    @Autowired
    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    @PutMapping("/add")
    public String postNote(NoteForm noteForm, Model model, HttpSession session){
        log.debug(TAG_ + "-> add method");

        int userId = (int)session.getAttribute("userId");
        String errorMsgstr = "";
        if(noteForm.getNoteId()==null || noteForm.getNoteId().equals("")){
            log.debug(TAG_ + "-> add new note method");
            Note note = new Note(noteForm.getNoteTitle(), noteForm.getNoteDescription(), userId);
            int addRow = this.noteService.addNote(note);
            if(addRow == 1){
                log.debug(TAG_ +  "-> add new note success");
                model.addAttribute("successResult", true);
            }else{
                log.debug(TAG_ +  "-> add new note failed");
                model.addAttribute("errorResult", true);
                errorMsgstr = "New note failed to add";
                model.addAttribute("errorResultMessage", errorMsgstr);
            }
        }else{
            log.debug(TAG_ + " update/edit note method");
            Note note = new Note(Integer.parseInt(noteForm.getNoteId()), noteForm.getNoteTitle(), noteForm.getNoteDescription(), userId);
            int updateRow = this.noteService.editNoteByNoteObject(note);
            if(updateRow == 1){
                model.addAttribute("successResult", true);
                log.debug(TAG_ +  "-> update/edit note success");
            }else{
                model.addAttribute("errorResult", true);
                log.debug(TAG_ +  "-> update/edit note failed");
                errorMsgstr = "Note failed to update/edit";
                model.addAttribute("errorResultMessage", errorMsgstr);
            }
        }
        return "result";
    }

    @GetMapping("/delete")
    public String deleteNote(@RequestParam(name="noteId") String noteId, Model model){
        log.debug(TAG_ +  "-> delete method with noteId: " + noteId);
        int delRow = this.noteService.deleteNote(Integer.parseInt(noteId));
        if(delRow == 1){
            model.addAttribute("successResult", true);
        }else{
            model.addAttribute("errorResult", true);
        }
        return "result";
    }




}
