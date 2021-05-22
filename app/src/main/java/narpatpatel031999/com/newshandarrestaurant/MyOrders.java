package narpatpatel031999.com.newshandarrestaurant;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import static android.widget.Toast.LENGTH_LONG;

public class MyOrders extends AppCompatActivity {

    RecyclerView oRecyclerView;
    List<Orders> list;
    OrdersAdapter adapter;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.myorders);

        setTitle("My Orders");

        oRecyclerView = findViewById(R.id.oRRecyclerview);

        db = FirebaseFirestore.getInstance();
        list = new ArrayList<>();
        LinearLayoutManager manager = new LinearLayoutManager(this);
        oRecyclerView.setLayoutManager(manager);
        oRecyclerView.setHasFixedSize(true);

        db.collection("Booking").document(FirebaseAuth.getInstance().getCurrentUser().getUid()).collection("AllOrders")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                            Orders order = documentSnapshot.toObject(Orders.class);
                            list.add(order);
                        }
                        adapter = new OrdersAdapter(list, MyOrders.this);
                        oRecyclerView.setAdapter(adapter);

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(MyOrders.this, e.getMessage(), LENGTH_LONG).show();
                    }
                });
    }
}
