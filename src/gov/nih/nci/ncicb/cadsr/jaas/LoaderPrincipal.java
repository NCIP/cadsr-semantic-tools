package gov.nih.nci.ncicb.cadsr.jaas;


import java.security.*;
import java.io.*;

public class LoaderPrincipal implements Principal, java.io.Serializable {

    private String name;

    public LoaderPrincipal() {
        name = "";
    }

    /**
     * Create a <code>RdbmsPrincipal</code> using a
     * <code>String</code> representation of the
     * user name.
     *
     * <p>
     *
     * @param name the user identification number (UID) for this user.
     *
     */
    public LoaderPrincipal(String newName) {
        name = newName;
    }

    /**
     * Compares the specified Object with this
     * <code>RdbmsPrincipal</code>
     * for equality.  Returns true if the given object is also a
     * <code>RdbmsPrincipal</code> and the two
     * RdbmsPrincipals have the same name.
     *
     * <p>
     *
     * @param o Object to be compared for equality with this
     *		<code>RdbmsPrincipal</code>.
     *
     * @return true if the specified Object is equal equal to this
     *		<code>RdbmsPrincipal</code>.
     */
    public boolean equals(Object o) {

        if (o == null)
            return false;

        if (this == o)
            return true;
 
        if (o instanceof LoaderPrincipal) {
            if (((LoaderPrincipal) o).getName().equals(name))
                return true;
            else
                return false;
        }
        else 
            return false;
    }

    /**
     * Return a hash code for this <code>RdbmsPrincipal</code>.
     *
     * <p>
     *
     * @return a hash code for this <code>RdbmsPrincipal</code>.
     */
    public int hashCode() {
        return name.hashCode();
    }

    /**
     * Return a string representation of this
     * <code>RdbmsPrincipal</code>.
     *
     * <p>
     *
     * @return a string representation of this
     *		<code>RdbmsPrincipal</code>.
     */
    public String toString() {
        return name;
    }

    /**
     * Return the user name for this
     * <code>RdbmsPrincipal</code>.
     *
     * <p>
     *
     * @return the user name for this
     *		<code>RdbmsPrincipal</code>
     */
    public String getName() {
        return name;
    }
}

