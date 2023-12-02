package com.example.jps;

import com.intellij.util.SmartList;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.jps.model.JpsElementChildRole;
import org.jetbrains.jps.model.artifact.elements.JpsPackagingElement;
import org.jetbrains.jps.model.artifact.elements.JpsPackagingElementFactory;
import org.jetbrains.jps.model.artifact.elements.ex.JpsComplexPackagingElementBase;
import org.jetbrains.jps.model.ex.JpsElementChildRoleBase;

import java.util.Collections;
import java.util.List;

public class MyJpsFacetResourcesPackagingElementImpl
        extends JpsComplexPackagingElementBase<MyJpsFacetResourcesPackagingElementImpl>
        implements MyJpsFacetResourcesPackagingElement {

    private static final JpsElementChildRole<MyJpsExtensionReference> REFERENCE_CHILD_ROLE = JpsElementChildRoleBase.create("my extension reference");

    public MyJpsFacetResourcesPackagingElementImpl(@NotNull MyJpsExtensionReference reference) {
        super();
        this.myContainer.setChild(REFERENCE_CHILD_ROLE, reference);
    }

    private MyJpsFacetResourcesPackagingElementImpl(MyJpsFacetResourcesPackagingElementImpl original) {
        super(original);
    }

    @Override
    public @NotNull MyJpsFacetResourcesPackagingElementImpl createCopy() {
        return new MyJpsFacetResourcesPackagingElementImpl(this);
    }

    @Override
    public @NotNull MyJpsExtensionReference getExtensionReference() {
        return this.myContainer.getChild(REFERENCE_CHILD_ROLE);
    }

    @Override
    public List<JpsPackagingElement> getSubstitution() {
        MyJpsModuleExtension extension = this.getExtensionReference().resolve();
        if (extension == null) {
            return Collections.emptyList();
        } else {
            List<MyJpsFacetConfiguration> myFacetConfigurations = extension.getFacetConfiguration();
            List<JpsPackagingElement> result = new SmartList<>();
            JpsPackagingElementFactory factory = JpsPackagingElementFactory.getInstance();
            for (MyJpsFacetConfiguration myFacetConfiguration : myFacetConfigurations) {
                result.add(factory.createParentDirectories(myFacetConfiguration.getRelativePath(), factory.createDirectoryCopy(myFacetConfiguration.getResourceDirectory())));
            }
            return result;
        }
    }
}
