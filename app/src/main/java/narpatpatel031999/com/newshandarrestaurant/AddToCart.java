package narpatpatel031999.com.newshandarrestaurant;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
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

public class AddToCart extends AppCompatActivity {
    RecyclerView cartRecycler;
    public static int b;
   public static List<cartItem> list;
    public static cartAdapter cAdapter;
    FirebaseFirestore database;
    Button cBookingButton;
    public static TextView totalPrice;
    static AddToCart addToCart;

    @Override
    protected void onStart() {
        super.onStart();
        if (foodDetail.cartList.size() == 0) {
            foodDetail.fetchCartList();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_to_cart);
        cartRecycler = findViewById(R.id.cRecyclerview);
        cBookingButton = findViewById(R.id.cBookButton);
        totalPrice = findViewById(R.id.totalPrice);
        setTitle("My cart");
        addToCart=this;

        cBookingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), BookingPage.class);
                b = 1;
                startActivity(intent);
            }
        });
        database = FirebaseFirestore.getInstance();
        list = new ArrayList<>();

        LinearLayoutManager manager = new LinearLayoutManager(this);
        cartRecycler.setLayoutManager(manager);
        cartRecycler.setHasFixedSize(true);
        for (int i = 0; i < foodDetail.cartList.size(); i++) {
            database.collection("data").whereEqualTo("Id", foodDetail.cartList.get(i))
                    .get()
                    .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @Override
                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                            for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                                cartItem cart = documentSnapshot.toObject(cartItem.class);
                                list.add(cart);
                            }
                            cAdapter = new cartAdapter(list, AddToCart.this);
                            cartRecycler.setAdapter(cAdapter);

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(AddToCart.this, e.getMessage(), LENGTH_LONG).show();
                        }
                    });
        }
    }
    public static AddToCart getInstance(){
        return addToCart;
    }
}