/*L
  Copyright Oracle Inc, SAIC, SAIC-F

  Distributed under the OSI-approved BSD 3-Clause License.
  See http://ncip.github.com/cadsr-semantic-tools/LICENSE.txt for details.
L*/

login.sql

spool test.out


PROMPT OBJECT CLASS 
@oc.sql

PROMPT OBJECT CLASS ALT DEF:
@ocaltdef.sql

PROMPT OBJECT CLASS ALT NAME:
@ocaltname.sql

PROMPT OBJECT CLASS CONCEPT:
@occoncepts.sql

PROMP PROPERTY 
@prop.sql

PROMPT PROPERTY ALT DEF:
@propaltdef.sql

PROMPT PROPERTY ALT NAME:
@propaltname.sql

PROMPT PROPERTY CONCEPT:
@propconcepts.sql

PROMPT DEC
@dec

PROMPT DEC ALT NAME
@decaltname

PROMPT DEC ALT DEF
@decaltdef

PROMPT DE
@de

PROMPT DE ALT NAME
@dealtname

PROMPT DE ALT DEF
@dealtdef

PROMPT ASSOCIATIONS: 
@associations.sql

PROMPT INHERITANCE:
@inheritance.sql

spool off

exit