package br.com.empresa.medbatch.batch.show;

import br.com.empresa.medbatch.entitys.TbCustomerAccount;
import org.easybatch.core.processor.RecordProcessor;
import org.easybatch.core.record.GenericRecord;
import org.easybatch.core.record.StringRecord;

/**
 *
 * @author Julio Cesar
 */
public class ShowTaskProcessor implements RecordProcessor<GenericRecord<TbCustomerAccount>, StringRecord> {

    private Double amount = 0.0;
    private Double average;
    private String response = "";
    private String frase = "";
    private TbCustomerAccount customer;
    int cont = 0;

    @Override
    public StringRecord processRecord(GenericRecord<TbCustomerAccount> record) throws Exception {
        this.customer = record.getPayload();
        this.amount += this.customer.getVlTotal().doubleValue();
        this.response += this.customer.toString() + "*div";
        this.average = this.amount / record.getHeader().getNumber();

        this.frase = "*divA média dos saldos é: " + this.average + "*divAbaixo lista de clientes: *div"
                + "usados para calculo:*div";
        
        //new File("response.txt").delete();
        return new StringRecord(record.getHeader(), this.frase + this.response);
    }
}
