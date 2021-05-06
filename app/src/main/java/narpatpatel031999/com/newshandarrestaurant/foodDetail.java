package narpatpatel031999.com.newshandarrestaurant;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;
public class foodDetail extends AppCompatActivity {
    ImageView dFImage;
    TextView dFDescription, dFName, dFPrice, Quantity;
    Button cartButton, bookButton;
    String userId;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore fStore;
    ImageView add, subtract;
    long count = 1;
   public static long totalSum = 0;
    public static List<String> cartList = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fooddetail);

        setTitle(getIntent().getStringExtra("dFoodName"));


        dFImage = findViewById(R.id.fDImage);
        bookButton = findViewById(R.id.bookButton);
        cartButton = findViewById(R.id.cartButton);
        dFName = findViewById(R.id.fDName);
        dFPrice = findViewById(R.id.fDPrice);
        Quantity = findViewById(R.id.quantity);
        dFDescription = findViewById(R.id.detailDescription);
        add = findViewById(R.id.add);
        subtract = findViewById(R.id.subtract);
        final ProgressDialog dialog=new ProgressDialog(foodDetail.this);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count++;
                Quantity.setText(String.valueOf(count));
            }
        });
        subtract.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (count > 1) {
                    count--;
                    Quantity.setText(String.valueOf(count));
                } else {
                    Toast.makeText(getApplicationContext(),"Minimum one is Required",Toast.LENGTH_SHORT).show();
                }
            }
        });
        bookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), BookingPage.class);
                totalSum = Long.parseLong(Quantity.getText().toString()) * (Long.parseLong(getIntent().getStringExtra("dPrice")));
                intent.putExtra("bPrice", getIntent().getStringExtra("dPrice"));
                intent.putExtra("bFood", getIntent().getStringExtra("dFoodName"));
                intent.putExtra("dQuantity","("+Quantity.getText().toString()+")");
                AddToCart.b=0;
                startActivity(intent);
            }
        });
        cartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseAuth = FirebaseAuth.getInstance();
                fStore = FirebaseFirestore.getInstance();
                userId = firebaseAuth.getCurrentUser().getUid();
                String id = getIntent().getStringExtra("fId");

                if (cartList.contains(id)) {
                    Toast.makeText(getApplicationContext(), "Already added to cart", Toast.LENGTH_SHORT).show();
                } else {
                    dialog.show();
                    cartList.add(id);
                    DocumentReference userReference = fStore.collection("user").document(userId);
                    userReference.update("cartList", FieldValue.arrayUnion(id));
                    Toast.makeText(getApplicationContext(), "Item Added Successfully", Toast.LENGTH_SHORT).show();

                }

            }
        });

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                dialog.dismiss();
            }
        },1500);

        Glide.with(getApplicationContext()).load(getIntent().getStringExtra("dImage")).into(dFImage);
        dFDescription.setText(getIntent().getStringExtra("dDescription"));
        dFPrice.setText("₹" + getIntent().getStringExtra("dPrice"));
        dFName.setText(getIntent().getStringExtra("dFoodName"));
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (cartList.size() == 0) {
            fetchCartList();
        }
    }



    public static void fetchCartList() {
        FirebaseFirestore.getInstance().collection("user").document(FirebaseAuth.getInstance().getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.getResult().get("cartList") != null) {
                    cartList = (List) task.getResult().get("cartList");
                }

            }
        });
    }
}
