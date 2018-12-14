package com.susancagle.finalproject2;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

@Database(entities = {Word.class}, version = 1, exportSchema = false)
public abstract class WordRoomDatabase extends RoomDatabase {

    private static WordRoomDatabase INSTANCE;

    public abstract WordDao wordDao();

    public static WordRoomDatabase getDatabase(final Context context){
        if (INSTANCE == null) {
            synchronized (WordRoomDatabase.class) {
                if(INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            WordRoomDatabase.class, "word_database")
                            //Wipes and rebuilds instead of migrating
                            // if not Migration object.
                            // Migration is not part of this practical.
                            .fallbackToDestructiveMigration()
                            .addCallback(roomDatabaseCallback)
                            .build();
                }
            }

        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback roomDatabaseCallback = new RoomDatabase.Callback(){
        @Override
        public void onCreate (@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(INSTANCE).execute();
        }
    };

    // Populate the database in the background.

    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {

        private WordDao wordDao;

        PopulateDbAsyncTask(WordRoomDatabase db) {
            wordDao = db.wordDao();
        }

        @Override
        protected Void doInBackground(final Void...voids) {
            wordDao.insert(new Word("Dolphin", "A type of sea Mammal."));
            wordDao.insert(new Word("Shark", "A type of sea creature."));
            wordDao.insert(new Word("Creature", "An animal or person."));
            return null;
        }
    }
}
