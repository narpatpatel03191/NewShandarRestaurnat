package narpatpatel031999.com.newshandarrestaurant;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class cartAdapter extends RecyclerView.Adapter<cartAdapter.cartViewAdapter> {

    public static List<cartItem> list;
    Context context;

    public static long totalPrice;

    public cartAdapter(List<cartItem> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public cartAdapter.cartViewAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.cart, parent, false);

        return new cartViewAdapter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull cartAdapter.cartViewAdapter holder, int position) {
        cartItem cartItems = list.get(position);
        holder.setCartDetails(cartItems.getFood_Name(), cartItems.getImage(), cartItems.getPrice(), cartItems.getQuantity());
        holder.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cartItems.quantity++;
                holder.foodQuantity.setText("" + cartItems.getQuantity());
                holder.totalPriceOfItem();
            }
        });

        holder.subtract.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cartItems.getQuantity() > 1) {
                    cartItems.quantity--;
                    holder.foodQuantity.setText("" + cartItems.getQuantity());

                } else if (cartItems.getQuantity() == 1) {
                    removeFromCart(position);

                }
                holder.totalPriceOfItem();
            }
        });


    }

    private void removeFromCart(int position) {

        ProgressDialog progress = new ProgressDialog(context);
        progress.setTitle("Item Remove");
        progress.setMessage("Wait...");
        progress.setCancelable(false);
        progress.show();

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                DocumentReference reference = FirebaseFirestore.getInstance().collection("user").document(FirebaseAuth.getInstance().getUid());

                reference.update("cartList", FieldValue.arrayRemove(list.get(position).getId()));

                foodDetail.cartList.remove(list.get(position).getId());

                list.remove(position);
                AddToCart.cAdapter.notifyDataSetChanged();

                progress.dismiss();

                Toast.makeText(context, "Item Removed Successfully", Toast.LENGTH_SHORT).show();

                if (list.size() == 0) {
                    ((Activity) context).finish();
                }
            }
        }, 500);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class cartViewAdapter extends RecyclerView.ViewHolder {
        ImageView foodImage;
        TextView foodName;
        TextView fPrice;
        ImageView add;
        ImageView subtract;
        TextView foodQuantity;

        public cartViewAdapter(@NonNull View itemView) {
            super(itemView);
            foodImage = itemView.findViewById(R.id.cFoodImage);
            foodName = itemView.findViewById(R.id.cFoodName);
            fPrice = itemView.findViewById(R.id.cOrderPrice);
            add = itemView.findViewById(R.id.add);
            foodQuantity = itemView.findViewById(R.id.cQuantity);
            subtract = itemView.findViewById(R.id.subtract);

        }

        public void setCartDetails(String food_name, String image, String price, long quantity) {
            foodName.setText(food_name);
            Glide.with(context).load(image).into(foodImage);
            fPrice.setText(price);
            foodQuantity.setText(String.valueOf(quantity));
            totalPriceOfItem();
        }


        public  void totalPriceOfItem() {
            totalPrice = 0;
            for (int i = 0; i < list.size(); i++) {
                totalPrice += (list.get(i).getQuantity()) * (Long.parseLong(list.get(i).getPrice()));
            }
            AddToCart.totalPrice.setText("₹" + String.valueOf(totalPrice));
        }
    }
}
