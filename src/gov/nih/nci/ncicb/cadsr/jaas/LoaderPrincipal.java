package gov.nih.nci.ncicb.cadsr.jaas;


import java.security.*;
import java.io.*;

/**
 * Implementation of Principal
 *
 * @author <a href="mailto:ludetc@mail.nih.gov">Christophe Ludet</a>
 */
public class LoaderPrincipal implements Principal, java.io.Serializable {

    private String name;

    public LoaderPrincipal() {
        name = "";
    }

    /**
     * Create a <code>LoaderPrincipal</code> using a
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
     * <code>Principal</code>
     * for equality.  Returns true if the given object is also a
     * <code>Principal</code> and the two
     * Principals have the same name.
     *
     * <p>
     *
     * @param o Object to be compared for equality with this
     *		<code>Principal</code>.
     *
     * @return true if the specified Object is equal equal to this
     *		<code>Principal</code>.
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
     * Return a hash code for this <code>Principal</code>.
     *
     * <p>
     *
     * @return a hash code for this <code>Principal</code>.
     */
    public int hashCode() {
        return name.hashCode();
    }

    /**
     * Return a string representation of this
     * <code>Principal</code>.
     *
     * <p>
     *
     * @return a string representation of this
     *		<code>Principal</code>.
     */
    public String toString() {
        return name;
    }

    /**
     * Return the user name for this
     * <code>Principal</code>.
     *
     * <p>
     *
     * @return the user name for this
     *		<code>Principal</code>
     */
    public String getName() {
        return name;
    }
}

