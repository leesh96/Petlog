package swpj.petlog.petlog2;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MypetAdapter extends RecyclerView.Adapter<MypetAdapter.CustomViewHolder> {
    private ArrayList<MypetData> mList = null;
    private Activity context = null;

    public interface OnItemClickListener {
        void onItemClick(View v, int pos);
    }

    private OnItemClickListener mListener = null;

    public void setOnItemClickListener (OnItemClickListener listener) {
        this.mListener = listener;
    }

    public MypetAdapter(Activity context, ArrayList<MypetData> list) {
        this.context = context;
        this.mList = list;
    }

    class CustomViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageViewFace;
        private TextView textViewName, textViewSpecie;

        public CustomViewHolder(View view) {
            super(view);
            this.imageViewFace = (ImageView) view.findViewById(R.id.item_face);
            this.textViewName = (TextView) view.findViewById(R.id.item_name);
            this.textViewSpecie= (TextView) view.findViewById(R.id.item_specie);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION) {
                        if (mListener != null) mListener.onItemClick(v, pos);
                    }
                }
            });
        }
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.mypet_list, null);
        CustomViewHolder viewHolder = new CustomViewHolder(view);

        return viewHolder;
    }

    public void onBindViewHolder(@NonNull CustomViewHolder viewHolder, int position) {
        viewHolder.imageViewFace.setImageResource(R.drawable.ic_gallary);
        viewHolder.textViewName.setText(mList.get(position).getMember_name());
        viewHolder.textViewSpecie.setText(mList.get(position).getMember_specie());
    }

    @Override
    public int getItemCount() {
        return (null != mList ? mList.size() : 0);
    }
}
