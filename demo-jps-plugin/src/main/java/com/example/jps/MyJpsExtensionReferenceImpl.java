package com.example.jps;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.jps.model.JpsElementFactory;
import org.jetbrains.jps.model.ex.JpsElementCollectionRole;
import org.jetbrains.jps.model.impl.JpsNamedElementReferenceImpl;
import org.jetbrains.jps.model.module.JpsModuleReference;

public class MyJpsExtensionReferenceImpl
        extends JpsNamedElementReferenceImpl<MyJpsModuleExtension, MyJpsExtensionReferenceImpl>
        implements MyJpsExtensionReference {

    public MyJpsExtensionReferenceImpl(@NotNull JpsElementCollectionRole<? extends MyJpsModuleExtension> role,
                                       @NotNull String moduleName,
                                       @NotNull String extensionName) {
        super(role, extensionName, JpsElementFactory.getInstance().createModuleReference(moduleName));
    }

    private MyJpsExtensionReferenceImpl(MyJpsExtensionReferenceImpl original) {
        super(original);
    }

    @Override
    public JpsModuleReference getParentReference() {
        return (JpsModuleReference)super.getParentReference();
    }

    @Override
    public String getExtensionName() {
        return this.myElementName;
    }

    @Override
    public JpsElementCollectionRole<? extends MyJpsModuleExtension> getChildRole() {
        return this.myCollectionRole;
    }

    @Override
    public @NotNull MyJpsExtensionReferenceImpl createCopy() {
        return new MyJpsExtensionReferenceImpl(this);
    }
}
