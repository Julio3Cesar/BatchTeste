package br.com.empresa.medbatch.main;

import br.com.empresa.medbatch.batch.persist.PersistTaskProcessor;
import br.com.empresa.medbatch.batch.show.ShowTaskProcessor;
import br.com.empresa.medbatch.entitys.TbCustomerAccount;
import br.com.empresa.medbatch.factory.EMFactory;
import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.math.BigDecimal;
import javax.persistence.EntityManagerFactory;
import org.easybatch.core.job.Job;
import org.easybatch.core.job.JobBuilder;
import org.easybatch.core.job.JobExecutor;
import org.easybatch.core.job.JobReport;
import org.easybatch.core.reader.FileRecordReader;
import org.easybatch.core.writer.FileRecordWriter;
import org.easybatch.jpa.JpaRecordReader;
import org.easybatch.jpa.JpaRecordWriter;

/**
 *
 * @author Julio Cesar
 */
public class Main {

    private static int i;

    public static void main(String[] args) throws IOException {
        populaFile();

        EntityManagerFactory emf = new EMFactory().getEntityManagerFactory();
        String query = "SELECT t FROM TbCustomerAccount t WHERE t.vlTotal > 560 "
                + "AND t.idCustomer BETWEEN 1500 AND 2700 ORDER BY t.vlTotal DESC";

        Job persistJob = new JobBuilder()
                .named("persistJob")
                .reader(new FileRecordReader(new File("data")))
                .processor(new PersistTaskProcessor())
                .writer(new JpaRecordWriter(emf))
                .build();

        Job showJob = new JobBuilder()
                .named("showJob")
                .reader(new JpaRecordReader<>(emf, query,
                        TbCustomerAccount.class))
                .processor(new ShowTaskProcessor())
                .writer(new FileRecordWriter(new File("response.txt")))
                .build();

        JobExecutor jobExecutor = new JobExecutor();
        JobReport persistJobReport = jobExecutor.execute(persistJob);
        JobReport showJobReport = jobExecutor.execute(showJob);
        jobExecutor.shutdown();
        System.out.println(persistJobReport);
        System.out.println(showJobReport);
        System.out.println(showResult().replace("*div", "\n"));
    }

    public static String showResult() throws IOException {
        InputStream is = new FileInputStream("response.txt");
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr);
        String linha = br.readLine();
        String saida = linha;
        while (saida != null) {
            saida = br.readLine();
            if (saida != null) {
                linha = saida;
            }
        }
        br.close();
        if (linha == null) {
            return "Sem resultados!";
        } else {
            return linha;
        }
    }

    public static void populaFile() throws IOException {
        Gson gson = new Gson();
        TbCustomerAccount customer;
        PrintWriter writer;
        FileWriter arq;

        File file = new File("data");
        file.mkdir();

        for (i = 1; i < 5000; i++) {
            arq = new FileWriter("data\\customers" + i + ".json");
            writer = new PrintWriter(arq);

            customer = new TbCustomerAccount();
            customer.setCpfCnpj("" + i + i + i + i + i + i + i + i + i + i + i);
            customer.setIsActive(true);
            customer.setNmCustomer("NameCustomer" + i);
            customer.setVlTotal(new BigDecimal(i * 2.1));
            writer.printf(gson.toJson(customer));
            arq.close();
        }
    }
}
