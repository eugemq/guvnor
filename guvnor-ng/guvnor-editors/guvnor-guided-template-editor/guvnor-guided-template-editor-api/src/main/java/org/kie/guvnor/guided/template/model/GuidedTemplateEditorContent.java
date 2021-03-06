/*
 * Copyright 2012 JBoss Inc
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.kie.guvnor.guided.template.model;

import org.jboss.errai.common.client.api.annotations.Portable;
import org.kie.guvnor.datamodel.oracle.DataModelOracle;
import org.kie.guvnor.services.config.model.imports.Import;
import org.kie.guvnor.services.config.model.imports.Imports;

import java.util.List;

/**
 * Container for data needed to edit a Guided Template
 */
@Portable
public class GuidedTemplateEditorContent {

    private DataModelOracle dataModel;
    private TemplateModel ruleModel;

    public GuidedTemplateEditorContent() {
    }

    public GuidedTemplateEditorContent( final DataModelOracle dataModel,
                                        final TemplateModel ruleModel ) {
        this.dataModel = dataModel;
        this.ruleModel = ruleModel;
    }

    public DataModelOracle getDataModel() {
        return dataModel;
    }

    public TemplateModel getRuleModel() {
        return ruleModel;
    }

}
