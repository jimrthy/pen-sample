pen-sample
==========

Sample of using the defunct penumbra project, if only because they're tough to find


# com.frereth/visuals

Trying to wrap my head around the way penumbra work(s/ed).

## Notes

For resizable windows, see:

http://stackoverflow.com/questions/5206775/how-do-i-make-an-lwjgl-window-resizable

which, ultimately, amounts to "Start with an AWT Frame and use
Display.setParent(Canvas) to stick the LWJGL content on it to get
a resizable window" with

http://hg.l33tlabs.org/twlthemeeditor/file/tip/src/de/matthiasmann/twlthemeeditor/Main.java

as an example (TODO: yes, I need to learn how to use Markdown's link
syntax).

More recently, though, LWJGL has added the ability to make the Display resizable
like a real grown-up window.

## Releases and Dependency Information


[Leiningen]: http://leiningen.org/
[Maven]: http://maven.apache.org/


## Usage

Don't. This isn't even alpha quality, and it's based around an
obsolete API that's no longer supported.

This really isn't suitable for anyone to be using.

In case you *still* aren't discouraged:

Get a reasonably modern copy of penumbra somewhere. You might update
project.clj to refer to one that's been deployed to clojars. Or
maybe get one from github and do `lein install` to get it into
your local repository cache. Then change project.clj to rely
on whatever credentials you gave it.

Odds are, it still won't work (it doesn't for me). Consider yourself
warned.

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


