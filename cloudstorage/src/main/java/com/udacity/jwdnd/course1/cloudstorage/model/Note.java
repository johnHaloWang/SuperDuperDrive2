package com.udacity.jwdnd.course1.cloudstorage.model;

import lombok.Data;
import lombok.ToString;

@Data
@ToString(includeFieldNames = true)
public class Note {
    private int noteId;
    private String noteTitle;
    private String noteDescription;
    private int userId;

    public Note(int noteId, String noteTitle, String noteDescription, int userId) {
        this.noteId = noteId;
        this.noteTitle = noteTitle;
        this.noteDescription = noteDescription;
        this.userId = userId;
    }
    public Note(String noteTitle, String noteDescription, int userId) {
        this.noteTitle = noteTitle;
        this.noteDescription = noteDescription;
        this.userId = userId;
    }

}
