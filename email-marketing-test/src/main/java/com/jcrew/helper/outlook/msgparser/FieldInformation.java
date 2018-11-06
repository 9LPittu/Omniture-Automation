package com.jcrew.helper.outlook.msgparser;

import org.apache.poi.poifs.filesystem.DocumentEntry;

/**
 * Convenience class for storing type information
 * about a {@link DocumentEntry}.
 *
 * @author Venkat P
 */
public class FieldInformation {

    /**
     * The default value for both the {@link #clazz} and
     * the {@link #type} properties.
     */
    public static final String UNKNOWN = "unknown";

    /**
     * The default value for the {@link #mapiType}
     */
    public static final int UNKNOWN_MAPITYPE = -1;

    /**
     * The class of the {@link DocumentEntry}.
     */
    protected String clazz = UNKNOWN;
    /**
     * The type of the {@link DocumentEntry}.
     */
    protected String type = UNKNOWN;

    /**
     * The mapi type of the {@link DocumentEntry}.
     */
    protected int mapiType = UNKNOWN_MAPITYPE;

    /**
     * Empty constructor that uses the default
     * values.
     */
    public FieldInformation() {
    }

    /**
     * Constructor that allows to set the class
     * and type properties.
     *
     * @param clazz The class of the {@link DocumentEntry}.
     * @param type  The type of the {@link DocumentEntry}.
     */
    @Deprecated
    public FieldInformation(String clazz, String type) {
        this.setClazz(clazz);
        this.setType(type);
    }

    /**
     * Constructor that allows to set the class
     * and type properties.
     *
     * @param clazz    The class of the {@link DocumentEntry}.
     * @param mapiType The mapiType of the {@link DocumentEntry} (see {@link MAPIProp}).
     */
    public FieldInformation(String clazz, int mapiType) {
        this.setClazz(clazz);
        this.setMapiType(mapiType);
    }

    /**
     * @return the clazz
     */
    public String getClazz() {
        return clazz;
    }

    /**
     * @param clazz the clazz to set
     */
    public void setClazz(String clazz) {
        this.clazz = clazz;
    }

    /**
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @return the mapiType
     */
    public int getMapiType() {
        return mapiType;
    }

    /**
     * @param mapiType the mapiType to set
     */
    public void setMapiType(int mapiType) {
        this.mapiType = mapiType;
    }


}
