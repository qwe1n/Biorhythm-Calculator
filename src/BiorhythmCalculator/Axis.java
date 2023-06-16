package BiorhythmCalculator;

import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.util.StringConverter;

import java.time.LocalDate;
import java.time.LocalTime;

public class Axis extends BorderPane {
    private final NumberAxis xAxis = new NumberAxis();
    private final NumberAxis yAxis = new NumberAxis();
    private final LineChart<Number, Number> chart = new LineChart<Number,Number>(xAxis,yAxis);

    public Axis() {
        super();

        xAxis.setLabel("日期");
        yAxis.setLabel("指标");

        xAxis.setAutoRanging(false);

        xAxis.setTickUnit(Math.PI / 2);
        xAxis.setLowerBound(-Math.PI);
        xAxis.setUpperBound(Math.PI);

        yAxis.setAutoRanging(false);
        yAxis.setLowerBound(-1.0);
        yAxis.setUpperBound(1.0);
        yAxis.setTickLabelsVisible(false);
        xAxis.setTickLabelsVisible(false);
        chart.setCreateSymbols(false);
        setCenter(chart);
    }

    public void add(SineWave sinewave) {
        chart.getData().add(sinewave.getData());
    }

    public void reset(){
        chart.getData().clear();
    }

    public void setxAxis(LocalDate localDate,int totalLength){
        xAxis.setTickLabelsVisible(true);
        xAxis.setTickLabelFormatter(new Converter(localDate,totalLength));
    }
    class Converter extends StringConverter<Number>{
        LocalDate localDate;
        int totalLength;
        public Converter(LocalDate localDate,int totalLength){
            this.localDate = localDate;
            this.totalLength = totalLength;
            //System.out.println(localDate.toString());
        }
        @Override
        public String toString(Number object) {
            double val = object.doubleValue();
            long days = (long)Math.round((val / (Math.PI * 2))*this.totalLength);
            //System.out.println(localDate.toString());
            return String.format(localDate.plusDays(days).toString());
        }

        @Override
        public Number fromString(String string) {
            return null;
        }
    }
}

