package narpatpatel031999.com.newshandarrestaurant;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import static android.widget.Toast.LENGTH_LONG;

public class MainScreen extends AppCompatActivity {
    RecyclerView recyclerView1;
    List<fooditem> list;
    ViewAdapter adapter;
    FirebaseFirestore db;
    ImageSlider mainSlider;
    private DrawerLayout dl;
    private ActionBarDrawerToggle t;
    private NavigationView nv;

    @Override
    protected void onStart() {
        super.onStart();
        if(foodDetail.cartList.size()==0)
        {
            foodDetail.fetchCartList();
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);
        setTitle("Menu Of The Day");
        dl = (DrawerLayout) findViewById(R.id.activity_main_screen);
        t = new ActionBarDrawerToggle(this, dl, R.string.open, R.string.close);


        dl.addDrawerListener(t);
        t.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        nv = (NavigationView) findViewById(R.id.nav);
        View headerView = nv.getHeaderView(0);

        nv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                switch (id) {
                    case R.id.userProfilee:
                        Toast.makeText(MainScreen.this, "My Profile", Toast.LENGTH_SHORT).show();
                        Intent intent4=new Intent(getApplicationContext(),UserDetailProfile.class);
                        startActivity(intent4);
                        break;
                    case R.id.userCart:
                        Toast.makeText(MainScreen.this, "My Cart", Toast.LENGTH_SHORT).show();
                        if(foodDetail.cartList.size()!=0)
                        {
                            Intent intent = new Intent(MainScreen.this, AddToCart.class);
                            startActivity(intent);
                        }
                        else
                        {

                            Intent intent = new Intent(MainScreen.this, EmptyCart.class);
                            startActivity(intent);
                        }
                        break;
                    case R.id.userOrdere:
                        Toast.makeText(MainScreen.this, "My Order", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.contacte:
                        Toast.makeText(MainScreen.this, "Contact Us", Toast.LENGTH_SHORT).show();
                        Intent intent1 = new Intent(MainScreen.this, ContactUs.class);
                        startActivity(intent1);
                        break;
                    case R.id.privacye:
                        Toast.makeText(MainScreen.this, " User Privacy", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(MainScreen.this, UserPrivacy.class);
                        startActivity(intent);
                        break;
                    case R.id.aboute:
                        Toast.makeText(MainScreen.this, "About Us", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.logOute:
                        AlertDialog.Builder builder1 = new AlertDialog.Builder(MainScreen.this);
                        builder1.setMessage("Are You Sure You Want To Logout From This Device?");
                        builder1.setCancelable(false);
                        builder1.setPositiveButton(
                                "Yes",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        Toast.makeText(MainScreen.this, "Logout Successfully", Toast.LENGTH_SHORT).show();
                                        FirebaseAuth.getInstance().signOut();
                                        finish();
                                        startActivity(new Intent(getApplicationContext(),MainActivity.class));

                                    }
                                });
                        builder1.setNegativeButton(
                                "No",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });
                        AlertDialog alert11 = builder1.create();
                        alert11.show();

                        break;
                    default:
                        return true;
                }


                return true;

            }
        });

        recyclerView1 = findViewById(R.id.recyclerview);
        mainSlider = (ImageSlider) findViewById(R.id.image_slider);
        db = FirebaseFirestore.getInstance();
        list = new ArrayList<>();
        final List<SlideModel> rImage = new ArrayList<>();
        LinearLayoutManager manager = new LinearLayoutManager(this);
        recyclerView1.setLayoutManager(manager);
        recyclerView1.setHasFixedSize(true);

        db.collection("slide").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                    rImage.add(new SlideModel(documentSnapshot.getString("image"), documentSnapshot.getString("name"), ScaleTypes.CENTER_CROP));
                }
                mainSlider.setImageList(rImage, ScaleTypes.CENTER_CROP);
            }
        });

        db.collection("data")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                            fooditem food = documentSnapshot.toObject(fooditem.class);
                            list.add(food);
                        }
                        adapter = new ViewAdapter(list, MainScreen.this);
                        recyclerView1.setAdapter(adapter);

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(MainScreen.this, e.getMessage(), LENGTH_LONG).show();
                    }
                });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.cartView:
                if(foodDetail.cartList.size()!=0)
                {
                    Intent intent = new Intent(MainScreen.this, AddToCart.class);
                    startActivity(intent);
                }
                else
                {

                    Intent intent = new Intent(MainScreen.this, EmptyCart.class);
                    startActivity(intent);
                }
        }

        if (t.onOptionsItemSelected(item))
            return true;

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;

    }
}

