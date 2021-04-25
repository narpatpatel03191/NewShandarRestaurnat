package narpatpatel031999.com.newshandarrestaurant;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class  ViewAdapter extends RecyclerView.Adapter<ViewAdapter.viewAdapter> {
    List<fooditem>list;
    Context context;

    public  ViewAdapter(List<fooditem>list,Context context){
        this.list=list;
        this.context=context;
    }
    @NonNull
    @Override
    public viewAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.food,parent,false);

        return new viewAdapter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewAdapter holder, int position) {

        fooditem current=list.get(position);
        holder.setDetails(current.getFood_Name(),current.getImage());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewAdapter extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView;

        public viewAdapter(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.foodimage);
            textView=itemView.findViewById(R.id.foodname);
        }
        public void setDetails(String name,String image){

            textView.setText(name);
            Glide.with(context).load(image).into(imageView);
        }
    }
}
