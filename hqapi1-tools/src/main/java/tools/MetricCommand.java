/*
 * 
 * NOTE: This copyright does *not* cover user programs that use HQ
 * program services by normal system calls through the application
 * program interfaces provided as part of the Hyperic Plug-in Development
 * Kit or the Hyperic Client Development Kit - this is merely considered
 * normal use of the program, and does *not* fall under the heading of
 * "derived work".
 * 
 * Copyright (C) [2008, 2009], Hyperic, Inc.
 * This file is part of HQ.
 * 
 * HQ is free software; you can redistribute it and/or modify
 * it under the terms version 2 of the GNU General Public License as
 * published by the Free Software Foundation. This program is distributed
 * in the hope that it will be useful, but WITHOUT ANY WARRANTY; without
 * even the implied warranty of MERCHANTABILITY or FITNESS FOR A
 * PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307
 * USA.
 * 
 */

package org.hyperic.hq.hqapi1.tools;

import joptsimple.OptionParser;
import joptsimple.OptionSet;
import org.hyperic.hq.hqapi1.HQApi;
import org.hyperic.hq.hqapi1.MetricApi;
import org.hyperic.hq.hqapi1.ResourceApi;
import org.hyperic.hq.hqapi1.XmlUtil;
import org.hyperic.hq.hqapi1.types.Metric;
import org.hyperic.hq.hqapi1.types.MetricsResponse;
import org.hyperic.hq.hqapi1.types.ResourceResponse;
import org.hyperic.hq.hqapi1.types.StatusResponse;
import org.hyperic.hq.hqapi1.types.ResourcesResponse;
import org.hyperic.hq.hqapi1.types.Resource;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

@Component
public class MetricCommand extends AbstractCommand {

    private static String CMD_LIST = "list";
    private static String CMD_SYNC = "sync";
    private static String CMD_RESCHEDULE = "reschedule";

    private static String[] COMMANDS = { CMD_LIST, CMD_SYNC, CMD_RESCHEDULE };

    private static final String OPT_RESOURCE_ID = "id";
    private static final String OPT_ENABLED = "enabled";

    private void printUsage() {
        System.err.println("One of " + Arrays.toString(COMMANDS) + " required");
    }
    
    public String getName() {
        return "metric";
     }

    public int handleCommand(String[] args) throws Exception {
        if (args.length == 0) {
            printUsage();
            return 1;
        }

        if (args[0].equals(CMD_LIST)) {
            list(trim(args));
        } else if (args[0].equals(CMD_SYNC)) {
            sync(trim(args));
        } else if (args[0].equals(CMD_RESCHEDULE)) {
            reschedule(trim(args));
        } else {
            printUsage();
            return 1;
        }
        return 0;
    }

    private void list(String[] args) throws Exception {

        OptionParser p = getOptionParser();

        p.accepts(OPT_RESOURCE_ID, "The resource id to query for metrics").
                withRequiredArg().ofType(Integer.class);

        p.accepts(OPT_ENABLED, "When specified, only list metrics that are " +
                  "currently enabled");

        OptionSet options = getOptions(p, args);

        HQApi api = getApi(options);
        ResourceApi resourceApi = api.getResourceApi();
        MetricApi metricApi = api.getMetricApi();

        ResourceResponse resourceResponse =
                resourceApi.getResource((Integer)getRequired(options, OPT_RESOURCE_ID),
                                        false, false);
        checkSuccess(resourceResponse);

        MetricsResponse metrics;
        if (options.has(OPT_ENABLED)) {
            metrics = metricApi.getMetrics(resourceResponse.getResource(), true);
        } else {
            metrics = metricApi.getMetrics(resourceResponse.getResource(), false);
        }

        XmlUtil.serialize(metrics, System.out, Boolean.TRUE);
    }

    private void sync(String[] args) throws Exception {

        OptionParser p = getOptionParser();
        OptionSet options = getOptions(p, args);

        HQApi api = getApi(options);

        MetricApi metricApi = api.getMetricApi();

        InputStream is = getInputStream(options);

        MetricsResponse resp = XmlUtil.deserialize(MetricsResponse.class, is);

        List<Metric> metrics = resp.getMetric();

        StatusResponse syncResponse = metricApi.syncMetrics(metrics);
        checkSuccess(syncResponse);

        System.out.println("Successfully synced " + metrics.size() + " metrics.");        
    }

    private long getResourceCount(List<Resource> resources) {
        long num = 0;
        for (Resource r : resources) {
            num++;
            if (r.getResource().size() > 0) {
                num += getResourceCount(r.getResource());
            }
        }
        return num;
    }

    private void reschedule(String args[]) throws Exception {

        OptionParser p = getOptionParser();
        OptionSet options = getOptions(p, args);

        HQApi api = getApi(options);

        MetricApi metricApi = api.getMetricApi();

        InputStream is = getInputStream(options);

        ResourcesResponse resp = XmlUtil.deserialize(ResourcesResponse.class, is);
        checkSuccess(resp);

        StatusResponse response = metricApi.reschedule(resp.getResource());
        checkSuccess(response);

        System.out.println("Successfully rescheduled " + getResourceCount(resp.getResource()) +
                           " resources");
    }
}
