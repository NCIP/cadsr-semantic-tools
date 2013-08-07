/*L
  Copyright Oracle Inc, SAIC, SAIC-F

  Distributed under the OSI-approved BSD 3-Clause License.
  See http://ncip.github.com/cadsr-semantic-tools/LICENSE.txt for details.
L*/

DECLARE 
  projectCS_name VARCHAR(255) := 'Essai-UML-Project2';
  projectCS_id char(36);

  projectCS_CSI_id char(36);  

BEGIN
  select cs_idseq into projectCS_id from classification_schemes where long_name = projectCS_name;

 select cs_csi_idseq into projectCS_CSI_IDSEQ from cs_csi where cs_idseq = 

END;
/
