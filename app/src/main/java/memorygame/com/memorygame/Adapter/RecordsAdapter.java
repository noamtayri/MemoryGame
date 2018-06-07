package memorygame.com.memorygame.Adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import memorygame.com.memorygame.Model.Record;
import memorygame.com.memorygame.R;

class RecordViewHolder extends RecyclerView.ViewHolder {

    public TextView title, name, address;
    protected double latitude, longitude;

    public RecordViewHolder(View itemView) {
        super(itemView);
        title = (TextView) itemView.findViewById(R.id.item_title);
        name = (TextView) itemView.findViewById(R.id.item_name);
        address = (TextView) itemView.findViewById(R.id.item_address);
    }

    /*
    @Override
    public void onClick(View v) {
        Intent intent = new Intent(context, FullscreenActivity.class);
        intent.putExtra(Constants.EXTRA_RECIPE_NAME, recipeName);
        intent.putExtra(Constants.EXTRA_FILE_NAME, fileName);
        context.startActivity(intent);
    }
    */
}


public class RecordsAdapter extends RecyclerView.Adapter<RecordViewHolder> {

    private List<Record> records;

    public RecordsAdapter(List<Record> recipes) {
        this.records = recipes;
    }

    @NonNull
    @Override
    public RecordViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.layout_item, parent, false);
        return new RecordViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecordViewHolder holder, int position) {
        holder.title.setText(
                records.get(position).getLevel()
                .concat(":  ")
                .concat(String.valueOf(records.get(position).getRecordPoints())));
        holder.name.setText(records.get(position).getName());
        holder.address.setText(records.get(position).getAddress());
        holder.latitude = records.get(position).getLocation().latitude;
        holder.longitude = records.get(position).getLocation().longitude;
    }

    @Override
    public int getItemCount() {
        return records.size();
    }
}
