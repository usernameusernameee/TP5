package com.example.tp5;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.tp5.classes.Etudiant;
import com.example.tp5.service.EtudiantService;

public class MainActivity extends AppCompatActivity {
    private EditText nom;
    private EditText prenom;
    private Button add;
    private EditText id;
    private Button rechercher;
    private TextView res;
    //Méthode pour vider les champs après l’ajout
    void clear(){
        nom.setText("");
        prenom.setText("");
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        final EtudiantService es = new EtudiantService(this);
        nom = findViewById(R.id.id);
        prenom = findViewById(R.id.prenom);
        add = findViewById(R.id.bn);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Insertion des étudiants
                es.create(new Etudiant(nom.getText().toString(), prenom.getText().toString()));
                clear();
                //Parcourir la liste des étudiants
                for(Etudiant e : es.findAll()){
                    Log.d(e.getId()+"", e.getNom()+" "+e.getPrenom());
                }
            }
        });
        id = (EditText)findViewById(R.id.id);
        rechercher = (Button)findViewById(R.id.load);
        res = (TextView)findViewById(R.id.res);
        rechercher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Etudiant e = es.findById(Integer.parseInt(id.getText().toString()));
                res.setText(e.getNom()+" "+e.getPrenom());
            }
        });

        //Insertion des étudiants
        es.create(new Etudiant("ALAMI", "ALI"));
        es.create(new Etudiant("RAMI", "AMAL"));
        es.create(new Etudiant("SAFI", "MHMED"));
        es.create(new Etudiant("SELAOUI", "REDA"));
        es.create(new Etudiant("ALAMI", "WAFA"));

        //Parcourir la liste des étudiants
        for(Etudiant e : es.findAll()){
            Log.d(e.getId()+"", e.getNom()+" "+e.getPrenom());
        }

        //Supprimer l'élement dont id = 3
        es.delete(es.findById(3));

        //Liste après suppression
        Log.d("delete","Après suppression");
        for(Etudiant e : es.findAll()){
            Log.d(e.getId()+"", e.getNom()+" "+e.getPrenom());
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}