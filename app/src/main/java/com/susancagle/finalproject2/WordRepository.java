package com.susancagle.finalproject2;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

public class WordRepository {
    private WordDao wordDao;
    private LiveData<List<Word>> allWords;

    public WordRepository(Application application) {
        WordRoomDatabase database = WordRoomDatabase.getDatabase(application);
        wordDao = database.wordDao();
        allWords = wordDao.getAllWords();
    }


    // Wrapper for the insert() method. Use an AsyncTask to all insert on a non UI thread.

    public void insert (Word word) {

        new insertWordAsyncTask(wordDao).execute(word);
    }


    public void update(Word word){
        new updateWordAsyncTask(wordDao).execute(word);
    }

    public void delete(Word word){
        new deleteWordAsyncTask(wordDao).execute(word);
    }

    public void deleteAllWords(){
        new deleteAllWordsAsyncTask(wordDao).execute();
    }

    // Wrapper method that returns the cached words as LiveData.
    public LiveData<List<Word>> getAllWords(){
        return allWords;
    }

    ////// Insert Async Task///////////////////////////////////////////////////////
    private static class insertWordAsyncTask extends AsyncTask<Word, Void, Void> {

        private WordDao wordDao;

        private insertWordAsyncTask(WordDao worddao) {

            this.wordDao = worddao;
        }

        @Override
        protected Void doInBackground(Word... words) {
            wordDao.insert(words[0]);
            return null;
            }
        }

    //////////////////////////update Async/////////////////////////////////////

    private static class updateWordAsyncTask extends AsyncTask<Word, Void, Void> {

        private WordDao wordDao;

        private updateWordAsyncTask(WordDao worddao) {

            this.wordDao = worddao;
        }

        @Override
        protected Void doInBackground(Word... words) {
            wordDao.update(words[0]);
            return null;
        }
    }

/////////////////////////////////delete word asynctast.//////////////////////////////
    private static class deleteWordAsyncTask extends AsyncTask<Word, Void, Void> {

        private WordDao wordDao;

        private deleteWordAsyncTask(WordDao worddao) {

            this.wordDao = worddao;
        }

        @Override
        protected Void doInBackground(Word... words) {
            wordDao.delete(words[0]);
            return null;
        }
    }


    /////////////////////////////////delete all words asynctast.//////////////////////////////
    private static class deleteAllWordsAsyncTask extends AsyncTask<Void, Void, Void> {

        private WordDao wordDao;

        private deleteAllWordsAsyncTask(WordDao worddao) {

            this.wordDao = worddao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            wordDao.deleteAllWords();
            return null;
        }
    }

}
