package narpatpatel031999.com.newshandarrestaurant;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class UserDetailProfile extends AppCompatActivity {
    TextView pUserName,pUserNumber,pUserAddress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_detail_profile);
        setTitle("My Profile");
        pUserAddress=findViewById(R.id.pUserAddress);
        pUserName=findViewById(R.id.pUserName);
        pUserNumber=findViewById(R.id.pUserNumber);
        FirebaseFirestore.getInstance().collection("user").document(FirebaseAuth.getInstance().getUid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {

                if (documentSnapshot.exists()) {
                    String address = documentSnapshot.getString("Address");
                    String name=documentSnapshot.getString("FullName");
                    String mobile=documentSnapshot.getString("Mobile");
                    pUserAddress.setText("Address:  "+address);
                    pUserName.setText(name);
                    pUserNumber.setText(mobile);
                }

            }
        });

    }
}