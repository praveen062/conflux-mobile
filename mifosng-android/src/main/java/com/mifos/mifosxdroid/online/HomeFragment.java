/*
 * This project is licensed under the open source MPL V2.
 * See https://github.com/openMF/android-client/blob/master/LICENSE.md
 */

package com.mifos.mifosxdroid.online;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.mifos.mifosxdroid.R;
import com.mifos.mifosxdroid.fragments.GetMenuListFragment;
import com.mifos.objects.MenuListData;
import com.mifos.objects.SearchedEntity;
import com.mifos.utils.Constants;
import com.mifos.utils.FragmentConstants;
import com.mifos.utils.MifosApplication;
import com.mifos.utils.SafeUIBlockingUtility;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import butterknife.OnEditorAction;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class HomeFragment extends Fragment {

    private static final String TAG = "Client Search Fragment";

    @InjectView(R.id.et_search_by_id)
    EditText et_searchById;
    @InjectView(R.id.bt_searchClient)
    ImageButton btn_searchClient;

    View rootView;


    ClientSearchFragment clientSearchFragment;
    //Search the Client
    public String getSearchQuery() {
        return searchQuery;
    }


    public void setSearchQuery(String searchQuery) {
        this.searchQuery = searchQuery;
    }

    private String searchQuery;

    InputMethodManager inputMethodManager;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //to disable the keyboard popup on view of an fragment
        inputMethodManager = (InputMethodManager) getActivity().getSystemService(
                Context.INPUT_METHOD_SERVICE);
        getActivity().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        clientSearchFragment = new ClientSearchFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_home, null);
       ButterKnife.inject(this, rootView);
        loadmenufragment();

        et_searchById.setText("");
        return rootView;
    }



    public void loadmenufragment()
    {
        //Import the the Home Screen Menu

        String[] strings=getResources().getStringArray(R.array.menu_list);

        MenuListData.setMenuListArray(strings);
        GetMenuListFragment getFragment = new GetMenuListFragment();
        FragmentTransaction fragmentTransaction=getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.home_dashboard_container,getFragment,"list fragment");
        fragmentTransaction.commit();
    }

    @OnClick(R.id.bt_searchClient)
    public void SearchClient()
    {

        String clientId=et_searchById.getText().toString().trim();
        clientSearchFragment.setSearchQuery(clientId);
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.addToBackStack(FragmentConstants.FRAG_CLIENT_SEARCH);
        fragmentTransaction.replace(R.id.home_dashboard_container, clientSearchFragment, FragmentConstants.FRAG_CLIENT_SEARCH);
        fragmentTransaction.commit();
    }



    @Override
    public void onResume() {
        super.onResume();
        et_searchById.setText("");
        Toast.makeText(getActivity(),"on Resume",Toast.LENGTH_LONG).show();
    }

    @Override
    public void onPause() {

        //Fragment getting detached, keyboard if open must be hidden
       /* hideKeyboard();*/

        super.onPause();
        Toast.makeText(getActivity(),"on Pause",Toast.LENGTH_LONG).show();
    }

    /*
        There is a need for this method in the following cases :
        1. If user entered a search query and went out of the app.
        2. If user entered a search query and got some search results and went out of the app.
     */

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        try{
            String queryString = et_searchById.getEditableText().toString();

            if(queryString != null && !(queryString.equals(""))) {
                outState.putString(TAG+et_searchById.getId(),queryString);
            }

        }catch(NullPointerException npe){
            //Looks like edit text didn't get initialized properly
        }

    }

    @Override
    public void onDetach() {

        super.onDetach();

    }

    @Override
    public void onViewStateRestored(Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if (savedInstanceState != null) {

            String queryString = savedInstanceState.getString(TAG+et_searchById.getId());

            if (queryString != null && !(queryString.equals(""))) {
                et_searchById.setText(queryString);

            }
        }


    }

}
