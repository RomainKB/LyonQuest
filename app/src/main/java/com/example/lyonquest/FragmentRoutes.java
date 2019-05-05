package com.example.lyonquest;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class FragmentRoutes extends Fragment implements View.OnClickListener {

    private List<Route> mRoutes = new ArrayList<>();

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
        final View view = inflater.inflate(R.layout.activity_fragment_routes, container, false);


        // TODO : Delete this function call when we receive information from the server
       routesList();
        construction(view);

       /* RequestQueue queue = Volley.newRequestQueue(getContext());
        //TODO : Penser à rentrer le bon url
        String url = getString(R.string.db_routes_list);
        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                JSONArray array = new JSONArray(response.getString("routes"));
                    for (int i = 0; i < array.length(); i++) {
                        // On récupère un objet JSON du tableau
                        JSONObject obj = new JSONObject(array.getString(i));
                        Route route = new Route(obj.getInt("route_id"),obj.getString("title"),obj.getString("description"),0,0,0,0);
                        mRoutes.add(route);
                    }
                    construction(view);
                }catch(JSONException e){e.printStackTrace();}
            }
            }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("ERROR : "+error);
            }
        });
        queue.add(getRequest);*/

        return view;
    }

    @Override
    public void onClick(View v) {
        // Get back the tag button to know which route the user selected
        int responseIndex = (int) v.getTag();

        Intent intent = new Intent(getActivity(), RouteDetail.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(getString(R.string.route),mRoutes.get(responseIndex));
        intent.putExtras(bundle);
        startActivity(intent);
    }
    /* TODO : Méthode à remplacer par les infos venant du serveur */
    private void routesList(){
         Route r1 = new Route(1,"Parcours historique", "Petit parcours qui vous fera visiter les principaux lieux historique de la ville.",4,120, 10, 0);
         Route r2 = new Route(2,"Visite vieux lyon", "Vous aimez les petites histoires ? Vous avez toujours eu envi de traverser les traboules ? Ce parcours est fait pour vous ! ", 5,80,125, 12);
         Route r3 = new Route (3,"Hardcore","Vous aimez le challenge et courrir ? Go ! ",  2, 35, 3, 121);
         mRoutes.add(r1);
         mRoutes.add(r2);
         mRoutes.add(r3);
    }

    private void construction(View view){
        /*Get back the layout*/
        LinearLayout monLayout = (LinearLayout) view.findViewById(R.id.activity_display_routes_layoutOfDynamicContent);

        // From here add 1 by 1 all buttons on the layer
        int i=0;
        for ( i = 0; i<mRoutes.size(); i++){

            Button btn = new Button(getContext());
            btn.setText(mRoutes.get(i).getmName());
            btn.setTag(i);
            btn.setOnClickListener(this);
            btn.setHeight(120);
            btn.setTextSize(20);
            monLayout.addView(btn, 0);

            //Just to create a space between buttons
            TextView espace = new TextView(getContext());
            espace.setHeight(20);
            monLayout.addView(espace,0);
        }
    }
}
