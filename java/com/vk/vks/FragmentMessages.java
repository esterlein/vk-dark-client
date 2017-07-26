package com.vk.vks;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.vk.sdk.api.VKApiConst;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;
import com.vk.sdk.api.model.VKApiMessage;

import org.json.JSONArray;
import org.json.JSONException;

public class FragmentMessages extends Fragment
{
    private int m_id = 0;

    private EditText text;
    private Button sendBtn;
    private ListView listMessages;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_messages, null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        text = (EditText) getView().findViewById(R.id.text);
        sendBtn = (Button) getView().findViewById(R.id.sendBtn);

        sendBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                VKRequest request = new VKRequest("messages.send", VKParameters.from(VKApiConst.USER_ID, m_id, VKApiConst.MESSAGE, text.getText().toString()));

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
    }

    public void updateMessages(int id)
    {
        m_id = id;

        VKRequest request = new VKRequest("messages.getHistory", VKParameters.from(VKApiConst.USER_ID, id));
        request.executeWithListener(new VKRequest.VKRequestListener()
        {
            @Override
            public void onComplete(VKResponse response)
            {
                super.onComplete(response);

                try{
                    JSONArray jsonarray = response.json.getJSONObject("response").getJSONArray("items");
                    VKApiMessage[] messages = new VKApiMessage[jsonarray.length()];

                    for(int i = 0; i < jsonarray.length(); ++i){
                        VKApiMessage msg = new VKApiMessage(jsonarray.getJSONObject(i));
                        messages[i] = msg;
                    }

                    listMessages = (ListView) getView().findViewById(R.id.listMessages);
                    listMessages.setAdapter(new DiaMsgAdapter(getActivity(), messages));
                }
                catch(JSONException e){
                    e.printStackTrace();
                }
            }
        });
    }
}
