package com.jcrew.helper.outlook.msgparser.attachment;

import com.jcrew.helper.outlook.msgparser.MessageProperty;

/**
 * Implementation of the {@link Attachment}
 * interface that represents a file attachment.
 * It contains some useful information (as long
 * as it is available in the .msg file) like
 * the attachment name, its size, etc.
 * 
 * @author Venkat P
 */
public class FileAttachment implements Attachment {

	/**
	 * The (by Outlook) shortened filename of
	 * the attachment.
	 */
	protected String filename = null;
	/**
	 * The full filename of the attachment.
	 */
	protected String longFilename = null;
	/**
	 * Mime type of the attachment
	 */
	protected String mimeTag = null;
	/**
	 * The extension of the attachment (may not
	 * be set).
	 */
	protected String extension = null;
	/**
	 * The attachment itself as a byte array.
	 */
	protected byte[] data = null;
	/**
	 * The size of the attachment.
	 */
	protected long size = -1;
		

	/**
	 * @return the extension
	 */
	public String getExtension() {
		return extension;
	}

	/**
	 * @param extension the extension to set
	 */
	public void setExtension(String extension) {
		this.extension = extension;
	}

	/**
	 * @return the filename
	 */
	public String getFilename() {
		return filename;
	}

	/**
	 * @param filename the filename to set
	 */
	public void setFilename(String filename) {
		this.filename = filename;
	}

	/**
	 * @return the longFilename
	 */
	public String getLongFilename() {
		return longFilename;
	}

	/**
	 * @param longFilename the longFilename to set
	 */
	public void setLongFilename(String longFilename) {
		this.longFilename = longFilename;
	}

	/**
	 * @return the mimeTag
	 */
	public String getMimeTag() {
		return mimeTag;
	}

	/**
	 * @param mimeTag the mimeTag to set
	 */
	public void setMimeTag(String mimeTag) {
		this.mimeTag = mimeTag;
	}

	/**
	 * @return the data
	 */
	public byte[] getData() {
		return data;
	}

	/**
	 * @param data the data to set
	 */
	public void setData(byte[] data) {
		this.data = data;
	}

	/**
	 * @return the size
	 */
	public long getSize() {
		return size;
	}

	/**
	 * @param size the size to set
	 */
	public void setSize(long size) {
		this.size = size;
	}
	
	/**
	 * Sets the property specified by the name
	 * parameter. Unknown names are ignored.
	 * 
	 * @param msgProp The property to be set.
	 */
	public void setProperty(MessageProperty msgProp) throws ClassCastException {
		String name = msgProp.getClazz();
		Object value = msgProp.getData();
		int size = msgProp.getSize();
		
		if ((name == null) || (value == null)) {
			return;
		}
		name = name.intern();
		
    	if (name == "3701") {
    		this.setSize(size);
    		this.setData((byte[]) value); 
    	} else if (name == "3704") {
    		this.setFilename((String) value);
    	} else if (name == "3707") {
    		this.setLongFilename((String) value);
    	} else if (name == "370e") {
    		this.setMimeTag((String) value);
    	} else if (name == "3703") {
    		this.setExtension((String) value);
    	}
	}
	
	/**
	 * Returns either the long filename or
	 * the short filename, depending on which is available.
	 * 
	 * @see Object#toString()
	 */
	@Override
	public String toString() {
		if (this.longFilename != null) {
			return this.longFilename;
		}
		return this.filename;
	}
}
