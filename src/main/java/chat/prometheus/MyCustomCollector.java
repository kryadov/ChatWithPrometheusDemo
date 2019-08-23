package chat.prometheus;

import io.prometheus.client.Collector;
import io.prometheus.client.GaugeMetricFamily;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class MyCustomCollector extends Collector {
        public List<MetricFamilySamples> collect() {
            List<MetricFamilySamples> mfs = new ArrayList<>();
            // With no labels.
            mfs.add(new GaugeMetricFamily("my_custom_active_chat_sessions", "help", 42));
            // With labels
            GaugeMetricFamily labeledGauge =
                    new GaugeMetricFamily("my_other_gauge", "help", Arrays.asList("labelname"));
            labeledGauge.addMetric(/* labels */Arrays.asList("foo"), 4);
            labeledGauge.addMetric(/* labels */Arrays.asList("bar"), 5);
            mfs.add(labeledGauge);
            return mfs;
        }
    // Registration
    static final MyCustomCollector requests = new MyCustomCollector().register();
}
