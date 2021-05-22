package narpatpatel031999.com.newshandarrestaurant;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class BookingPage extends AppCompatActivity {
    EditText editAddress;
    Button edit, booking;
    TextView showAllOrders, showTotalPrice, dateTime;
    FirebaseFirestore fStore;
    String userId;
    FirebaseAuth firebaseAuth;
    String name, mobile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_page);
        setTitle("Booking Page");
        showAllOrders = findViewById(R.id.showOrders);
        showTotalPrice = findViewById(R.id.showTotalPrice);
        editAddress = findViewById(R.id.editAddress);
        edit = findViewById(R.id.edit);
        dateTime = findViewById(R.id.dateAndTime);
        booking = findViewById(R.id.confirmBooking);
        SimpleDateFormat date = new SimpleDateFormat("yyyy.MM.dd  '     ' HH:mm:ss ");
        String currentDateAndTime = date.format(new Date());
        dateTime.setText(currentDateAndTime);


        FirebaseFirestore.getInstance().collection("user").document(FirebaseAuth.getInstance().getUid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {

                if (documentSnapshot.exists()) {
                    String address = documentSnapshot.getString("Address");
                    editAddress.setText(address, TextView.BufferType.EDITABLE);
                }

            }
        });
        editAddress.setFocusable(false);

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editAddress.setFocusableInTouchMode(true);
            }
        });
        if (AddToCart.b == 1) {
            for (int i = 0; i < cartAdapter.list.size(); i++) {
                showAllOrders.setText(showAllOrders.getText() + cartAdapter.list.get(i).getFood_Name() + "         " + "(" + cartAdapter.list.get(i).getQuantity() + ")" + "                               " + cartAdapter.list.get(i).getPrice() + "\n");

            }
            showTotalPrice.setText("कुल रुपये " + String.valueOf(cartAdapter.totalPrice));
        } else {
            showTotalPrice.setText("कुल रुपये " + foodDetail.totalSum);
            showAllOrders.setText(showAllOrders.getText() + getIntent().getStringExtra("bFood") + "         " + getIntent().getStringExtra("dQuantity") + "                               " + getIntent().getStringExtra("bPrice") + "\n");
        }

        firebaseAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        userId = firebaseAuth.getCurrentUser().getUid();
        fStore.collection("user").document(userId).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {

                if (documentSnapshot.exists()) {
                    name = documentSnapshot.getString("FullName");
                    mobile = documentSnapshot.getString("Mobile");
                }

            }
        });
        DocumentReference docRef = fStore.collection("Booking").document(userId);
        DocumentReference documentReference = fStore.collection("Booking").document(userId).collection("AllOrders").document();
        booking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!editAddress.getText().toString().isEmpty() && !dateTime.getText().toString().isEmpty()) {

                    String uAddress = editAddress.getText().toString();
                    String dTime = dateTime.getText().toString();
                    String allOrders = showAllOrders.getText().toString();
                    String price = showTotalPrice.getText().toString();
                    Map<String, Object> user = new HashMap<>();
                    user.put("FullName", name);
                    user.put("Address", uAddress);
                    user.put("Mobile", mobile);
                    user.put("DateAndTime", dTime);
                    user.put("AllOrders", allOrders);
                    user.put("TotalPrice", price);

                    Map<String, Object> user1 = new HashMap<>();
                    user1.put("Address", uAddress);
                    user1.put("DateAndTime", dTime);
                    user1.put("AllOrders", allOrders);
                    user1.put("TotalPrice", price);


                    docRef.set(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(BookingPage.this, "Your Booking is Confirmed", Toast.LENGTH_SHORT).show();

                            } else {
                                Toast.makeText(BookingPage.this, "Your Booking is Not Confirmed", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                    documentReference.set(user1).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {

                                  DocumentReference df=FirebaseFirestore.getInstance().collection("user").document(FirebaseAuth.getInstance().getCurrentUser().getUid());
                                  df.update("cartList", FieldValue.delete());
                                  foodDetail.cartList.clear();
                                  AddToCart.list.clear();
                                  AddToCart.getInstance().finish();

                                  finish();
                            }
                        }
                    });
                } else {
                    Toast.makeText(BookingPage.this, "All Fields are required", Toast.LENGTH_SHORT).show();
                    return;
                }

            }
        });
    }
}