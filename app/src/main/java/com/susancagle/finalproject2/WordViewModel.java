package com.susancagle.finalproject2;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;

public class WordViewModel extends AndroidViewModel {
    private WordRepository repository;
    private LiveData<List<Word>> allWords;


    public WordViewModel(@NonNull Application application) {
        super(application);
        repository = new WordRepository(application);
        allWords = repository.getAllWords();
    }


    public void insert(Word word) {
        repository.insert(word);

    }

    public void update(Word word) {
        repository.update(word);

    }
    public void delete(Word word) {
        repository.delete(word);

    }

    public void deleteAllWords() {
        repository.deleteAllWords();

    }

    LiveData<List<Word>> getAllWords() {

        return allWords;
    }

}