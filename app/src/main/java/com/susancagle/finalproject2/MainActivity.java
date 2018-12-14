package com.susancagle.finalproject2;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final int NEW_WORD_ACTIVITY_REQUEST_CODE = 1;
    public static final int EDIT_WORD_ACTIVITY_REQUEST_CODE = 2;

    private WordViewModel wordViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /////////////////////////////////////////////////////////////////
        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        final WordListAdapter adapter = new WordListAdapter();
        recyclerView.setAdapter(adapter);

        wordViewModel = ViewModelProviders.of(this).get(WordViewModel.class);
        wordViewModel.getAllWords().observe(this, new Observer<List<Word>>(){
            @Override
            public void onChanged(@Nullable final List<Word> words) {
                // Update the cached copy of the words in the adapter.
                adapter.setWords(words);
            }
        });

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, NewEditWordActivity.class);
                startActivityForResult(intent, NEW_WORD_ACTIVITY_REQUEST_CODE);
            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                wordViewModel.delete(adapter.getWordAt(viewHolder.getAdapterPosition()));
                Toast.makeText(MainActivity.this, "Note deleted", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);

        adapter.setOnItemClickListener(new WordListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Word word) {
                Intent intent = new Intent(MainActivity.this, NewEditWordActivity.class);
                intent.putExtra(NewEditWordActivity.EXTRA_ID, word.getId());
                intent.putExtra(NewEditWordActivity.EXTRA_WORD, word.getWord());
                intent.putExtra(NewEditWordActivity.EXTRA_DESCRIPTION, word.getDefinition());
                startActivityForResult(intent, EDIT_WORD_ACTIVITY_REQUEST_CODE);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
      switch (item.getItemId()) {
          case R.id.delete_all_notes:
              wordViewModel.deleteAllWords();
              Toast.makeText(this, "All notes deleted", Toast.LENGTH_SHORT).show();
              return true;
          default:
              return super.onOptionsItemSelected(item);

      }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == NEW_WORD_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            String title = data.getStringExtra(NewEditWordActivity.EXTRA_WORD);
            String definition = data.getStringExtra(NewEditWordActivity.EXTRA_DESCRIPTION);

            Word word = new Word(title, definition);
            wordViewModel.insert(word);

            Toast.makeText(this, "Note saved", Toast.LENGTH_SHORT).show();
        } else if (requestCode == EDIT_WORD_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK){

            int id = data.getIntExtra(NewEditWordActivity.EXTRA_ID, -1);

           if (id == -1) {
               Toast.makeText(this, "Note Can't be Updated", Toast.LENGTH_SHORT).show();
               return;
           }

            String title = data.getStringExtra(NewEditWordActivity.EXTRA_WORD);
            String definition = data.getStringExtra(NewEditWordActivity.EXTRA_DESCRIPTION);

            Word word = new Word(title, definition);
            word.setId(id);
            wordViewModel.update(word);
            Toast.makeText(this, "Note Updated", Toast.LENGTH_SHORT).show();

        } else {
            Toast.makeText(
                    getApplicationContext(),
                    R.string.empty_not_saved,
                    Toast.LENGTH_LONG).show();
        }
    }
}
