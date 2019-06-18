package com.xiaoshulin.vipbanlv.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.xiaoshulin.vipbanlv.R;
import com.xiaoshulin.vipbanlv.adapter.MessageAdaptr;
import com.xiaoshulin.vipbanlv.base.BaseMVPFragment;
import com.xiaoshulin.vipbanlv.base.BasePresenter;
import com.xiaoshulin.vipbanlv.database.entity.MessageListEntity;
import com.xiaoshulin.vipbanlv.presenter.MessagePresenter;
import com.xiaoshulin.vipbanlv.view.IMessageView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jipeng on 2018/11/23.
 */
public class MessageFragment extends BaseMVPFragment implements IMessageView {
    private static final String TAG = "MessageFragment";
    private MessagePresenter presenter;
    private XRecyclerView mess_recycle;
    public MessageFragment() {
        // Required empty public constructor
    }

    public static MessageFragment newInstance() {
        MessageFragment fragment = new MessageFragment();
        return fragment;
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.MessageListData(getActivity());
    }

    @Override
    public BasePresenter initPresenter() {
        presenter=new MessagePresenter(this);
        return presenter;
    }

    @Override
    protected void initData() {
//         presenter.MessageListData(getActivity());
    }

    @Override
    protected void initView() {
        mess_recycle=mRootView.findViewById(R.id.mess_recycle);
        mess_recycle.setPullRefreshEnabled(false);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(getActivity());
        mess_recycle.setLayoutManager(manager);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_message_layout;
    }

    @Override
    public void getMessageListData(List<MessageListEntity> messageListEntities) {
        Log.e(TAG, "getMessageListData: "+messageListEntities.size());
        MessageAdaptr adaptr=new MessageAdaptr(getActivity(),messageListEntities);
        mess_recycle.setAdapter(adaptr);
    }
}
