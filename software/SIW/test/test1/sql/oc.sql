PROMPT Checking for "SEQUENCE"
select preferred_name, long_name, preferred_definition, definition_source, version, asl_name
from object_classes_ext
where long_name = 'Sequence';

PROMPT Checking for "Clinical Trial Protocol"
select preferred_name, long_name, preferred_definition, definition_source, version, asl_name
from Object_classes_ext
where long_name = 'Clinical Trial Protocol';
