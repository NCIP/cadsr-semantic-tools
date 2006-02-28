<?xml version="1.0" encoding="utf-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                version="1.0">
  <xsl:template match="node()|@*">
    <xsl:copy>
      <xsl:apply-templates select="node()|@*"/>
    </xsl:copy>
  </xsl:template>
  
  <xsl:template match="bean[@name='publicCadsrModule']/property/value">
    <value>http://cabio-dev.nci.nih.gov/cacore31/http/remoteService</value>
  </xsl:template>

</xsl:stylesheet>
