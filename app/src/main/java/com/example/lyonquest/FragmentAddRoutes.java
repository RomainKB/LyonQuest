package com.example.lyonquest;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class FragmentAddRoutes extends Fragment implements View.OnClickListener{

    private Route route;
    private EditText editTextName;
    private EditText editTextDescription;
    private Button btnRouteCreation;

    public static FragmentAddRoutes newInstance() {
        FragmentAddRoutes fragment = new FragmentAddRoutes();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        route = new Route();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_fragment_add_routes, container, false);
        System.out.println("onCreateView");
        btnRouteCreation =  view.findViewById(R.id.create_route_button);
        btnRouteCreation.setTag(0);
        btnRouteCreation.setOnClickListener(this);

        editTextName = view.findViewById(R.id.add_routes_name_route_edit);
        editTextDescription = view.findViewById(R.id.add_routes_description_route_edit);
        return view;
    }

    @Override
    public void onClick(View v) {
        // Get back the tag button to know which route the user selected

        int responseIndex = (int) v.getTag();

        route.setmName(editTextName.getText().toString());

        route.setmDescription(editTextDescription.getText().toString());


        if(responseIndex == 0) {
            Intent intent = new Intent(getActivity(), RiddleCreationActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable(getString(R.string.route),route);
            intent.putExtras(bundle);
            startActivity(intent);
        }
    }
}

