# FOR ECLIPSE:

## To install the correct formatter settings in Eclipse:

* Window -> Preferences -> Java -> Code style -> Formatter :
* And then import the file:
* ebx-linkedin-sdk/CodeStyle/"Eclipse_EBX_Code_Style_formatter_settings.XML"
* Then in the "Organize imports" tab :
* Window -> Preferences -> Java -> Code style -> Organize imports
* And then import the file:
* ebx-linkedin-sdk/CodeStyle/"Eclipse_EBX_Code_Style_Imports.importorder"

You can now use the formatter by either highlighting the code you want to format, or the entire
file, and Press ctrl-shift-F. This will automatically format the code so that it complies with the
EchoBox Style guide.

To format the import statements simply press ctrl-shift-O which will Organize the imports and
place them in the correct order.

To format code correctly: **Ctrl-Shift-F**  
To format Imports correctly: **Ctrl-Shift-O**

**_Use the formatter!!!!_**

## To Configure the Eclipse "check style" plug-in:

When first importing the maven project you should be prompted to install
the Eclipse "check style" plug-in. This will act as a kind of "compiler" and underline code which
does not follow the check style standard, allowing you to fix violations as you go.

If you are not prompted to install this plug-in reimport the project.

When the plug-in is installed it defaults to the "Google style guide".

To change this to the "EchoBox style guide":

* Window -> Preferences -> checkStyle :
* Click : "NEW" on the right-hand side
* Then in the drop-down menu select "External configuration file"
* In location select ebx-linkedin-sdk/CodeStyle/"checkstyle.xml"
* you can name this anything, I suggest "EbxStyle".
* Click : OK
* Next click on this new configurations and click "Set as default"

Now the Eclipse check style plug-in is configured with the "EchoBox style guide" and should
behave correctly.

# FOR Intellj-IDEA:

## To install the correct formatter settings in Intellj-IDEA:

* File -> Settings -> Code style -> Java , Click: Manage -> import -> Intellj-IDEA code style XML:
* And then import the file:
* ebx-linkedin-sdk/CodeStyle/"Intellij_EBX_Code_Style_formatter_settings"

You can now use the formatter by either highlighting the code you want to format, or the entire
file, and Press Ctrl-Alt-L. This will automatically format the code so that it complies with the
EchoBox Style guide.

To format the import statements simply press Ctrl-Alt-O which will optimise the imports and
place them in the correct order.

To format code correctly: **Ctrl-Alt-L**  
To format Imports correctly: **Ctrl-Alt-O**

**_Use the formatter!!!!_**

## To Configure the Intellj "check style" plug-in:

to Install the Intellj "check style" plug-in go to:

* Settings -> Plug-ins /
* Click on the "Browse repositories" button at the bottom.
* And then enter "CheckStyle-IDEA"
* Click install

Then to configure this plug-in to conform with "EBXStyle"

* Settings -> Other Settings -> checkstyle
* click on the green plus button
* Add in the description "ebx_checks"
* Add the checkstyle.xml File found in ebx-linkedin-sdk\CodeStyle\checkstyle.xml
* Tick the "Treat checkstyle errors as warnings" box
* click Apply -> OK

Now any violations of the checkstyle rules will be highlighted as a warning live in the code you
write.
