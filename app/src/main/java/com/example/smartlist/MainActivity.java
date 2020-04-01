package com.example.smartlist;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class MainActivity extends AppCompatActivity implements IEvaluable {

    private ViewPager2 viewPager2;
    private TabLayout tabLayout;
    private OrderPageAdapter orderPageAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        getUpView();
    }

    private void getUpView(){
        viewPager2 = findViewById(R.id.viewPage);
        tabLayout = findViewById(R.id.tabLayout);
        orderPageAdapter = new OrderPageAdapter(this);
        viewPager2.setAdapter(orderPageAdapter);
        TabLayoutMediator tabLayoutMediator = new TabLayoutMediator(
                tabLayout, viewPager2, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                switch (position){
                    case 0: {
                        tab.setText("Lista");
                        tab.setIcon(R.drawable.list);

                        BadgeDrawable badgeDrawable = tab.getOrCreateBadge();

                        badgeDrawable.setBackgroundColor(
                                ContextCompat.getColor(getApplicationContext(), R.color.orange)
                        );
                        badgeDrawable.setNumber(3);
                        badgeDrawable.setVisible(true);
                        break;
                    }
                    case 1: {
                        tab.setText("Lista QR");
                        tab.setIcon(R.drawable.qrcode);
                        break;
                    }
                        default: {
                            tab.setText("Historial");
                            tab.setIcon(R.drawable.list);
                            break;
                        }
                }
            }
        });
        tabLayoutMediator.attach();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        for (Fragment fragment : getSupportFragmentManager().getFragments()) {
            fragment.onActivityResult(requestCode, resultCode, data);
        }
    }
}
