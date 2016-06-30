package in.vaksys.takeorder.adapters;

/**
 * Created by dell980 on 6/18/2016.
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import in.vaksys.takeorder.R;


public class NavigationDrawerAdapter extends RecyclerView.Adapter<NavigationDrawerAdapter.MyViewHolder> {
    //List<NavDrawerItem> data = Collections.emptyList();
    private LayoutInflater inflater;
    private Context context;
    private String[] title;
    private int[] image;

    public NavigationDrawerAdapter(Context context, String[] title, int[] image) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        //this.data = data;
        this.title = title;
        this.image = image;
    }

    /*public void delete(int position) {
        data.remove(position);
        notifyItemRemoved(position);
    }*/

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.nav_drawer_row, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        holder.title.setText(title[position]);
        holder.image.setBackgroundResource(image[position]);

        if (position == 0) {
            holder.viewId.setVisibility(View.GONE);
        }

        if (position == 2) {
            holder.viewId.setVisibility(View.GONE);
        }

        if (position == 1) {
            holder.ll.setPadding(50, 0, 0, 0);
            holder.title.setTextSize(12);
        }

        if (position == 3) {
            holder.ll.setPadding(50, 0, 0, 0);
            holder.title.setTextSize(12);
        }

    }

    @Override
    public int getItemCount() {
        return title.length;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        ImageView image;
        View ll, viewId;

        public MyViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.titleNav);
            image = (ImageView) itemView.findViewById(R.id.imageNav);
            ll = itemView.findViewById(R.id.ll);
            viewId = itemView.findViewById(R.id.viewId);
        }
    }
}