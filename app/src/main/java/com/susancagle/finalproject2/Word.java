package com.susancagle.finalproject2;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.support.annotation.NonNull;

// This is the entity which is 1 table in the database.
// tablename is if you want to change the database from default

@Entity(tableName = "word_table")
public class Word {

    @PrimaryKey(autoGenerate = true)
    private int id;


    @NonNull
    private String word;
    private String definition;


    // Constructor
    public Word(@NonNull String word, String definition){
        this.word = word;
        this.definition = definition;
    }


    // Getter Methods for the 3 fields.
    public String getWord(){
        return this.word;
    }

    public int getId() {
        return id;
    }

    public String getDefinition() {
        return definition;
    }

    // Room will use this to set the ID on the object
    public void setId(int id) {
        this.id = id;
    }
}


