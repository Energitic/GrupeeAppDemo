package adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dipali.grupeedemoapp.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import database.DatabaseHandler;
import model.DogDetails;

public class DogDetailsAdapter extends RecyclerView.Adapter<DogDetailsAdapter.MyViewHolder> {

    private Context context;
    private ArrayList<DogDetails> arrayList;
    private DatabaseHandler db;

    public DogDetailsAdapter(Context context, ArrayList<DogDetails> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.raw_profile_list, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        db = new DatabaseHandler(context);

        //Get DogDetails Data
        arrayList = db.getDogDetails();

        Picasso.with(context).load(arrayList.get(position).getDog_img()).into((ImageView) holder.img_dog);
        holder.tv_date.setText(arrayList.get(position).getDate());
        holder.tv_time.setText(arrayList.get(position).getTime());

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView img_dog;
        private TextView tv_date, tv_time;

        public MyViewHolder(View view) {
            super(view);
            img_dog = (ImageView) view.findViewById(R.id.img_dog);
            tv_date = (TextView) view.findViewById(R.id.tv_date);
            tv_time = (TextView) view.findViewById(R.id.tv_time);
        }
    }
}
