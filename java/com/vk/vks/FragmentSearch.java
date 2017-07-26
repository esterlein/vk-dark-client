package com.vk.vks;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.vk.sdk.api.VKApi;
import com.vk.sdk.api.VKApiConst;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;
import com.vk.sdk.api.model.VKApiUserFull;
import com.vk.sdk.api.model.VKList;

public class FragmentSearch extends Fragment
{
    private EditText searchText;
    private Button searchBtn;
    private ListView listSearch;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_search, null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        searchText = (EditText) getView().findViewById(R.id.searchText);
        searchBtn = (Button) getView().findViewById(R.id.searchBtn);

        searchBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                VKRequest request = VKApi.users().search(VKParameters.from(VKApiConst.Q, searchText.getText().toString()));
                request.executeWithListener(new VKRequest.VKRequestListener()
                {
                    @Override
                    public void onComplete(VKResponse response)
                    {
                        super.onComplete(response);
                        VKList<VKApiUserFull> users = (VKList<VKApiUserFull>) response.parsedModel;

                        listSearch = (ListView) getView().findViewById(R.id.listSearch);
                        listSearch.setAdapter(new SearchAdapter(getActivity(), users));
                    }
                });
            }
        });
    }
}
