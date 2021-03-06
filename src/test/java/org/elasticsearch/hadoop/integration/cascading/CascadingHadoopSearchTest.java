/*
 * Copyright 2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.elasticsearch.hadoop.integration.cascading;

import org.elasticsearch.hadoop.cascading.ESTap;
import org.elasticsearch.hadoop.integration.TestSettings;
import org.elasticsearch.hadoop.integration.cascading.HadoopPrintStreamTap.Stream;
import org.junit.Test;

import cascading.flow.hadoop.HadoopFlowConnector;
import cascading.pipe.Pipe;
import cascading.tap.Tap;

public class CascadingHadoopSearchTest {

    @Test
    public void testReadFromES() throws Exception {
        Tap in = new ESTap("cascading-hadoop/artists/_search?q=me*");
        Pipe copy = new Pipe("copy");
        // print out
        Tap out = new HadoopPrintStreamTap(Stream.OUT);

        new HadoopFlowConnector(TestSettings.TESTING_PROPS).connect(in, out, copy).complete();
    }
}
