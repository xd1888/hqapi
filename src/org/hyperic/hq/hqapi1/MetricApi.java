package org.hyperic.hq.hqapi1;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.hyperic.hq.hqapi1.types.*;

public class MetricApi {

    private final HQConnection _conn;

    MetricApi(HQConnection conn) {
        _conn = conn;
    }
    
    /**
     * List {@link org.hyperic.hq.hqapi1.types.Metric}s associated with a
     * {@link org.hyperic.hq.hqapi1.types.Resource}
     *
     * @param resource The associated {@link Resource} which the metrics belong.
     *
     * @return {@link org.hyperic.hq.hqapi1.types.ResponseStatus#SUCCESS} if the
     * metrics were successfully retrieved.
     *
     * @throws IOException If a network error occurs while making the request.
     */
    public ListMetricResponse listMetrics(Resource resource)
        throws IOException
    {
        Map<String, String> params = new HashMap<String, String>();
        params.put("resourceId", Integer.toString(resource.getId()));
        return _conn.doGet("/hqu/hqapi1/metric/listMetrics.hqu", params,
                           ListMetricResponse.class);
    }

    /**
     * Get the {@link org.hyperic.hq.hqapi1.types.Metric} associated
     * with the metricId
     *
     * @param id The id used to retrieve the associated
     *  {@link org.hyperic.hq.hqapi1.types.Metric}.
     *
     * @return {@link org.hyperic.hq.hqapi1.types.ResponseStatus#SUCCESS} if the
     * {@link org.hyperic.hq.hqapi1.types.Metric} was retrieved
     * successfully.
     *
     * @throws IOException If a network error occurs while making the request.
     */
    public GetMetricResponse getMetric(int id)
        throws IOException
    {
        Map<String, String> params = new HashMap<String, String>();
        params.put("id", Integer.toString(id));
        return _conn.doGet("/hqu/hqapi1/metric/getMetric.hqu", params,
                           GetMetricResponse.class);
    }
    
    /**
     * Disable a {@link org.hyperic.hq.hqapi1.types.Metric}.
     *
     * @param m The {@link Metric} to disable.
     *
     * @return {@link org.hyperic.hq.hqapi1.types.ResponseStatus#SUCCESS} if the
     * metric was successfully disabled.
     *
     * @throws IOException If a network error occurs while making the request.
     */
    public DisableMetricResponse disableMetric(Metric m)
        throws IOException
    {
        Map<String, String> params = new HashMap<String, String>();
        params.put("id", Integer.toString(m.getId()));
        return _conn.doGet("/hqu/hqapi1/metric/disableMetric.hqu", params,
                           DisableMetricResponse.class);
    }

    /**
     * Enable a {@link org.hyperic.hq.hqapi1.types.Metric}.
     *
     * @param m The metric to enable.
     * @param interval The interval for collection in milliseconds.  The
     * interval must be set on 1 minute increments otherwise an invalid
     * arguments error will be returned.
     * 
     * @return {@link org.hyperic.hq.hqapi1.types.ResponseStatus#SUCCESS} if the
     * metric was successfully enabled.
     *
     * @throws IOException If a network error occurs while making the request.
     */
    public EnableMetricResponse enableMetric(Metric m, long interval)
        throws IOException
    {
        Map<String, String> params = new HashMap<String, String>();
        params.put("id", Integer.toString(m.getId()));
        params.put("interval", Long.toString(interval));
        return _conn.doGet("/hqu/hqapi1/metric/enableMetric.hqu", params,
                           EnableMetricResponse.class);
    }

    /**
     * Set a {@link org.hyperic.hq.hqapi1.types.Metric} collection interval.
     *
     * @param m The metric to change the collection interval.
     * @param interval The interval for collection in milliseconds.  The
     * interval must be set on 1 minute increments otherwise an invalid
     * arguments error will be returned.
     *
     * @return {@link org.hyperic.hq.hqapi1.types.ResponseStatus#SUCCESS} if the
     * {@link org.hyperic.hq.hqapi1.types.Metric}s collection interval was
     * successfully updated.
     *
     * @throws IOException If a network error occurs while making the request.
     */
    public SetMetricIntervalResponse setInterval(Metric m, long interval)
        throws IOException
    {
        Map<String, String> params = new HashMap<String, String>();
        params.put("id", Integer.toString(m.getId()));
        params.put("interval", Long.toString(interval));
        return _conn.doGet("/hqu/hqapi1/metric/setInterval.hqu", params,
                           SetMetricIntervalResponse.class);
    }

    /**
     * Get the {@link org.hyperic.hq.hqapi1.types.MetricTemplate} associated
     * with the metric id.
     *
     * @param id The id used to retrieve the associated
     *  {@link org.hyperic.hq.hqapi1.types.MetricTemplate}.
     *
     * @return {@link org.hyperic.hq.hqapi1.types.ResponseStatus#SUCCESS} if the
     * {@link org.hyperic.hq.hqapi1.types.MetricTemplate} was retrieved
     * successfully
     *
     * @throws IOException If a network error occurs while making the request.
     */
    public GetMetricTemplateResponse getMetricTemplate(int id)
        throws IOException
    {
        Map<String, String> params = new HashMap<String, String>();
        params.put("id", Integer.toString(id));
        return _conn.doGet("/hqu/hqapi1/metric/getMetricTemplate.hqu", params,
                           GetMetricTemplateResponse.class);
    }
    
    /**
     * Sets the default on behavior for all
     *  {@link org.hyperic.hq.hqapi1.types.Metric}s associated with this
     *  {@link org.hyperic.hq.hqapi1.types.MetricTemplate}
     *
     * @param template The {@link org.hyperic.hq.hqapi1.types.MetricTemplate} to operate on.
     * @param on The flag to set for default on.
     *
     * @return {@link org.hyperic.hq.hqapi1.types.ResponseStatus#SUCCESS} if the
     * {@link org.hyperic.hq.hqapi1.types.Metric}s collection interval was
     * successfully updated.
     *
     * @see org.hyperic.hq.hqapi1.ErrorCode#INVALID_PARAMETERS
     * @throws IOException If a network error occurs while making the request.
     */
    public SetMetricDefaultOnResponse setDefaultOn(MetricTemplate template, boolean on)
        throws IOException
    {
        Map<String, String> params = new HashMap<String, String>();
        params.put("templateId", Integer.toString(template.getId()));
        params.put("on", Boolean.toString(on));
        return _conn.doGet("/hqu/hqapi1/metric/setDefaultOn.hqu", params,
                           SetMetricDefaultOnResponse.class);
    }
    
    /**
     * Sets the default indicator for all
     *  {@link org.hyperic.hq.hqapi1.types.Metric}s associated with this
     *  {@link org.hyperic.hq.hqapi1.types.MetricTemplate}
     *
     * @param template The {@link org.hyperic.hq.hqapi1.types.MetricTemplate} to operate on.
     * @param on The flag to set for default indicator.
     *
     * @return {@link org.hyperic.hq.hqapi1.types.ResponseStatus#SUCCESS} if the
     * metric's collection interval was successfully updated.
     *
     * @throws IOException If a network error occurs while making the request.
     */
    public SetMetricDefaultIndicatorResponse setDefaultIndicator(MetricTemplate template,
                                                                 boolean on)
        throws IOException
    {
        Map<String, String> params = new HashMap<String, String>();
        params.put("templateId", Integer.toString(template.getId()));
        params.put("on", Boolean.toString(on));
        return _conn.doGet("/hqu/hqapi1/metric/setDefaultIndicator.hqu", params,
                           SetMetricDefaultIndicatorResponse.class);
    }
    
    /**
     * Set a {@link org.hyperic.hq.hqapi1.types.Metric} collection interval.
     *
     * @param template The {@link org.hyperic.hq.hqapi1.types.MetricTemplate} to operate on
     * @param interval The interval for collection in milliseconds.  The
     * interval must be set on 1 minute increments otherwise an invalid
     * arguments error will be returned.
     * 
     * @return {@link org.hyperic.hq.hqapi1.types.ResponseStatus#SUCCESS} if the
     * metric template's default collection interval was successfully updated.
     *
     * @throws IOException If a network error occurs while making the request.
     */
    public SetMetricDefaultIntervalResponse setDefaultInterval(MetricTemplate template,
                                                               long interval)
        throws IOException
    {
        Map<String, String> params = new HashMap<String, String>();
        params.put("templateId", Integer.toString(template.getId()));
        params.put("interval", Long.toString(interval));
        return _conn.doGet("/hqu/hqapi1/metric/setDefaultInterval.hqu", params,
                           SetMetricDefaultIntervalResponse.class);
    }
}
