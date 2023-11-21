# Demo Artifact
<!-- Plugin description -->
This demo artifact will package module/lib directory to module/target directory.

### Usage
1. Open this project with IntelliJ IDEA, then debug the "Run Plugin" configuration.
2. In debugging IDEA session, create "lib" directory in module directory.
3. Then go to File -> Project Structure, and add "MyFacet" facet and add "MyArtifact" artifact. (Don't forget to apply changes.)
4. Now create a run configuration of any type, add a before launch task to build added artifact
5. Check contents in module/target directory.

<!-- Plugin description end -->
