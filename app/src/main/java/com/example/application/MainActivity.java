import android.graphics.Color;
import android.os.Bundle;

import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.Style;
import com.mapbox.mapboxsdk.style.layers.CircleLayer;
import com.mapbox.mapboxsdk.style.sources.VectorSource;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import static com.mapbox.mapboxsdk.style.expressions.Expression.exponential;
import static com.mapbox.mapboxsdk.style.expressions.Expression.get;
import static com.mapbox.mapboxsdk.style.expressions.Expression.interpolate;
import static com.mapbox.mapboxsdk.style.expressions.Expression.stop;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.circleColor;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.circleOpacity;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.circleRadius;

/**
 * Display
 */
public class MainActivity extends AppCompatActivity{ //implements
      //  OnMapReadyCallback {

   // private static final String SOURCE_ID = "SOURCE_ID";
   // private static final String ICON_ID = "ICON_ID";
   // private static final String LAYER_ID = "LAYER_ID";
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
        mapView.getMapAsync(new OnMapReadyCallback(){

        @Override
        public void onMapReady(@NonNull final MapboxMap mapboxMap) {

            mapboxMap.setStyle(StyleDARK, new Style.OnStyleLoaded() {

                @Override
                public void onStyleLoaded(@NonNull Style style) {

                    VectorSource vectorSource = new VectorSource(
                            "trees-source", "http://api.mapbox.com/v4/lucascrespo75.8igw8kt2.json?acess_token="
                            + getString(R.string.mapbox_acess_token)
                    );
                    style.addSource(vectorSource);
                    CircleLayer circleLayer = new CircleLayer("trees-style", "trees-source");

                    circleLayer.setSourceLayer("street-trees-DC-9gvg5l");
                    circleLayer.withPropreties(
                            circleOpacity(0.6f),
                            circleColor(Color.parseColor("#070808")),
                            circleRadius(
                                    interpolate(exponential(1.0f), get("DBH"),
                                            stop(0, 0f),
                                            stop(1, 1f),
                                            stop(110, 11f)
                                    )
                            )
                    );
                    style.addLayer(circleLayer);
                  }
               });
            });
          }

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
        protected void onStart() {
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
        protected void onStop() {
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