package narpatpatel031999.com.newshandarrestaurant;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import static android.widget.Toast.LENGTH_LONG;

public class MainScreen  extends AppCompatActivity {
    RecyclerView recyclerView1;
   List<fooditem>list;
   ViewAdapter adapter;
   FirebaseFirestore db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainscreen);

        recyclerView1 = findViewById(R.id.recyclerview);

        db = FirebaseFirestore.getInstance();
        list=new ArrayList<>();
        LinearLayoutManager manager= new LinearLayoutManager(this);
        recyclerView1.setLayoutManager(manager);
        recyclerView1.setHasFixedSize(true);
        db.collection("data")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for(QueryDocumentSnapshot documentSnapshot :queryDocumentSnapshots){
                            fooditem food=documentSnapshot.toObject(fooditem.class);
                            list.add(food);
                        }
                        adapter=new ViewAdapter(list,MainScreen.this);
                        recyclerView1.setAdapter(adapter);

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(MainScreen.this,e.getMessage(), LENGTH_LONG).show();
                    }
                });
    }
}

