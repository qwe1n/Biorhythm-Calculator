package BiorhythmCalculator;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class BiorhythmCalculator extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        final Axis axisPane = new Axis();

        final double emotionalPeriod = 28;
        final String emotionalLabel = "情绪";
        final double physicalPeriod = 23;
        final String physicalLabel = "体力";
        final double intellectualPeriod = 33;
        final String intellectualLabel = "智力";

        VBox rvb = new VBox();
        TitledPane tp1 = new TitledPane();
        tp1.setText("出生日期");
        DatePicker dp1 = new DatePicker();

        String string = LocalDate.now().toString();
        String filename = "remember.txt";
        File f = new File(filename);
        if (f.exists()){
            try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    string = line;
                }
            } catch (IOException e) {
                System.out.println("读取文件时发生错误！");
            }
        }
        LocalDate ld;
        try {
            ld = LocalDate.parse(string);
            dp1.setValue(ld);
        } catch (Exception ex){
            System.out.println(ex.getMessage());
        }

        Label label = new Label("记住我");
        CheckBox checkBox = new CheckBox();
        checkBox.setSelected(true);
        HBox hb0 = new HBox(label,checkBox);
        VBox vb0 = new VBox(dp1,hb0);
        tp1.setContent(vb0);
        rvb.getChildren().addAll(tp1);

        TitledPane tp2 = new TitledPane();
        tp2.setText("查询日期");
        DatePicker dp2 = new DatePicker();
        dp2.setValue(LocalDate.now());
        tp2.setContent(dp2);
        rvb.getChildren().add(tp2);
        Button btn = new Button("查询");

        btn.setStyle("-fx-background-color: lightblue; "
                + "-fx-text-fill: black; "
                + "-fx-cursor: hand; ");
        btn.setAlignment(Pos.CENTER);

        btn.setOnAction( e -> {
            axisPane.reset();
            axisPane.setxAxis(dp2.getValue());

            double k1 = 20 / emotionalPeriod;
            double k2 = 20 / physicalPeriod;
            double k3 = 20 / intellectualPeriod;

            double A = 1;
            long d = ChronoUnit.DAYS.between(dp1.getValue(),dp2.getValue());

            double d1 =  k1 * d;
            d1 *= Math.PI / 10;
            double d2 =  k2 * d;
            d2 *= Math.PI / 10;
            double d3 =  k3 * d;
            d3 *= Math.PI / 10;
            SineWave sw1 = new SineWave(A,k1,d1,emotionalLabel);
            SineWave sw2 = new SineWave(A,k2,d2,physicalLabel);
            SineWave sw3 = new SineWave(A,k3,d3,intellectualLabel);

            axisPane.add(sw1);
            axisPane.add(sw2);
            axisPane.add(sw3);

            if (checkBox.isSelected()){
                File file = new File("remember.txt");
                try {
                    file.createNewFile();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                try (FileWriter writer = new FileWriter(file)) {
                    writer.write(dp1.getValue().toString());
                } catch (IOException ex) {
                    System.out.println("写入文件时发生错误！");
                }
            }
        });

        rvb.getChildren().add(btn);


        Button btn1 = new Button("-1");
        btn1.setStyle("-fx-background-color: lightblue;-fx-cursor: hand;");
        btn1.setPrefSize(50,30);
        btn1.setOnAction(e -> {
            dp2.setValue(dp2.getValue().plusDays(-1));
            btn.fire();
        });
        Button btn2 = new Button("+1");
        btn2.setPrefSize(50,30);
        btn2.setStyle("-fx-background-color: lightblue;-fx-cursor: hand;");
        btn2.setOnAction(e -> {
            dp2.setValue(dp2.getValue().plusDays(1));
            btn.fire();
        });

        Button btn0 = new Button("今天");
        btn0.setPrefSize(50,30);
        btn0.setStyle("-fx-background-color: lightblue;-fx-cursor: hand;");
        btn0.setOnAction(e -> {
            dp2.setValue(LocalDate.now());
            btn.fire();
        });

        Button btn3 = new Button("-5");
        btn3.setStyle("-fx-background-color: lightblue;-fx-cursor: hand;");
        btn3.setPrefSize(50,30);
        btn3.setOnAction(e -> {
            dp2.setValue(dp2.getValue().plusDays(-5));
            btn.fire();
        });
        Button btn4 = new Button("+5");
        btn4.setPrefSize(50,30);
        btn4.setStyle("-fx-background-color: lightblue;-fx-cursor: hand;");
        btn4.setOnAction(e -> {
            dp2.setValue(dp2.getValue().plusDays(5));
            btn.fire();
        });
        HBox hBox = new HBox(20,btn3,btn1,btn0,btn2,btn4);
        hBox.setAlignment(Pos.CENTER);

        axisPane.setRight(rvb);

        Node p = axisPane.getCenter();
        VBox vb2 = new VBox(p,hBox);
        vb2.setStyle("-fx-padding:5px");
        axisPane.setCenter(vb2);

        Scene scene = new Scene(axisPane);

        primaryStage.setScene(scene);
        primaryStage.setTitle("人体生物节律计算器");
        btn.fire();
        primaryStage.show();

    }
}

