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
package org.kie.guvnor.guided.dtable.client.widget;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.google.gwt.event.shared.EventBus;
import org.kie.guvnor.datamodel.model.IPattern;
import org.kie.guvnor.datamodel.oracle.DataModelOracle;
import org.kie.guvnor.datamodel.oracle.DataType;
import org.kie.guvnor.guided.dtable.client.resources.i18n.Constants;
import org.kie.guvnor.guided.dtable.model.BRLColumn;
import org.kie.guvnor.guided.dtable.model.BRLConditionColumn;
import org.kie.guvnor.guided.dtable.model.BRLConditionVariableColumn;
import org.kie.guvnor.guided.dtable.model.BRLRuleModel;
import org.kie.guvnor.guided.dtable.model.CompositeColumn;
import org.kie.guvnor.guided.dtable.model.GuidedDecisionTable52;
import org.kie.guvnor.guided.rule.client.editor.RuleModellerConfiguration;
import org.kie.guvnor.guided.rule.model.RuleModel;
import org.kie.guvnor.guided.template.model.InterpolationVariable;
import org.kie.guvnor.guided.template.model.RuleModelCloneVisitor;
import org.uberfire.backend.vfs.Path;

/**
 * An editor for a BRL Condition Columns
 */
public class BRLConditionColumnViewImpl extends AbstractBRLColumnViewImpl<IPattern, BRLConditionVariableColumn>
        implements
        BRLConditionColumnView {

    private Presenter presenter;

    public BRLConditionColumnViewImpl( final Path path,
                                       final DataModelOracle oracle,
                                       final GuidedDecisionTable52 model,
                                       final BRLConditionColumn column,
                                       final EventBus eventBus,
                                       final boolean isNew,
                                       final boolean isReadOnly ) {
        super( path,
               oracle,
               model,
               column,
               eventBus,
               isNew,
               isReadOnly );

        setTitle( Constants.INSTANCE.ConditionBRLFragmentConfiguration() );
    }

    protected boolean isHeaderUnique( String header ) {
        for ( CompositeColumn<?> cc : model.getConditions() ) {
            for ( int iChild = 0; iChild < cc.getChildColumns().size(); iChild++ ) {
                if ( cc.getChildColumns().get( iChild ).getHeader().equals( header ) ) {
                    return false;
                }
            }
        }
        return true;
    }

    public BRLRuleModel getRuleModel( BRLColumn<IPattern, BRLConditionVariableColumn> column ) {
        BRLRuleModel ruleModel = new BRLRuleModel( model );
        List<IPattern> definition = column.getDefinition();
        ruleModel.lhs = definition.toArray( new IPattern[ definition.size() ] );
        return ruleModel;
    }

    public RuleModellerConfiguration getRuleModellerConfiguration() {
        return new RuleModellerConfiguration( false,
                                              true,
                                              true );
    }

    public void setPresenter( Presenter presenter ) {
        this.presenter = presenter;
    }

    @Override
    protected void doInsertColumn() {
        this.editingCol.setDefinition( Arrays.asList( this.ruleModel.lhs ) );
        presenter.insertColumn( (BRLConditionColumn) this.editingCol );
    }

    @Override
    protected void doUpdateColumn() {
        this.editingCol.setDefinition( Arrays.asList( this.ruleModel.lhs ) );
        presenter.updateColumn( (BRLConditionColumn) this.originalCol,
                                (BRLConditionColumn) this.editingCol );
    }

    @Override
    protected List<BRLConditionVariableColumn> convertInterpolationVariables( Map<InterpolationVariable, Integer> ivs ) {

        //If there are no variables add a boolean column to specify whether the fragment should apply 
        if ( ivs.size() == 0 ) {
            BRLConditionVariableColumn variable = new BRLConditionVariableColumn( "",
                                                                                  DataType.TYPE_BOOLEAN );
            variable.setHeader( editingCol.getHeader() );
            variable.setHideColumn( editingCol.isHideColumn() );
            List<BRLConditionVariableColumn> variables = new ArrayList<BRLConditionVariableColumn>();
            variables.add( variable );
            return variables;
        }

        //Convert to columns for use in the Decision Table
        BRLConditionVariableColumn[] variables = new BRLConditionVariableColumn[ ivs.size() ];
        for ( Map.Entry<InterpolationVariable, Integer> me : ivs.entrySet() ) {
            InterpolationVariable iv = me.getKey();
            int index = me.getValue();
            BRLConditionVariableColumn variable = new BRLConditionVariableColumn( iv.getVarName(),
                                                                                  iv.getDataType(),
                                                                                  iv.getFactType(),
                                                                                  iv.getFactField() );
            variable.setHeader( editingCol.getHeader() );
            variable.setHideColumn( editingCol.isHideColumn() );
            variables[ index ] = variable;
        }

        //Convert the array into a mutable list (Arrays.toList provides an immutable list)
        List<BRLConditionVariableColumn> variableList = new ArrayList<BRLConditionVariableColumn>();
        for ( BRLConditionVariableColumn variable : variables ) {
            variableList.add( variable );
        }
        return variableList;
    }

    @Override
    protected BRLColumn<IPattern, BRLConditionVariableColumn> cloneBRLColumn( BRLColumn<IPattern, BRLConditionVariableColumn> col ) {
        BRLConditionColumn clone = new BRLConditionColumn();
        clone.setHeader( col.getHeader() );
        clone.setHideColumn( col.isHideColumn() );
        clone.setDefinition( cloneDefinition( col.getDefinition() ) );
        clone.setChildColumns( cloneVariables( col.getChildColumns() ) );
        return clone;
    }

    private List<BRLConditionVariableColumn> cloneVariables( List<BRLConditionVariableColumn> variables ) {
        List<BRLConditionVariableColumn> clone = new ArrayList<BRLConditionVariableColumn>();
        for ( BRLConditionVariableColumn variable : variables ) {
            clone.add( cloneVariable( variable ) );
        }
        return clone;
    }

    private BRLConditionVariableColumn cloneVariable( BRLConditionVariableColumn variable ) {
        BRLConditionVariableColumn clone = new BRLConditionVariableColumn( variable.getVarName(),
                                                                           variable.getFieldType(),
                                                                           variable.getFactType(),
                                                                           variable.getFactField() );
        clone.setHeader( variable.getHeader() );
        clone.setHideColumn( variable.isHideColumn() );
        clone.setWidth( variable.getWidth() );
        return clone;
    }

    private List<IPattern> cloneDefinition( List<IPattern> definition ) {
        RuleModelCloneVisitor visitor = new RuleModelCloneVisitor();
        RuleModel rm = new RuleModel();
        for ( IPattern pattern : definition ) {
            rm.addLhsItem( pattern );
        }
        RuleModel rmClone = visitor.visitRuleModel( rm );
        List<IPattern> clone = new ArrayList<IPattern>();
        for ( IPattern pattern : rmClone.lhs ) {
            clone.add( pattern );
        }
        return clone;
    }

}
