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

/** Analizador gen&eacute;rico de certificados.
 * @author Carlos Gamuci Mill&aacute;n */
public final class GenericCertAnalizer extends CertAnalyzer {

    @Override
    public boolean isValidCert(final X509Certificate cert) {
        return true;
    }

    @Override
    public CertificateInfo analizeCert(final X509Certificate cert) {
    	return new CertificateInfo(
    		cert,
			null, 
			null, 
			null, 
			null
		);
    }
}
