package com.example.lyonquest;


import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FragmentRoutes extends Fragment {

    private List<Route> mRoutes = new ArrayList<>();;


    public static FragmentRoutes newInstance() {
        FragmentRoutes fragment = new FragmentRoutes();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_fragment_routes, container, false);

        routesList();


        /*récupérer le layout*/
        LinearLayout monLayout = (LinearLayout) view.findViewById(R.id.activity_display_routes_layoutOfDynamicContent);
        /*rajouter le textView au layout*/

        // A partir d'ici on va faire une boucle qui permettra d'afficher les parcours 1 par 1.
        int i=0;
        for ( i = 0; i<mRoutes.size(); i++){

                Button btn = new Button(getContext());
                btn.setText(mRoutes.get(i).getmName());

                btn.setOnClickListener(new View.OnClickListener()
                {
                   @Override
                   public void onClick(View v)
                   {
                       // do something
                  }
                 });
                btn.setHeight(120);
                btn.setTextSize(20);
                monLayout.addView(btn, 0);

                //Pour faire un espace entre les boutons
                TextView espace = new TextView(getContext());
                espace.setHeight(20);
                monLayout.addView(espace,0);
        }


        return view;
    }
/* TODO : Méthode à remplacer par les infos venant du serveur */
    public void routesList(){
        System.out.println("Je suis 1");
         Route r1 = new Route("Parcours historique", "Petit parcours qui vous fera visiter les principaux lieux historique de la ville.","INSA de Lyon",4,120);
         Route r2 = new Route("Visite vieux lyon", "Vous aimez les petites histoires ? Vous avez toujours eu envi de traverser les traboules ? Ce parcours est fait pour vous ! ", "Place Bellecour",5,80);
         Route r3 = new Route ("Hardcore","Vous aimez le challenge et courrir ? Go ! ", "Tête d'Or", 2, 35);
        System.out.println("Je suis 2");
         mRoutes.add(r1);
         mRoutes.add(r2);
         mRoutes.add(r3);

        System.out.println("Je suis 3");
    }
}
