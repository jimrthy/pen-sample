pen-sample
==========

Sample of using the defunct penumbra project, if only because they're tough to find


# com.frereth/visuals

Experiment to see if I can get penumbra working with
a github dependency instead of pulling from clojars.

## Notes

Use lein git-deps to pull my branch of penumbra from github.

Then, in the .lein-git-deps/penumbra directory, run lein javac.

That should generate some .class files under
.lein-git-deps/penumbra/target/classes.

Copy them into the project-root/target/classes directory, recursively.



## Releases and Dependency Information

* Releases are published to TODO_LINK

* Latest stable release is TODO_LINK

* All released versions TODO_LINK

[Leiningen] dependency information:

    [com.frereth/visuals "0.1.0-SNAPSHOT"]

[Maven] dependency information:

    <dependency>
      <groupId>com.frereth</groupId>
      <artifactId>visuals</artifactId>
      <version>0.1.0-SNAPSHOT</version>
    </dependency>

[Leiningen]: http://leiningen.org/
[Maven]: http://maven.apache.org/



## Usage

TODO



## Change Log

* Version 0.1.0-SNAPSHOT



## Copyright and License

Copyright © 2013 James Gatannah All rights reserved.

The use and distribution terms for this software are covered by the
[Eclipse Public License 1.0] which can be found in the file
epl-v10.html at the root of this distribution. By using this software
in any fashion, you are agreeing to be bound by the terms of this
license. You must not remove this notice, or any other, from this
software.

[Eclipse Public License 1.0]: http://opensource.org/licenses/eclipse-1.0.php


