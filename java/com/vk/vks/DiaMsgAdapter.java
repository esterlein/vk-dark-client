package com.vk.vks;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.vk.sdk.api.model.VKApiDialog;
import com.vk.sdk.api.model.VKApiMessage;
import com.vk.sdk.api.model.VKList;

public class DiaMsgAdapter extends BaseAdapter
{
    private FragmentTransaction fTrans;

    private Context m_context;
    private VKList<VKApiDialog> m_dialogs;
    private VKApiMessage[] m_messages;

    public DiaMsgAdapter(Context context, VKList<VKApiDialog> dialogs)
    {
        this.m_context = context;
        this.m_dialogs = dialogs;
    }

    public DiaMsgAdapter(Context m_context, VKApiMessage[] messages)
    {
        this.m_context = m_context;
        this.m_messages = messages;
    }

    @Override
    public int getCount()
    {
        if(m_dialogs != null) {
            return m_dialogs.size();
        }
        else{
            return m_messages.length;
        }
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
        SetData setData = new SetData();
        LayoutInflater inflater = (LayoutInflater) m_context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.diamsg_item, null);

        setData.username = (TextView) view.findViewById(R.id.username);
        setData.message = (TextView) view.findViewById(R.id.message);

        if(m_dialogs == null){

            setData.username.setText(String.valueOf(m_messages[position].user_id));
            setData.message.setText(m_messages[position].body);

        }
        else{

            setData.username.setText(String.valueOf(m_dialogs.get(position).message.user_id));
            setData.message.setText(m_dialogs.get(position).message.body);

            view.setOnClickListener(new View.OnClickListener()
            {

                @Override
                public void onClick(View v)
                {
                    final int id = m_dialogs.get(position).message.user_id;

                    fTrans = ((Activity) m_context).getFragmentManager().beginTransaction();
                    fTrans.replace(R.id.frameContainer, ((MainActivity) m_context).fragMessages);

                    ((MainActivity) m_context).fragMessages.updateMessages(id);

                    fTrans.addToBackStack(null);
                    fTrans.commit();
                }
            });
        }

        return view;
    }

    public class SetData
    {
        TextView username, message;
    }
}