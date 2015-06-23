<?xml version="1.0"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
version="1.0">

<xsl:template match="/">
  <html>
  <body bgcolor="#C1E1A6"> 
  <table border="0" Align ="center" width="75%">

   <tr>
   <td width="10%" bgcolor="#91B5FF" > Execution Tool </td> 
   <td width="15%" bgcolor="#E0E6F8" >
   <xsl:value-of select="HOWTOS/TOPIC/ExecutionTool"/> </td>
   <td width="10%" bgcolor="#91B5FF" > Project Name </td> 
   <td width="15%" bgcolor="#E0E6F8" >
   <xsl:value-of select="HOWTOS/TOPIC/ProjectName"/> </td>
   </tr>
   
   <tr>
   <td width="10%" bgcolor="#91B5FF" > Test Case Name </td> 
   <td width="15%" bgcolor="#E0E6F8" >
   <xsl:value-of select="HOWTOS/TOPIC/TestCaseName"/> </td>
   <td width="10%" bgcolor="#91B5FF" > Mobile OS</td> 
   <td width="15%" bgcolor="#E0E6F8" >
   <xsl:value-of select="HOWTOS/TOPIC/MobileOS"/> </td>
   </tr>
   
   <tr>
   <td width="10%" bgcolor="#91B5FF" > Platform </td> 
   <td width="15%" bgcolor="#E0E6F8" >
   <xsl:value-of select="HOWTOS/TOPIC/Platform"/> </td>
    <td width="10%" bgcolor="#91B5FF" > Iteration Mode </td> 
   <td width="15%" bgcolor="#E0E6F8" >
   <xsl:value-of select="HOWTOS/TOPIC/IterationMode"/> </td>
   </tr>
   
   <tr>
   <td width="10%" bgcolor="#91B5FF" > Start Iteration </td> 
   <td width="15%" bgcolor="#E0E6F8" >
   <xsl:value-of select="HOWTOS/TOPIC/StartIteration"/> </td>
    <td width="10%" bgcolor="#91B5FF" > End Iteration </td> 
   <td width="15%" bgcolor="#E0E6F8" >
   <xsl:value-of select="HOWTOS/TOPIC/EndIteration"/> </td>
   </tr>
   
   <tr>
   <td width="10%" bgcolor="#91B5FF" > Start Time </td> 
   <td width="15%" bgcolor="#E0E6F8"  >
   <xsl:value-of select="HOWTOS/TOPIC/Starttime"/> </td>
    <td width="10%" bgcolor="#91B5FF" > End Time </td> 
   <td width="15%" bgcolor="#E0E6F8" >
   <xsl:value-of select="HOWTOS/TOPIC/Endtime"/> </td>
   </tr>
   
   <tr>
   <td width="10%" bgcolor="#91B5FF" > Test Case Status </td> 
   <td width="15%" bgcolor="#E0E6F8"  >
    <xsl:value-of select="HOWTOS/TOPIC/TestCaseStatus"/> </td> 
   <td width="10%" bgcolor="#91B5FF" >Total No Of Iterations </td> 
   <td width="15%" bgcolor="#E0E6F8" >
   <xsl:value-of select="HOWTOS/TOPIC/TotalIterations"/> </td> 
   </tr>
    <tr>
   <td width="10%" bgcolor="#91B5FF"  >No Of Iteration Passed  </td> 
   <td width="15%" bgcolor="#E0E6F8"  ><a href=""></a>
    <xsl:value-of select="HOWTOS/TOPIC/IterationPassed"/> </td> 
   
   <td width="10%" bgcolor="#91B5FF" > No Of Iteration Failed  </td> 
   <td width="15%" bgcolor="#E0E6F8"  >
    <xsl:value-of select="HOWTOS/TOPIC/IterationFailed"/> </td> 
   </tr>
  </table>
   <br/>
  

 <table border="0" Align ="center" width ="75%" >
 <tr bgcolor="#91B5FF">
 
 <th width="10%" >S.No</th>
 <th width="10%" >ITERATION.NO</th>
 <th width="10%" >TIMESTAMP</th>
 <th width="20%"> ACTION </th>
 <th width="10%"> STATUS </th>
 <th width="30%"> SCREENSHOT </th>
 </tr>
 <xsl:apply-templates/>  
  </table>
  </body>
  </html>
  
</xsl:template>

<xsl:template match="HOWTOS/TOPIC">
    <tr bgcolor="#F2F9FF">
      <xsl:apply-templates select="SNO"/>
      <xsl:apply-templates select="IterationNO"/>  
      <xsl:apply-templates select="TimeStamp"/>  
      <xsl:apply-templates select="Message"/>
   <xsl:choose>
      <xsl:when test="LogType='PASS'">
         <td Align ="center" > 
           <span style="color:#074707"> PASS </span>
          </td>
      </xsl:when>
      

      
      <xsl:when test="LogType='FAIL'" >
           <td Align ="center" > 
           <span style="color:#ff0000"> FAIL </span>
          </td>
      </xsl:when>
      
  
      
      <xsl:when test="LogType='DONE'" >
           <td Align ="center" > 
           <span style="color:#070719"> DONE</span>
          </td>

      </xsl:when>

      <xsl:otherwise>
      </xsl:otherwise>
      </xsl:choose>
     
      <xsl:apply-templates select="Screenshot"/>
      <xsl:apply-templates select="NoScreenshot"/>
    </tr>    
</xsl:template>

<xsl:template match="SNO">
  <td Align ="center" ><xsl:value-of select="."/> </td>
</xsl:template>
<xsl:template match="IterationNO">
  <td Align ="center" ><xsl:value-of select="."/> </td>
</xsl:template>
<xsl:template match="TimeStamp">
  <td Align ="center" ><xsl:value-of select="."/> </td>
</xsl:template>

<xsl:template match="Message">
<td Align ="center" > <xsl:value-of select="."/> </td>
</xsl:template>

<xsl:template match="Screenshot">
<td >
<img width="100%" >
<xsl:attribute name="src"><xsl:value-of select="."/>  </xsl:attribute> 
</img>
</td>
</xsl:template>

<xsl:template match="NoScreenshot">
<td Align ="center" > <span style="color:#F2F9FF"> </span> </td>
</xsl:template>

</xsl:stylesheet>