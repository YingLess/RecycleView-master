package com.dabin.www.recycleview01;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;

import adpter.HomeAdapter;
import bean.NewBase;
import custom.DividerItemDecoration;
import okhttp3.Call;
import utils.GsonObjectCallback;
import utils.NetWorkUtils;
import utils.OkHttp3Utils;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    HomeAdapter homeAdapter;
    List<NewBase.DataBean> data;
    private String path = "http://api.expoon.com/AppNews/getNewsList/type/1/p/1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //判断网络状态
        boolean netWorkAvailable = NetWorkUtils.isNetWorkAvailable(this);
        if (!netWorkAvailable) {
            Toast.makeText(MainActivity.this, "联网：" + netWorkAvailable, Toast.LENGTH_SHORT).show();
        }
        //okhttp请求数据
        OkHttp3Utils.getInstance().doGet(path, new GsonObjectCallback<NewBase>() {
            @Override
            public void onUi(NewBase newBase) {
                data = newBase.getData();
                homeAdapter = new HomeAdapter(MainActivity.this, data);   //适配器 一定要写在刚得到数据之后
                mRecyclerView.setAdapter(homeAdapter);  // 设置适配器
                //设置点击事件 (接口回调)
                homeAdapter.setOnItemClickLitener(new HomeAdapter.OnItemClickLitener() {
                    @Override
                    //点击吐司
                    public void onItemClick(View view, int position) {
                        Toast.makeText(MainActivity.this, position + " click",
                                Toast.LENGTH_SHORT).show();
                    }

                    //长按吐司删除
                    @Override
                    public void onItemLongClick(View view, int position) {
                        Toast.makeText(MainActivity.this, position + " long click",
                                Toast.LENGTH_SHORT).show();
                        homeAdapter.removeData(position);
                    }
                });
            }
            @Override
            public void onFailed(Call call, IOException e) {

            }
        });

        mRecyclerView = (RecyclerView) findViewById(R.id.id_recyclerview);

        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(4,StaggeredGridLayoutManager.HORIZONTAL));//设置StaggeredGridLayoutManager横向布局管理器(修改item布局就可以控制大小)
        //mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL)); // 设置默认分割线
        //mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.HORIZONTAL_LIST)); //自定义横向分割线

        // 设置item动画
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    //添加
    public void add(View v) {
        homeAdapter.addData(1);
    }

    //删除
    public void delete(View v) {
        homeAdapter.removeData(1);
    }

    //线性布局
    public void Linears(View v) {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this)); //设置LinearLayoutManager布局管理器
    }
    //网格布局
    public void Grids(View v) {
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 4));//设置GridLayoutManager布局管理器
    }
    //瀑布横向布局
    public void Staggeredx(View v) {
        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(4,StaggeredGridLayoutManager.HORIZONTAL));//设置StaggeredGridLayoutManager纵向布局管理器
    }
    //瀑布纵向布局
    public void Staggeredy(View v) {
        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(4,StaggeredGridLayoutManager.VERTICAL));//设置StaggeredGridLayoutManager纵向布局管理器
    }
    //横向分割线
    public void hang(View v) {
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.HORIZONTAL_LIST)); //自定义纵向分割线
    }
    //纵向分割线
    public void zong(View v) {
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST)); //自定义纵向分割线

    }
    //总分割线
    public void zongall(View v) {
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST)); //自定义纵向分割线
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.HORIZONTAL_LIST)); //自定义纵向分割线

    }

}
