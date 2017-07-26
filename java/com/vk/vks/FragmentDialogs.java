package com.vk.vks;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.vk.sdk.api.VKApi;
import com.vk.sdk.api.VKApiConst;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;
import com.vk.sdk.api.model.VKApiDialog;
import com.vk.sdk.api.model.VKApiGetDialogResponse;
import com.vk.sdk.api.model.VKList;

import java.util.ArrayList;

public class FragmentDialogs extends Fragment
{
    private ListView m_listDialogs;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_dialogs, null);
    }

    public void updateDialogs()
    {
        final VKRequest request = VKApi.messages().getDialogs(VKParameters.from(VKApiConst.COUNT, 10));
        request.executeWithListener(new VKRequest.VKRequestListener()
        {
            @Override
            public void onComplete(VKResponse response)
            {
                super.onComplete(response);
                VKApiGetDialogResponse getDialogResponse = (VKApiGetDialogResponse) response.parsedModel;
                final VKList <VKApiDialog> dialogs = getDialogResponse.items;

                /*
                ArrayList <String> messageList = new ArrayList<String>();
                ArrayList <String> userList = new ArrayList<String>();
                for(VKApiDialog msg : dialogs){
                    userList.add(String.valueOf(msg.message.user_id));
                    messageList.add(msg.message.body);
                }
                */

                m_listDialogs = (ListView) getView().findViewById(R.id.listDialogs);
                m_listDialogs.setAdapter(new DiaMsgAdapter(getActivity(), dialogs));
            }
        });
    }
}
