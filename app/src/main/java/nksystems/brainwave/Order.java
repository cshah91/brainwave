package nksystems.brainwave;

/**
 * @author Charmy Shah
 * @version 1.0
 * @date 02-07-2017
 */
public class Order {

    String customeraddress, customercity, customerstate, customerpincode, customeremail, customername, orderdeliverycharge,
            ordergstamount, ordermedicationamount, ordername, orderoriginalamount, orderstatus, ordersubmissiondate,
            ordertotalamount, ordertype, taxpercent, servicetype, serviceproblem;

    /**
     * @param customeraddress
     * @param customercity
     * @param customerstate
     * @param customerpincode
     * @param customeremail
     * @param customername
     * @param orderdeliverycharge
     * @param ordergstamount
     * @param ordermedicationamount
     * @param ordername
     * @param orderoriginalamount
     * @param orderstatus
     * @param ordersubmissiondate
     * @param ordertotalamount
     * @param ordertype
     * @param taxpercent
     * @param servicetype
     * @param serviceproblem
     */
    public Order(String customeraddress, String customercity, String customerstate, String customerpincode,
                 String customeremail, String customername, String orderdeliverycharge,
                 String ordergstamount, String ordermedicationamount, String ordername, String orderoriginalamount,
                 String orderstatus, String ordersubmissiondate,
                 String ordertotalamount, String ordertype, String taxpercent, String servicetype, String serviceproblem) {

        this.customeraddress = customeraddress;
        this.customercity = customercity;
        this.customerstate = customerstate;
        this.customerpincode = customerpincode;
        this.customeremail = customeremail;
        this.customername = customername;
        this.orderdeliverycharge = orderdeliverycharge;
        this.ordergstamount = ordergstamount;
        this.ordermedicationamount = ordermedicationamount;
        this.ordername = ordername;
        this.orderoriginalamount = orderoriginalamount;
        this.orderstatus = orderstatus;
        this.ordersubmissiondate = ordersubmissiondate;
        this.ordertotalamount = ordertotalamount;
        this.ordertype = ordertype;
        this.taxpercent = taxpercent;
        this.servicetype = servicetype;
        this.serviceproblem = serviceproblem;
    }
}
