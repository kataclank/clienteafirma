/*
 * Controlador Java de la Secretaría de Estado de Administraciones Públicas
 * para el DNI electrónico.
 *
 * El Controlador Java para el DNI electrónico es un proveedor de seguridad de JCA/JCE 
 * que permite el acceso y uso del DNI electrónico en aplicaciones Java de terceros 
 * para la realización de procesos de autenticación, firma electrónica y validación 
 * de firma. Para ello, se implementan las funcionalidades KeyStore y Signature para 
 * el acceso a los certificados y claves del DNI electrónico, así como la realización 
 * de operaciones criptográficas de firma con el DNI electrónico. El Controlador ha 
 * sido diseñado para su funcionamiento independiente del sistema operativo final.
 * 
 * Copyright (C) 2012 Dirección General de Modernización Administrativa, Procedimientos 
 * e Impulso de la Administración Electrónica
 * 
 * Este programa es software libre y utiliza un licenciamiento dual (LGPL 2.1+
 * o EUPL 1.1+), lo cual significa que los usuarios podrán elegir bajo cual de las
 * licencias desean utilizar el código fuente. Su elección deberá reflejarse 
 * en las aplicaciones que integren o distribuyan el Controlador, ya que determinará
 * su compatibilidad con otros componentes.
 *
 * El Controlador puede ser redistribuido y/o modificado bajo los términos de la 
 * Lesser GNU General Public License publicada por la Free Software Foundation, 
 * tanto en la versión 2.1 de la Licencia, o en una versión posterior.
 * 
 * El Controlador puede ser redistribuido y/o modificado bajo los términos de la 
 * European Union Public License publicada por la Comisión Europea, 
 * tanto en la versión 1.1 de la Licencia, o en una versión posterior.
 * 
 * Debería recibir una copia de la GNU Lesser General Public License, si aplica, junto
 * con este programa. Si no, consúltelo en <http://www.gnu.org/licenses/>.
 * 
 * Debería recibir una copia de la European Union Public License, si aplica, junto
 * con este programa. Si no, consúltelo en <http://joinup.ec.europa.eu/software/page/eupl>.
 *
 * Este programa es distribuido con la esperanza de que sea útil, pero
 * SIN NINGUNA GARANTÍA; incluso sin la garantía implícita de comercialización
 * o idoneidad para un propósito particular.
 */
package es.gob.jmulticard.apdu.iso7816four;

import es.gob.jmulticard.apdu.Apdu;
import es.gob.jmulticard.apdu.ResponseApdu;

/** APDU respuesta al comando APDU ISO 7816-4 de selecci&oacute;n de fichero.
 * @author Tom&aacute;s Garc&iacute;a-Mer&aacute;s */
public final class SelectFileApduResponse extends ResponseApdu {

    private byte[] dfName = null;
    private byte[] fileId = null;
    private byte[] fileLength = null;

    /** Construye una APDU respuesta al comando APDU ISO 7816-4 de selecci&oacute;n de fichero.
     * @param apduResponse APDU devuelta por la operaci&oacute;n de selecci&oacute;n de fichero. */
    public SelectFileApduResponse(final Apdu apduResponse) {
        super(apduResponse.getBytes());
        this.decode();
    }

    private void decode() {
        if (this.isOk()) {
            // Longitud del troncho.
            final int length = this.getData()[1];

            // El primer byte es 0x6F el segundo es la long. y los 2 ultimos son el sw. por eso length - 2.
            if (this.getData().length - 2 == length) {
                int propInformationIndex = 2;
                if (this.getData()[propInformationIndex] == (byte) 0x84) {
                    final int nameLength = this.getData()[++propInformationIndex];
                    this.dfName = this.getBytesFromData(++propInformationIndex, nameLength);
                    propInformationIndex += nameLength;
                }
                if (this.getData()[propInformationIndex] == (byte) 0x85 && this.getData()[propInformationIndex + 1] == 10) {
                    this.fileId = this.getBytesFromData(propInformationIndex + 3, 2);
                    this.fileLength = this.getBytesFromData(propInformationIndex + 5, 2);
                }
            }
        }
    }

    private byte[] getBytesFromData(final int offset, final int length) {
        final byte[] result = new byte[length];
        System.arraycopy(this.getData(), offset, result, 0, length);
        return result;
    }

    /** Obtiene el nombre del DF.
     * @return Nombre del DF */
    byte[] getDfName() {
        final byte[] out = new byte[this.dfName.length];
        System.arraycopy(this.dfName, 0, out, 0, this.dfName.length);
        return out;
    }

    /** Devuelve el identificador del fichero seleccionado.
     * @return Identificador del fichero */
    byte[] getFileId() {
        final byte[] out = new byte[this.fileId.length];
        System.arraycopy(this.fileId, 0, out, 0, this.fileId.length);
        return out;
    }

    /** Devuelve la longitud del fichero seleccionado.
     * @return Longitud del fichero */
    public int getFileLength() {
        return ((this.fileLength[0] & 0xFF) << 8) | (this.fileLength[1] & 0xFF);
    }

    /** {@inheritDoc} */
    public boolean isOk() {
        this.getBytesFromData(this.getData().length - 2, 2);
        return super.isOk() && this.getData()[0] == (byte) 0x6F && this.getData().length > 2;
    }

}