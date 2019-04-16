package adpter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dabin.www.recycleview01.R;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

import bean.NewBase;

/**
 * Created by Dabin on 2017/5/13.
 */

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.MyViewHolder> {
    private Context context;
    private  List<NewBase.DataBean> mDatas;

    private OnItemClickLitener mOnItemClickLitener;  // 监听



    //定义接口
    public interface OnItemClickLitener {
        void onItemClick(View view, int position);

        void onItemLongClick(View view, int position);
    }


    public void setOnItemClickLitener(OnItemClickLitener mOnItemClickLitener) {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }
    //构造方法
    public HomeAdapter(Context context, List<NewBase.DataBean> mDatas) {
        this.context = context;
        this.mDatas = mDatas;
    }

    // 实例化 Holder
    @Override
    public HomeAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        MyViewHolder holder = new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item, parent, false));
        return holder;
    }

    //绑定holder  在onBindHolder中 设置数据
    @Override
    public void onBindViewHolder(final HomeAdapter.MyViewHolder holder, int position) {
        ImageLoader instance = ImageLoader.getInstance();

        holder.tv.setText(mDatas.get(position).getNews_title().toString());
        instance.displayImage(mDatas.get(position).getPic_url(),holder.imageView);
        // 如果设置了回调，则设置点击事件
        if (mOnItemClickLitener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = holder.getLayoutPosition();
                    mOnItemClickLitener.onItemClick(holder.itemView, pos);
                }
            });

            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int pos = holder.getLayoutPosition();
                    mOnItemClickLitener.onItemLongClick(holder.itemView, pos);
                    return false;
                }
            });
        }
    }

    // 总条数
    @Override
    public int getItemCount() {
        return mDatas.size();
    }


    // 在 holder中 找到控件
    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv;
        ImageView imageView;
        public MyViewHolder(View view) {
            super(view);
            tv = view.findViewById(R.id.id_num);
            imageView = view.findViewById(R.id.images);
        }
    }

    //添加更新数据
    public void addData(int position) {
        //mDatas.add(position, "Dabin");
        notifyItemInserted(position);
    }

    //删除更新数据
    public void removeData(int position) {
        //mDatas.remove(position);
        notifyItemRemoved(position);
    }
}
