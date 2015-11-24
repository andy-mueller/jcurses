# jcurses

This is a "fork" of the [jcurses](http://sourceforge.net/projects/javacurses/) project hosted on sourceforge. It is based on the last released 0.9.5b version. The source code was integrated into a maven project. It produces a jar file incorporating the required native libraries as resources. They will be extracted and loaded automatically, when the library is used. The mechanism was inspired by the jcurses fork of the [kolja project](https://github.com/codehaus/kolja/tree/master/jcurses). Additionally, some minor bug fixes were applied to the code base.

The project still misses native libraries for Mac OS and contributions would be appreciated.

All projects were released under the LGPL, therefor this project is under the LGPL v2.

## Summary of the original project

Taken from the source forge web space:

The Java Curses Library is a library that makes is possible to create
text- based terminal applications in the Java programming language,
like curses under Unix.  For this purpose a windowing toolkit is
implemented, that, like AWT, consists of many classes for text based
windows and GUI elements, that are layout within these windows. An
application,that bases on the library, creates one or more of this
windows and reacts on events coming by user interactions with GUI
elements.
