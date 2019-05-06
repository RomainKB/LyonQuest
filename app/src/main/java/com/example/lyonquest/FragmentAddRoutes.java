package com.example.lyonquest;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by romaink on 30/04/2019.
 *
 * Fragment which is display to the MainActicity activity. It allow to the user to create a new route.
 *
 */
public class FragmentAddRoutes extends Fragment implements View.OnClickListener{
    /**
     * The actual route create by the user
     */
    private Route route;
    /**
     * The EditText where the user enter the route name.
     */
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
        boolean complete = true;
        View focusView = null;
        String name = editTextName.getText().toString();
        String description = editTextDescription.getText().toString();

        if(TextUtils.isEmpty(description))
        {
            editTextDescription.setError(getString(R.string.error_field_required));
            focusView = editTextDescription;
            complete = false;
        }
        if(TextUtils.isEmpty(name))
        {
            editTextName.setError(getString(R.string.error_field_required));
            focusView = editTextName;
            complete = false;
        }

        if(complete)
        {
            route.setmName(name);
            route.setmDescription(description);
            Intent intent = new Intent(getActivity(), RiddleCreationActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable(getString(R.string.route),route);
            intent.putExtras(bundle);
            startActivity(intent);
        }
        else
        {
            focusView.requestFocus();
        }

    }
}

