package com.example.partial;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private Button convert;
    private Spinner din, in;
    private EditText value;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        convert = findViewById(R.id.convert);
        din = findViewById(R.id.spinner1);
        in = findViewById(R.id.spinner2);
        value = findViewById(R.id.value);

        List<String> masuri = new ArrayList<>();
        masuri.add("cm");
        masuri.add("inch");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, masuri);
        din.setAdapter(adapter);
        in.setAdapter(adapter);

        convert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float val = Float.parseFloat((value.getText().toString()));
                float result = 0;
                String unit1 = din.getSelectedItem().toString();
                String unit2 = in.getSelectedItem().toString();
                if (unit1.equals("cm") && unit2.equals("inch")) {
                    result = (float) (val / 2.54);
                } else if (unit2.equals("cm") && unit1.equals("inch")) {
                    result = (float) (val * 2.54);
                } else if( unit1.equals(unit2)){
                    result = val;
                }
                CustomDialog c = new CustomDialog(unit1, unit2, val, result);
                c.show(getSupportFragmentManager(), "tag");
            }
        });
    }

    public static class CustomDialog extends DialogFragment {
        private String din, in;
        private float val1, val2;

        public CustomDialog(String din, String in, float val1, float val2) {
            this.din = din;
            this.in = in;
            this.val1 = val1;
            this.val2 = val2;
        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the Builder class for convenient dialog construction
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage(val1 + " " + din + " = " + val2 + " " + in)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dismiss();
                        }
                    })
                    .setNegativeButton("Share", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            Toast.makeText(getContext(), val1 + " " + din + " = " + val2 + " " + in, Toast.LENGTH_SHORT).show();
                        }
                    }).setTitle("Rezultat");
            // Create the AlertDialog object and return it
            return builder.create();
        }
    }
}

