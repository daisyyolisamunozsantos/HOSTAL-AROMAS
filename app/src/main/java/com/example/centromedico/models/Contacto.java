package com.example.centromedico.models;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.centromedico.ContactoListActivity;
import com.example.centromedico.helpers.QueueUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Contacto {
    public String phone;
    public String nickname;
    public String image;

    public String getSmallImage(){
        return this.image;
    };

    public Contacto(String _phone, String _nickname,String _image) {
        this.phone = _phone;
        this.nickname = _nickname;
        this.image = _image;
    }

    public static ArrayList getCollection() {
        ArrayList<Contacto> collection = new ArrayList<>();
        collection.add(new Contacto("981999923", "Bichito","https://mymodernmet.com/wp/wp-content/uploads/2019/09/100k-ai-faces-3.jpg"));
        collection.add(new Contacto("9859913923", "Plaga","https://mymodernmet.com/wp/wp-content/uploads/2019/09/100k-ai-faces-8.jpg"));
        collection.add(new Contacto("981914213", "Libelula","https://mymodernmet.com/wp/wp-content/uploads/2019/09/100k-ai-faces-7.jpg"));
        collection.add(new Contacto("958899251", "Jhamillex","https://mymodernmet.com/wp/wp-content/uploads/2019/09/100k-ai-faces-4.jpg"));
        return collection;
    }
    public static void injectContactsFromCloud(final QueueUtils.QueueObject o,
                                               final ArrayList<Contacto> collection,
                                               final ContactoListActivity _interface) {
        String url = "http://fipo.equisd.com/api/users.json";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        if (response.has("objects")) {

                            try {
                                JSONArray list = response.getJSONArray("objects");
                                for (int i=0; i < list.length(); i++) {
                                    JSONObject o = list.getJSONObject(i);
                                    collection.add(new Contacto(o.getString("first_name"),
                                            o.getString("last_name"),(o.getString("avatar"))));
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            _interface.refreshList(); // Esta función debemos implementarla
                            // en nuestro activity
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        o.addToRequestQueue(jsonObjectRequest);
    }
    public static void sendRequestPOST(QueueUtils.QueueObject o, final ContactoListActivity _interface) {
        String url = "http://rrojasen.alwaysdata.net/purchaseorders.json";
        url = "http://fipo.equisd.com/api/users.json";
        //url = "http://192.168.58.3:8056/api/users/new.json"; cuando se encuentra en una computadora
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            //Do it with this it will work
                            JSONObject _response = new JSONObject(response);
                            if (_response.has("object")) {
                                JSONObject object_response = null;
                                try {
                                    object_response = _response.getJSONObject("object");
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                                if ( object_response != null ) {
                                    try {
                                        System.out.println(object_response.getInt("id"));
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put("first_name","Jhamil");
                params.put("last_name","Huaman");
                params.put("avatar","https://scontent.flim19-1.fna.fbcdn.net/v/t1.0-9/p960x960/64350326_2812192525519385_8592604670088708096_o.jpg?_nc_cat=102&_nc_ohc=U027N7b1l9kAQmo0j-WVE9fUTqdAgj21gLU86OfwsIukOZ8_WI-CVSBeA&_nc_ht=scontent.flim19-1.fna&_nc_tp=1&oh=61f0107fddab55911a9f0d5be7682cab&oe=5EA9269A");

                return params;
            }
        };
        o.addToRequestQueue(stringRequest);
    }
}
