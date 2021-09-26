package com.example.application;
package com.mapbox.mapboxandroiddemo.examples.styles;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.mapbox.geojson.Feature;
import com.mapbox.geojson.FeatureCollection;
import com.mapbox.geojson.Point;
import com.mapbox.mapboxandroiddemo.R;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.Style;
import com.mapbox.mapboxsdk.style.layers.SymbolLayer;
import com.mapbox.mapboxsdk.style.sources.GeoJsonSource;

import java.util.ArrayList;
import java.util.List;

import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconAllowOverlap;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconIgnorePlacement;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconImage;



/**
 * Display
 */
public class MainActivity extends AppCompatActivity implements
        OnMapReadyCallback {

    private static final String SOURCE_ID = "SOURCE_ID";
    private static final String ICON_ID = "ICON_ID";
    private static final String LAYER_ID = "LAYER_ID";
    private MapView mapView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

// Mapbox access token is configured here. This needs to be called either in your application
// object or in the same activity which contains the mapview.
        Mapbox.getInstance(this, getString(R.string.mapbox_access_token));

// This contains the MapView in XML and needs to be called after the access token is configured.
        setContentView(R.layout.activity_main);
        mapView = findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this){

        }

        @Override
        public void onMapReady(new OnMapReadyCallback() {

            //Criando a lista de simbolos apartir da <Feature>, coloquei o Symboll, para que eles seguissem o caminho indicado a baixo
//sendo esse motivo, de ter colocado o simbolLayerFeatureList, com as coordenadas, para elas ficarem estaticas no local da coordenada indicada
// as coordenadas para um determinado local, de acordo adicionanduas na lista com "add"
            List<Feature> symbolLayerIconFeatureList = new ArrayList<>();
    symbolLayerIconFeatureList.add(Feature.fromGeometry(
            Point.fromLngLat(-7.9986401,-34.8459552))); //Olinda
    symbolLayerIconFeatureList.add(Feature.fromGeometry(
            Point.fromLngLat(-22.9110137,-43.2093727)));//Rio de Janeiro
    symbolLayerIconFeatureList.add(Feature.fromGeometry(
            Point.fromLngLat(-9.3817334,-40.4968875)));//Petrolina
    mapView.getMapAsync(new OnMapReadyCallback(){

                @Override
                public void onMapReady(MapBox mapboxMap){
                    MainActivity.this.mapboxMap = mapboxMap;

                }
            });

    mapboxMap.setStyle(new Style.Builder().fromUri("mapbox://styles/lucascrespo75/cku09x5dp0ade17owp1tqoowc")
            // Add the SymbolLayer icon image to the map style
            .withImage(ICON_ID, BitmapFactory.decodeResource(
                    MainActivity.this.getResources(), R.drawable.mapbox_marker_icon_default))

// Adicionando o GeoJason, que serve como intercambio de dados geoespacial, padrao, para representar feições geograficas simples
                    .withSource(new GeoJasonSource(SOURCE_ID, Featurecollection.fromFeatures(symbolLayerIconFeatureList))


                    .withLayer(new SymbolLayer(LAYER_ID, SOURCE_ID)
                    .withProperties(
                    iconImage(ICON_ID),
            iconAllowOverlap(true),
            iconIgnorePlacement(true)
                    )
                            ), new Style.OnStyleLoaded(){
                //Termino
                // apenas fazer as alterações quais quer
                @Override
                public void onStyleLoaded(@NonNull Style style) {


                }
            });
        }

        @Override
        public void onResume() {
            super.onResume();
            mapView.onResume();
        }

        @Override
        protected void onStart() {
            super.onStart();
            mapView.onStart();
        }

        @Override
        protected void onStop() {
            super.onStop();
            mapView.onStop();
        }

        @Override
        public void onPause() {
            super.onPause();
            mapView.onPause();
        }

        @Override
        public void onLowMemory() {
            super.onLowMemory();
            mapView.onLowMemory();
        }

        @Override
        protected void onDestroy() {
            super.onDestroy();
            mapView.onDestroy();
        }

        @Override
        protected void onSaveInstanceState(Bundle outState) {
            super.onSaveInstanceState(outState);
            mapView.onSaveInstanceState(outState);
        }
    }