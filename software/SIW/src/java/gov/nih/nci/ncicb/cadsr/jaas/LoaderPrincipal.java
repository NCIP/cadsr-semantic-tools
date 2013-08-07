/*L
 * Copyright Oracle Inc, SAIC, SAIC-F
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cadsr-semantic-tools/LICENSE.txt for details.
 */

/*
 * Copyright 2000-2003 Oracle, Inc. This software was developed in conjunction with the National Cancer Institute, and so to the extent government employees are co-authors, any rights in such works shall be subject to Title 17 of the United States Code, section 105.
 *
 Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this list of conditions and the disclaimer of Article 3, below. Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in the documentation and/or other materials provided with the distribution.
 *
 * 2. The end-user documentation included with the redistribution, if any, must include the following acknowledgment:
 *
 * "This product includes software developed by Oracle, Inc. and the National Cancer Institute."
 *
 * If no such end-user documentation is to be included, this acknowledgment shall appear in the software itself, wherever such third-party acknowledgments normally appear.
 *
 * 3. The names "The National Cancer Institute", "NCI" and "Oracle" must not be used to endorse or promote products derived from this software.
 *
 * 4. This license does not authorize the incorporation of this software into any proprietary programs. This license does not authorize the recipient to use any trademarks owned by either NCI or Oracle, Inc.
 *
 * 5. THIS SOFTWARE IS PROVIDED "AS IS," AND ANY EXPRESSED OR IMPLIED WARRANTIES, (INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE) ARE DISCLAIMED. IN NO EVENT SHALL THE NATIONAL CANCER INSTITUTE, ORACLE, OR THEIR AFFILIATES BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE 
 */
package gov.nih.nci.ncicb.cadsr.jaas;


import java.security.*;
import java.io.*;

/**
 * Implementation of Principal
 *
 * @author <a href="mailto:chris.ludet@oracle.com">Christophe Ludet</a>
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

