package com.susancagle.finalproject2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class NewEditWordActivity extends AppCompatActivity {

    public static final String EXTRA_ID = "com.susancagle.finalproject2.EXTRA_ID";
    public static final String EXTRA_WORD = "com.susancagle.finalproject2.EXTRA_WORD";
    public static final String EXTRA_DESCRIPTION = "com.susancagle.finalproject2.EXTRA_DESCRIPTION";


    private EditText editWord;
    private EditText editDefinition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_word);
        editWord = findViewById(R.id.edit_word);
        editDefinition = findViewById(R.id.edit_definition);

        Intent intent = getIntent();

        if (intent.hasExtra(EXTRA_ID)){
            setTitle("Edit Word");
            editWord.setText(intent.getStringExtra(EXTRA_WORD));
            editDefinition.setText(intent.getStringExtra(EXTRA_DESCRIPTION));
        } else {
            setTitle("Add Note");
        }


        final Button button = findViewById(R.id.button_save);

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                String word = editWord.getText().toString();
                String definition = editDefinition.getText().toString();

                if (word.trim().isEmpty() || definition.trim().isEmpty()) {
                    Toast.makeText(
                            getApplicationContext(),
                            R.string.empty_not_saved,
                            Toast.LENGTH_LONG).show();
                    return;
                }

                Intent data = new Intent();
                data.putExtra(EXTRA_WORD, word);
                data.putExtra(EXTRA_DESCRIPTION, definition);

                int id = getIntent().getIntExtra(EXTRA_ID, -1);
                if (id != -1) {
                    data.putExtra(EXTRA_ID, id);
                }

                setResult(RESULT_OK, data);
                finish();
            }
        });


    }
}
