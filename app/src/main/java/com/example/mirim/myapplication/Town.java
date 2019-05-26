package com.example.mirim.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapView;

public class Town extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_town);

        MapView mapView = new MapView(Town.this);
        mapView.setDaumMapApiKey("5bbdaed4e0e8d7abb8b0392600e0a0d4");
        ViewGroup mapViewContainer = (ViewGroup) findViewById(R.id.map_view);
        MapPoint mapPoint = MapPoint.mapPointWithGeoCoord(37.466324, 126.9307193);
        mapView.setMapCenterPoint(mapPoint, true);
        mapViewContainer.addView(mapView);

        MapPOIItem marker = new MapPOIItem();
        marker.setItemName("Default Marker");//marker 누르면 뜨는 설명
        marker.setTag(0);
        marker.setMapPoint(mapPoint);
//        marker.setMarkerType(MapPOIItem.MarkerType.RedPin); // 기본으로 제공하는 RedPin 마커 모양.
//        marker.setSelectedMarkerType(MapPOIItem.MarkerType.BluePin); // 마커를 클릭했을때, 기본으로 제공하는 BluePin 마커 모양.

        mapView.addPOIItem(marker);
    }

    public MapView getCalloutBalloon(MapView mapView, MapPOIItem marker) {
        marker.setMarkerType(MapPOIItem.MarkerType.RedPin);
        marker.setItemName(marker.getItemName());
        return mapView;
    }
}
