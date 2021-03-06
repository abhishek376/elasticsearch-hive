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
package org.junit.rules;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.rules.ExternalResource;

public class ChainedExternalResource extends ExternalResource {

    private ExternalResource[] resources;

    private static final Log log = LogFactory.getLog(ChainedExternalResource.class);

    public ChainedExternalResource(ExternalResource... resources) {
        this.resources = resources;
    }

    @Override
    protected void before() throws Throwable {
        for (ExternalResource resource : resources) {
            try {
                resource.before();
            } catch (Exception ex) {
                log.warn("Cannot 'before' for resource " + resource, ex);
            }
        }
    }

    @Override
    protected void after() {
        // call after in reverse order
        for (int i = resources.length - 1; i >= 0; i--) {
            ExternalResource resource = resources[i];
            try {
                resource.after();
            } catch (Exception ex) {
                log.warn("Cannot 'after' for resource " + resource, ex);
            }
        }
    }
}
