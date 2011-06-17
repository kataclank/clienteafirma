/*
 * Este fichero forma parte del Cliente @firma. 
 * El Cliente @firma es un aplicativo de libre distribucion cuyo codigo fuente puede ser consultado
 * y descargado desde www.ctt.map.es.
 * Copyright 2009,2010,2011 Gobierno de Espana
 * Este fichero se distribuye bajo licencia GPL version 3 segun las
 * condiciones que figuran en el fichero 'licence' que se acompana. Si se distribuyera este 
 * fichero individualmente, deben incluirse aqui las condiciones expresadas alli.
 */

package es.gob.afirma.standalone.crypto;

import java.security.cert.X509Certificate;

import javax.swing.Icon;
import javax.swing.ImageIcon;

import es.atosorigin.AOCertVerifier;
import es.gob.afirma.misc.AOUtil;
import es.gob.afirma.misc.Platform;

/** Informaci&oacute;n para la visualizaci&oacute;n y validaci&oacute;n del certificado.
 * @author Carlos gamuci Mill&aacute;n */
public final class CertificateInfo {

    /** Configuraci&oacute;n del OCSP para la validaci&oacute;n del certificado. */
    private final AOCertVerifier ocspConfig;

    /** Icono ilustrativo del Certificado. */
    private final Icon icon;
    
    private final String iconTooltip;

    /** Texto descriptivo del certificado. */
    private String descriptionText;

    /** Construye el objeto con la informaci&oacute;n del certificado.
     * @param cert Certificado al cual se refierre la informaci&oacute;n
     * @param description Texto descriptivo del certificado. 
     * @param ocsp Configuraci&oacute;n de OCSP para la validaci&oacute;n del certificado
     * @param i Icono para el certificado
     * @param iTooltip <i>Tooltip</i> para el icono del certificado */
    public CertificateInfo(final X509Certificate cert, final String description, final AOCertVerifier ocsp, final Icon i, final String iTooltip) {
        
    	if (description == null || "".equals(description)) {
        	if (cert == null) {
        		this.descriptionText = "Certificado generico X.509v3";
        	}
        	else {
        		this.descriptionText = "<html>" + ((Platform.OS.MACOSX.equals(Platform.getOS())) ? "<br>" : "") + "Titular del certificado: <a href=\"http://certinfo\">" + AOUtil.getCN(cert) + "</a>. Emisor del certificado: <a href=\"http://certinfo\">" + AOUtil.getCN(cert.getIssuerX500Principal().toString()) + "</a>" + "</html>";
        	}
        }
        else {
            this.descriptionText = description;
        }
        
    	this.ocspConfig = ocsp;
        
        if (i == null) {
        	this.icon = new ImageIcon(this.getClass().getResource("/resources/default_cert_ico.png"));
        }
        else {
        	this.icon = i;
        }
        
        if (iTooltip == null) {
        	this.iconTooltip = "Certificado X.509v3 generico";
        }
        else {
        	this.iconTooltip = iTooltip;
        }
        
    }

    /**
     * Obtiene la configuraci&oacute;n OCSP para validar el certificado.
     * @return Configuraci&oacute;n OCSP para validar el certificado
     */
    public AOCertVerifier getCertVerifier() {
        return this.ocspConfig;
    }

    
    /** Obtiene el icono del certificado.
     * @return Icono del certificado
     */
    public Icon getIcon() {
        return this.icon;
    }
    
    /** Obtiene el texto del <i>tooltip</i> para el icono del certificado.
     * @return Texto del <i>tooltip</i> para el icono del certificado.
     */
    public String getIconTooltip() {
    	return this.iconTooltip;
    }

    /** Obtiene un texto descriptivo del certificado.
     * @return Descripci&oacute;n del certificado
     */
    public String getDescriptionText() {
        return this.descriptionText;
    }
}
