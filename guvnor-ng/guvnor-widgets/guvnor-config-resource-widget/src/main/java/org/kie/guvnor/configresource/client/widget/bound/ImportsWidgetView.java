package org.kie.guvnor.configresource.client.widget.bound;

import com.google.gwt.user.client.ui.IsWidget;
import org.kie.guvnor.datamodel.oracle.DataModelOracle;
import org.kie.guvnor.services.config.model.imports.Imports;
import org.uberfire.backend.vfs.Path;

public interface ImportsWidgetView
        extends IsWidget {

    interface Presenter {

        void onAddImport();

        void onRemoveImport();

        void setContent( final DataModelOracle oracle,
                         final Imports resourceImports,
                         final boolean isReadOnly );

    }

    void addImport( final String type );

    String getSelected();

    void removeImport( final String selected );

    void setReadOnly( final boolean isReadOnly );

    void setPresenter( final Presenter presenter );

    void showPleaseSelectAnImport();
}
