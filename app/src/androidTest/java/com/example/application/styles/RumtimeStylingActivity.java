package com.example.application.styles;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.Style;
import com.mapbox.mapboxsdk.style.layers.Layer;

import com.mapbox.mapboxsdk.style.layers.PropertyFactory;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class RumtimeStylingActivity extends AppCompatActivity {

    private MapView mapview;
    private FloatingActionButton changeMapPropertiesFab;
    private Layer waterLayer;

    @Override
    protected void onCreate(Budle savedInstacesState){
        super.onCreate(SavedIntanceState);

        Mapbox.getInstace(this, getString(R.string.mapbox_acess_token));
        setContentView(R.layout.activity_main);

        mapview = findViewBydId(R.id.runtime_mapview);
        mapview.onCreate(savedInstanceState);

        changeMapPorpertiesFab = findViewById(R.id.floatingActionButton);

        mapView.getMapAsync(new OnMapReadyCallback()){

            @Override
            public void onMapReady(@NonNull final MapboxMap mapboxMap){
            mapboxMap.setStyle(Style.MAPBOX_STREETS, new Style.OnStyleLoaded()){
                @Override
                public void onStyleLoaded(@NonNull final Style style){
                    waterLayer = style.getLayer("water");
                    changeMapPropertiesFab.setOnClickkListener(new View.OnClickListener(){

                        @Override
                        public void onClick(View v){
                            //se houver um click, a cor da agua muda de cor para a cor indicada, mais escura
                            if(waterLayer != null){
                                waterLayer.setProperties(PropertyFactory.fillColor(Color.parseColor("#023689"))
                                );
                            }

                            //Metodo alternativo
                         //   for (Layer singleMapLayer : mapboxMap.getLayers()) {
                            //    Log.d("MainActivity", "onMapReady: layer name = " + singleMapLayer.getId());
                         //   }

                            //"for int" para continar o modo escuro pedindo todas as informações, dxando a agua com sombra, alterando o tamanho da fonte das letras, as cores, e a sobreposição de certas coisas
                            for(Layer singleMapLayer : style.getLayers()){
                                if(singleMapLayer.getId().contains("water-")&& !singleMapLayer.getId().equals("water-shadow")){
                                    singleMapLayer.setProperties(PropetyFactory.extHaloBlur(10f),
                                            ProppertyFactory.textSize(25f),
                                            PropertyFactory.textColor("#FFFFFF")),
                                            PropertyFactory.textOpacity(1f)
                                    );
                                }
                            }
                        }
                    });
                    }
                });
            }
        });
    }
    // Add the mapView's own lifecycle methods to the activity's lifecycle methods
    @Override
    public void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        mapView.onStop();
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

