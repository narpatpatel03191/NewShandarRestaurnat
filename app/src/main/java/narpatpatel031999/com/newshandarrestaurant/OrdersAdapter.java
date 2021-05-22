package narpatpatel031999.com.newshandarrestaurant;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class OrdersAdapter extends RecyclerView.Adapter<OrdersAdapter.ordersAdapter> {

    List<Orders> list;
    Context context;

    public OrdersAdapter(List<Orders> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public OrdersAdapter.ordersAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.orders,parent,false);
        return new ordersAdapter(view);

    }

    @Override
    public void onBindViewHolder(@NonNull OrdersAdapter.ordersAdapter holder, int position) {

        Orders current=list.get(position);
        holder.setDetails(current.getAddress(),current.getDateAndTime(),current.getAllOrders(),current.getTotalPrice());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ordersAdapter extends RecyclerView.ViewHolder {

        TextView mAddress,mDateAndTime,mAllOrders,mTotalPrice;
        public ordersAdapter(@NonNull View itemView) {
            super(itemView);

            mAddress=itemView.findViewById(R.id.customerAddress);
            mDateAndTime=itemView.findViewById(R.id.timeStamp);
            mAllOrders=itemView.findViewById(R.id.mAllOrders);
            mTotalPrice=itemView.findViewById(R.id.mTotalPrice);

        }

        public void setDetails(String address, String dateAndTime, String allOrders, String totalPrice) {


            mAddress.setText("MyAddress: "+address);
            mDateAndTime.setText("DateAndTime: "+dateAndTime);
            mAllOrders.setText("Order: "+allOrders);
            mTotalPrice.setText("TotalPrice: "+totalPrice);

        }
    }
}
