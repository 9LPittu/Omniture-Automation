package com.jcrew.helper.outlook.msgparser.rtf;

import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;

import javax.swing.JEditorPane;
import javax.swing.text.Document;
import javax.swing.text.EditorKit;

public class JEditorPaneRTF2HTMLConverter implements RTF2HTMLConverter {

	public String rtf2html(String rtf) throws Exception {
		JEditorPane p = new JEditorPane();
		p.setContentType("text/rtf");
		EditorKit kitRtf = p.getEditorKitForContentType("text/rtf");
		try {
			StringReader rtfReader = new StringReader(rtf);
			kitRtf.read(rtfReader, p.getDocument(), 0);
			kitRtf = null;
			Document t = p.getDocument();
			EditorKit kitHtml = p.getEditorKitForContentType("text/html");
			Writer writer = new StringWriter();
			kitHtml.write(writer, p.getDocument(), 0, p.getDocument().getLength());
			return writer.toString();
		} catch (Exception e) {
			throw new Exception("Could not convert RTF to HTML.", e);
		}
	}

}
