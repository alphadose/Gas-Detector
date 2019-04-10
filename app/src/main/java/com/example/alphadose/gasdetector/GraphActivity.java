package com.example.alphadose.gasdetector;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GridLabelRenderer;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.HashMap;

public class GraphActivity extends AppCompatActivity {

    private static HashMap<String, LineGraphSeries<DataPoint>> GraphData = new HashMap<>();
    private String gas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);

        gas = getIntent().getStringExtra("gas");

        GraphView graph = findViewById(R.id.graph);

        if(!GraphData.containsKey(gas)) {
            GraphData.put(gas, new LineGraphSeries<>(
                    new DataPoint[] {
                            new DataPoint(0, 0),
                    }
            ));
        }
        graph.addSeries(GraphData.get(gas));

        GridLabelRenderer gridLabel = graph.getGridLabelRenderer();
        gridLabel.setHorizontalAxisTitle("Time in seconds");
        gridLabel.setVerticalAxisTitle(gas + "     Concentration in ppm");

        graph.getViewport().setScalable(true);
        graph.getViewport().setScrollable(true);
        graph.getViewport().setScalableY(true);
        graph.getViewport().setScrollableY(true);

        graph.getViewport().setXAxisBoundsManual(true);
        graph.getViewport().setMinX(0);
        graph.getViewport().setMaxX(20);

        graph.getViewport().setYAxisBoundsManual(true);
        graph.getViewport().setMinY(100);
        graph.getViewport().setMaxY(1400);
    }

    public static void updateGasData(String data) {
        HashMap<String, String> GasMap = new HashMap<>();
        String units[] = data.split(",");
        for(String unit: units) {
            GasMap.put(unit.split("=")[0], unit.split("=")[1]);
        }
        for(String gas: GasMap.keySet()) {
            if(!GraphData.containsKey(gas)) {
                GraphData.put(gas, new LineGraphSeries<>(
                        new DataPoint[] {
                                new DataPoint(0, 0),
                        }
                ));
            }
            GraphData.get(gas).appendData(
                    new DataPoint(
                            GraphData.get(gas).getHighestValueX()+1,
                            Double.parseDouble(GasMap.get(gas))
                    ),
                    true,
                    40
            );
        }
    }

}
