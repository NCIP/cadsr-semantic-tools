/*L
  Copyright Oracle Inc, SAIC, SAIC-F

  Distributed under the OSI-approved BSD 3-Clause License.
  See http://ncip.github.com/cadsr-semantic-tools/LICENSE.txt for details.
L*/

PROMPT Checking for Property "Symbol"
select long_name, preferred_name, preferred_definition, definition_source, version, asl_name
from Properties_ext
where long_name = 'Symbol';

PROMPT Checking for Property "Preferred Name"
select long_name, preferred_name, preferred_definition, definition_source, version, asl_name
from Properties_ext
where long_name = 'Preferred Name';


