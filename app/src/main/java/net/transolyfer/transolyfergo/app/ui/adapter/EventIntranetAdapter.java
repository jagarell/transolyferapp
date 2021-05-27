package net.transolyfer.transolyfergo.app.ui.adapter;

import android.content.Context;
import android.graphics.Color;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import net.transolyfer.transolyfergo.R;
import net.transolyfer.transolyfergo.app.ui.components.CustomTextView;
import net.transolyfer.transolyfergo.app.utils.ImageUtils;
import net.transolyfer.transolyfergo.domain.model.EventIntranet;
import net.transolyfer.transolyfergo.presenter.view.ReportIntranetView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EventIntranetAdapter extends RecyclerView.Adapter<EventIntranetAdapter.ViewHolder> {

    private List<EventIntranet> eventIntranetList;
    private ReportIntranetView onView;
    private Context context;

    public EventIntranetAdapter(List<EventIntranet> eventIntranetList, ReportIntranetView onView, Context context) {
        this.onView = onView;
        this.eventIntranetList = eventIntranetList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_report, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        EventIntranet eventIntranet = getItem(position);
        if (eventIntranet != null) {
            holder.tvEvent.setText(eventIntranet.getEventOrder());
            holder.tvDate.setText(eventIntranet.getDateEventOrder());
            holder.ivIconEvent.setImageResource(getImageId(context,eventIntranet.getIconImage()));
            ImageUtils.setTintAdapter(holder.ivIconEvent, Color.parseColor(eventIntranet.getColorImage()),context);
            if(!eventIntranet.getImageUrl().equals("")){
                holder.tvPhoto.setVisibility(View.VISIBLE);
                holder.tvPhoto.setOnClickListener(view -> onView.showPhoto(eventIntranet.getImageUrl()));
            }else{
                holder.tvPhoto.setVisibility(View.GONE);
            }
            if(!eventIntranet.getImageUrl2().equals("")){
                holder.tvPhotoRef.setVisibility(View.VISIBLE);
                holder.tvPhotoRef.setOnClickListener(view -> onView.showPhoto(eventIntranet.getImageUrl2()));
            }else{
                holder.tvPhotoRef.setVisibility(View.GONE);
            }
        }
    }

    private static int getImageId(Context context, String imageName) {
        return context.getResources().getIdentifier("drawable/" + imageName, null, context.getPackageName());
    }

    private EventIntranet getItem(int position){
        return eventIntranetList.get(position);
    }

    @Override
    public int getItemCount() {
        return eventIntranetList.size();
    }

    class ViewHolder extends  RecyclerView.ViewHolder{

        @BindView(R.id.tvEvent) CustomTextView tvEvent;
        @BindView(R.id.tvDate) CustomTextView tvDate;
        @BindView(R.id.tvPhoto) CustomTextView tvPhoto;
        @BindView(R.id.tvPhotoRef) CustomTextView tvPhotoRef;
        @BindView(R.id.ivIconEvent)
        ImageView ivIconEvent;

        public ViewHolder(View headerView) {
            super(headerView);
            ButterKnife.bind(this, headerView);
        }
    }
}
