package com.example.jps;

import com.intellij.util.SmartList;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.jps.model.serialization.JpsModelSerializerExtension;
import org.jetbrains.jps.model.serialization.artifact.JpsPackagingElementSerializer;
import org.jetbrains.jps.model.serialization.facet.JpsFacetConfigurationSerializer;

import java.util.List;

public class MyJpsModelSerializerExtension extends JpsModelSerializerExtension {

    public MyJpsModelSerializerExtension() {
        System.out.println("MyJpsModelSerializerExtension");
    }

    @Override
    public @NotNull List<? extends JpsFacetConfigurationSerializer<?>> getFacetConfigurationSerializers() {
        return new SmartList<>(new MyJpsFacetConfigurationSerializer());
    }

    @Override
    public @NotNull List<? extends JpsPackagingElementSerializer<?>> getPackagingElementSerializers() {
        return new SmartList<>(new MyJpsFacetResourcesPackagingElementSerializer());
    }
}
