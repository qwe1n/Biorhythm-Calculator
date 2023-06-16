# Biorhythm-Calculator

##### 主题介绍
>使用JavaFX制作一个人体生物节律计算器，要求输入出生日期和查询日期后绘出查询日期前后的人体生物节律曲线图，并能够实现对出生日期的存储记忆功能

##### 项目制作

- 首先，写一个曲线图类SineWave，用于计算和存储正弦曲线数据的的键值对，构造函数需要传入最值A,频率k和偏移量d以及曲线代号name,如下
```
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
```

- 接着，写一个图表类Axis，继承于BorderPane,图表置于其center位置
  - Axis中有三个变量xAxis,yAxis,chart，分别表示横坐标、纵坐标和图表
  - 添加方法add(SineWave sinewave),便于向表中添加曲线
  - 添加方法reset(),清空表中的曲线，便于重写
  - 添加方法setxAxis(LocalDate localDate),此方法的作用是，修改横坐标标签，使其显示为时间，为此写了一个内部的转化类，将数字按一定规则转化为日期，日期间隔为5天，$2\pi$对应20天，代码如下
	```
	public void setxAxis(LocalDate localDate){
        xAxis.setTickLabelsVisible(true);
        xAxis.setTickLabelFormatter(new Converter(localDate));
    }
    class Converter extends StringConverter<Number>{
        LocalDate localDate;
        public Converter(LocalDate localDate){
            this.localDate = localDate;
            //System.out.println(localDate.toString());
        }
        @Override
        public String toString(Number object) {
            double val = object.doubleValue();
            long days = (long)Math.round((val / (Math.PI * 2))*20);
            //System.out.println(localDate.toString());
            return String.format(localDate.plusDays(days).toString());
        }

        @Override
        public Number fromString(String string) {
            return null;
        }
    }
	```
- 最后利用上面的两个类，写一个JavaFX程序
  - 利用Axis提供的BorderPane为主体进一步进行构建
  - 右边放置两个日期选择器分别表示出生日期和查询日期，和一个按钮表示查询
  - 查询按钮绑定事件：首先重置坐标轴，接着利用LocalDate内置函数计算出生日期和查询日期的日期差，从而计算出d1,d2,d3，表示三个曲线的偏移量，k1,k2,k3则是根据三个生物节律的周期计算得来，从而求出新的三个曲线的对应的SineWave,接着添加进坐标轴上，并根据目标日期设置横坐标标签
  - 出生日期记忆功能实现：加入一个CheckBox,可以选择是否记住，若是，则在按下查询时，创建/打开一个文本文件写入出生日期；程序启动时会检查有无此文件，并尝试以文件中的内容初始化出生日期对应的日期选择器。由此实现记忆功能
  - 在坐标轴下方加入五个按钮，可以更方便的微调目标日期
  - 初始化程序时，还会把目标日期初始化为当前日期，此外，若没有存储出生日期，出生日期会被也会被初始化为当前日期，并在程序运行初始，计算一次图表。
