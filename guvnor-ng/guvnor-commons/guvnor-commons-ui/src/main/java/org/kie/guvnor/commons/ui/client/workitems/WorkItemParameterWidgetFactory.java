/*
 * Copyright 2011 JBoss Inc
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.kie.guvnor.commons.ui.client.workitems;

import org.kie.guvnor.datamodel.model.workitems.PortableBooleanParameterDefinition;
import org.kie.guvnor.datamodel.model.workitems.PortableEnumParameterDefinition;
import org.kie.guvnor.datamodel.model.workitems.PortableFloatParameterDefinition;
import org.kie.guvnor.datamodel.model.workitems.PortableIntegerParameterDefinition;
import org.kie.guvnor.datamodel.model.workitems.PortableListParameterDefinition;
import org.kie.guvnor.datamodel.model.workitems.PortableObjectParameterDefinition;
import org.kie.guvnor.datamodel.model.workitems.PortableParameterDefinition;
import org.kie.guvnor.datamodel.model.workitems.PortableStringParameterDefinition;

/**
 * A Factory to create Widgets to edit Work Item parameters
 */
public class WorkItemParameterWidgetFactory {

    public static WorkItemParameterWidget getWidget( PortableParameterDefinition ppd,
                                                     IBindingProvider bindingProvider,
                                                     boolean isReadOnly ) {
        if ( ppd instanceof PortableBooleanParameterDefinition ) {
            return new WorkItemBooleanParameterWidget( (PortableBooleanParameterDefinition) ppd,
                                                       bindingProvider,
                                                       isReadOnly );
        }
        if ( ppd instanceof PortableEnumParameterDefinition ) {
            return new WorkItemEnumParameterWidget( (PortableEnumParameterDefinition) ppd,
                                                    bindingProvider,
                                                    isReadOnly );
        }
        if ( ppd instanceof PortableFloatParameterDefinition ) {
            return new WorkItemFloatParameterWidget( (PortableFloatParameterDefinition) ppd,
                                                     bindingProvider,
                                                     isReadOnly );
        }
        if ( ppd instanceof PortableIntegerParameterDefinition ) {
            return new WorkItemIntegerParameterWidget( (PortableIntegerParameterDefinition) ppd,
                                                       bindingProvider,
                                                       isReadOnly );
        }
        if ( ppd instanceof PortableListParameterDefinition ) {
            return new WorkItemListParameterWidget( (PortableListParameterDefinition) ppd,
                                                    bindingProvider,
                                                    isReadOnly );
        }
        if ( ppd instanceof PortableObjectParameterDefinition ) {
            return new WorkItemObjectParameterWidget( (PortableObjectParameterDefinition) ppd,
                                                      bindingProvider,
                                                      isReadOnly );
        }
        if ( ppd instanceof PortableStringParameterDefinition ) {
            return new WorkItemStringParameterWidget( (PortableStringParameterDefinition) ppd,
                                                      bindingProvider,
                                                      isReadOnly );
        }
        throw new IllegalArgumentException( "Unrecognized PortableParameterDefinition" );
    }

}
