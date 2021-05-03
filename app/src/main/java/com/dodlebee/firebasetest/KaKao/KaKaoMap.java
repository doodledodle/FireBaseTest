package com.dodlebee.firebasetest.KaKao;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.ViewGroup;

import com.dodlebee.firebasetest.R;

import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapView;

public class KaKaoMap extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ka_kao_map);

        MapView mapView = new MapView(this);

        ViewGroup mapViewContainer = (ViewGroup) findViewById(R.id.map_view);


        MapPoint MARKER_POINT = MapPoint.mapPointWithGeoCoord(37.538429, 126.821574);
        mapView.setMapCenterPoint(MARKER_POINT, true);
        mapView.zoomIn(false);
        mapView.zoomOut(false);

        MapPOIItem marker = new MapPOIItem();
        marker.setItemName("Default Marker");
//        marker.setTag(0);
        marker.setMapPoint(MARKER_POINT);
        marker.setMarkerType(MapPOIItem.MarkerType.BluePin); // 기본으로 제공하는 BluePin 마커 모양.
        marker.setSelectedMarkerType(MapPOIItem.MarkerType.RedPin); // 마커를 클릭했을때, 기본으로 제공하는 RedPin 마커 모양.
        mapView.addPOIItem(marker);

        mapViewContainer.addView(mapView);
    }
}