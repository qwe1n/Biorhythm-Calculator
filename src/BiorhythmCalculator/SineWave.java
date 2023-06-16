package BiorhythmCalculator;

import javafx.scene.chart.XYChart;

public class SineWave {
    private final XYChart.Series<Number,Number> dataSeries = new XYChart.Series<Number,Number>();

    public SineWave(double A, double k, double d, String name) {
        for (double x = -Math.PI; x < Math.PI ; x += 0.1){
            dataSeries.getData().add((new XYChart.Data<Number, Number>(x,A*Math.sin(k*x+d))));
        }
        dataSeries.setName(name);
    }

    public XYChart.Series<Number,Number> getData() {
        return dataSeries;
    }
}
