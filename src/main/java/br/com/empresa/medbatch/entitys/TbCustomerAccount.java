package br.com.empresa.medbatch.entitys;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Julio Cesar
 */
@Entity
@Table(name = "tb_customer_account", catalog = "back", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TbCustomerAccount.findAll", query = "SELECT t FROM TbCustomerAccount t")
    , @NamedQuery(name = "TbCustomerAccount.findByRangeIdAndVl",
            query = "SELECT t FROM TbCustomerAccount t WHERE t.vlTotal > :vl AND t.idCustomer BETWEEN :ini AND :fim ORDER BY t.vlTotal DESC")
    , @NamedQuery(name = "TbCustomerAccount.findByIdCustomer", query = "SELECT t FROM TbCustomerAccount t WHERE t.idCustomer = :idCustomer")
    , @NamedQuery(name = "TbCustomerAccount.findByCpfCnpj", query = "SELECT t FROM TbCustomerAccount t WHERE t.cpfCnpj = :cpfCnpj")
    , @NamedQuery(name = "TbCustomerAccount.findByNmCustomer", query = "SELECT t FROM TbCustomerAccount t WHERE t.nmCustomer = :nmCustomer")
    , @NamedQuery(name = "TbCustomerAccount.findByIsActive", query = "SELECT t FROM TbCustomerAccount t WHERE t.isActive = :isActive")
    , @NamedQuery(name = "TbCustomerAccount.findByVlTotal", query = "SELECT t FROM TbCustomerAccount t WHERE t.vlTotal = :vlTotal")})
public class TbCustomerAccount implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_customer")
    private Integer idCustomer;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "cpf_cnpj")
    private String cpfCnpj;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "nm_customer")
    private String nmCustomer;
    @Basic(optional = false)
    @NotNull
    @Column(name = "is_active")
    private boolean isActive;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Column(name = "vl_total")
    private BigDecimal vlTotal;

    public TbCustomerAccount() {
    }

    public TbCustomerAccount(Integer idCustomer) {
        this.idCustomer = idCustomer;
    }

    public TbCustomerAccount(Integer idCustomer, String cpfCnpj, String nmCustomer, boolean isActive, BigDecimal vlTotal) {
        this.idCustomer = idCustomer;
        this.cpfCnpj = cpfCnpj;
        this.nmCustomer = nmCustomer;
        this.isActive = isActive;
        this.vlTotal = vlTotal;
    }

    public Integer getIdCustomer() {
        return idCustomer;
    }

    public void setIdCustomer(Integer idCustomer) {
        this.idCustomer = idCustomer;
    }

    public String getCpfCnpj() {
        return cpfCnpj;
    }

    public void setCpfCnpj(String cpfCnpj) {
        this.cpfCnpj = cpfCnpj;
    }

    public String getNmCustomer() {
        return nmCustomer;
    }

    public void setNmCustomer(String nmCustomer) {
        this.nmCustomer = nmCustomer;
    }

    public boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    public BigDecimal getVlTotal() {
        return vlTotal;
    }

    public void setVlTotal(BigDecimal vlTotal) {
        this.vlTotal = vlTotal;
    }

    @Override
    public String toString() {
        return "Cliente{" + "id: " + idCustomer + ", cpf ou Cnpj: " + cpfCnpj + ", nome: " + nmCustomer + ", status: " + isActive + ", saldo: " + vlTotal + "}";
    }
}
