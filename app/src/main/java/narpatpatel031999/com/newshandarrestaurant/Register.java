package narpatpatel031999.com.newshandarrestaurant;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity {
    EditText name;
    EditText address;
    Button register;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore fStore;
    String userId;
    String mobile;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        setTitle("User Detail");
         mobile = getIntent().getStringExtra("mobile");
        name = findViewById(R.id.userName);
        address = findViewById(R.id.userAddress);
        register = findViewById(R.id.Register);
        firebaseAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        userId = firebaseAuth.getCurrentUser().getUid();
        DocumentReference docRef = fStore.collection("user").document(userId);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!name.getText().toString().isEmpty() && !address.getText().toString().isEmpty()) {
                    String uName = name.getText().toString();
                    String uAddress = address.getText().toString();
                    Map<String, Object> user = new HashMap<>();
                    user.put("FullName", uName);
                    user.put("Address", uAddress);
                    user.put("Mobile", mobile);
                    docRef.set(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                startActivity(new Intent(getApplicationContext(), MainScreen.class));
                                finish();
                            } else {
                                Toast.makeText(Register.this, "Data is Not Inserted", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } else {
                    Toast.makeText(Register.this, "All Fields are required", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        });
    }
}


