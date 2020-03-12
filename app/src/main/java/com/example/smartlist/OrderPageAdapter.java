package com.example.smartlist;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class OrderPageAdapter extends FragmentStateAdapter {
    public OrderPageAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 1:
                return new Scanner_ListaDelTotal();
            case 2:
                return new HistorialFragment();
            default:
                return new ListaDeCompra();
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
