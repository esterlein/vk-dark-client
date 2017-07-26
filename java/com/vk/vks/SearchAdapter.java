package com.vk.vks;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.vk.sdk.api.VKApi;
import com.vk.sdk.api.VKApiConst;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;
import com.vk.sdk.api.model.VKApiUserFull;
import com.vk.sdk.api.model.VKList;

public class SearchAdapter extends BaseAdapter
{
    private Context m_context;
    private VKList<VKApiUserFull> m_users;
    private Button addBtn;

    public SearchAdapter(Context context, VKList<VKApiUserFull> users)
    {
        this.m_context = context;
        this.m_users = users;
    }

    @Override
    public int getCount()
    {
        return m_users.size();
    }

    @Override
    public Object getItem(int position)
    {
        return position;
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent)
    {
        final SetData setData = new SetData();
        LayoutInflater inflater = (LayoutInflater) m_context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.usersearch_item, null);

        setData.username = (TextView) view.findViewById(R.id.username);
        setData.username.setText(m_users.get(position).toString());

        VKApiUserFull user = m_users.get(position);
        setData.userid = user.getId();

        addBtn = (Button) view.findViewById(R.id.addBtn);
        addBtn.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                VKRequest request = VKApi.friends().add(VKParameters.from(VKApiConst.USER_ID, setData.userid));
                request.executeWithListener(new VKRequest.VKRequestListener()
                {
                    @Override
                    public void onComplete(VKResponse response)
                    {
                        super.onComplete(response);
                    }
                });
            }
        });

        return view;
    }

    public class SetData
    {
        TextView username;
        int userid;
    }
}
