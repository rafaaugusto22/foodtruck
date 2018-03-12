#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
/**
 * 
 */
/**
 * @author c108854
 *
 */

@javax.xml.bind.annotation.XmlSchema(
    namespace = "${groupId}.sisgd.mensagens",
    elementFormDefault = javax.xml.bind.annotation.XmlNsForm.QUALIFIED,
  	xmlns = {@XmlNs(prefix="GED", namespaceURI="${groupId}.sisgd.mensagens")}
)

package ${package}.model.trasient.ged.mensagens;
import javax.xml.bind.annotation.XmlNs;
