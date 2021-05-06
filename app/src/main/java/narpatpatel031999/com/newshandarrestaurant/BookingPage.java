package narpatpatel031999.com.newshandarrestaurant;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Date;

public class BookingPage extends AppCompatActivity {
    EditText editAddress;
    Button edit;
    TextView showAllOrders, showTotalPrice,dateTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_page);
        setTitle("Booking Page");
        showAllOrders = findViewById(R.id.showOrders);
        showTotalPrice = findViewById(R.id.showTotalPrice);
        editAddress = findViewById(R.id.editAddress);
        edit = findViewById(R.id.edit);
        dateTime=findViewById(R.id.dateAndTime);
        SimpleDateFormat date= new SimpleDateFormat("yyyy.MM.dd  '     ' HH:mm:ss ");
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
        }
        else
        {
            showTotalPrice.setText("कुल रुपये " + foodDetail.totalSum);
            showAllOrders.setText(showAllOrders.getText() + getIntent().getStringExtra("bFood") + "         " + getIntent().getStringExtra("dQuantity") + "                               " + getIntent().getStringExtra("bPrice") + "\n");
        }
    }
}