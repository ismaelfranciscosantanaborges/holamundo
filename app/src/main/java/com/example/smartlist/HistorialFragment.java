package com.example.smartlist;


import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class HistorialFragment extends Fragment {

    private View rootView;
    private ImageView imgPerfil;
    private RatingBar ratingBar;
    private TextView tvCalificacion;

    public HistorialFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_historial, container, false);

        imgPerfil = rootView.findViewById(R.id.imgPerfil);
        ratingBar = rootView.findViewById(R.id.ratingBar);
        tvCalificacion = rootView.findViewById(R.id.calificacion);

        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {

            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                if(rating >= 1 && rating < 2)
                    tvCalificacion.setText("Estoy seguro que te equivocaste");
                if(rating >= 2 && rating < 3)
                    tvCalificacion.setText("Venga, no me la pongas difícil");
                if(rating >= 3 && rating < 4)
                    tvCalificacion.setText("Buena Aplicacion");
                if(rating >= 4 && rating < 5)
                    tvCalificacion.setText("Excelente Aplicacion");
                if(rating == 5)
                    tvCalificacion.setText("El final, mejor que Facebook");
            }

        });

        Drawable drawable = getResources().getDrawable(R.drawable.mifoto);
        Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();

        //CREAMOS EL DRAWABLE REDONDEADO
        RoundedBitmapDrawable roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(getResources(), bitmap);
        //ASIGNAMOS EL CORDER RADIUS
        roundedBitmapDrawable.setCornerRadius(bitmap.getHeight());



        imgPerfil.setImageDrawable(roundedBitmapDrawable);


        return rootView;
    }

}
