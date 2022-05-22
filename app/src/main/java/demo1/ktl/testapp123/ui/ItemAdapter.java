package demo1.ktl.testapp123.ui;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.thuchanh2.R;
import demo1.ktl.testapp123.data.model.Item;

import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder> {

    Context context;
    List<Item> itemList;

    public ItemAdapter(Context context, List<Item> itemList) {
        this.context = context;
        this.itemList = itemList;
    }

    public List<Item> getItemList() {
        return itemList;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        Item item = itemList.get(position);
        holder.tvName.setText("Title: " + item.getName());
        holder.tvDate.setText("Deadline: " + item.getDate());
        holder.tvStatus.setText("Status: " + item.getStatus());
//        holder.tvIsCoop.setText(item.isCoop() ? " - Đã kích hoạt" : " - Chưa kích hoạt");

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, EditActivity.class);
                i.putExtra("item", item);
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder{
        TextView tvName, tvDate, tvStatus, tvIsCoop;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_item_name);
            tvStatus = itemView.findViewById(R.id.tv_item_status);
            tvDate = itemView.findViewById(R.id.tv_item_date);
        }
    }
}


