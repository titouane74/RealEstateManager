package com.openclassrooms.realestatemanager.view.fragments;

import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.navigation.NavController;
import androidx.navigation.NavHost;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.databinding.FragmentReListBinding;
import com.openclassrooms.realestatemanager.view.adapters.ReListAdapter;
import com.openclassrooms.realestatemanager.viewmodel.ReListViewModel;

import java.util.ArrayList;
import java.util.List;

public class ReListFragment extends BaseFragment<FragmentReListBinding> {

    private ReListViewModel mViewModel;
    private View mFragView;
    private FragmentReListBinding mBinding;

    private ReListAdapter mAdapter;
    private Context mContext;
    private boolean mIsTablet;
    private NavController mNavController;

    @Override
    protected int getFragmentLayout() { return R.layout.fragment_re_list; }

    @Override
    protected int getMenuAttached() {return R.menu.menu_general;}

    @Override
    protected void configureDesign(FragmentReListBinding pBinding, NavController pNavController, boolean pIsTablet) {
        mBinding = pBinding;
        mFragView = mBinding.getRoot();
        mContext = mFragView.getContext();
        mIsTablet = pIsTablet;
        mNavController = pNavController;
        initRecyclerView();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(ReListViewModel.class);
        // TODO: Use the ViewModel
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem pItem) {
            return NavigationUI.onNavDestinationSelected(pItem,mNavController) || super.onOptionsItemSelected(pItem);
    }

    private void initRecyclerView() {
        mAdapter = new ReListAdapter();
        mBinding.fragReListRv.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL,false));
        mBinding.fragReListRv.setAdapter(mAdapter);
        initPhotoList();
    }
    private void initPhotoList() {
        List<String> lPhotoList = new ArrayList<>();
        lPhotoList.add("https://www.30millionsdamis.fr/uploads/pics/conseils-erreurs-chat-1171.jpg");
        lPhotoList.add("https://cdn.futura-sciences.com/buildsv6/images/wide1920/a/0/f/a0fc73919d_50166390_chaton.jpg");
        lPhotoList.add("https://www.i-cad.fr/uploads/5bec27af5afec.jpeg");
        lPhotoList.add("https://www.dogteur.com/media/magpleasure/mpblog/list_thumbnail_file/e/a/cache/5/ece9a24a761836a70934a998c163f8c8/eaf7d56dbea1bb003bb0bb649c022bab.jpg");
        lPhotoList.add("https://lemagduchat.ouest-france.fr/images/dossiers/2018-11/chat-drole-113730.jpg");
        lPhotoList.add("https://cdn-s-www.ledauphine.com/images/5FC3042C-0F6B-4D19-87CF-01591980B2D3/NW_detail_M/title-1602592555.jpg");
        lPhotoList.add("https://www.ultrapremiumdirect.com/img/cms/blog/loulou-ronron-therapie.jpg");
        mAdapter.setReList(lPhotoList);
    }
}