package cn.kgc.tangcco.smbms.pojo;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class Bill {
    private Integer id;
    private String billCode;//账单编码
    private String productName;//商品名称
    private String productDesc;//商品描述
    private String productUnit;//商品单位
    private double productCount;//商品数量
    private double totalPrice;//商品总额
    private Integer isPayment;//是否支付（1：未支付 2：已支付）
    private Integer createdBy;//创建者(userId)
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date creationDate;//创建时间
    private Integer modifyBy;//更新者(userId)
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date modifyDate;//更新时间
    private Integer providerId;//供应商ID
    //为了显示供应商名称添加的属性
    private Provider providerName;
    private String _providerName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBillCode() {
        return billCode;
    }

    public void setBillCode(String billCode) {
        this.billCode = billCode;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductDesc() {
        return productDesc;
    }

    public void setProductDesc(String productDesc) {
        this.productDesc = productDesc;
    }

    public String getProductUnit() {
        return productUnit;
    }

    public void setProductUnit(String productUnit) {
        this.productUnit = productUnit;
    }

    public double getProductCount() {
        return productCount;
    }

    public void setProductCount(double productCount) {
        this.productCount = productCount;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Integer getIsPayment() {
        return isPayment;
    }

    public void setIsPayment(Integer isPayment) {
        this.isPayment = isPayment;
    }

    public Integer getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Integer getModifyBy() {
        return modifyBy;
    }

    public void setModifyBy(Integer modifyBy) {
        this.modifyBy = modifyBy;
    }

    public Date getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
    }

    public Integer getProviderId() {
        return providerId;
    }

    public void setProviderId(Integer providerId) {
        this.providerId = providerId;
    }

    public Provider getProviderName() {
        return providerName;
    }

    public void setProviderName(Provider providerName) {
        this.providerName = providerName;
    }

    public String get_providerName() {
        return _providerName;
    }

    public void set_providerName(String _providerName) {
        this._providerName = _providerName;
    }
}

