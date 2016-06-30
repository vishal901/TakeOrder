package in.vaksys.takeorder.dbPojo;

import io.realm.RealmObject;

/**
 * Created by dell980 on 6/29/2016.
 */
public class AddContact extends RealmObject {

    String contactId;
    private String buyerName;
    private String phone;
    private String spId;
    private String city;

    public String getContactId() {
        return contactId;
    }

    public void setContactId(String contactId) {
        this.contactId = contactId;
    }

    public String getBuyerName() {
        return buyerName;
    }

    public void setBuyerName(String buyerName) {
        this.buyerName = buyerName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
