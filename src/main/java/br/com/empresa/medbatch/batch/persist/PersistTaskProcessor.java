package br.com.empresa.medbatch.batch.persist;

import br.com.empresa.medbatch.entitys.TbCustomerAccount;
import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import org.easybatch.core.processor.RecordProcessor;
import org.easybatch.core.record.GenericRecord;

/**
 *
 * @author Julio Cesar
 */
public class PersistTaskProcessor implements RecordProcessor<GenericRecord<File>, GenericRecord<TbCustomerAccount>> {

    private final Gson gson;

    public PersistTaskProcessor() {
        this.gson = new Gson();
    }

    @Override
    public GenericRecord<TbCustomerAccount> processRecord(GenericRecord<File> record) throws Exception {
        try (BufferedReader br = new BufferedReader(new FileReader(record.getPayload()))) {
            TbCustomerAccount customer = gson.fromJson(br.readLine(), TbCustomerAccount.class);
            return new GenericRecord(record.getHeader(), customer);
        }
    }
}
