package com.mifos.mifosxdroid.fragments;

/**
 * Created by conflux37 on 10/22/2015.
 */
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.mifos.mifosxdroid.R;
import com.mifos.mifosxdroid.adapters.MenuListAdapter;
import com.mifos.mifosxdroid.online.CentersActivity;
import com.mifos.mifosxdroid.online.ClientListFragment;
import com.mifos.mifosxdroid.online.CreateCenterFragment;
import com.mifos.mifosxdroid.online.CreateNewClientFragment;
import com.mifos.utils.FragmentConstants;


public class GetMenuListFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    View rootView;
    private RecyclerView mRecyclerView;
    private StaggeredGridLayoutManager mStaggeredLayoutManager;
    private MenuListAdapter mAdapter;
    private boolean isListView;

    Fragment newFragment;


    // TODO: Rename and change types and number of parameters
    public GetMenuListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView= inflater.inflate(R.layout.fragment_get_menu_recyclerview, null);


        System.out.println("rootview "+rootView);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.list);
        mStaggeredLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mStaggeredLayoutManager);
        mRecyclerView.setHasFixedSize(false);

        //Data size is fixed - improves performance
        mAdapter = new MenuListAdapter(getActivity().getApplicationContext());
        System.out.println("get Fragment base context"+getActivity().getApplicationContext());
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(onItemClickListener);

        isListView = true;


        return rootView;
    }


    MenuListAdapter.OnItemClickListener onItemClickListener= new MenuListAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(View view, int position) {
            Toast.makeText(getActivity(), "position " + position, Toast.LENGTH_LONG).show();
            String TAG;
            switch (position)
            {
                case 0:popStacEntryFragments();
                    newFragment= new CreateNewClientFragment();
                    TAG=FragmentConstants.FRAG_CREATE_NEW_CLIENT;
                    startNewFragment(newFragment,TAG);
                    break;
                case 1: popStacEntryFragments();
                    /*newFragment = new ClientListFragment();
                    TAG=FragmentConstants.FRAG_CLIENT_SEARCH;*/
                    newFragment=new CreateCenterFragment();
                    TAG=FragmentConstants.FRAG_CREATE_CENTER;
                    startNewFragment(newFragment,TAG);
                    break;
                case 3: popStacEntryFragments();
                    startActivity(new Intent(getActivity(), CentersActivity.class));
                    break;
                default:break;

            }

        }
    };


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed() {

    }
    public void popStacEntryFragments()
    {
        if(getActivity().getSupportFragmentManager().getBackStackEntryCount()!=0)
        {
            getActivity().getSupportFragmentManager().popBackStackImmediate();
        }
    }
public void startNewFragment(Fragment fragment,String TAG)
{
    FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
    fragmentTransaction.addToBackStack(TAG);
    fragmentTransaction.replace(R.id.dashboard_global_container, fragment,TAG);
    fragmentTransaction.commit();
}

    @Override
    public void onDetach() {
        super.onDetach();

    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction();
    }

}